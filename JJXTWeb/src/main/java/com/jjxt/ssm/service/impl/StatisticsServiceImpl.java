package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ArrivalRate;
import com.jjxt.ssm.entity.ReportRate;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.mapper.StatisticsMapper;
import com.jjxt.ssm.service.StatisticsService;

@Service("StatisticsService")
@Transactional
public class StatisticsServiceImpl implements StatisticsService{
	@Autowired
	private StatisticsMapper statisticsMapper;

	@Override
	public List<Statistics> findAppStatisticsList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findAppStatisticsList(map);
	}

	@Override
	public Statistics findAppStatisticsTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findAppStatisticsTotal(map);
	}

	@Override
	public List<Statistics> findChaSattistics(Map<String, Object> map) throws Exception {
		return statisticsMapper.findChaSattistics(map);
	}

	@Override
	public List<Statistics> findChaSattisticsTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findChaSattisticsTotal(map);
	}

	@Override
	public List<Statistics> findChaAppSattistics(Map<String, Object> map) throws Exception {
		return statisticsMapper.findChaAppSattistics(map);
	}
	@Override
	public List<ArrivalRate> findArrivalRate(Map<String, Object> map) throws Exception {
		return statisticsMapper.findArrivalRate(map);
	}

	@Override
	public List<ArrivalRate> findArrivalRateTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findArrivalRateTotal(map);
	}

	@Override
	public List<ReportRate> findToatlReportRate(Map<String, Object> map) throws Exception {
		return statisticsMapper.findToatlReportRate(map);
	}
	
	@Override
	public List<ReportRate> findFailureRate(Map<String, Object> map) throws Exception {
		return statisticsMapper.findFailureRate(map);
	}

	@Override
	public List<Statistics> findChaRealSattistics(Map<String, Object> map) throws Exception {
		return statisticsMapper.findChaRealSattistics(map);
	}

	@Override
	public List<Statistics> findChaRealSattisticsTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findChaRealSattisticsTotal(map);
	}

	@Override
	public List<Statistics> findAppProfitList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findAppProfitList(map);
	}

	@Override
	public List<Statistics> findProviderStatisticsList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProviderStatisticsList(map);
	}

	@Override
	public Statistics findProviderStatisticsTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProviderStatisticsTotal(map);
	}

	@Override
	public List<Statistics> findProvinceStatisticsByChannelList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProvinceStatisticsByChannelList(map);
	}

	@Override
	public Statistics findProvinceStatisticsByChannelTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProvinceStatisticsByChannelTotal(map);
	}

	@Override
	public List<Statistics> findProvinceStatisticsByAppList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProvinceStatisticsByAppList(map);
	}

	@Override
	public Statistics findProvinceStatisticsByAppTotal(Map<String, Object> map) throws Exception {
		return statisticsMapper.findProvinceStatisticsByAppTotal(map);
	}

	@Override
	public List<Statistics> findAppChanStatisticsList(Map<String, Object> map) throws Exception {
		return statisticsMapper.findAppChanStatisticsList(map);
	}

	@Override
	public Map<String, Object> findChanDetailById(String channelId) throws Exception {
		return statisticsMapper.findChanDetailById(channelId);
	}
}
