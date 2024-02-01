package com.hnust.interflow.Service.AI;

import com.hnust.interflow.Configure.AIConfig;
import com.hnust.interflow.Data.Exception.HTTPException;
import com.hnust.interflow.Util.Http;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Data
public class Gemini implements AIMode {




	private AIConfig config;

	private  String server;

	private static Logger logger =LoggerFactory.getLogger(Gemini.class);







	public String common(String content){
		String url=server+"/gemini/common?content=\""+content+"\"";
		try {
			return Http.doGET(url,null);
		} catch (IOException e) {
			logger.error("http错误："+e.getMessage());
			throw new HTTPException(e);
		}
	}
}
