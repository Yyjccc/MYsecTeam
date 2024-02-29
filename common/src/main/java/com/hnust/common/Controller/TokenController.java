package com.hnust.common.Controller;


import com.alibaba.fastjson.JSON;
import com.hnust.common.Mode.Base.Response;
import com.hnust.common.Mode.Base.Token;
import com.hnust.common.Mode.emnus.ServiceStatus;
import com.hnust.common.Service.Interface.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

//提供jwt服务
@RestController
@RequestMapping("/token")
public class TokenController {

	public static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@Autowired
	private TokenService tokenService;

	@GetMapping("/generate/{module}/{id}")
	public Response generate(@PathVariable String module,@PathVariable String id){
		try {
			Long index=new Random().nextLong();
			Token token= tokenService.generate(module,index,id);
			logger.info("token服务--generate: "+ JSON.toJSON(token));
			return Response.response(token.getToken());
		}catch (Exception e){
			return Response.error(e, TokenController.class);
		}
	}

	@PostMapping("/check")
	public Response check(String token){
		try {
			if(token==null){
				return Response.error(ServiceStatus.NO_MUST_ARG);
			}
			boolean res=tokenService.check(token);
			return Response.response(res);
		}catch (Exception e){
			return Response.error(e, TokenController.class);
		}
	}


	@PostMapping("/decode")
	public Response decode(@RequestParam String token){
		try {
			if(!tokenService.check(token)){
				return Response.error(ServiceStatus.INVALID_TOKEN);
			}
			String res=tokenService.decode(token);
			logger.info("token服务--decode: "+res);
			return Response.response(res);
		}catch (Exception e){
			return Response.error(e, TokenController.class);
		}
	}
}
