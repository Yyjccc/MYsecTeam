package com.hnust.myctf.Utils;

import java.lang.reflect.Field;
import java.util.UUID;

public class Common {
	//反射修改实例属性
	public  static void modifyByReflect(Object o,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {
		Class c=o.getClass();
		Field field = c.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(o, value);
	}
	//反射修改静态属性
	public  static void modifyByReflect(Class c,String fieldName,Object value) throws NoSuchFieldException, IllegalAccessException {
		Field field=c.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(null,value);
	}

	//随机生成flag
	public static String getRandomFlag() {
		// 生成随机的UUID
		UUID uuid = UUID.randomUUID();

		// 将UUID转换为字符串
		String uuidString = uuid.toString();

		// 截取UUID的一部分，去掉中间的短横线
		String shortUuid = uuidString.substring(0, 8) +"-"+ uuidString.substring(9, 13) +"-"+
				uuidString.substring(14, 18) +"-"+ uuidString.substring(19, 23) +"-"+
				uuidString.substring(24);

		// 格式化字符串
		return "MYCTF{" + shortUuid + "}";
	}
}
