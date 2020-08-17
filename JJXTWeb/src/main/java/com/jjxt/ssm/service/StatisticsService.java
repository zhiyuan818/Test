package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ArrivalRate;
import com.jjxt.ssm.entity.ReportRate;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.utils.DataSource;

public interface StatisticsService {
	@DataSource("master")
	public List<Statistics> findAppStatisticsList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findAppChanStatisticsList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Statistics findAppStatisticsTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findChaSattistics(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findChaSattisticsTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findChaAppSattistics(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<ArrivalRate> findArrivalRate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<ArrivalRate> findArrivalRateTotal(Map<String, Object> map) throws Exception;	
	@DataSource("master")
	public List<ReportRate> findToatlReportRate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<ReportRate> findFailureRate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findChaRealSattistics(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findChaRealSattisticsTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findAppProfitList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findProviderStatisticsList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Statistics findProviderStatisticsTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findProvinceStatisticsByChannelList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Statistics findProvinceStatisticsByChannelTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Statistics> findProvinceStatisticsByAppList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Statistics findProvinceStatisticsByAppTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Map<String, Object> findChanDetailById(String channelId) throws Exception;
}
