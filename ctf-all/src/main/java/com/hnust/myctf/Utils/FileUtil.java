package com.hnust.myctf.Utils;


import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.GZIPInputStream;




public class FileUtil {

	//获取文件后缀
	public static String getFileExtension(String originalFilename) {
		if (StringUtils.hasText(originalFilename)) {
			int lastIndex = originalFilename.lastIndexOf(".");
			if (lastIndex != -1 && lastIndex < originalFilename.length() - 1) {
				return originalFilename.substring(lastIndex + 1);
			}
		}
		return null;
	}


	//将上传的文件转为FileInputStream对象
	public static FileInputStream convert(MultipartFile multipartFile) throws IOException {
		// 获取MultipartFile的输入流
		InputStream inputStream = multipartFile.getInputStream();

		// 创建临时文件
		File tempFile = File.createTempFile("temp", null);

		// 将输入流写入临时文件
		multipartFile.transferTo(tempFile);

		// 返回文件输入流
		return new FileInputStream(tempFile);
	}
	public static File convertMultipartToFile(MultipartFile multipartFile,String dirname) throws IOException {
		File file = new File(dirname);
		FileCopyUtils.copy(multipartFile.getBytes(), file);
		return file;
	}

	private static boolean checkMagicNumber(FileInputStream fileInput, byte[] magicNumber) throws IOException {
		InputStream inputStream=fileInput;
		byte[] buffer = new byte[magicNumber.length];
		int bytesRead = inputStream.read(buffer, 0, magicNumber.length);
		if (bytesRead != magicNumber.length) {
			return false;
		}
		for (int i = 0; i < magicNumber.length; i++) {
			if (buffer[i] != magicNumber[i]) {
				return false;
			}
		}
		// Do not close the input stream here
		return true;
	}


	public static void unPackZip(File zipFile, String unpackFolder,String dirName) {
		String fileEncoding = null;
		try {
			fileEncoding = checkEncoding(zipFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String fileEncoding1 = (fileEncoding != null) ? fileEncoding : "UTF-8";
		try (ZipArchiveInputStream zais = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), 4096), fileEncoding1)) {
			ZipArchiveEntry entry = null;
			while ((entry = zais.getNextZipEntry()) != null) {
				//遍历压缩包，如果进行有选择解压，可在此处进行过滤

				String filePath=entry.getName().substring(entry.getName().indexOf("/") );
				String path=dirName+filePath;
				File tmpFile = new File(unpackFolder,path);
				if (entry.isDirectory()) {
					tmpFile.mkdirs();
				} else {
					File file = new File(tmpFile.getAbsolutePath());
					if (!file.exists()) {
						if (!file.getParentFile().exists()) {
							file.getParentFile().mkdirs();
						}
					}
					try (OutputStream os = new BufferedOutputStream(new FileOutputStream(tmpFile), 4096)) {
						IOUtils.copy(zais, os);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//判断字符编码
	private static String checkEncoding(File file) throws IOException {
		InputStream in = new FileInputStream(file);
		byte[] b = new byte[3];
		try {
			int i = in.read(b);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		if (b[0] == -1 && b[1] == -2) {
			return "UTF-16";
		} else if (b[0] == -2 && b[1] == -1) {
			return "Unicode";
		} else if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
			return "UTF-8";
		} else {
			return "GBK";
		}
	}



	public static void  unPackTar(File file,  String unpackFolder,String dirName) throws IOException {

		try (FileInputStream inputStream = new FileInputStream(file);
		     TarArchiveInputStream iStream = new TarArchiveInputStream(inputStream);
		     BufferedInputStream bis = new BufferedInputStream(iStream);) {
			TarArchiveEntry entry = iStream.getNextTarEntry();
			while (entry != null) {
				//遍历压缩包，如果进行有选择解压，可在此处进行过滤
				File unpackFolderFile = new File(unpackFolder);
				String filePath=entry.getName().substring(entry.getName().indexOf("/") );
				String path=dirName+filePath;
				File tmpFile = new File(unpackFolder,path);
				if (!unpackFolderFile.exists()) {
					unpackFolderFile.mkdirs();
				}
				if (entry.isDirectory()) {
					tmpFile.mkdirs();
				} else {

					try (OutputStream out = new FileOutputStream(tmpFile)) {
						int length ;
						byte[] b = new byte[2048];
						while ((length = bis.read(b)) != -1) {
							out.write(b, 0, length);
						}
						out.flush();
					}
				}
				entry = (TarArchiveEntry) iStream.getNextEntry();
			}
		}
	}

	public static void unPackTarGz(File file, final String unpackFolder, String dirName) throws IOException, ArchiveException {
		try (FileInputStream fileInputStream = new FileInputStream(file);
		     GZIPInputStream iStream = new GZIPInputStream(fileInputStream);
		     ArchiveInputStream in = new ArchiveStreamFactory().createArchiveInputStream("tar", iStream);
		     BufferedInputStream bis = new BufferedInputStream(in)) {
			TarArchiveEntry entry;
			while ((entry = (TarArchiveEntry) in.getNextEntry()) != null) {
				//遍历压缩包，如果进行有选择解压，可在此处进行过滤
				File unpackFolderFile = new File(unpackFolder);
				if (!unpackFolderFile.exists()) {
					unpackFolderFile.mkdirs();
				}
				if (entry.getName().contains(".tar")) {
					unPackTar(file, unpackFolder,dirName);
					break;
				}
				String filePath=entry.getName().substring(entry.getName().indexOf("/") );
				String path=dirName+filePath;
				File tmpFile = new File(unpackFolder,path);
				if (!unpackFolderFile.exists()) {
					unpackFolderFile.mkdirs();
				}
				if (entry.isDirectory()) {
					tmpFile.mkdirs();
				} else {
					try (OutputStream out = new FileOutputStream(tmpFile)) {
						int length;
						byte[] b = new byte[2048];
						while ((length = bis.read(b)) != -1) {
							out.write(b, 0, length);
						}
						out.flush();
					}
				}
			}
		}
	}


}
