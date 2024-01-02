package org.hnust.MYSec.Service.File;

import lombok.Data;
import org.hnust.MYSec.Configure.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Data
@Component

public class CommonUpload {
	@Autowired
	private final FileManager fileConfig; //文件管理设置

	public CommonUpload(FileManager fileConfig){
		this.fileConfig=fileConfig;
	}

	public void uploadFile(MultipartFile file) throws Exception {
		// 获取文件的原始名称
		String fileName = file.getOriginalFilename();

		// 创建目标路径
		Path targetPath = Path.of(fileConfig.uploadRootDir, fileName);

		// 将文件保存到目标路径
		Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	}


}
