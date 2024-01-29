package com.hnust.myctf.Service.DockerAPI.Mode;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 *  属性注释：
 *  status： 容器状态，例如 "Up Less than a second"     <br/>
 *  mounts   容器挂载信息列表                           <br/>
 *  ports   容器端口映射信息列表                         <br/>
 *  labels  容器标签信息                               <br/>
 *  image   容器使用的镜像                             <br/>
 *  created 容器创建时间戳                             <br/>
 *  names   容器名称列表                               <br/>
 *  networkSettings 容器网络设置信息                    <br/>
 *  command  容器启动命令                              <br/>
 *  state   容器状态，例如 "running"                   <br/>
 *  imageID 容器使用的镜像ID                           <br/>
 *  hostConfig  容器宿主机配置信息                      <br/>
 *  id  容器ID                                       <br/>
 * */
@Data
public class Container implements Serializable {

    private String status;
    private List<Mount> mounts;
    private List<Port> ports;
    private Label labels;
    private String image;
    private long created;
    private List<String> names;
    private NetworkSettings networkSettings;
    private String command;
    private String state;
    private String imageID;
    private HostConfig hostConfig;
    private String id;

//    public String getId(){
//       // String realId = id.substring(0, 12);
//        return Id;
//    }
}

/**
 *  属性：                             <br/>
 *  destination     目标路径            <br/>
 *  type    挂载类型                    <br/>
 *  propagation 传播属性                <br/>
 *  RW  是否可读写                       <br/>
 *  mode    挂载模式                    <br/>
 *  source  源路径                     <br/>
 */
@Data
class Mount {
    private String destination;
    private String type;
    private String propagation;
    private boolean RW;
    private String mode;
    private String source;

}

/**
 * 属性           <br/>
 * type  端口类型   <br/>
 * IP   IP地址       <br/>
 * privatePort  内部端口    <br/>
 * publicPort   公共端口
 */
@Data
class Port {
    private String type;
    private String IP;
    private int privatePort;
    private int publicPort;
}

@Data
class NetworkSettings {
    /**
     * 网络信息映射
     */
    private Map<String, Network> networks;

}

@Data
class Network {
    /**
     * 全局IPv6地址
     */
    private String globalIPv6Address;
    /**
     * IP前缀长度
     */
    private int ipPrefixLen;
    /**
     * 全局IPv6前缀长度
     */
    private int globalIPv6PrefixLen;
    /**
     * 网络ID
     */
    private String networkID;
    /**
     * MAC地址
     */
    private String macAddress;
    /**
     * 网关
     */
    private String gateway;
    /**
     * 终端点ID
     */
    private String endpointID;
    /**
     * IPv6网关
     */
    private String IPv6Gateway;
    /**
     * IP地址
     */
    private String ipAddress;
}

@Data
class HostConfig {
    /**
     * 网络模式
     */
    private String networkMode;
}

