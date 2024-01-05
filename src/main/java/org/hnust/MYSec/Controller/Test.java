package org.hnust.MYSec.Controller;


import com.alibaba.fastjson.JSON;
import org.hnust.MYSec.Mode.Mapper.CTFUserMapper;
import org.hnust.MYSec.Mode.Base.Student;
import org.hnust.MYSec.Mode.CTFUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Test {
	@Autowired
	private CTFUserMapper ctfUserMapper;
	@GetMapping(value = "/test/sql",produces = { "application/json"})
	public String getall() {
		List<CTFUser> ctfUsers = ctfUserMapper.selectList(null);
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
}
