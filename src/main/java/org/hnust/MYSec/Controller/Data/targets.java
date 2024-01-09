package org.hnust.MYSec.Controller.Data;

import org.hnust.MYSec.Mode.Base.Exception.ArgsError;
import org.hnust.MYSec.Mode.Message;
import org.hnust.MYSec.Mode.TargetDrone;
import org.hnust.MYSec.Service.Data.TargetDroneService;
import org.hnust.MYSec.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/data/targetDrone",produces = {"application/json"})
public class targets {

	private TargetDroneService targetService;


	@Autowired
	public targets(TargetDroneService targetDroneService){
		this.targetService=targetDroneService;
		targetService.init();
	}

	//根据分类，获取
	@GetMapping("/get/{category}")
	public Message getall(@PathVariable String category){
		try {
			List<TargetDrone> data = targetService.getAllByCategory(category);
			return ResposeUtil.response(data);
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}


}
