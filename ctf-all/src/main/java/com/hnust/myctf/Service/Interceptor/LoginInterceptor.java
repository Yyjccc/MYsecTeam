package com.hnust.myctf.Service.Interceptor;


import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.hnust.myctf.Mode.Base.Message;
import com.hnust.myctf.Service.Data.User.Token;
import com.hnust.myctf.Service.Interceptor.CookieSession.CookieManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	// 在请求处理之前进行拦截，检查用户是否已登录
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (checkLogin(request)) {
			return true;
		}
		//设置返回json,设置状态码未授权
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json;charset=UTF-8");
		Message message=new Message();
		message.setOk(false);
		message.setCode(401);
		message.setInfo("无权访问，请先登录");
		response.getWriter().write(JSON.toJSONString(message));
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// 在请求处理之后但在视图渲染之前执行
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// 在整个请求完成后执行，包括视图渲染完成后
	}
	public static  boolean checkLogin(HttpServletRequest request){
		String jwtToken= CookieManager.getJwtByCookie(request);
		if(jwtToken!=null&& Token.validateJwt(jwtToken)){
			return true;
		}
		Object user = request.getSession().getAttribute("user");
		if(user!=null){
			return true;
		}
			return false;
	}
}
