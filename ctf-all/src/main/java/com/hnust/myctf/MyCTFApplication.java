package com.hnust.myctf;

import com.hnust.myctf.Configure.MYsecConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Import;



@SpringBootApplication
@MapperScan("com.hnust.myctf.Mode.Mapper")
@Import(MYsecConfig.class)
public class MyCTFApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCTFApplication.class, args);
    }



}
