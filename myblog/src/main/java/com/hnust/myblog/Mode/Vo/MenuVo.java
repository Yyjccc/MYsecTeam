package com.hnust.myblog.Mode.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuVo {
	private String name;
	private String path;
	private String component;
	private boolean alwaysShow;
	private String redirect;
	private Meta meta;
	private List<MenuVo> children;

	public void setMeta(String title,String icon,boolean hidden){
		Meta meta = new Meta(title, icon, hidden);
		this.meta=meta;
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Meta{
	private String title;
	private String icon;
	private boolean hidden;
}
