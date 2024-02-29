package com.hnust.myblog.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hnust.myblog.Mode.Base.FriendLink;
import com.hnust.myblog.Mode.Mapper.FriendLinkMapper;
import com.hnust.myblog.Service.interfaces.FriendLinkService;
import com.hnust.myblog.Service.utils.SystemConstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

	@Override
	public List<FriendLink> getAllLink() {
		LambdaQueryWrapper<FriendLink> wrapper=new LambdaQueryWrapper<>();
		wrapper.eq(FriendLink::getStatus, SystemConstance.LINK_NORMAL_STATUS);
		List<FriendLink> linkList = list(wrapper);
		return linkList;
	}
}
