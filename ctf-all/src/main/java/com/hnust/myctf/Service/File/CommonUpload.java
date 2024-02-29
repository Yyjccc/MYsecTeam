package com.hnust.myctf.Service.File;


import com.hnust.myctf.Mode.Vo.FileUploadVo;
import com.hnust.myctf.Service.ServiceDiscoveryService;
import lombok.Data;
import com.hnust.myctf.Configure.FileManager;
import com.hnust.myctf.Mode.Base.Exception.FileError;
import com.hnust.myctf.Utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Data
@Component

public class CommonUpload {
	@Autowired
	private final FileManager fileConfig; //文件管理设置

	private final String ConfigName = "docker-compose.yaml";

	@Autowired
	public ServiceDiscoveryService discoveryService;


	public CommonUpload(FileManager fileConfig) {
		this.fileConfig = fileConfig;
	}




	public FileUploadVo moduleUpload(String path, MultipartFile file){
		String filepath=fileConfig.getUploadRootDir()+path;
		// 获取文件的原始名称
		String fileName = file.getOriginalFilename();
		// 创建目标路径
		Path targetPath = Path.of(filepath, fileName);

		// 将文件保存到目标路径
		try {
			Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

			//获取本服务的主机地址
			List<String> serviceInstances = discoveryService.getServiceInstances("ctfAll");
			String src=serviceInstances.get(0)+(filepath+"/"+fileName).substring(1);
			FileUploadVo uploadVo = new FileUploadVo("local");
			uploadVo.setFilename(fileName);
			uploadVo.setSrc(src);
			uploadVo.setModuleType(path);
			return uploadVo;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	public void uploadFile(MultipartFile file) throws Exception {
		// 获取文件的原始名称
		String fileName = file.getOriginalFilename();

		// 创建目标路径
		Path targetPath = Path.of(fileConfig.uploadRootDir, fileName);

		// 将文件保存到目标路径
		Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	}

	/**
	 * 保存上传的题目，以后可能打算建立缓存目录
	 * @param multiFile
	 * @param category  题目分类
	 * @param dirName  目录名字
	 * @throws IOException
	 */
	public void saveTargets(MultipartFile multiFile,String category,String dirName) throws IOException {
		// 定义常见的压缩包文件后缀
		String directoryName=fileConfig.getTargetsDir()+"/"+category;
		// 获取文件的原始名称
		String fileName = multiFile.getOriginalFilename();
		String fileExtension = FileUtil.getFileExtension(fileName);
		File file=FileUtil.convertMultipartToFile(multiFile,dirName+"."+fileExtension);
		if(fileExtension.equals("zip")){
			FileUtil.unPackZip(file,directoryName,dirName);
		} else if (fileExtension.equals("tar")) {
			FileUtil.unPackTar(file,directoryName,dirName);
		} else if (fileExtension.equals("tar.gz")) {
			try {
				FileUtil.unPackTarGz(file,directoryName,dirName);
			} catch (Exception e){
				throw new FileError("仅支持rar5版本以下的解压");
			}
		} else {
			throw new FileError("文件类型错误");
		}
		//解压成功后删除源文件
		if(file.exists()){
		file.delete();
		}
	}
	public void saveAttachment(MultipartFile multiFile,String id) throws IOException {
		String originalFilename = multiFile.getOriginalFilename();
		String fileName=id+"."+FileUtil.getFileExtension(originalFilename);
		// 创建目标路径
		Path targetPath = Path.of(fileConfig.getAttachmentDir(), fileName);
		Files.copy(multiFile.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
	}


	/**
	 *
	 * @param srcPath  原来的category分类+id
	 * @param destPath  新的category+id
	 */
	//移动文件夹
	public  void moveDockerFolder(String srcPath, String destPath) {
		// 创建一个 File 对象表示源文件夹
		File srcFolder = new File(fileConfig.getTargetsDir()+"\\"+srcPath);
		// 创建一个 File 对象表示目标文件夹
		File destFolder = new File(fileConfig.getTargetsDir()+"\\"+destPath);
		// 如果目标文件夹不存在，就创建它
		if (!destFolder.exists()) {
			destFolder.mkdirs();
		}
		// 获取源文件夹中的所有文件和子文件夹
		File[] files = srcFolder.listFiles();
		// 遍历所有文件和子文件夹
		for (File file : files) {
			// 获取文件或子文件夹的绝对路径
			String filePath = file.getAbsolutePath();
			// 获取文件或子文件夹的名称
			String fileName = file.getName();
			// 拼接目标文件夹的绝对路径和文件或子文件夹的名称
			String destFilePath = destFolder.getAbsolutePath() + File.separator + fileName;
			// 如果是文件，就直接移动
			if (file.isFile()) {
				file.renameTo(new File(destFilePath));
			}
			// 如果是子文件夹，就递归调用本方法
			else if (file.isDirectory()) {
				moveDockerFolder(filePath, destFilePath);
			}
		}
		// 删除源文件夹
		srcFolder.delete();
	}


}
