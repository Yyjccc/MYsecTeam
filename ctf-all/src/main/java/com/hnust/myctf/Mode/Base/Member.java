package com.hnust.myctf.Mode.Base;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Member extends CTFUser {
    //主要方向
    private String mainTarget;
    //积分
    private int Core;
    //最近活跃时间
    private Data activeTime;
    //

}
