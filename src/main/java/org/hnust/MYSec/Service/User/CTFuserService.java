package org.hnust.MYSec.Service.User;

import org.hnust.MYSec.Mapper.CTFUserMapper;
import org.hnust.MYSec.Mapper.StudentMapper;
import org.hnust.MYSec.Mode.CTFUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTFuserService {
	@Autowired
	private CTFUserMapper ctfUserMapper;

	@Autowired
	private StudentMapper studentMapper;

	public CTFUser getCTFUserByName(String name){
		List<CTFUser> ctfUsers=ctfUserMapper.findUserByName(name);
		if(ctfUsers.isEmpty()){
		return null;
		}else {
			return ctfUsers.get(0);
		}
	}

}
