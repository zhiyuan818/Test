package com.jjxt.ssm.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
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
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.FailureReportRate;
import com.jjxt.ssm.entity.ReportRate;
import com.jjxt.ssm.entity.ToatlReportRate;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.StatisticsService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class FailureRateController {

private static Logger logger = Logger.getLogger(FailureRateController.class);
	
	private static final String PATH = "statistics/";
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/goFailureRatePageList.action")
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
			logger.error("[ERROR][FAILURE_RATE] ", e);
		}
		
		List<Channel> channels = new ArrayList<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][FAILURE_RATE] ", e);
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		
		return PATH + "failureRateList";
	}

	
	@RequestMapping("findToatlReportRate.action")
	@ResponseBody
	public List<ToatlReportRate> findToatlReportRate(HttpServletRequest request, HttpServletResponse response, Integer appId, Integer channelId, String logDate){
		Long sTime = System.currentTimeMillis();
		
		if(StringUtil.isEmpty(appId) && StringUtil.isEmpty(channelId)){
			return null;
		}
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[ERROR][FAILURE_RATE] 时间转换异常.", e);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logDate", logDate);
		map.put("appId", appId);
		map.put("channelId", channelId);
		
		List<ReportRate> list = new ArrayList<ReportRate>();
		try {
			list=statisticsService.findToatlReportRate(map);
		} catch (Exception e) {
			logger.error("[ERROR][FAILURE_RATE] 查询今日数据出错.", e);
		}
		
		int sendTotalCharge=0;
		int totalDelivrdCharge=0;
		int totalNullCharge=0;
		int totalFailCharge=0;
		
		for (ReportRate report : list) {
			String reportStatus =report.getReportStatus();
			Integer number = report.getNumber();
			
			if(StringUtil.isEmpty(reportStatus) || reportStatus.equalsIgnoreCase("null")){
				totalNullCharge+=number;
			}else if(reportStatus.equalsIgnoreCase("DELIVRD")){
				totalDelivrdCharge+=number;
			}else{
				totalFailCharge+=number;
			}
		}
		sendTotalCharge=totalDelivrdCharge+totalNullCharge+totalFailCharge;
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);  
		
		List<ToatlReportRate> newList = new ArrayList<ToatlReportRate>();
		ToatlReportRate rate = new ToatlReportRate();
		rate.setSendTotalCharge(sendTotalCharge+"");
		if(sendTotalCharge!=0){
			rate.setTotalDelivrdCharge(totalDelivrdCharge+"("+numberFormat.format((float) totalDelivrdCharge/ (float)sendTotalCharge * 100)+"%)");
			rate.setTotalNullCharge(totalNullCharge+"("+numberFormat.format((float) totalNullCharge/ (float)sendTotalCharge * 100)+"%)");
			rate.setTotalFailCharge(totalFailCharge+"("+numberFormat.format((float) totalFailCharge/ (float)sendTotalCharge * 100)+"%)");
		}else{
			rate.setTotalDelivrdCharge(0+"(0%)");
			rate.setTotalNullCharge(0+"(0%)");
			rate.setTotalFailCharge(0+"(0%)");
		}
		
		newList.add(rate);
		
		logger.debug("[BINS][FAILURE_RATE] logDate=" + logDate + ",appId=" + appId + ",channelId=" + channelId
				+ ",time=" + (System.currentTimeMillis() - sTime));
		return newList;
	}

	
	@RequestMapping("findFailureRate.action")
	@ResponseBody
	public List<FailureReportRate> findFailureRate(HttpServletRequest request, HttpServletResponse response, Integer appId, Integer channelId, String logDate){
		Long sTime = System.currentTimeMillis();
		
		if(StringUtil.isEmpty(appId) && StringUtil.isEmpty(channelId)){
			return null;
		}
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[ERROR][FAILURE_RATE] 时间转换异常.", e);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logDate", logDate);
		map.put("appId", appId);
		map.put("channelId", channelId);
		
		List<ReportRate> list = new ArrayList<ReportRate>();
		try {
			list=statisticsService.findFailureRate(map);
		} catch (Exception e) {
			logger.error("[ERROR][FAILURE_RATE] 查询今日数据出错.", e);
		}
		
		int count=0;
		for (ReportRate reportRate : list) {
			count+=reportRate.getNumber();
		}
		
		
		List<FailureReportRate> newList = new ArrayList<FailureReportRate>();
		int num=0;
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);  
		
		for (int i = 0; i < list.size(); i++) {
			ReportRate rate = list.get(i);
			
			if(i>19){
				num+=rate.getNumber();
			}else{
				FailureReportRate failureReportRate = new FailureReportRate();
				failureReportRate.setReportStatus(rate.getReportStatus());
				failureReportRate.setProvider(rate.getProvider());
				failureReportRate.setNumber(rate.getNumber());
				if(count>0){
					failureReportRate.setPercent(numberFormat.format((float) rate.getNumber()/ (float)count * 100)+"%");
				}else{
					failureReportRate.setPercent(0+"%");
				}
				
				newList.add(failureReportRate);
			}
		}
		
		FailureReportRate reportRate = new FailureReportRate();
		reportRate.setReportStatus("other");
		reportRate.setProvider("");
		reportRate.setNumber(num);
		if(count>0){
			reportRate.setPercent(numberFormat.format((float) num/ (float)count * 100)+"%");
		}else{
			reportRate.setPercent(0+"%");
		}
		newList.add(reportRate);
		
		logger.debug("[BINS][FAILURE_RATE] logDate=" + logDate + ",appId=" + appId + ",channelId=" + channelId
				+ ",time=" + (System.currentTimeMillis() - sTime));
		return newList;
	}
}
