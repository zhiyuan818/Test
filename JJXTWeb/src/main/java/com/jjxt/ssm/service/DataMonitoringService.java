package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.RedisInfo;
import com.jjxt.ssm.utils.DataSource;

public interface DataMonitoringService {
	@DataSource("master")
	List<RedisInfo> findRedisList() throws Exception;

	Map<String,String> findAccStatistic(String accId);

	Map<String, String> findChannelStatistic(String channelId);

	Map<String, Map<String,List<String>>> findAccMinStatistic(String accId);

	Map<String, Map<String, List<String>>> findChanMinStatistic(String channelId);

	Map<String, Object> findDataChart(String cla, String type, String[] data, String[] category,String selectTime);

	Map<String, Long> findStatisticSum(String cla, String[] data);
	
	@DataSource("master")
	List<Map<String,String>> findProviderList();
	
	
}
