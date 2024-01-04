package org.hnust.MYSec.Utils;

import org.hnust.MYSec.Mapper.CTFUserMapper;
import org.hnust.MYSec.Mode.CTFUser;
import org.hnust.MYSec.Service.DockerAPI.ContainerAPI;

import org.hnust.MYSec.Service.DockerAPI.DockerManger;

import java.util.List;

public class test {
    public static void main(String[] args) {
        CTFUserMapper ctfUserMapper=null;
            List<CTFUser> ctfUsers=ctfUserMapper.selectList(null);
            ctfUsers.forEach(System.out::println);

    }
}
