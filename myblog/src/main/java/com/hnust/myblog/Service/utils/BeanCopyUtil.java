package com.hnust.myblog.Service.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {
	public static <V> V copyBean(Object source, Class<V> clazz) {
		V result = null;
		try {
			 result = clazz.newInstance();
			BeanUtils.copyProperties(source,result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static <T,V> List<V> copyBeanList(List<T> list, Class<V> clazz){
		List<V> res = list.stream()
				.map(o -> copyBean(o, clazz))
				.collect(Collectors.toList());
		return res;
	}
}