package com.hnust.myblog.Mode.Vo;

import com.hnust.myblog.Mode.Base.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryList {
	private Long id;
	private Long pid;
	private String name;

	public CategoryList(Category category){
		this.id=category.getId();
		this.pid=category.getPid();
		this.name=category.getCategoryName();
	}
}
