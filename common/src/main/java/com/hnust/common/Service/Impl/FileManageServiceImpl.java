package com.hnust.common.Service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.common.Configure.FileMangerConfig;
import com.hnust.common.Mode.Base.StaticFile;
import com.hnust.common.Mode.Mapper.StaticFileMapper;
import com.hnust.common.Service.Interface.FileManageService;
import com.hnust.common.Service.Util.SystemConstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service
public class FileManageServiceImpl extends ServiceImpl<StaticFileMapper, StaticFile> implements FileManageService {

	@Autowired
	private FileMangerConfig config;

	@Autowired
	private RestTemplate restTemplate;


	@Autowired
	public ServiceDiscoveryService discoveryService;

	private static final Logger logger = LoggerFactory.getLogger(FileManageServiceImpl.class);

	@Override
	public StaticFile upload(String module, String type, MultipartFile file) throws IOException {
		StaticFile staticFile = new StaticFile(file.getOriginalFilename(),type);
		String mode = config.getMode();
		if(!check(type)){
			throw new RuntimeException("不允许的文件类型");
		}


			//本地文件
		if(mode.equals(SystemConstance.FILE_LOCALHOST_MODE)){
			staticFile.setMode(mode);
			try {
				String src = putFile(file, getPath(module, type));
				staticFile.setSrc(src);
				staticFile.isLocal();
				getBaseMapper().insert(staticFile);
				return staticFile;
			}catch (IOException e){
				throw new RuntimeException("文件上传出错");
			}
		}else {
			//远程模式
			return null;
		}
	}

	@Override
	public InputStream getFile(Long id) {
		StaticFile staticfile = getById(id);
		if(staticfile==null){
			return null;
		}
		if(staticfile.isLocal()){
			//本地获取模式
			ResponseEntity<byte[]> response = restTemplate.getForEntity(staticfile.getSrc(), byte[].class);
			byte[] imageData = response.getBody();
			staticfile.setUpdateTime(new Date());
			updateById(staticfile);
			// 创建一个 InputStreamResource 对象，并设置文件的 MediaType
			InputStream inputStream = new ByteArrayInputStream(imageData);
			logger.info("file服务--get: "+ JSON.toJSONString(staticfile));
			// 返回图片数据
			return inputStream;
		}else {
			return null;
		}
	}

	private boolean check(String type){
		if(config.getAllowType().contains(type)){
			return true;
		}
		return false;
	}

	public String getPath(String module,String type){
		return config.getRootDir()+"/"+module+"/"+type;
	}




	private String putFile(MultipartFile file,String path) throws IOException {
		// 获取文件的原始名称
		String fileName = file.getOriginalFilename();
		// 创建目标路径
		Path targetPath = Path.of(path, fileName);

		Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		List<String> services = discoveryService.getServiceInstances(SystemConstance.SERVICE_NAME);
		String host;
		if(services.isEmpty()){
			host="http://127.0.0.1:12340";
		}else {
			host=services.get(0);
		}
		return host+(path+"/"+fileName).substring(1);
	}


}
