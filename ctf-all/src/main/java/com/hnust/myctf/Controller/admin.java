package com.hnust.myctf.Controller;

import com.hnust.myctf.Configure.MYsecConfig;
import com.hnust.myctf.Mode.Message;
import com.hnust.myctf.Service.AdminService;
import com.hnust.myctf.Service.Data.TargetDroneService;
import com.hnust.myctf.Utils.Common;
import com.hnust.myctf.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin",produces = {"application/json"})
public class admin {

	@Autowired
	private TargetDroneService targetDroneService;


	private AdminService adminService;

	private List<String> argList;


	@Autowired
	public admin(AdminService adminService){
		this.adminService=adminService;
		Class c= new MYsecConfig().getClass();
		Field[] fields=c.getDeclaredFields();
		this.argList=new ArrayList<>();
		for(Field field:fields){
			this.argList.add(field.getName());
		}
	}

	@PostMapping("/")
	public Message upload(){

		return null;
	}


	@GetMapping("/config/{type}/{value}")
	public Message updateConfig(@PathVariable String type,@PathVariable String value){
		if(argList.contains(type)){
			try {
				if(value.equals("true")||value.equals("false")){
					Common.modifyByReflect(MYsecConfig.class,type,Boolean.parseBoolean(value));
				} else if (value.matches("-?\\d+(\\.\\d+)?")) {
					Common.modifyByReflect(MYsecConfig.class,type,Integer.parseInt(value));
				} else {
					Common.modifyByReflect(MYsecConfig.class,type,value);
				}
				return null;
			}catch (Exception e){
				return  ResposeUtil.error(e,"不允许修改");
			}
		}else {
			return ResposeUtil.error("没有此配置: "+type);
		}
	}
}
