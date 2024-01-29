package com.hnust.myctf.Utils;

import java.io.IOException;

public class test {
	public static void main(String[] args) throws IOException {
//		YamlConfigParse yamlConfigParse=new YamlConfigParse("F:\\code\\myProject\\MYSec\\upload\\docker\\web\\CVE-2021-44228\\docker-compose.yml");
//		YamlConfigParse yamlConfigParse1=new YamlConfigParse("F:\\code\\myProject\\MYSec\\upload\\test\\docker-compose.yml");
//		yamlConfigParse1.saveYAMLToFile(yamlConfigParse.getYamlMap());
//		//System.out.println(yamlConfigParse.convertJsonToYaml(yamlConfigParse1.getConfigJson()));
//		yamlConfigParse1.modify("ports",new String[]{"8848"});
		System.out.println(Common.getRandomFlag());
	}
}
