package org.hnust.MYSec.Conteller.API;


import com.alibaba.fastjson.JSON;
import org.hnust.MYSec.Mode.Message;
import org.hnust.MYSec.Service.File.CommonUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class upload {
	@Autowired
	private CommonUpload fileUploadService;

	@Autowired
	private Message message;

	@PostMapping(value = "/common",produces = {"application/json"})
	public String handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			fileUploadService.uploadFile(file);
			message.setOK();
			message.setInfo("File uploaded successfully!");
		} catch (Exception e) {
			message.setFail();
			message.setInfo("File upload fail :"+e.getMessage());
		}
		return JSON.toJSONString(message);
	}
}
