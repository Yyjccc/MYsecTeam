package com.hnust.myblog.Service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Menu;
import com.hnust.myblog.Mode.Vo.*;
import com.hnust.myblog.Mode.Base.User;
import com.hnust.myblog.Mode.Mapper.UserMapper;
import com.hnust.myblog.Mode.enums.HttpCode;
import com.hnust.myblog.Service.interfaces.MenuService;
import com.hnust.myblog.Service.interfaces.UserService;
import com.hnust.myblog.Service.utils.BeanCopyUtil;
import com.hnust.myblog.Service.utils.CommServiceClient;
import com.hnust.myblog.Service.utils.RedisCache;
import com.hnust.myblog.Service.utils.SystemConstance;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



	@Autowired
	private RedisCache redisCache;

	@Autowired
	private CommServiceClient CommServiceClient;

	@Autowired
	public ServiceDiscoveryService discoveryService;

	@Autowired
	private MenuService menuService;



	@Override
	public BlogLoginVo login(User user) {
		//校验密码
		LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(User::getUserName,user.getUserName());
		User loginUser = getBaseMapper().selectOne(wrapper);
		if(loginUser==null){
			throw new RuntimeException("用户不存在");
		}
		//生成token

		//Token token = new Token();
		String id = loginUser.getId().toString();
		String token= CommServiceClient.generate(SystemConstance.MODULE_NAME, id).getData().toString();

		//将用户信息存入redis
		redisCache.setCacheObject("user:"+id,loginUser);
		BlogLoginVo blogLoginVo = new BlogLoginVo(token, BeanCopyUtil.copyBean(loginUser, TokenVo.class),false);

		return blogLoginVo;
	}



	@Override
	public void logout(String id) {
		logout(id,false);
	}

	public  void logout(String id,boolean isAdmin){
		if(isAdmin){
			redisCache.deleteObject("AdminUser:"+id);
		}else {
			redisCache.deleteObject("user:"+id);
		}
	}

	@Override
	public TokenVo getUserInfo(String id) {
		User user = getById(Long.parseLong(id));
		return BeanCopyUtil.copyBean(user,TokenVo.class);
	}

	@Override
	public void updateAvatar(StaticFile staticFile, String id) throws IOException {
		if(staticFile==null||staticFile.getSrc()==null){
			throw new IOException("服务端上传文件失败");
		}
		String host = discoveryService.getServiceInstances(SystemConstance.MODULE_NAME).get(0);
		String src= host+"/static/"+staticFile.getId();
		User user = getById(id);
		user.setAvatar(src);
		getBaseMapper().updateById(user);
	}

	@Override
	public void updateUserInfo(User user) {
		updateById(user);
	}

	@Override
	public void register(User user) {
		if(user==null){
			throw new RuntimeException("数据错误");
		}
		if(findUserByName(user.getUserName()) || findUserByNick(user.getNickName())){
			throw new RuntimeException("用户名或者昵称已存在");
		}else {
			if(findUserByEmail(user.getEmail())){
				throw new RuntimeException("邮箱已经注册账号");
			}

			getBaseMapper().insert(user);
		}
	}

	@Override
	public AdminUserInfoVo getAdminUserInfo(String id) {
		List<String> perms = menuService.getPerms();
		User loginUser = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject("AdminUser:"+id)), User.class);
		List<String> roles=new ArrayList<>();
		roles.add("3");
		return new AdminUserInfoVo(Long.parseLong(id),loginUser.getAvatar(),perms,roles);

	}

	private boolean findUserByName(String name){
		LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(User::getUserName,name);
		List<User> list = list(wrapper);
		return !list.isEmpty();
	}

	private boolean findUserByNick(String name){
		LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(User::getNickName,name);
		List<User> list = list(wrapper);
		return !list.isEmpty();
	}

	private boolean findUserByEmail(String email){
		LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(User::getEmail,email);
		List<User> list = list(wrapper);
		return !list.isEmpty();
	}

	public String parseToken(String token){
		if(token==null){
			throw new RuntimeException(HttpCode.NO_AUTH.getMsg());
		}
		return CommServiceClient.decode(token).getData();

	}

	@Override
	public String AdminLogin(User user) {
		//校验密码
		LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(User::getUserName,user.getUserName());
		User loginUser = getBaseMapper().selectOne(wrapper);
		if(loginUser==null){
			throw new RuntimeException("用户不存在");
		}
		//生成token
		String id = loginUser.getId().toString();
		String token= CommServiceClient.generate(SystemConstance.MODULE_NAME, id).getData().toString();
		redisCache.setCacheObject("AdminUser:"+id,loginUser);
		return token;
	}


}
