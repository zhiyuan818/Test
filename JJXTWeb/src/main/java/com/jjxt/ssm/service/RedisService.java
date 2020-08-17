package com.jjxt.ssm.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RedisService {
	
	List<String> getRedisNameList(String redisName);
}
