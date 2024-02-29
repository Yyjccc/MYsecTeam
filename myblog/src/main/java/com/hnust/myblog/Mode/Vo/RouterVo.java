package com.hnust.myblog.Mode.Vo;

import com.hnust.myblog.Mode.Base.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterVo {
	List<Menu> menus;
}
