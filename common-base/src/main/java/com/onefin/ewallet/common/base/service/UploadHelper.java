package com.onefin.ewallet.common.base.service;


import com.onefin.ewallet.common.base.constants.OneFinConstants;
import com.onefin.ewallet.common.base.errorhandler.RuntimeInternalServerException;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UploadHelper {
	public static String handleNameUpload(MultipartFile file, List<String> listExtension, String username, String getFileExtension) {
		Boolean isPermissionFileExtesion = listExtension.stream().anyMatch(e -> e.equals(getFileExtension.toLowerCase()));
		if (!isPermissionFileExtesion) throw new RuntimeInternalServerException("Invalid file extension");

		DateFormat df = new SimpleDateFormat(OneFinConstants.DATE_FORMAT_ddMMyyyyHHmmss);
		String dateFormat = df.format(new Date());

		String fileName = file.getOriginalFilename().replace(getFileExtension, "_" + username + "_" + dateFormat + getFileExtension);
		return fileName;
	}
}
