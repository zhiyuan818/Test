package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.RedisInfo;

public interface DataMonitoringMapper {
	//查询redis列表
	List<RedisInfo> findRedisList();
	//查询码号运营商列表
	List<Map<String,String>> findProviderList();

}
