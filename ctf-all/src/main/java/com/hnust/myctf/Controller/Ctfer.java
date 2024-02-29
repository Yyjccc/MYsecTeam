package com.hnust.myctf.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hnust.myctf.Mode.Base.Member;
import com.hnust.myctf.Mode.Base.CTFUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctf")
public class Ctfer {

    //首页
    @RequestMapping(value = "/",produces = { "application/json"})
    public String index(){
        CTFUser ctfUser=new Member();
        return JSON.toJSONString(ctfUser, SerializerFeature.WriteMapNullValue);
    }
}
