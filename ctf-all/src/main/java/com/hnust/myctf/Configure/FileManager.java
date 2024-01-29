package com.hnust.myctf.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@Configuration
@NoArgsConstructor
public class FileManager {
	@Value("${file.root}")
	public String uploadRootDir;

	@Value("${file.path.attachment}")
	public String attachment;

	@Value("${file.path.picture}")
	public String picturePath;

	@Value("${file.path.docker}")
	public String docker;

	//题目目录
	private String targetsDir;
	//附件目录
	private String attachmentDir;

	public String getAttachmentDir() {
		return uploadRootDir+attachment;
	}

	public String getTargetsDir() {
		return uploadRootDir+docker;
	}
}
