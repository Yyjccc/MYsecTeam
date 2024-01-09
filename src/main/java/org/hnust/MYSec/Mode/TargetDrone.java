package org.hnust.MYSec.Mode;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
@Data
@NoArgsConstructor
@TableName("targetDrone")
//靶机
public class TargetDrone {
    //靶机id
    @TableId
    private Long id;
    //题目名字
    private String name;
    //描述
    private  String description;
    //附件地址
    private String attachment;
    //提示
    private String[] hints;
    //难度等级
    private int level;
    //wp地址
    private String[] wp;
    //解出的用户
    private String[] solvedUser;
    //分类
    private String category;

    //标签
    private String[] labels;

    //来源
    private String source;

    //靶机地址
    private String target;

    //上传日期
    private Date  date;

}
