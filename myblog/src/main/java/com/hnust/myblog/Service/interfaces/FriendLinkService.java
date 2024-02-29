package com.hnust.myblog.Service.interfaces;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.FriendLink;

import java.util.List;

public interface FriendLinkService extends IService<FriendLink> {
	List<FriendLink> getAllLink();
}
