package com.hnust.myblog.Controller;

import com.alibaba.fastjson.JSON;
import com.hnust.myblog.Mode.Base.Response;
import com.hnust.myblog.Mode.Vo.*;
import com.hnust.myblog.Mode.Base.User;
import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.enums.HttpCode;
import com.hnust.myblog.Service.interfaces.ArticleService;
import com.hnust.myblog.Service.interfaces.UserService;
import com.hnust.myblog.Service.impl.ServiceDiscoveryService;
import com.hnust.myblog.Service.utils.CommServiceClient;

import com.hnust.myblog.Service.utils.SystemConstance;
import com.hnust.myblog.Other.annocation.SystemLog;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@RestController
public class BlogUserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CommServiceClient client;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ServiceDiscoveryService discoveryService;

	@Autowired
	private ArticleService articleService;

	@SystemLog(ServiceName = "登录接口")
	@PostMapping("/login")
	public ResponseResult login(@RequestBody User user) {
		try {
			BlogLoginVo tokenVo = userService.login(user);
			return ResponseResult.response(tokenVo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseResult.error(e, BlogUserController.class);
		}
	}
	@SystemLog(ServiceName = "注销接口")
	@PostMapping("/logout")
	public ResponseResult logout(@RequestHeader("token") String token) {
		if (token == null) {
			return ResponseResult.error(HttpCode.NO_AUTH);
		} else {
			String claims = client.decode(token).getData();
			userService.logout(claims);
			return ResponseResult.response();
		}
	}



//	@GetMapping("admin/userInfo")
//	public ResponseResult getInfo(@RequestHeader("token") String token){
//		try {
//			AdminUserInfoVo adminUserInfo = userService.getAdminUserInfo(userService.parseToken(token));
//			return ResponseResult.response(adminUserInfo);
//		}catch (Exception e){
//			return ResponseResult.error(e, BlogUserController.class);
//		}
//	}

	@PostMapping("/user/register")
	public ResponseResult register(@RequestBody User user){
		try{
			userService.register(user);
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}

	@GetMapping("/user/userInfo")
	public ResponseResult getUserInfo(@RequestHeader("token") String token) {
		if (token == null) {
			return ResponseResult.error(HttpCode.NO_AUTH);
		} else {
			String claims = client.decode(token).getData();
			if (claims == null) {
				return ResponseResult.error(HttpCode.INVALID_TOKEN);
			}
			TokenVo user = userService.getUserInfo(claims);
			return ResponseResult.response(user);
		}
	}

	@PutMapping("/user/userInfo")
	public ResponseResult updateUserInfo(@RequestBody User user){
		try {
			if(user==null){
				throw new RuntimeException("数据不能为空");
			}else {
				userService.updateUserInfo(user);
			}
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}



	@PostMapping("/upload")
	public ResponseResult upload(@RequestHeader("token") String token,@RequestPart MultipartFile file) {
		if (token == null) {
			return ResponseResult.error(HttpCode.NO_AUTH);
		} else {
			String claims = client.decode(token).getData();
			if (claims == null) {
				return ResponseResult.error(HttpCode.INVALID_TOKEN);
			}
			try {

				Response img = client.uploadFile(SystemConstance.MODULE_NAME, "img", file);
				StaticFile staticFile = JSON.parseObject(img.getData(), StaticFile.class);
				userService.updateAvatar(staticFile, claims);
				return ResponseResult.response();
			} catch (Exception e) {
				return ResponseResult.error(e, BlogUserController.class);
			}
		}
	}

	@GetMapping(value = "/static/{id}",produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	public void getFile(HttpServletResponse response, @PathVariable Long id) throws IOException {
		String url=discoveryService.getServiceInstances("common").get(0)+"/file/get/"+id;
		ResponseEntity<byte[]> res = restTemplate.getForEntity(url, byte[].class);
		byte[] imageData = res.getBody();
		InputStream in = new ByteArrayInputStream(imageData);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		IOUtils.copy(in,response.getOutputStream());
	}

	@PostMapping("/admin/article/upload")
	public ResponseResult Adminupload(@RequestPart MultipartFile file,@RequestHeader("id") String id){
		try {
			Response img = client.uploadFile(SystemConstance.MODULE_NAME, "img", file);
			StaticFile staticFile = JSON.parseObject(img.getData(), StaticFile.class);
			articleService.updateImg(id,staticFile);
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}

}