package com.hnust.myblog.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hnust.myblog.Mode.Base.Category;
import com.hnust.myblog.Mode.Vo.CategoryList;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService extends IService<Category> {
	List<CategoryList> getCategoryList();
}
