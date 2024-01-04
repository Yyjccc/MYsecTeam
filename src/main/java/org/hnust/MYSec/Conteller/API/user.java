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

@RestController
@RequestMapping("/api/user")
public class user {
	@Autowired
	private Message message;

	@Autowired
	private CTFuserService ctFuserService;

	private static final Logger logger = LoggerFactory.getLogger(DockerManger.class);



	//暂无验证码
	@GetMapping(value = "/login/{username}/{password}",produces = {"application/json"})
	public String login(@PathVariable String username,@PathVariable String password){
		CTFUser ctfUser=ctFuserService.getCTFUserByName(username);
		if(ctfUser==null){
			message.setFail();
			message.setInfo("用户不存在");
		}else {
			if(ctfUser.getPassword().equals(password)){
				message.setOK();
				Token jwt=new Token();
				jwt.generateJwt(ctfUser.toString(),3600*50);
				message.setInfo(jwt);
				logger.info(ctfUser.getUsername()+"用户登录成功");

			}else {
				message.setFail();
				message.setInfo("用户名或者密码错误");
			}
		}
		return JSON.toJSONString(message);
	}

	@PostMapping("/register")
	public String register(CTFUser ctfUser){

		return JSON.toJSONString(message);
	}
}
