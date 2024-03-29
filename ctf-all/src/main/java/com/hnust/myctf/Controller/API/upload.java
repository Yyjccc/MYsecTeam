package com.hnust.myctf.Controller.API;

import com.hnust.myctf.Mode.Base.Exception.ArgsError;
import com.hnust.myctf.Mode.Base.Message;
import com.hnust.myctf.Mode.Base.TargetDrone;
import com.hnust.myctf.Mode.Vo.FileUploadVo;
import com.hnust.myctf.Service.Data.TargetDroneService;
import com.hnust.myctf.Service.File.CommonUpload;
import com.hnust.myctf.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//文件上传的总接口
@RestController
@RequestMapping(value = "/api/upload",produces = {"application/json"})
public class upload {



	@Autowired
	private CommonUpload fileUploadService;

	@Autowired
	private TargetDroneService targetDroneService;


	private static List<String> allowType=new ArrayList<>(List.of(new String[]{"img"}));



	@PostMapping( "/common")
	public Message handleFileUpload(@RequestParam("file") MultipartFile file) {
		try {
			fileUploadService.uploadFile(file);
			return 	ResposeUtil.response("File uploaded successfully!");
		} catch (Exception e) {
			return ResposeUtil.error("File upload fail :"+e.getMessage());
		}

	}

	/**
	 * 功能：只负责题目靶机上传，不需要验证身份
	 * @param id     题目id
	 * @param file     上传的文件对象
	 * @return
	 */

	@PostMapping("/docker/{id}")
	public Message uploadTargets(@PathVariable String id, @RequestParam("file") MultipartFile file){
		TargetDrone targetDrone = targetDroneService.getTargetDroneMapper().selectById(Long.parseLong(id));
		if(targetDrone==null){
			return ResposeUtil.error("id不存在");
		}
		try {
			//保存题目
			fileUploadService.saveTargets(file,targetDrone.getCategory(),id);
			//修改数据
			targetDroneService.updateTarget("docker","true",Long.parseLong(id));
			return ResposeUtil.response("文件上传成功");
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	@PostMapping("/attachment/{id}")
	public Message uploadAttachment(@PathVariable String id, @RequestParam("file") MultipartFile file){
		try {
			//保存附件
			fileUploadService.saveAttachment(file,id);
			//修改数据
			targetDroneService.updateTarget("attachment","true",Long.parseLong(id));
			return ResposeUtil.response("上传成功");
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	//提供其他模块的上传接口
	@PostMapping("/{module}/{fileType}")
	public Message modleCommon(@PathVariable String module,@PathVariable String fileType,@RequestParam("file") MultipartFile file){
		try {
			if(!allowType.contains(fileType)){
				throw new ArgsError("不允许的文件类型");
			}
			if(module.equals("blog")){
				String path="/"+module+"/"+fileType;
				FileUploadVo uploadVo = fileUploadService.moduleUpload(path, file);
				return ResposeUtil.response(uploadVo);
			}
			throw new ArgsError("模块错误");
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}


}
