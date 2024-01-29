package com.hnust.myctf.Service.Interceptor.CookieSession;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//全局cookie管理
public  class CookieManager {

	public static void setLoginCookie(HttpServletResponse response,String jwt){
		Cookie cookie=new Cookie("jwt",jwt);
		response.addCookie(cookie);
	}

	public  static  String getJwtByCookie(HttpServletRequest request){
		Cookie[] cookies=request.getCookies();
		String jwtToken=null;
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				// 判断是否为目标Cookie
				if ("jwt".equals(cookie.getName())) {
					// 获取JWT Token的值
					return cookie.getValue();
				}
			}
		}
		return jwtToken;
	}

}
