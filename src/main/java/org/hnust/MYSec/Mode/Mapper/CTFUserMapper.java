package org.hnust.MYSec.Mode.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.hnust.MYSec.Mode.CTFUser;

import java.util.List;


public interface CTFUserMapper extends BaseMapper<CTFUser> {

	@Select("SELECT id FROM ctfuser WHERE username = #{username}")
	Long findIdByUsername(@Param("username") String username);

	@Select("SELECT * FROM ctfuser WHERE username = #{username}")
	List<CTFUser> findUserByName(@Param("username") String username);

	@Delete("delete * FROM ctfuser WHERE username= #{username}")
	Boolean deleteByName(@Param("username") String username);

}
