//package com.onefin.ewallet.common.base.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.*;
//import com.ctc.wstx.util.StringUtil;
//import com.onefin.ewallet.common.base.errorhandler.RuntimeInternalServerException;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public abstract class S3AmazonAdapter {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(S3AmazonAdapter.class);
//
//	private static final String RULE_FILE_EXTENSION = "^.*\\.(jpg|JPG|gif|GIF|doc|DOC|pdf|PDF|png|xlsx)$";
//	private AmazonS3 amazonS3;
//
//	public void setAmazonS3(AmazonS3 amazonS3){
//		this.amazonS3 = amazonS3;
//	}
//
//	public ObjectListing listObject(String bucketName){
//		return this.amazonS3.listObjects(bucketName);
//	}
//
//	public String  updloadFile(MultipartFile file,String fileName,String bucketName){
//		File fileObj = convertMultiPartFileToFile(file);
//		Pattern pattern = Pattern.compile(fileName.substring(fileName.lastIndexOf(".")));
//		Matcher matcher = pattern.matcher(RULE_FILE_EXTENSION);
//		if(matcher.find()) {
//			amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
//		} else {
//			throw new RuntimeInternalServerException("Invalid file: " +
//					(fileName.contains("/") ? fileName.substring(fileName.lastIndexOf("/") + 1) : fileName));
//		}
//		fileObj.delete();
//
//		return bucketName + "/" + fileName;
//	}
//
//	private File convertMultiPartFileToFile(MultipartFile file) {
//		File convertedFile = new File(file.getOriginalFilename());
//		try(FileOutputStream os = new FileOutputStream(convertedFile)){
//			os.write(file.getBytes());
//		} catch (IOException e) {
//			LOGGER.warn("File not Invalid");
//		}
//		return convertedFile;
//	}
//
//	public void deleteFile(String fileName,String bucketName) {
//		amazonS3.deleteObject(bucketName, fileName);
//	}
//
//	public String getResource(String bucketName,String key){
//		return String.valueOf(amazonS3.getUrl(bucketName,key));
//	}
//
//}
