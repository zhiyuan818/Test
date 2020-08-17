package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ArrivalRate;
import com.jjxt.ssm.entity.ReportRate;
import com.jjxt.ssm.entity.Statistics;

public interface StatisticsMapper {

	public List<Statistics> findAppStatisticsList(Map<String, Object> map);
	
	public List<Statistics> findAppChanStatisticsList(Map<String, Object> map);
	
	public Statistics findAppStatisticsTotal(Map<String, Object> map);
	
	public List<Statistics> findChaSattistics(Map<String, Object> map);
	
	public List<Statistics> findChaSattisticsTotal(Map<String, Object> map);

	public List<Statistics> findChaAppSattistics(Map<String, Object> map);

	public List<ArrivalRate> findArrivalRate(Map<String, Object> map);
	
	public List<ArrivalRate> findArrivalRateTotal(Map<String, Object> map);	
	
	public List<ReportRate> findToatlReportRate(Map<String, Object> map);
	
	public List<ReportRate> findFailureRate(Map<String, Object> map);
	
	public List<Statistics> findChaRealSattistics(Map<String, Object> map);
	
	public List<Statistics> findChaRealSattisticsTotal(Map<String, Object> map);
	
	public List<Statistics> findAppProfitList(Map<String, Object> map);
	
	public List<Statistics> findProviderStatisticsList(Map<String, Object> map);
	
	public Statistics findProviderStatisticsTotal(Map<String, Object> map);
	
	public List<Statistics> findProvinceStatisticsByChannelList(Map<String, Object> map);
	
	public Statistics findProvinceStatisticsByChannelTotal(Map<String, Object> map);
	
	public List<Statistics> findProvinceStatisticsByAppList(Map<String, Object> map);
	
	public Statistics findProvinceStatisticsByAppTotal(Map<String, Object> map);
	
	public Map<String, Object> findChanDetailById(String channelId);
}
