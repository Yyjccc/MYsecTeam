package com.hnust.common.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceDiscoveryService {

	@Autowired
	private DiscoveryClient discoveryClient;

	public List<String> getServiceInstances(String serviceName) {
		return discoveryClient.getInstances(serviceName).stream()
				.map(instance -> instance.getUri().toString())
				.collect(Collectors.toList());
	}
}
