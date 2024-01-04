package com.onefin.ewallet.common.base.service;

import lombok.AllArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@AllArgsConstructor
public class MultipartFileCustom implements MultipartFile {

	private byte[] fileByte;
	private String name;



	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getOriginalFilename() {
		return this.name;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public long getSize() {
		return 0;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return this.fileByte;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(fileByte);
	}

	@Override
	public void transferTo(File file) throws IOException, IllegalStateException {
		new FileOutputStream(file).write(fileByte);
	}
}
