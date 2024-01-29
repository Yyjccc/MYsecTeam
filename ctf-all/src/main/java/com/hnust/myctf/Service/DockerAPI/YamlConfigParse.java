package com.hnust.myctf.Service.DockerAPI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import com.hnust.myctf.Mode.Base.Exception.DataError;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//主要解析yaml配置文件（docker-compose.yaml）
@Data
public class YamlConfigParse {
	//yaml解析成map的数据
	private Map<String, Object> yamlMap;
	//yaml文件的路径
	private String filePath;
	//配置文件中的端口相关设置
	private String[] portsConfig;

	private FileInputStream inputStream;

	private Yaml yaml;
	//标志位
	private int flag=0;

	public YamlConfigParse(String filepath) throws IOException {
		this.filePath=filepath;
		inputStream = new FileInputStream(filepath);
		yaml = new Yaml();
		// 使用文件输入流读取YAML文件
		yamlMap = yaml.load(inputStream);
		portsConfig = getPortConfig();
	}

	//递归遍历,搜索字段
	public Collection<?> getField(Map<String,Object> map,String name) {
		List<Object> result = new ArrayList<>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (key.equals(name)) {
				// 找到目标字段，将其值添加到结果列表中
				result.add(value);
				return result;
			}
			if (value instanceof Map) {
				// 递归调用以处理嵌套的Map
				result.addAll(getField((Map<String, Object>) value,name));
			} else if (value instanceof List) {
				// 递归调用以处理嵌套的List
				for (Object item : (List<Object>) value) {
					if (item instanceof Map) {
						result.addAll(getField((Map<String, Object>) item,name));
					}
				}
			}
		}
		return result;
	}

	//获取端口字段
	private String[] getPortConfig(){
		Collection<?> portsMap = getField(yamlMap, "ports");
		Iterator<?> iterator = portsMap.iterator();
		ArrayList<String> ports=null;
		while (iterator.hasNext()){
			ports= (ArrayList<String>) iterator.next();
		}
		if(ports==null){
			return new String[]{};
		}
		return ports.toArray(new String[0]);
	}
	//修改yaml文件中指定的字段
	public void modify(String fieldName,Object value){
		Map<String,Object> res=null;
		//使用标志位标记是否成功找到
		flag=0;
		res=modify(yamlMap,fieldName,value);
		if(flag==0){
			throw new DataError("没有这个属性"+fieldName);
		}
		yamlMap=res;
		flag=0;
		saveYAMLToFile(yamlMap);
	}

	//内部递归查找
	private Map<String,Object> modify(Map<String,Object> map,String name,Object value){
		if(map.containsKey(name)){
			map.put(name,value);
			flag=1;
		}
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object v = entry.getValue();
			String k=entry.getKey();
			if(v instanceof Map){
				Map<String,Object> innerMap=(Map<String, Object>) v;
				map.put(k,modify(innerMap,name,value));
			}else {
				map.put(k,v);
			}
		}
		return map;
	}

	//将数据保存到yaml文件中
	public   void saveYAMLToFile(Map<String, Object> data) {
		try (FileWriter writer = new FileWriter(filePath)) {
			DumperOptions options = new DumperOptions();
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			options.setPrettyFlow(true);
			Representer representer = new Representer();
			representer.addClassTag(YamlConfigParse.class, new Tag("!modify_yaml"));
			Yaml yaml = new Yaml(representer, options);
			yaml.dump(data, writer);
			update();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getConfigJson(){
		return JSON.toJSONString(yamlMap);
	}

	public String convertJsonToYaml(String jsonData) {
		// 使用FastJSON解析JSON
		JSONObject jsonObject = JSONObject.parseObject(jsonData);
		// 使用SnakeYAML将JSON转换为YAML
		DumperOptions options = new DumperOptions();
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		options.setPrettyFlow(true);
		Yaml yaml = new Yaml(options);
		return yaml.dump(jsonObject);
	}

	//更新状态
	private void update() throws FileNotFoundException {
		inputStream = new FileInputStream(filePath);
		yamlMap = yaml.load(inputStream);
		portsConfig = getPortConfig();
	}
}
