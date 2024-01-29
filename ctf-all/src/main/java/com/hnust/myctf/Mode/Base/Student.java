package com.hnust.myctf.Mode.Base;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName("students")
public class Student {

    @TableId
    private Long id;

    //真实姓名
    private String realName;
    //年级
    private int age;

    //学院
    private String department;
    //专业
    private String major;
    //方向
    private String direction;

    //QQ号
    private String qq;


}
