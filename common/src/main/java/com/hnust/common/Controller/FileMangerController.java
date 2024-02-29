package com.hnust.common.Controller;

import com.alibaba.fastjson.JSON;
import com.hnust.common.Mode.Base.Response;
import com.hnust.common.Mode.Base.StaticFile;
import com.hnust.common.Service.Interface.FileManageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


//上传文件统一管理
@RestController
@RequestMapping("/file")
public class FileMangerController {

	@Autowired
	private FileManageService fileService;

	public static final Logger logger = LoggerFactory.getLogger(FileMangerController.class);



	@PostMapping("/upload/{module}/{type}")
	public Response uploadFile(@PathVariable String module,@PathVariable String type,MultipartFile file){
		try {
			StaticFile staticFile= fileService.upload(module,type,file);
			logger.info("file服务--upload: "+ JSON.toJSON(staticFile));
			return Response.response(staticFile);
		}catch (Exception e){
			return Response.error(e, FileMangerController.class);
		}
	}

	@GetMapping(value = "/get/{id}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public void getFile(HttpServletResponse response, @PathVariable Long id) throws IOException {
		InputStream in =fileService.getFile(id);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		IOUtils.copy(in,response.getOutputStream());
	}


}
