package com.hnust.myblog.Controller;

import com.hnust.myblog.Mode.Base.FriendLink;
import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Service.interfaces.FriendLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/link")
public class LinkController {

	@Autowired
	private FriendLinkService friendLinkService;

	@GetMapping("/getAllLink")
	public ResponseResult getAllLink(){
		List<FriendLink> links= friendLinkService.getAllLink();
		return ResponseResult.response(links);
	}

}
