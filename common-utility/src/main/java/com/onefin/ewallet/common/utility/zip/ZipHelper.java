package com.onefin.ewallet.common.utility.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.FileSystemUtils;

/**
 * This is Zip Helpper which takes care of compress functionality of system
 */
@Component
public class ZipHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZipHelper.class);
	/**
	 * A constants for buffer size used to read/write data
	 */
	private static final int BUFFER_SIZE = 4096;

	/**
	 * Zip a stream of data
	 *
	 * @return Zipped stream
	 * @throws IOException
	 */
	public byte[] doZip(byte[] data) throws IOException {
		byte[] ret = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		ZipEntry entry = new ZipEntry("zipentry");
		try {
			entry.setSize(data.length);
			zos.putNextEntry(entry);
			zos.write(data, 0, data.length);
			zos.closeEntry();
		} finally {
			zos.close();
			ret = baos.toByteArray();
			baos.close();
		}

		return ret;
	}

	/**
	 * Zip a list of stream of data
	 *
	 * @param datas
	 * @return
	 * @throws IOException
	 */
	public byte[] doZip(Map<String, byte[]> datas) throws IOException {
		byte[] ret = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			datas.forEach((fileName, fileContent) -> {
				try {
					ZipEntry entry = new ZipEntry(fileName);
					entry.setSize(fileContent.length);
					zos.putNextEntry(entry);
					zos.write(fileContent, 0, fileContent.length);

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						zos.closeEntry();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			zos.close();
			ret = baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Unzip a stream of data
	 *
	 * @throws IOException
	 */
	public byte[] doUnZip(byte[] data) throws IOException {
		byte[] ret = null;
		BufferedInputStream bais = new BufferedInputStream(new ByteArrayInputStream(data));
		ZipInputStream zis = new ZipInputStream(bais);
		try {
			ZipEntry entry = zis.getNextEntry();
			byte[] buf = new byte[BUFFER_SIZE];
			int n = 0;
			if (entry != null) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				while ((n = zis.read(buf, 0, BUFFER_SIZE)) > -1) {
					baos.write(buf, 0, n);
				}

				ret = baos.toByteArray();
				baos.close();
				zis.closeEntry();
			} else { // not a zip archive
				ret = data;
			}
		} finally {
			bais.close();
			zis.close();
		}
		return ret;
	}

	/**
	 * Compresses a collection of files to a destination zip file
	 *
	 * @param listFiles A collection of files and directories
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public byte[] zipFiles(File[] listFiles) throws FileNotFoundException, IOException {
		byte[] ret = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					addFolderToZip(file, file.getName(), zos);
				} else {
					addFileToZip(file, zos);
				}
			}
		} finally {
			zos.close();
			ret = baos.toByteArray();
			baos.close();
		}

		return ret;
	}

	/**
	 * Compresses a collection of files to a destination zip file
	 *
	 * @param listFiles   A collection of files and directories
	 * @param destZipFile The path of the destination zip file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void zipFiles(File[] listFiles, String destZipFile) throws FileNotFoundException, IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));
		try {
			for (File file : listFiles) {
				if (file.isDirectory()) {
					addFolderToZip(file, file.getName(), zos);
				} else {
					addFileToZip(file, zos);
				}
			}
		} finally {
			zos.flush();
			zos.close();
		}

	}

	/**
	 * Adds a directory to the current zip output stream
	 *
	 * @param folder       the directory to be  added
	 * @param parentFolder the path of parent directory
	 * @param zos          the current zip output stream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void addFolderToZip(File folder, String parentFolder, ZipOutputStream zos) throws FileNotFoundException, IOException {
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					addFolderToZip(file, parentFolder + "/" + file.getName(), zos);
					continue;
				}

				zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

				long bytesRead = 0;
				byte[] bytesIn = new byte[BUFFER_SIZE];
				int read = 0;

				while ((read = bis.read(bytesIn)) != -1) {
					zos.write(bytesIn, 0, read);
					bytesRead += read;
				}

				zos.closeEntry();
				bis.close();
			}
		} else {
			LOGGER.info("Folder empty !");
		}
	}

	/**
	 * Adds a file to the current zip output stream
	 *
	 * @param file the file to be added
	 * @param zos  the current zip output stream
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void addFileToZip(File file, ZipOutputStream zos) throws FileNotFoundException, IOException {
		zos.putNextEntry(new ZipEntry(file.getName()));

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		long bytesRead = 0;
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;

		while ((read = bis.read(bytesIn)) != -1) {
			zos.write(bytesIn, 0, read);
			bytesRead += read;
		}

		zos.closeEntry();
		bis.close();
	}

	/**
	 * @param zipfile       byte array data of the zip file to be extracted
	 * @param destDirectory Path of the destination directory
	 * @throws IOException IOException if any I/O error occurred
	 */
	public void doUnZipFile(byte[] zipfile, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}
		BufferedInputStream bais = new BufferedInputStream(new ByteArrayInputStream(zipfile));
		ZipInputStream zipIn = new ZipInputStream(bais);

		ZipEntry entry = zipIn.getNextEntry();

		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				extractFile(zipIn, filePath);
			} else if (entry.isDirectory()) {
				File dir = new File(filePath);
				dir.mkdir();
			}

			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		bais.close();
		zipIn.close();
	}

	/**
	 * @param zipFilePath   Path of the zip file to be extracted
	 * @param destDirectory Path of the destination directory
	 * @throws IOException IOException if any I/O error occurred
	 */
	public void unZipFile(String zipFilePath, String destDirectory) throws IOException {
		File destDir = new File(destDirectory);
		if (!destDir.exists()) {
			destDir.mkdir();
		}

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));

		ZipEntry entry = zipIn.getNextEntry();

		while (entry != null) {
			String filePath = destDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				extractFile(zipIn, filePath);
			} else if (entry.isDirectory()) {
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}

	/**
	 * Extracts a single file
	 *
	 * @param zipIn    the ZipInputStream
	 * @param filePath Path of the destination file
	 * @throws IOException if any I/O error occurred
	 */
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		File newFile = new File(filePath);
		//create all non exists folders
		//else you will hit FileNotFoundException for compressed folder
		new File(newFile.getParent()).mkdirs();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}

	/**
	 * Zip all directories in input path into a list of .zip file
	 *
	 * @param path Input path
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void zipAllDirectories(String path) throws FileNotFoundException, IOException {
		File parent = new File(path);
		Assert.isTrue(parent.isDirectory(), "Invalid Path");

		File[] fileList = parent.listFiles();
		if (fileList == null) {
			return;
		}

		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				File[] zipInput = {fileList[i]};
				zipFiles(zipInput, fileList[i].getAbsolutePath() + ".zip");
			}
		}
	}

	/**
	 * @param zipFile
	 * @param tmpDir
	 * @throws IOException
	 */
	public void unZipFile(byte[] zipFile, String tmpDir, String extractFolder) throws IOException {
		LOGGER.info("-- Prepare temporary folder for seeddata at " + tmpDir);
		File temp = new File(tmpDir);

		if (!temp.exists()) {
			temp.mkdirs();
		} else {
			FileSystemUtils.deleteRecursively(temp);
			temp.mkdirs();
		}
		LOGGER.info("-- Clean folder " + tmpDir + " successful");

		doUnZipFile(zipFile, tmpDir + extractFolder);
	}
}