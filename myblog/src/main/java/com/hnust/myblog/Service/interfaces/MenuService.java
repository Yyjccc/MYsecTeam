package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.Menu;
import com.hnust.myblog.Mode.Vo.MenuVo;

import java.util.List;


public interface MenuService extends IService<Menu> {
	public List<String> getPerms();



	List<MenuVo> getUserMenu(String id);
}
