package com.hnust.myblog.Mode.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hnust.myblog.Mode.Base.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
	@Select("select id,parent_id,menu_name,path,component,visible,status,IFNULL(perms,'') from menu where parent_id=0")
	List<Menu> getRootRouters();

}
