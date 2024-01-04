package com.onefin.ewallet.common.base.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.onefin.ewallet.common.base.errorhandler.RuntimeInternalServerException;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.apache.commons.io.IOUtils;

import io.minio.messages.Bucket;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public abstract class BaseMinIOAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMinIOAdapter.class);

	private MinioClient minioClient;

	protected void setBaseMinioClient(MinioClient minioClient) {
		this.minioClient = minioClient;
	}

	public List<Bucket> getAllBuckets() {
		try {
			return minioClient.listBuckets();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public void uploadFile(String bucket, String location, File file, String contentType) {
		try {
			minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucket).object(location)
					.filename(file.toString()).contentType(contentType).build());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public byte[] getFile(String bucket, String path) {
		try {
			InputStream obj = minioClient
					.getObject(GetObjectArgs.builder().bucket(bucket).object(path).build());
			byte[] content = IOUtils.toByteArray(obj);
			obj.close();
			return content;
		} catch (Exception e) {
			LOGGER.error("Fail to get file from minio", e);
		}
		return null;
	}


	public void uploadByte(String bucket,
						   String location,
						   byte[] file,
						   String contentType) {
		try {
			InputStream targetStream = new ByteArrayInputStream(file);
			minioClient.putObject(
					PutObjectArgs.builder()
							.bucket(bucket)
							.object(location)
							.stream(targetStream,-1, 10485760)
							.contentType(contentType).build());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	public Iterable<Result<Item>> getListObject(String bucket) {
		try {
			Iterable<Result<Item>> results = minioClient.listObjects(
					ListObjectsArgs.builder().bucket(bucket).build());
			return results;
		} catch (Exception e) {
			LOGGER.error("Fail to get file from minio", e);
		}
		return null;
	}
	public Iterable<Result<Item>> listObject(String bucket,String prefix){
		return minioClient.listObjects(
				ListObjectsArgs.builder().bucket(bucket).prefix(prefix).build());
	}

	public Boolean checkObjectExist(String bucket, String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

		try {
			StatObjectResponse objectStat =
					minioClient.statObject(
							StatObjectArgs.builder()
									.bucket(bucket)
									.object(objectName)
									.build());
			LOGGER.info(objectStat.toString());
			return true;
		} catch (Exception e) {

			LOGGER.error("Fail to check file in minio", e);
			return false;
		}
	}

	public String handleUploadOption(MultipartFile file, String location, String bucket, String fileExtension,boolean isVersioning) throws IOException {
		String contentType = handleExtendedFile(fileExtension);
		if(isVersioning){
			AtomicInteger versionNumber = new AtomicInteger(1);
			listObject(bucket,
					(String) location.subSequence(0,location.lastIndexOf("/") + 1)).forEach(e -> {
				versionNumber.getAndIncrement();
			});
			location = location.replace(location.substring(location.lastIndexOf(".")),"_v" + versionNumber.get() + location.substring(location.lastIndexOf(".")));
		}
		uploadByte(bucket, location, file.getBytes(), contentType);
		return location;
	}


	public String handleExtendedFile(String fileExtension) {
		switch (fileExtension.toLowerCase()) {
			case ".pdf":
				return MediaType.APPLICATION_PDF_VALUE;
			case ".xlsx":
				return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			case ".xls":
				return "application/vnd.ms-excel";
			case ".csv":
				return "text/csv";
			case ".doc":
				return "application/msword";
			case ".docx":
				return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
			case ".txt":
				return MediaType.TEXT_PLAIN_VALUE;
			case ".gif":
				return MediaType.IMAGE_GIF_VALUE;
			case ".ief":
				return "image/ief";
			case ".png":
				return MediaType.IMAGE_PNG_VALUE;
			case ".jpg":
			case ".jpeg":
				return MediaType.IMAGE_JPEG_VALUE;
			case ".dat":
				return "application/dat";
			case ".rar":
				return "application/vnd.rar";
			case ".zip":
				return "application/zip";
			case ".mp4":
				return "video/mp4";
		}
		throw new RuntimeInternalServerException("File is not valid");

	}

//	public void deleteFolder(String bucketName,String location) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//		minioClient.removeObject(RemoveObjectArgs
//				.builder()
//				.bucket(bucketName)
//				.object(location)
//				.build());
//	}

//	public void moveFile(String bucketName, String oldLocation, String newLocation, String fileExtension, String oldFileName) {
//		try {
//			boolean isExist = checkObjectExist(bucketName, oldLocation);
//			if (!isExist) return;
//			byte[] getFile = getFile(bucketName, oldLocation);
//			if (getFile == null) throw new RuntimeInternalServerException("File Not Found");
//			String contentType = handleExtendedFile(fileExtension);
//			deleteFolder(bucketName, oldLocation);
//			uploadByte(bucketName, newLocation + oldFileName, getFile, contentType);
//		} catch (Exception ex) {
//			throw new RuntimeInternalServerException("Invalid file");
//		}
//	}

}