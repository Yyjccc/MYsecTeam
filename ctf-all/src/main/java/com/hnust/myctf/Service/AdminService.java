package com.hnust.myctf.Service;

import lombok.Data;
import com.hnust.myctf.Mode.CTFUser;
import com.hnust.myctf.Service.Data.TargetDroneService;
import com.hnust.myctf.Service.File.CommonUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class AdminService {
	private CTFUser adminInfo;

	@Autowired
	private TargetDroneService targetDroneService;

	@Autowired
	private CommonUpload commonUpload;


	//上传文件





}
