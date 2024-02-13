package com.hnust.myblog.Controller;

import com.hnust.myblog.Mode.ResponseResult;
import com.hnust.myblog.Mode.Vo.CategoryList;
import com.hnust.myblog.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;


	@GetMapping("/getCategoryList")
	public ResponseResult getCategoryList(){
		List<CategoryList> res= categoryService.getCategoryList();
		return ResponseResult.response(res);
	}

}
