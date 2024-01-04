package org.hnust.MYSec;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.hnust.MYSec.Mapper")
public class MySecApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySecApplication.class, args);
    }

}
