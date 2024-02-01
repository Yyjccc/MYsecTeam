package com.hnust.myctf.Service.DockerAPI;

import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.hnust.myctf.Configure.RemoteHost;
import com.hnust.myctf.Service.DockerAPI.Mode.Container;
import com.hnust.myctf.Utils.Http;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ContainerAPI {

    private static final String getIdCommand="sudo docker ps -q --latest";

    public static final Logger logger= LoggerFactory.getLogger(ContainerAPI.class);

    //获取所有容器
    public static List<Container> getAllContainer(RemoteHost remoteHost){
        List<Container> containers=null;
        String url=remoteHost.dockerApiHost+"/containers/json";
        containers=JSON.parseArray(Http.doGet(url),Container.class);
        return containers;
    }

    //获取单个docker容器实例信息
    public  static Container getContainerInfo(RemoteHost host,String containerName){
        List<Container> containers=getAllContainer(host);
        for (Container container:containers){
            if(container.getLabels().getProject().equals(containerName)){
                return container;
            }
        }
        return null;
    }

    //启动一个容器
    public static String createContain(String composeFilePath,String contaierName,int index) throws IOException, InterruptedException {
        try {

            // 定义 Docker Compose 文件路径
            String composeFile = composeFilePath + "/docker-compose.yml";
            // 构建 Docker Compose 命令
            ProcessBuilder processBuilder = new ProcessBuilder("sudo docker-compose","-p",contaierName, "-f", composeFile, "up", "-d");
            // 设置工作目录（Docker Compose 文件所在的目录）
            processBuilder.directory(new java.io.File(composeFilePath));
            // 启动 Docker Compose
            Process process = processBuilder.start();
            // 等待 Docker Compose 进程执行完毕
            int exitCode = process.waitFor();

            // 输出执行结果
            if (exitCode == 0) {
                logger.debug("in "+composeFile+" a docker start");
            } else {
                logger.debug("Error starting Docker "+composeFile+". Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        try{
        // 获取最后启动的容器 ID
        Process process = new ProcessBuilder("docker", "ps", "-q", "--latest")
                .redirectErrorStream(true)
                .start();
        // 读取容器 ID
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String containerId = reader.readLine().trim();
        // 等待命令执行完毕
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return containerId;
        } else {
            System.out.println("Failed to get Container ID.");
        }
    } catch (IOException | InterruptedException e) {
        e.printStackTrace();
    }
        return null;
}
    //开启远程的docker


    /**
     *
     * @param remoteHost            远程主机对象
     * @param remoteComposeDirectory  docker-compose.yaml所在目录
     * @param containerName         容器名称
     * @param index             启动容器的索引
     * @return
     */

    public static  String createContain(RemoteHost remoteHost, String remoteComposeDirectory,String containerName,int index){

            try {
                // 使用 JSch 创建 SSH 会话
                Session session=connectSSH(remoteHost);
                // 远程 Docker Compose 文件路径
                String remoteComposeFilePath = remoteComposeDirectory+"/docker-compose.yml";
                // 构建 Docker Compose 启动命令
                String startCommand = "sudo docker-compose  -p "+containerName+"  -f " + remoteComposeFilePath + " up -d";
                 String result =executeRemoteCommand(session,startCommand);
                 String id=executeRemoteCommand(session,getIdCommand);
                 session.disconnect();
                // 输出执行结果
                logger.debug(result);
                return id;
            } catch (  com.jcraft.jsch.JSchException e) {
                e.printStackTrace();
            }
            return null;
    }


    //关闭容器,判断是否成功，放置在上面一层封装
    public static void stopContainer(String containerId)  {
        try{
            // 构建 Docker 命令
            ProcessBuilder processBuilder = new ProcessBuilder("sudo","docker", "stop", containerId);
            // 启动 Docker 命令
            Process process = processBuilder.start();
            // 读取命令执行的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            // 等待 Docker 命令执行完毕
            int exitCode = process.waitFor();
            // 输出执行结果
            if (exitCode == 0) {
               logger.debug(output.toString());

            } else {
               logger.debug("Error stopping container. Exit code: " + exitCode);
                logger.debug( output.toString());
            }
        } catch (Exception e) {
        e.printStackTrace();
        }

    }

    //关闭远程的docker
    public static void stopContainer(RemoteHost remoteHost,String id){
        try {
            // 远程 Docker 主机的 SSH
           Session session=connectSSH(remoteHost);
            //Docker 停止命令
            String stopCommand = "sudo docker stop "+id;
            executeRemoteCommand(session,stopCommand);
            session.disconnect();
            // 输出执行结果
           logger.debug("Docker Compose containers on remote host stopped successfully.");
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    //连接ssh
    public static Session connectSSH(RemoteHost remoteHost) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(remoteHost.username, remoteHost.host, 22);
        session.setConfig("StrictHostKeyChecking", "4"); // 跳过 Host Key 检查
        session.setPassword(remoteHost.password);
        session.connect();
        return session;
    }

    //使用ssh执行命令
    public static String executeRemoteCommand(Session session, String command) {
        try {
            // 打开远程执行通道
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            // 获取执行结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream()));
            channel.connect();
            String result = reader.readLine();
            // 等待命令执行完毕
            channel.disconnect();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
	        throw new RuntimeException(e);
        }
	    return null;
    }
}