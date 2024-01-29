package com.hnust.myctf.Controller.Data;


import com.alibaba.fastjson.JSON;
import com.hnust.myctf.Mode.CTFUser;
import com.hnust.myctf.Mode.Message;
import com.hnust.myctf.Service.DockerAPI.DockerManger;
import com.hnust.myctf.Service.DockerAPI.Mode.Container;
import com.hnust.myctf.Utils.ResposeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/data/docker",produces = { "application/json"})
public class Docker {
    private final DockerManger dockerManger;


    private String basePath;

    @Autowired
    public Docker(DockerManger dockerManger) {
        this.dockerManger = dockerManger;
        this.basePath=dockerManger.remoteHost.baseurl;
    }


    //获取所有活跃的靶机
    @GetMapping(value = "/all")
    public String getall(){
       List<Container> containers=dockerManger.getAllContainer();
        return JSON.toJSONString(containers);
    }
    //获取某个靶机的运行状态
    @PostMapping("/query")
    public String query(){
        return null;
    }

    //开启靶机,测试接收参数@ModelAttribute CTFUser ctfUser改为了username
    @GetMapping (value = "/start/{username}/{path}")
    public Message startDocker(@PathVariable String username, @PathVariable String path){

        //测试代码部分
        CTFUser ctfUser=new CTFUser();
        ctfUser.setUsername(username);
        //test 只检查了username
        if(dockerManger.checkExistUser(ctfUser)){
            return ResposeUtil.error("用户只能开启一个容器");
        }
        dockerManger.addContainer(ctfUser,basePath+path);
        //Container container=dockerManger.userDockerMap.get(ctfUser);
        //测试，只拿username相同的
        Container container=dockerManger.userDockerMap.get(ctfUser);
        if(container==null){

            return  ResposeUtil.error("启动靶机失败");
        }else {
            return ResposeUtil.response(container);
        }
    }

    @GetMapping (value = "/stop")
    //关闭靶机,测试接收参数@ModelAttribute CTFUser ctfUser改为了username
    public Message stopDocker(String username){
        //测试代码
        CTFUser ctfUser=new CTFUser();
        ctfUser.setUsername(username);
        //
        //判断是否存在靶机,同上test
        if (dockerManger.checkExistUser(ctfUser)){
            if(dockerManger.closeContainer(ctfUser)){
                return ResposeUtil.response();
            }else {
               return ResposeUtil.error("关闭靶机失败");
            }
        }else {
           return ResposeUtil.error("用户未开启该靶机");
        }
    }

    //需要鉴定管理员权限
    @GetMapping(value = "/stopAll")
    public Message stopAllContainer(){
        dockerManger.closeAllContainer();
        if(dockerManger.activeDocker.isEmpty()){
            return ResposeUtil.response();
        }
        return ResposeUtil.error("关闭所有靶机失败");
    }
}
