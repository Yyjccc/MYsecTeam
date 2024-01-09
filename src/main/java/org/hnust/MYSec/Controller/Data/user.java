package org.hnust.MYSec.Controller.Data;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hnust.MYSec.Mode.Base.Exception.AuthenError;
import org.hnust.MYSec.Mode.Base.Exception.DataError;
import org.hnust.MYSec.Mode.CTFUser;
import org.hnust.MYSec.Mode.Message;
import org.hnust.MYSec.Service.Interceptor.CookieSession.CookieManager;
import org.hnust.MYSec.Service.User.CTFuserService;
import org.hnust.MYSec.Service.User.Token;
import org.hnust.MYSec.Utils.AppLogger;
import org.hnust.MYSec.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/data/user",produces = {"application/json"})
public class user implements AppLogger {

	@Autowired
	private CTFuserService ctfUserService;

	//暂无验证码
	@GetMapping(value = "/login/{username}/{password}",produces = {"application/json"})
	public Message login(@PathVariable String username, @PathVariable String password, HttpSession session, HttpServletResponse response){

		try {
			CTFUser ctfUser=ctfUserService.getCTFUserByName(username);
			if(ctfUser.checkPassword(password)){
				Token jwt=new Token();
				jwt.generateJwt(JSON.toJSONString(ctfUser),3600*50);
				//开启session
				session.setAttribute("user",JSON.toJSONString(ctfUser));
				//设置cookie
				CookieManager.setLoginCookie(response,jwt.getJwt());
				logger.info(ctfUser.getUsername()+"用户登录成功");
				return ResposeUtil.response(jwt);
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
}
