package com.hnust.myblog.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hnust.myblog.Mode.Base.Article;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Base.Tag;
import com.hnust.myblog.Mode.Base.User;
import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.Vo.AdminUserInfoVo;
import com.hnust.myblog.Mode.Vo.MenuVo;

import com.hnust.myblog.Mode.Vo.admin.ArticleListVo;
import com.hnust.myblog.Mode.Vo.admin.Count;
import com.hnust.myblog.Other.annocation.SystemLog;

import com.hnust.myblog.Service.CountService;
import com.hnust.myblog.Service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private CountService countService;


	@Autowired
	private TagService tagService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/report")
	public ResponseResult report(){
		return ResponseResult.response();
	}

	@SystemLog(ServiceName = "后台登录接口")
	@PostMapping("/login")
	public ResponseResult adminLogin(@RequestBody User user){
		try {
			String token = userService.AdminLogin(user);
			return ResponseResult.response(token);
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}

	@SystemLog(ServiceName = "后台系统退出登录接口")
	@GetMapping("/logout")
	public ResponseResult logout(@RequestHeader("token") String token){
		try {
			userService.logout(userService.parseToken(token),true);
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}


	@GetMapping("/user/getUserInfo")
	public ResponseResult getUserInfo(@RequestHeader("token") String token){
		try {
			AdminUserInfoVo adminUserInfo = userService.getAdminUserInfo(userService.parseToken(token));
			return ResponseResult.response(adminUserInfo);
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}

	}

	@GetMapping("/user/getUserMenu")
	public ResponseResult getUserMenu(@RequestHeader("token") String token){
		try {
			String id=userService.parseToken(token);
			List<MenuVo> menuVos= menuService.getUserMenu(id);
			return ResponseResult.response(menuVos);
		}catch (Exception e){
			return ResponseResult.error(e, BlogUserController.class);
		}
	}

	@GetMapping("")
	public ResponseResult admin(@RequestHeader("token") String token){
		try {
			String id=userService.parseToken(token);
			Count count= countService.index(id);
			return ResponseResult.response(count);
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}
	}

	@GetMapping("/tag/option")
	public ResponseResult tagOption(){
		try {
			List<Tag> list = tagService.list().stream().
					map(tag -> {
						tag.setCreateBy(null);
						tag.setUpdateTime(null);
						tag.setCreateTime(null);
						return tag;
					}).collect(Collectors.toList());
			return ResponseResult.response(list);
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}
	}

	@GetMapping("/category/option")
	public ResponseResult categoryOption(){
		try {
			List<Category> list = categoryService.list().stream()
					.map(i->{
						Category category = new Category(i.getId(), i.getCategoryName());
						return category;
					})
					.collect(Collectors.toList());
			return ResponseResult.response(list);
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}
	}


	@PostMapping("/article/add")
	public ResponseResult addArticle(@RequestBody Article article,@RequestHeader("Token") String token){
		try {
			article.setUserId(Long.parseLong(userService.parseToken(token)));
			article.setUpdateTime(new Date());
			article.setCreateTime(new Date());
			LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
			wrapper.eq(Category::getCategoryName,article.getCategoryName());
			article.setCategoryId(categoryService.getBaseMapper().selectOne(wrapper).getId());
			articleService.getBaseMapper().insert(article);
			if(!article.getTagNameList().isEmpty()){
				for (String s : article.getTagNameList()) {
					Long tagId=tagService.getIdByName(s);
					tagService.addArticleTag(tagId,article.getId());
				}
			}
			return ResponseResult.response();
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}
	}

	@GetMapping("/article/list")
	public ResponseResult getArticleList(@RequestHeader("token") String token){
		try {
			List<Article> articles = articleService.getListByUserId(userService.parseToken(token));
			for (Article article : articles) {
				articleService.setTagList(article);
			}
			ArticleListVo vo = new ArticleListVo(articles);
			return ResponseResult.response(vo);
		}catch (Exception e){
			return ResponseResult.error(e, AdminController.class);
		}
	}


	@GetMapping("/article/edit/{articleId}")
	public ResponseResult AdminEditArticle(@PathVariable Long articleId){
		try {
			Article article= articleService.getAdminArticle(articleId);
			return ResponseResult.response(article);
		}catch (Exception e){
			return ResponseResult.error(e,AdminController.class);
		}
	}
}
