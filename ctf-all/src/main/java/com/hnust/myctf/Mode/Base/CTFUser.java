package com.hnust.myctf.Mode.Base;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.hnust.myctf.Utils.HashUtil;

import java.util.Objects;


@Data
@NoArgsConstructor
@TableName("`ctfuser`")
public class CTFUser {

    @TableId
    private Long id;

    //用户名
    private String username;
    //账号密码
    private String password;

    //密码加盐
    private String salt;
    //是否为校内成员
    @TableField("`inner`")
    private boolean inner;
    //邮箱
    private String email;

    private Long studentInfoId;

    @TableField(exist = false)
    //校内成员信息
    private Student studentInfo;

    //是否为管理员
    private boolean manager;

    private boolean avatar=false;

    // 重写hashCode和equals方法,
    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CTFUser ctfUser = (CTFUser) obj;
        return Objects.equals(username, ctfUser.username);
    }
    //保留测试数据，没有salt就是明文
    public boolean checkPassword(String value){
        if (salt!=null){
            return HashUtil.hashPassword(value,salt).equals(password);
        }else {
            return this.getPassword().equals(value);
        }
    }

    //设置密码时候一定是先生成salt,再设置密码，保留测试数据，没有salt就是明文
    public void setPassword(String password) {
        if(salt==null){
            this.password = password;
        }else {
            this.password=HashUtil.hashPassword(password,salt);
        }
    }

    public void HashPassword(){
        String raw=password;
        this.setSalt(HashUtil.generateSalt());
        this.setPassword(raw);
    }
}
