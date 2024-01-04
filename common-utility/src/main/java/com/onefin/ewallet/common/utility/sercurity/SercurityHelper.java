package com.onefin.ewallet.common.utility.sercurity;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.onefin.ewallet.common.utility.json.JSONHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("sercurityHelper")
public class SercurityHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(SercurityHelper.class);

	private static final String _MORE12DIGIT = "[0-9]{12}\\w*";
	private static final Pattern _MORE12DIGIT_PATTERN = Pattern.compile(_MORE12DIGIT);

	private static final String _05TO11DIGIT = "[0-9]{5,11}";
	private static final Pattern _05TO11DIGIT_PATTERN = Pattern.compile(_05TO11DIGIT);

	@Autowired
	private JSONHelper jSonHelper;

	public String MD5Hashing(String input, boolean uppercase) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());
			byte[] digest = md.digest();
			String myHash = uppercase == true ? DatatypeConverter.printHexBinary(digest).toUpperCase() : DatatypeConverter.printHexBinary(digest).toLowerCase();
			return myHash;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public String hashMD5Napas(String src, String secretKey) {
		String strChecksum = MD5Hashing(src, false);
		String str = "";
		secretKey = "5" + secretKey + "5";
		String strKQ = strChecksum;
		if (isNumeric(secretKey)) {
			char[] chars = secretKey.toCharArray();
			for (int i = 0; i < chars.length - 1; i++) {
				str = str + strChecksum.substring(Character.getNumericValue(chars[i]), Character.getNumericValue(chars[i]) + 20 - Character.getNumericValue(chars[i + 1]));
			}
			strKQ = MD5Hashing(str, false);
		}
		return strKQ;
	}

	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public boolean validateChecksum(String data, String checksum) {
		String oneFinCheckSum = DigestUtils.sha256Hex(data);
		if (!oneFinCheckSum.equalsIgnoreCase(checksum)) {
			LOGGER.error("== Checksum fail: ONEFIN Checksum {} - 3Rd Checksum {}", oneFinCheckSum, checksum);
			return false;
		}
		LOGGER.info("== Checksum success");
		return true;
	}

	public Map<String, Object> base64Decode(String data) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
		byte[] decodeData = Base64.getDecoder().decode(data);
		String dataString = new String(decodeData);
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData = (Map<String, Object>) jSonHelper.convertString2Map(dataString, Map.class);
		return mapData;
	}

	public String truncateCard(String input) {
		Matcher matcherMore12 = _MORE12DIGIT_PATTERN.matcher((CharSequence) input);
		Matcher matcher05_11 = _05TO11DIGIT_PATTERN.matcher((CharSequence) input);
		String truncateText = null;
		// Format 970400****1234
		if (matcherMore12.matches()) {
			String truncateTextStart = matcherMore12.group().substring(0, 6);
			String truncateTextEnd = matcherMore12.group().substring(matcherMore12.group().length() - 4, matcherMore12.group().length());
			truncateText = truncateTextStart + StringUtils.repeat("*", matcherMore12.group().length() - 10) + truncateTextEnd;
			return truncateText;

		}
		// Format ******1234
		if (matcher05_11.matches()) {
			String truncateTextEnd = matcher05_11.group().substring(matcher05_11.group().length() - 4, matcher05_11.group().length());
			truncateText = StringUtils.repeat("*", matcher05_11.group().length() - 4) + truncateTextEnd;
			return truncateText;
		}
		return input;
	}

	public String genrateOTP(int lengthOfOTP) {
		StringBuilder generatedOTP = new StringBuilder();
		SecureRandom secureRandom = new SecureRandom();
		try {
			secureRandom = SecureRandom.getInstance(secureRandom.getAlgorithm());
			for (int i = 0; i < lengthOfOTP; i++) {
				generatedOTP.append(secureRandom.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedOTP.toString();
	}

}