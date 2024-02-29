package com.hnust.myctf.Controller;


import com.alibaba.fastjson.JSON;
import com.hnust.myctf.Configure.MYsecConfig;

import com.hnust.myctf.Mode.Base.Student;
import com.hnust.myctf.Mode.Base.CTFUser;
import com.hnust.myctf.Mode.Mapper.CTFUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class Test {

	@Autowired
	private RestTemplate restTemplate;


	@Autowired
	private CTFUserMapper ctfUserMapper;

	@GetMapping(value = "/test/sql",produces = { "application/json"})
	public String getall() {
		List<CTFUser> ctfUsers = ctfUserMapper.selectList(null);
		Student forObject = restTemplate.getForObject("http://127.0.0.1:12345/test/obj", Student.class);
		System.out.println(forObject);
		return JSON.toJSONString(ctfUsers);
	}

	@GetMapping(value = "/test/obj",produces = { "application/json"})
	public String test1(){
		Student student=new Student();
		student.setAge(22);
		student.setQq("3393867490");
		student.setDepartment("计算机学院");
		student.setDirection("web,misc");
		student.setRealName("yyjccc");
		student.setMajor("信息安全");
		return JSON.toJSONString(student);
	}
	@GetMapping("/test/config")
	public String test(){
		if(MYsecConfig.random){
			return "this is random config";
		}else {
			return "not flag";
		}
	}
}
