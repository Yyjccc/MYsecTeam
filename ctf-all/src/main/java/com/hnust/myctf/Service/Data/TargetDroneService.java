package com.hnust.myctf.Service.Data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import com.hnust.myctf.Mode.Base.Exception.ArgsError;
import com.hnust.myctf.Mode.Base.Exception.DataError;
import com.hnust.myctf.Mode.Mapper.TargetDroneMapper;
import com.hnust.myctf.Mode.TargetDrone;
import com.hnust.myctf.Service.File.CommonUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Data

@Service
//管理靶机
public class TargetDroneService {


    private TargetDroneMapper targetDroneMapper;

    private List<String> argList;

    @Autowired
    private CommonUpload commonUpload;

    //初始化获取参数列表
    @Autowired
    public TargetDroneService(TargetDroneMapper targetDroneMapper){
        this.targetDroneMapper=targetDroneMapper;
        Class c= new TargetDrone().getClass();
        Field[] fields=c.getDeclaredFields();
        this.argList=new ArrayList<>();
        for(Field field:fields){
            this.argList.add(field.getName());
        }
    }


    //初始化,检查数据库是否与现存的靶机文件同步
    public void init(){

    }



    public  List<TargetDrone> getAll(){
        return targetDroneMapper.selectAll();
    }


    //根据分类查询
    public List<TargetDrone> getAllByCategory(String category){
        return targetDroneMapper.selectList(EqWrapperGenerator("category", category));
    }


    //分类有限查询
    public List<TargetDrone> getAllByCategory(String category,int limit){

        return null;
    }


    //条件查询,搜索




    //增加数据
    @Transactional
    public Long addTarget(TargetDrone targetDrone){
        if(targetDrone.getName()==null|| targetDrone.getCategory()==null){
            throw new ArgsError("缺少必要参数");
        }
        if(checkExist(targetDrone.getName())){
            throw new DataError(targetDrone.getName()+" 已存在");
        }
        targetDroneMapper.insert(targetDrone);
        return targetDrone.getId();
    }


    //删除
    public void deleteTarget(String name){
        if(!checkExist(name)){
            throw new DataError(name+"不存在");
        }
        targetDroneMapper.delete(EqWrapperGenerator("name",name));
    }

    //修改
    public void updateTarget(String type,String value,Long id) throws NoSuchFieldException, IllegalAccessException {
        if(!argList.contains(type)){
            throw new ArgsError("参数错误");
        }else {
            TargetDrone drone = targetDroneMapper.selectById(id);
            if(drone==null){
                throw new DataError("id错误");
            }
            if(type.equals("attachment")){
                drone.setAttachment(Boolean.parseBoolean(value));
            } else if (type.equals("docker")) {
                drone.setDocker(Boolean.parseBoolean(value));
            } else if (type.equals("category")) {
                updateCategory(drone,value);
            } else {
                //反射修改值
                Class<?> clazz = drone.getClass();
                Field field = clazz.getDeclaredField(type);
                field.setAccessible(true);
                field.set(drone, value);
            }
            targetDroneMapper.updateById(drone);
        }

    }

    public boolean checkExist(String name){
        int count = targetDroneMapper.count(name);
        if (count > 0) {
            return true;
        }
        return false;
    }

    //创建条件构造器,某个字段等于某个值的条件
    private QueryWrapper<TargetDrone> EqWrapperGenerator(String type,Object value){
        if(argList.contains(type)){
            QueryWrapper<TargetDrone> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(type,value);
            return queryWrapper;
        }else {
            //感觉是多余代码
            throw new ArgsError("参数错误");
        }
    }

    //特殊情况，更新category考虑靶机位置移动
    private void updateCategory(TargetDrone targetDrone,String value){
        //如果存在靶机
        if(targetDrone.isDocker()){
            String src=targetDrone.getCategory()+"\\"+targetDrone.getId();
            commonUpload.moveDockerFolder(src,value+"\\"+targetDrone.getId());
        }
        targetDrone.setCategory(value);
        targetDroneMapper.updateById(targetDrone);
    }


}

