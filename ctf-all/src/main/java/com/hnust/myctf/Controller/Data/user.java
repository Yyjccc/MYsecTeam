package com.hnust.myctf.Controller.Data;

import com.alibaba.fastjson.JSON;
import com.hnust.myctf.Mode.Base.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.hnust.myctf.Mode.Base.Exception.AuthenError;
import com.hnust.myctf.Mode.Base.Exception.DataError;
import com.hnust.myctf.Mode.CTFUser;
import com.hnust.myctf.Mode.Message;
import com.hnust.myctf.Service.Data.User.CTFuserService;
import com.hnust.myctf.Service.Data.User.Token;
import com.hnust.myctf.Service.Interceptor.CookieSession.CookieManager;
import com.hnust.myctf.Utils.AppLogger;
import com.hnust.myctf.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController

@RequestMapping(value = "/api/data/user",produces = {"application/json"})
public class user implements AppLogger {

	@Autowired
	private CTFuserService ctfUserService;
	//暂无验证码
	@CrossOrigin(originPatterns = "*", allowCredentials = "true")
	@GetMapping(value = "/login/{username}/{password}",produces = {"application/json"})
	public Message login(@PathVariable String username, @PathVariable String password, HttpSession session, HttpServletResponse response){
		try {
			CTFUser ctfUser=ctfUserService.getCTFUserByName(username);
			if(ctfUser.checkPassword(password)){
				Token jwt=new Token();
				jwt.generateJwt(JSON.toJSONString(ctfUser),3600*50);
				session.setAttribute("user",JSON.toJSONString(ctfUser));
				//设置cookie
				CookieManager.setLoginCookie(response,jwt.getJwt());
				logger.info(ctfUser.getUsername()+"用户登录成功");
				HashMap<String,Object> data=new HashMap<>();
				data.put("jwt",jwt.getJwt());
				data.put("JSESSIONID",session.getId());
				return ResposeUtil.response(data);
			}else {
				return ResposeUtil.error("用户名或者密码错误");
			}
		}catch (DataError e){
			return ResposeUtil.error(e);
		}
	}

	@PostMapping("/register")
	public Message register(@RequestBody CTFUser ctfUser){
		if(ctfUserService.checkExist(ctfUser.getUsername())){
			return ResposeUtil.error("用户名已存在");
		}else {
			try {
				ctfUserService.addCTFUser(ctfUser);
				return ResposeUtil.response();
			}catch (Exception e){
				return ResposeUtil.error(e,"注册失败");
			}
		}
	}

	@GetMapping("/destory")
	public Message destory(HttpServletRequest request){
		try {
			CTFUser ctfUser = ctfUserService.getUserByRequest(request);
			ctfUserService.deleteUser(ctfUser);
			return ResposeUtil.response();
		}catch (AuthenError e){
			return ResposeUtil.error(e,AuthenError.type);
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	@GetMapping("/update/{type}/{value}")
	public Message update(@PathVariable String type, @PathVariable String value, HttpServletRequest request){
		try {
			CTFUser ctfUser = ctfUserService.getUserByRequest(request);
			if (type.equals("username") && ctfUserService.checkExist(value)) {
				return ResposeUtil.error("用户名已存在");
			} else {
				ctfUserService.updateValue(ctfUser, type, value);
				return ResposeUtil.response();
			}
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	@GetMapping("/query/{username}")
	public Message query(@PathVariable String username){
		try {
			CTFUser ctfUser=ctfUserService.getCTFUserByName(username);
			return ResposeUtil.response(ctfUser);
		}catch (DataError e){
			return ResposeUtil.error(e);
		}
	}

	@GetMapping("/student")
	public Message getStudent(HttpServletRequest request){
		try {
			Student student = ctfUserService.getStudent(ctfUserService.getUserByRequest(request));
			return ResposeUtil.response(student);
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	//修改
	@PostMapping("/student")
	public Message updateStudent(@RequestBody Student student,HttpServletRequest request){
		try {
			CTFUser user = ctfUserService.getUserByRequest(request);
			ctfUserService.updateStudent(user,student);
			return ResposeUtil.response();
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}


	//添加
	@PutMapping("/student")
	public Message addStudent(@RequestBody Student student,HttpServletRequest request){
		try {
			CTFUser ctfUser=ctfUserService.getUserByRequest(request);
			ctfUserService.deleteStudent(ctfUser);
			return ResposeUtil.response();
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	@DeleteMapping("/student")
	public Message deleteStudent(HttpServletRequest request){
		try {
			CTFUser ctfUser=ctfUserService.getUserByRequest(request);
			ctfUserService.deleteStudent(ctfUser);
			return ResposeUtil.response();
		}catch (Exception e){
			return ResposeUtil.error(e);
		}
	}

	@GetMapping("/getName/{id}")
	public Message getName(@PathVariable String id){
		try {
			CTFUser user = ctfUserService.getUserById(Long.parseLong(id));
			return ResposeUtil.response(user.getUsername());
		}catch (Exception e){
			return ResposeUtil.response();
		}
	}
}
