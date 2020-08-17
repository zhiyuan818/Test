package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.StatisticsService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class ProvinceStatisticsController {

	private static Logger logger = Logger.getLogger(ProvinceStatisticsController.class);

	private static final String PATH = "statistics/";

	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BaseCompanyService companyService;

	@RequestMapping("/goProvinceStatisticsPageList.action")
	public String goProvinceStatisticsPageList(HttpServletRequest request) {
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
			logger.error("[ERROR][STATISTIC] ", e);
		}
		List<BaseCompany> companys = new ArrayList<>();
		try {
			companys = companyService.findCompanyKeyBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		
		List<Channel> channels = new ArrayList<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		request.setAttribute("companys", companys);
		return PATH + "provinceStatisticsPageList";
	}

	
	@RequestMapping("/findProvinceStatisticsList.action")
	@ResponseBody
	public List<Statistics> findProvinceStatisticsList(HttpServletRequest request, String province, String channelId, String appId, String companyId,
			String startTime, String endTime, String statisticType, String separateType) {
		logger.debug("[BINS][PROVINCE_STATISTIC] province=" + province + ",channelId=" + channelId + ",appId=" + appId + ",companyId=" + companyId + ",startTime=" + startTime + ",endTime=" + endTime + ",statisticType=" + statisticType + ",separateType=" + separateType);
		
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = DateUtil.convertDate3(new Date());
			endTime = startTime;
		} else {
			try {
				startTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(startTime));
				endTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(endTime));
			} catch (ParseException e) {
				logger.error("[ERROR][PROVINCE_STATISTIC] 时间转换异常.", e);
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Statistics> statisticsList = new ArrayList<Statistics>();
		
		if (separateType == null) {
			separateType = "channel";
		}
		
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			paramMap.put("chineseName", ucenterManager.getChineseName());
			paramMap.put("title", ucenterManager.getTitle());
			paramMap.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			paramMap.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		
		if ("app".equals(separateType)) {
			paramMap.put("statisticType", statisticType);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("province", province);
			paramMap.put("appId", appId);
			paramMap.put("companyId", companyId);
			try {
				statisticsList = statisticsService.findProvinceStatisticsByAppList(paramMap);
			} catch (Exception e) {
				logger.error("[BINS][APP_STATISTIC] ", e);
			}
			Statistics statistics = null;
			try {
				statistics = statisticsService.findProvinceStatisticsByAppTotal(paramMap);
				statisticsList.add(statistics);
			} catch (Exception e) {
				logger.error("[BINS][PROVINCE_STATISTIC] ", e);
			}
		}else {
			paramMap.put("statisticType", statisticType);
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);
			paramMap.put("province", province);
			paramMap.put("channelId", channelId);
			try {
				statisticsList = statisticsService.findProvinceStatisticsByChannelList(paramMap);
			} catch (Exception e) {
				logger.error("[BINS][APP_STATISTIC] ", e);
			}
			Statistics statistics = null;
			try {
				statistics = statisticsService.findProvinceStatisticsByChannelTotal(paramMap);
				statisticsList.add(statistics);
			} catch (Exception e) {
				logger.error("[BINS][PROVINCE_STATISTIC] ", e);
			}
		}
		return statisticsList;
	}


	
}
