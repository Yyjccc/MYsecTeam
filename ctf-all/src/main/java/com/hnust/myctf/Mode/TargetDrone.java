package com.hnust.myctf.Mode;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@TableName("targetdrone")
//靶机
public class TargetDrone {
    //靶机id
    @TableId
    private Long id;
    //题目名字
    private String name;
    //描述
    private  String description;
    //是否存在附件地址
    private boolean attachment;
    //是否存在靶机
    private boolean docker;
    //提示，数组类型json字符串
    private String hints;
    //难度等级
    private int level;
    //wp地址,数组类型json字符串
    private String wp;
    //解出的用户，数组类型json字符串
    private String solvedUser;
    //分类
    private String category;

    //标签，数组类型json字符串
    private String labels;

    //提供者的id
    private Long provider;
    //来源
    private String source;

    //上传日期
    private String  date;

}
