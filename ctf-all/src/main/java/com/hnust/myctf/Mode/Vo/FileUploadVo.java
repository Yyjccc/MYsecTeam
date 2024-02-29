package com.hnust.myctf.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadVo {
	private String filename;
	private String moduleType;
	private String src;
	private String type;



	public FileUploadVo(String type){
		this.type=type;
	}
}
