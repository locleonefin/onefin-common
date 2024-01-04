package com.onefin.ewallet.common.utility.string;

import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class StringHelper {

	public static final String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	public static boolean emailValidation(String text) {
		return patternMatches(text, emailRegexPattern);
	}

	public static boolean patternMatches(String text, String regexPattern) {
		return Pattern.compile(regexPattern)
				.matcher(text)
				.matches();
	}

	public boolean checkNullEmptyBlank(String string) {
		if (string == null || string.isEmpty() || string.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean checkNullEmptyBlankStatic(String string) {
		if (string == null || string.isEmpty() || string.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean checkNullEmptyBlank(Object string) {
		if (string == null) {
			return true;
		}
		if (string.toString().isEmpty() || string.toString().trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static String deAccent(String str) {
		String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(nfdNormalizedString).replaceAll("");
	}

	public String fillPathVariable(String path, List<String> pathVariables) {
		if (pathVariables != null) {
			for (String value : pathVariables) {
				path = path.replaceFirst("\\{.*?\\}", value);
			}
		}
		return path;
	}

}