package org.hnust.MYSec.Service.Interceptor.CookieSession;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
