package com.hnust.myblog.Mode.Base;

import com.alibaba.fastjson.JSON;
import com.hnust.myblog.Other.annocation.SystemLog;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect

//切面类AOP
public class LogAspect {


	private  static final Logger log = LoggerFactory.getLogger(LogAspect.class);

	//
	@Pointcut("@annotation(com.hnust.myblog.Other.annocation.SystemLog)")
	public void point(){}

	@Around("point()")
	public Object pointLog(ProceedingJoinPoint joinPoint) throws Throwable {

		Object ret;
		try {
			beforeHandle(joinPoint);
			ret = joinPoint.proceed();
			afterHandle(ret);
		} finally {
			log.info(System.lineSeparator());
		}
		return  ret;

	}

	public void beforeHandle(ProceedingJoinPoint joinPoint){
		ServletRequestAttributes requestAttribute= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=requestAttribute.getRequest();
		SystemLog systemLog=getSystemLog(joinPoint);
		log.info("接口名称： "+systemLog.ServiceName());
		log.info("HEAD  --   "+request.getMethod()+"\t\t"+request.getRequestURL());
		String methodName=joinPoint.getSignature().getDeclaringTypeName();
		log.info("Class Method --  \t\t"+methodName+"."+((MethodSignature) joinPoint.getSignature()).getName());
		log.info("IP --  \t\t"+request.getRemoteHost());
		log.info("Request Args --  \t\t"+ JSON.toJSONString(joinPoint.getArgs()));
	}

	public void afterHandle(Object ret){
		log.info("Response -- \t\t"+JSON.toJSONString(ret));
	}

	private SystemLog getSystemLog(ProceedingJoinPoint joinPoint){
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		SystemLog systemLog=methodSignature.getMethod().getAnnotation(SystemLog.class);
		return systemLog;
	}

}
