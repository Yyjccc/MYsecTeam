package org.hnust.MYSec.Mode;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hnust.MYSec.Mode.Base.Student;


import java.util.Objects;


@Data
@NoArgsConstructor
@TableName("`ctfuser`")
public class CTFUser {
    //
    @TableId
    private Long id;

    //用户名
    private String username;
    //账号密码
    private String password;
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

}
