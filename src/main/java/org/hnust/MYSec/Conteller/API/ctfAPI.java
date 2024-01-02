package org.hnust.MYSec.Conteller.API;



import com.alibaba.fastjson.JSON;
import org.hnust.MYSec.Mode.CTFUser;
import org.hnust.MYSec.Mode.Message;
import org.hnust.MYSec.Service.DockerAPI.DockerManger;
import org.hnust.MYSec.Service.DockerAPI.Mode.Container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ctf")
public class ctfAPI {
    private final DockerManger dockerManger;

    //由spring自动注入
    @Autowired
    private Message message;


    private String basePath;

    @Autowired
    public ctfAPI(DockerManger dockerManger) {
        this.dockerManger = dockerManger;
        this.basePath=dockerManger.remoteHost.baseurl;
    }


    //获取所有活跃的靶机
    @GetMapping(value = "docker/all",produces = { "application/json"})
    public String getall(){
       List<Container> containers=dockerManger.getAllContainer();
        return JSON.toJSONString(containers);
    }
    //获取某个靶机的运行状态
    @PostMapping("docker/query")
    public String query(){
        return null;
    }

    //开启靶机,测试接收参数@ModelAttribute CTFUser ctfUser改为了username
    @GetMapping (value = "docker/start",produces = { "application/json"})
    public String startDocker(String username,String path){
        //测试代码部分
        CTFUser ctfUser=new CTFUser();
        ctfUser.setUsername(username);
        //test 只检查了username
        if(dockerManger.checkExistUser(ctfUser)){
            message.setInfo("一个用户只能开启一台靶机");
            message.setFail();
            return  JSON.toJSONString(message);
        }
        dockerManger.addContainer(ctfUser,basePath+path);
        //Container container=dockerManger.userDockerMap.get(ctfUser);
        //测试，只拿username相同的
        Container container=dockerManger.userDockerMap.get(ctfUser);
        if(container==null){
            message.setInfo("启动靶机失败");
            message.setFail();
            return  JSON.toJSONString(message);
        }else {
        message.setOK();
        message.setInfo(container);
        return JSON.toJSONString(message);
        }
    }

    @GetMapping (value = "docker/stop",produces = { "application/json"})
    //关闭靶机,测试接收参数@ModelAttribute CTFUser ctfUser改为了username
    public String stopDocker(String username){
        //测试代码
        CTFUser ctfUser=new CTFUser();
        ctfUser.setUsername(username);
        //
        //判断是否存在靶机,同上test
        if (dockerManger.checkExistUser(ctfUser)){
            if(dockerManger.closeContainer(ctfUser)){
                message.setOK();
                message.setInfo("关闭靶机成功");
                return JSON.toJSONString(message);
            }else {
                message.setInfo("关闭靶机失败");
            }
        }else {
            message.setInfo("用户未开启该靶机");
        }
        message.setFail();
        return JSON.toJSONString(message);
    }

    //需要鉴定管理员权限
    @GetMapping(value = "docker/stopAll",produces = { "application/json"})
    public String stopAllContainer(){
        dockerManger.closeAllContainer();
        if(dockerManger.activeDocker.isEmpty()){
            message.setOK();
            message.setInfo("关闭所有靶机成功");
            return JSON.toJSONString(message);
        }
         message.setInfo("关闭所有靶机失败");
        message.setFail();
        return JSON.toJSONString(message);
    }

    //创建队伍
    @PostMapping("/create")
    public String createTeam(){
        return null;
    }
}
