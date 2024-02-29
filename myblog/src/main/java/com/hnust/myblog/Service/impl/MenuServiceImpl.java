package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.Menu;
import com.hnust.myblog.Mode.Mapper.MenuMapper;
import com.hnust.myblog.Mode.Vo.MenuVo;
import com.hnust.myblog.Service.interfaces.MenuService;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
	public List<String> getPerms(){
		LambdaQueryWrapper<Menu> wrapper=new LambdaQueryWrapper<>();
		wrapper.in(Menu::getMenuType, SystemConstance.MENU,SystemConstance.BUTTON);
		List<Menu> menus = list(wrapper);
		List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
		return perms;
	}

	@Override
	public List<MenuVo> getUserMenu(String id) {
		LambdaQueryWrapper<Menu> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Menu::getMenuType,"M");
		List<Menu> list = list(wrapper);
		List<MenuVo> res=new ArrayList<>();
		for (Menu menu : list) {
			MenuVo menuVo = new MenuVo();
			CopyBean(menu,menuVo,true);
			setRouterChildren(menuVo,menu);
			res.add(menuVo);
		}
		return res;
	}



	private void setRouterChildren(MenuVo menuVo,Menu menu){
		LambdaQueryWrapper<Menu> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(Menu::getMenuType,"C");
		wrapper.eq(Menu::getParentId,menu.getId());
		List<Menu> list = list(wrapper);
		List<MenuVo> menuVos=new ArrayList<>();
		for (Menu menu1 : list) {
			MenuVo vo = new MenuVo();
			CopyBean(menu1,vo,false);
			menuVos.add(vo);
		}
		menuVo.setChildren(menuVos);
	}
	private void CopyBean(Menu menu,MenuVo vo,boolean isDir){
		vo.setComponent(menu.getComponent());
		vo.setName(menu.getMenuName());
		vo.setMeta(menu.getMenuName(),menu.getIcon(),menu.isHidden());
		vo.setPath(menu.getPath());
		if(isDir){
			vo.setAlwaysShow(true);
			vo.setRedirect("noRedirect");
		}
	}
}
