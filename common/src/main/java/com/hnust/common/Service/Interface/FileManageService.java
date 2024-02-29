package com.hnust.common.Service.Interface;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.common.Mode.Base.StaticFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FileManageService extends IService<StaticFile> {
	StaticFile upload(String module,String type, MultipartFile file) throws IOException;

	InputStream getFile(Long id);
}
