package com.hnust.myctf.Controller.Data;


import com.hnust.myctf.Mode.Base.Message;
import com.hnust.myctf.Mode.Base.TargetDrone;
import com.hnust.myctf.Service.Data.TargetDroneService;
import com.hnust.myctf.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;


//题目
@RestController
@RequestMapping(value = "/api/data/targetDrone",produces = {"application/json"})
public class targets {

	private TargetDroneService targetService;


	@Autowired
	public targets(TargetDroneService targetDroneService){
		this.targetService=targetDroneService;
		targetService.init();
	}

	@GetMapping("/getAll")
	public Message getAll(){
		try {
			List<TargetDrone> targetDrones=targetService.getAll();
			return ResposeUtil.response(targetDrones);
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}


	//根据分类，获取
	@GetMapping("/get/{category}")
	public Message getByCategory(@PathVariable String category){
		try {
			List<TargetDrone> data = targetService.getAllByCategory(category);
			return ResposeUtil.response(data);
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	//条件查询
	//...


	@PostMapping("/add")
	public Message add(@RequestBody TargetDrone targetDrone){
		try {
			Long id=targetService.addTarget(targetDrone);
			HashMap<String,Long> result=new HashMap<>();
			result.put("id",id);
			return  ResposeUtil.response(result);
		}catch (Exception e){
			e.printStackTrace();
			return ResposeUtil.error(e);
		}
	}


	@GetMapping("/update/{id}/{type}/{value}")
	public Message update(@PathVariable Long id,@PathVariable String type,@PathVariable String value){
		try {
			targetService.updateTarget(type, value, id);
			return ResposeUtil.response();
		}catch (Exception e) {
			return ResposeUtil.error(e);
		}
	}

	@GetMapping("/delete/{name}")
	public Message delete(@PathVariable String name){
		try {
			targetService.deleteTarget(name);
			return ResposeUtil.response();
		}catch (Exception e){
			return ResposeUtil.error(e);
		}

	}


}
