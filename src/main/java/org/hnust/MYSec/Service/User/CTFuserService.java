package org.hnust.MYSec.Service.User;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import org.hnust.MYSec.Mode.Base.Exception.ArgsError;
import org.hnust.MYSec.Mode.Base.Exception.AuthenError;
import org.hnust.MYSec.Mode.Base.Exception.DataError;
import org.hnust.MYSec.Mode.Mapper.CTFUserMapper;
import org.hnust.MYSec.Mode.Mapper.StudentMapper;
import org.hnust.MYSec.Mode.Base.Student;
import org.hnust.MYSec.Mode.CTFUser;
import org.hnust.MYSec.Service.DockerAPI.DockerManger;
import org.hnust.MYSec.Service.Interceptor.CookieSession.CookieManager;
import org.hnust.MYSec.Utils.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CTFuserService {
	@Autowired
	private CTFUserMapper ctfUserMapper;

	@Autowired
	private StudentMapper studentMapper;

	private static final Logger logger = LoggerFactory.getLogger(DockerManger.class);

	public CTFUser getCTFUserByName(String name){
		List<CTFUser> ctfUsers=ctfUserMapper.findUserByName(name);
		if(ctfUsers.isEmpty()){
			throw new DataError("用户名不存在");
		}else {
			return ctfUsers.get(0);
		}
	}

	public void addCTFUser(CTFUser ctfUser){
		//密码进行加盐hash
		String rawPassword=ctfUser.getPassword();
		ctfUser.setSalt(HashUtil.generateSalt());
		ctfUser.setPassword(rawPassword);
		if (ctfUser.isInner()) {
			Student student = ctfUser.getStudentInfo();
			studentMapper.insert(student);
			ctfUser.setStudentInfoId(student.getId());

		}
		ctfUserMapper.insert(ctfUser);
	}

	public void deleteUser(CTFUser ctfUser){
		if(ctfUser.isInner()){
			studentMapper.deleteById(ctfUser.getStudentInfoId());
		}
		ctfUserMapper.deleteByName(ctfUser.getUsername());
	}
	//这个方法能改则该。检查数据库中用户名是否存在
	public boolean checkExist(String username){
		List<CTFUser> ctfUsers=ctfUserMapper.findUserByName(username);
		return !ctfUsers.isEmpty();
	}

	public boolean updateValue(CTFUser ctfUser, String type, String value){
		List<String> allow=new ArrayList<String>(List.of("username","password","email","manager"));
		if(!allow.contains(type)){
			throw new ArgsError("参数值错误  type:"+type);
		}
		if(type.equals("username")){
			ctfUser.setUsername(value);
			} else if (type.equals("password")) {
			ctfUser.setPassword(value);
		} else if (type.equals("email")) {
			ctfUser.setEmail(value);
		} else if (type.equals("manager")) {
			if(value.equals("true")){
			ctfUser.setManager(true);
			}else {
				ctfUser.setManager(false);
			}
		}
		return ctfUserMapper.updateById(ctfUser)>0;
	}


	//通过jwt字符串反序列化,还原对象
	public CTFUser getUserByToken(String jwt){
		CTFUser ctfUser=null;
		try {
			String s=Token.decodeJwt(jwt).getSubject();
			ctfUser= JSON.parseObject(s,CTFUser.class);
		}catch (Exception e){
			logger.error(e.getMessage());
		}
		return ctfUser;
	}
	//优先Cookie,再Session，获取ctfuser对象
	public CTFUser  getUserByRequest(HttpServletRequest request){
		String jwtToken= CookieManager.getJwtByCookie(request);
		if(jwtToken!=null && Token.validateJwt(jwtToken)) {
			return getUserByToken(jwtToken);
		}

		String jwt = request.getSession().getAttribute("user").toString();
		if(jwt!=null) {
			return getUserByToken(jwt);
		}
		throw new AuthenError("无效cookie");
	}
}
