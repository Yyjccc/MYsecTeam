package com.hnust.myctf.Configure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@Configuration
public class RemoteHost {
    @Value("${docker.remote.host}")
    public  String host ;

    @Value("${docker.remote.username}")
    public  String username ;

    @Value("${docker.remote.password}")
    public  String password ;

    @Value("${docker.service.api}")
    public  String dockerApiHost;
    @Value("${docker.service.mode}")
    public String mode;

    @Value("${docker.service.baseurl}")
    public String baseurl;

    public RemoteHost(){}

}
