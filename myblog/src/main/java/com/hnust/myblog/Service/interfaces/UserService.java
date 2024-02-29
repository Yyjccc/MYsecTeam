package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Vo.*;
import com.hnust.myblog.Mode.Base.User;

import java.io.IOException;
import java.util.List;

public interface UserService extends IService<User> {
	BlogLoginVo login(User user);


	void logout(String id);

	void logout(String id,boolean isAdmin);

	TokenVo getUserInfo(String id);

	void updateAvatar(StaticFile staticFile, String id) throws IOException;

	void updateUserInfo(User user);

	void register(User user);

	AdminUserInfoVo getAdminUserInfo(String s);

	String parseToken(String token);

	String AdminLogin(User user);


}
