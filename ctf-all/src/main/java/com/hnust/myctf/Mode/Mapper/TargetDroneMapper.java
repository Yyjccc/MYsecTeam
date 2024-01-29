package com.hnust.myctf.Mode.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.hnust.myctf.Mode.TargetDrone;

import java.util.List;

public interface TargetDroneMapper extends BaseMapper<TargetDrone> {

	@Select("SELECT * FROM targetdrone")
	List<TargetDrone> selectAll();

	@Select("SELECT count(id) FROM targetdrone WHERE name = #{username}")
	int count(@Param("username") String username);
}
