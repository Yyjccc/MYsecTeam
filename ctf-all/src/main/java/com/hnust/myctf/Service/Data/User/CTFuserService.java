package com.hnust.myctf.Service.Data.User;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import com.hnust.myctf.Mode.Base.Exception.ArgsError;
import com.hnust.myctf.Mode.Base.Exception.AuthenError;
import com.hnust.myctf.Mode.Base.Exception.DataError;
import com.hnust.myctf.Mode.Base.Student;
import com.hnust.myctf.Mode.CTFUser;
import com.hnust.myctf.Mode.Mapper.CTFUserMapper;
import com.hnust.myctf.Mode.Mapper.StudentMapper;
import com.hnust.myctf.Service.DockerAPI.DockerManger;
import com.hnust.myctf.Service.Interceptor.CookieSession.CookieManager;
import com.hnust.myctf.Utils.HashUtil;
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


	public CTFUser getUserById(Long id){
		return ctfUserMapper.selectById(id);
	}

	public void addCTFUser(CTFUser ctfUser){
		//密码进行加盐hash
		String rawPassword=ctfUser.getPassword();
		ctfUser.setSalt(HashUtil.generateSalt());
		ctfUser.setPassword(rawPassword);
		if (ctfUser.isInner()) {
			Student student = studentMapper.selectById(ctfUser.getStudentInfoId());
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

	public void deleteStudent(CTFUser ctfUser){
		Long id=ctfUser.getStudentInfoId();
		if(id==null|| id==0||id==-1){
			throw new DataError();
		}
		studentMapper.deleteById(ctfUser.getStudentInfoId());
		ctfUser.setStudentInfo(null);
		ctfUser.setStudentInfoId(-1L);
		ctfUserMapper.updateById(ctfUser);
	}

	public void updateStudent(CTFUser ctfUser,Student student){
		Long studentInfoId = ctfUser.getStudentInfoId();
		student.setId(studentInfoId);
		studentMapper.updateById(student);

	}

	public Student getStudent(CTFUser ctfUser){
		return studentMapper.selectById(ctfUser.getStudentInfoId());
	}
	public Long addStudent(CTFUser ctfUser,Student student){
		studentMapper.insert(student);
		ctfUser.setStudentInfoId(student.getId());
		ctfUserMapper.updateById(ctfUser);
		return  student.getId();
	}
}
