package org.hnust.MYSec.Service.Data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.hnust.MYSec.Mode.Base.Exception.ArgsError;
import org.hnust.MYSec.Mode.Mapper.TargetDroneMapper;
import org.hnust.MYSec.Mode.TargetDrone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

@Data

@Service
//管理靶机
public class TargetDroneService {

    @Autowired
    private TargetDroneMapper targetDroneMapper;

    private List<String> argList;

    //初始化获取参数列表
    public TargetDroneService(){
        Class c= TargetDrone.class;
        Field[] fields=c.getFields();
        for(Field field:fields){
            this.argList.add(field.getName());
        }
    }


    //初始化,检查数据库是否与现存的靶机文件同步
    public void init(){

    }

    public List<TargetDrone> getAllByCategory(String category){
        if(argList.contains(category)){
            throw new ArgsError("参数错误 category:"+category);
        }else {
            QueryWrapper<TargetDrone> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("category", category);
            return targetDroneMapper.selectList(queryWrapper);
        }
    }

}

