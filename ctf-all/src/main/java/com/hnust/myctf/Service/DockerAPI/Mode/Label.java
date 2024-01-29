package com.hnust.myctf.Service.DockerAPI.Mode;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Label {
	// 依赖的服务
	@JSONField(name = "com.docker.compose.depends_on")
	private String dependsOn;

	// 项目工作目录
	@JSONField(name = "com.docker.compose.project.working_dir")
	private String projectWorkingDir;

	// Docker镜像
	@JSONField(name = "com.docker.compose.image")
	private String image;

	// Docker服务
	@JSONField(name = "com.docker.compose.service")
	private String service;

	// 项目名称
	@JSONField(name = "com.docker.compose.project")
	private String project;

	// 项目配置文件路径
	@JSONField(name = "com.docker.compose.project.config_files")
	private String projectConfigFiles;

	//Docker Compose版本
	@JSONField(name = "com.docker.compose.version")
	private String version;

	// 配置哈希值
	@JSONField(name = "com.docker.compose.config-hash")
	private String configHash;

	// 容器编号
	@JSONField(name = "com.docker.compose.container-number")
	private int containerNumber;

	// 是否为一次性容器
	@JSONField(name = "com.docker.compose.oneoff")
	private boolean oneOff;
}
