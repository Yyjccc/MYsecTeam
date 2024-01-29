package com.hnust.myctf.Service.DockerAPI;

import lombok.Data;
import com.hnust.myctf.Configure.RemoteHost;
import com.hnust.myctf.Mode.CTFUser;
import com.hnust.myctf.Service.DockerAPI.Mode.Container;
import com.hnust.myctf.Service.DockerAPI.Mode.Label;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Data
@Component
@Service
public class DockerManger {

    public String mode;

    @Autowired
    public RemoteHost remoteHost;

    private static final Logger logger = LoggerFactory.getLogger(DockerManger.class);
    public List<Container> activeDocker;
    /**
     * 状态级别:                    <br/>
     * -1 初始化失败，或未初始化       <br/>
     * 1    正常                  <br/>
     * 2    系统超负荷状态         <br/>
     */
    public short status;

    public  HashMap<CTFUser, Container> userDockerMap;




    public DockerManger(RemoteHost remoteHost){
        this.remoteHost=remoteHost;
        try {
            this.activeDocker = ContainerAPI.getAllContainer(remoteHost);
        }catch (Exception e){
            this.status=-1;
           logger.error("docker 服务器错误，请检查");
        }
        this.status=1;
        this.userDockerMap=new HashMap<>();

        this.mode=remoteHost.mode;
    }

    public synchronized void addContainer(CTFUser ctfUser,String path){
        //计算出第几个实例
        int count=getCurrentContainerCount(path)+1;
        String containerId=null;
        //容器名字
        String containerName=path.replace("/","-")+"_"+count;
        try {
            if(mode.equals("local")){
                containerId=ContainerAPI.createContain(path,containerName,count);
            } else if (mode.equals("remote")) {
                containerId=ContainerAPI.createContain(remoteHost,path,containerName,count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(containerId!=null){
            activeDocker=ContainerAPI.getAllContainer(remoteHost);
            Container container=ContainerAPI.getContainerInfo(remoteHost,containerName);
            if(container!=null){
                userDockerMap.put(ctfUser,container);
            }else {
                closeContainer(ctfUser);
                throw new RuntimeException("获取容器信息失败");
            }

            logger.info("用户"+ctfUser.getUsername()+"成功启动一个容器,id为： "+containerId);
        }else {
            logger.error("启动容器出错");

        }
    }

    //判断该用户是否开启容器校验，放在控制器部分
    /**
     * 关闭容器实例
     * @param ctfUser
     * @return bool
     */
    public boolean closeContainer(CTFUser ctfUser){
            Container container=userDockerMap.get(ctfUser);


            if(mode.equals("local")){
                ContainerAPI.stopContainer(container.getId());
            }else if(mode.equals("remote")) {
                ContainerAPI.stopContainer(remoteHost,container.getId());
            }
            //跟新状态
           updateList();
            if(!activeDocker.contains(container)){
               userDockerMap.remove(ctfUser);
                return true;
            }
            return false;
    }


    public void closeAllContainer(){
        if(mode.equals("local")){
            for (Container container:activeDocker) {
                ContainerAPI.stopContainer(container.getId());
            }
        } else if (mode.equals("remote")) {
            for (Container container:activeDocker) {
                ContainerAPI.stopContainer(remoteHost,container.getId());
            }
        }
        activeDocker=ContainerAPI.getAllContainer(remoteHost);
        userDockerMap=new HashMap<>();

    }


    public List<Container> getAllContainer(){
        activeDocker=ContainerAPI.getAllContainer(remoteHost);
        return activeDocker;
    }


    //获取同一个题目的开启的实例个数
    public int getCurrentContainerCount(String dockerPath){
        int count=0;
        // 获取HashMap的值集合
        Collection<Container> values = userDockerMap.values();
        // 或者使用增强for循环遍历值集合
        for (Container container : values) {
            Label labels = container.getLabels();
            if(labels.getProjectWorkingDir().equals(dockerPath)){
                count+=1;
            }
        }
        return count;
    }

    //根据用户名获取靶机
//    public Container getContainer(CTFUser ctfUser){
//        for(Map.Entry<CTFUser,Container> entry:userDockerMap.entrySet()){
//            if(entry.getKey().getUsername().equals(ctfUser.getUsername())){
//                return entry.getValue();
//            }
//        }
//        return null;
//    }
    //检查容器实例是否存在
    private boolean searchContainer(String id){
        for(Container container:userDockerMap.values()){
            if(container.getId().equals(id)){
                return true;
            }
        }
        for(Container container:activeDocker){
            if(container.getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    /**检查用户是否经开启一个靶机
     * 如果已开启返回 true
     * @param user
     * @return
     */
    public boolean checkExistUser(CTFUser user){
        return userDockerMap.containsKey(user);
    }

    //更新状态
    public void updateList(){
        this.activeDocker=ContainerAPI.getAllContainer(remoteHost);
    }

}
