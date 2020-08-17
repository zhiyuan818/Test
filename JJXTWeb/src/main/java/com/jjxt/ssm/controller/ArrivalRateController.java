package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.ArrivalRate;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.StatisticsService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class ArrivalRateController {

private static Logger logger = Logger.getLogger(ArrivalRateController.class);
	
	private static final String PATH = "statistics/";
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/goArrivalRatePageList.action")
	public String goArrivalRatePageList(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][ARRIVALRATE] ", e);
		}
		
		List<Channel> channels = new ArrayList<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][ARRIVALRATE] ", e);
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		
		return PATH + "arrivalRateList";
	}

	
	@RequestMapping("findArrivalRate.action")
	@ResponseBody
	public PageResult<ArrivalRate> findArrivalRate(HttpServletRequest request, HttpServletResponse response, Integer appId, Integer channelId, String logDate, Integer rateButton){
		logger.debug("[BINS][ARRIVALRATE] appId="+appId+",channelId="+channelId+",logDate="+logDate);
		int total = 0;
		Map<String, Object> map = null;
		Long sTime = System.currentTimeMillis();
		List<ArrivalRate> lists = new ArrayList<>();
		List<ArrivalRate> list = null;
		Date date = new Date();
		if (StringUtil.isEmpty(logDate) || logDate.equals(DateUtil.convertDate3(date))) {
			
			try {
				
				logDate = DateUtil.convertDate(date); //表名
				Long endStemp = date.getTime();
				Date zeroDate = DateUtil.converDateFromStr5(DateUtil.getZeroOfDay(date)); //00:00:00的Date
				Long beginStemp = zeroDate.getTime();
				Long num = (endStemp - beginStemp)/3600000;
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i <= num; i++) {
					String startTime = DateUtil.convertDate2(zeroDate);
					cal.setTime(zeroDate);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					Date endDate = cal.getTime();
					String endTime = DateUtil.convertDate2(endDate);
					map = new HashMap<>();
					list = new ArrayList<>();
					map.put("i", i);
					map.put("logDate", logDate);
					map.put("startTime", startTime);
					map.put("endTime", endTime);
					map.put("appId", appId);
					map.put("channelId", channelId);
					map.put("rateButton", rateButton);
					
					list = statisticsService.findArrivalRate(map);
					
					lists.addAll(list);
					zeroDate = endDate;
				}
				//计算总数
				List<ArrivalRate> listTotal = new ArrayList<>();
				listTotal = statisticsService.findArrivalRateTotal(map);
				lists.addAll(listTotal);
				total = lists.size();
				
				Long eTime = System.currentTimeMillis();
				logger.debug("[BINS][ARRIVALRATE] logDate=" + logDate + "appId" + appId + "channelId" + channelId
						+ ",time=" + (eTime - sTime));
				return new PageResult<ArrivalRate>(total, lists);
				
			} catch (Exception e) {
				logger.error("[ERROR][ARRIVALRATE] 查询今日数据出错.", e);
			}
		} else {
			try {
				
				Date date1 = DateUtil.converDateFromStr3(logDate); //yyyy-MM-dd
				logDate = DateUtil.convertDate(date1); //表名
				Date zeroDate = DateUtil.converDateFromStr5(DateUtil.getZeroOfDay(date1)); //00:00:00的Date
				Calendar cal = Calendar.getInstance();
				for (int i = 0; i < 24; i++) {
					String startTime = DateUtil.convertDate2(zeroDate);
					cal.setTime(zeroDate);
					cal.add(Calendar.HOUR_OF_DAY, 1);
					Date endDate = cal.getTime();
					String endTime = DateUtil.convertDate2(endDate);
					map = new HashMap<>();
					list = new ArrayList<>();
					map.put("i", i);
					map.put("logDate", logDate);
					map.put("startTime", startTime);
					map.put("endTime", endTime);
					map.put("appId", appId);
					map.put("channelId", channelId);
					map.put("rateButton", rateButton);
					try {
						list = statisticsService.findArrivalRate(map);
					} catch (Exception e) {
						logger.error("[ERROR][ARRIVALRATE] ", e);
					}
					
					lists.addAll(list);
					zeroDate = endDate;
				}
				
				//计算总数
				List<ArrivalRate> listTotal = new ArrayList<>();
				try {
					listTotal = statisticsService.findArrivalRateTotal(map);
				} catch (Exception e) {
					logger.error("[ERROR][ARRIVALRATE] ", e);
				}
				lists.addAll(listTotal);
				total = lists.size();
				
				Long eTime = System.currentTimeMillis();
				logger.debug("[BINS][ARRIVALRATE] logDate=" + logDate + "appId" + appId + "channelId" + channelId
						+ ",time=" + (eTime - sTime));
				return new PageResult<ArrivalRate>(total, lists);
				
			} catch (ParseException e) {
				logger.error("[ERROR][ARRIVALRATE] 查询到达率出错.", e);
			}
		}
		
		return new PageResult<ArrivalRate>(0, lists);
	}
	
	
}
