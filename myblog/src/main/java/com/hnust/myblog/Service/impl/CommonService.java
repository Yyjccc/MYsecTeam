package com.hnust.myblog.Service.impl;


import com.hnust.myblog.Mode.Vo.StaticFile;
import com.hnust.myblog.Service.impl.ServiceDiscoveryService;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {

	@Autowired
	private ServiceDiscoveryService discovery;

	public  String  getFileSrc(StaticFile staticFile){
		return getHost()+"/static/"+staticFile.getId();

	}

	private String getHost(){
		List<String> instances = discovery.getServiceInstances(SystemConstance.MODULE_NAME);
		return instances.get(0);
	}

}
