package org.hnust.MYSec.Conteller.API;

import com.alibaba.fastjson.JSON;
import org.hnust.MYSec.Mode.CTFUser;
import org.hnust.MYSec.Mode.Message;
import org.hnust.MYSec.Service.DockerAPI.DockerManger;
import org.hnust.MYSec.Service.User.CTFuserService;
import org.hnust.MYSec.Service.User.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/api/user",produces = {"application/json"})
public class user {
	@Autowired
	private Message message;

	@Autowired
	private CTFuserService ctfUserService;

	private static final Logger logger = LoggerFactory.getLogger(DockerManger.class);



	//暂无验证码
	@GetMapping(value = "/login/{username}/{password}",produces = {"application/json"})
	public String login(@PathVariable String username,@PathVariable String password){
		message.setFail();
		CTFUser ctfUser=ctfUserService.getCTFUserByName(username);
		if(ctfUser==null){
			message.setInfo("用户不存在");
		}else {
			if(ctfUser.getPassword().equals(password)){
				message.setOK();
				Token jwt=new Token();
				jwt.generateJwt(JSON.toJSONString(ctfUser),3600*50);
				message.setInfo(jwt);
				logger.info(ctfUser.getUsername()+"用户登录成功");

			}else {
				message.setInfo("用户名或者密码错误");
			}
		}
		return JSON.toJSONString(message);
	}

	@PostMapping("/register")
	public String register(@RequestBody CTFUser ctfUser){
		message.setFail();
		if(ctfUserService.checkExist(ctfUser.getUsername())){
			message.setInfo("用户名已存在");
		}else {
			if (ctfUserService.addCTFUser(ctfUser)) {
				message.setOK();
				message.setInfo("注册成功");
			} else {
				message.setInfo("注册失败");
			}
		}
		return JSON.toJSONString(message);
	}

	@GetMapping("/destory")
	public String destory(@CookieValue(name = "jwt") String cookieValue){
		message.setFail();
		CTFUser ctfUser=ctfUserService.getUserByToken(cookieValue);
		if(ctfUser==null){
			message.setInfo("无效jwt");
		}else {
			if(ctfUserService.deleteUser(ctfUser)){
				message.setOK();
				message.setInfo("注销账号成功");
			}else {
				message.setInfo("服务器错误");
			}
		}
		return JSON.toJSONString(message);
	}

	@GetMapping("/{type}/{value}")
	public String update(@PathVariable String type,@PathVariable String value,@CookieValue(name = "jwt") String cookieValue){
		message.setFail();
		List<String> allow=new ArrayList<String>(List.of("username","password","email","manager"));
		if (!allow.contains(type)){
			message.setInfo("参数错误");
		}else {
			CTFUser ctfUser=ctfUserService.getUserByToken(cookieValue);
			if(ctfUser==null){
				message.setInfo("无效cookie");
			}else {
				if(type.equals("username")&&ctfUserService.checkExist(value)){
					message.setInfo("用户名已存在");
				}else {
					if(ctfUserService.updateValue(ctfUser,type,value)){
						message.setOK();
						message.setInfo("修改成功");
					}else {
						message.setInfo("服务器出错");
					}
				}
			}
		}
		return JSON.toJSONString(message);
	}

	@GetMapping("/query/{username}")
	public String query(@PathVariable String username){
		message.setFail();
		if(!ctfUserService.checkExist(username)){
			message.setInfo("用户名不存在");
		}else {
			CTFUser ctfUser=ctfUserService.getCTFUserByName(username);
			message.setInfo(JSON.toJSONString(ctfUser));
		}
		return JSON.toJSONString(message);
	}
}
