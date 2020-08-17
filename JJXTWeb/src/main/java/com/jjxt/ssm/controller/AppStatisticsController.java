package com.jjxt.ssm.controller;

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
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Product;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ProductService;
import com.jjxt.ssm.service.StatisticsService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.DoubleUtil;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class AppStatisticsController {

	private static Logger logger = Logger.getLogger(AppStatisticsController.class);

	private static final String PATH = "statistics/";

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private BaseCompanyService companyService;
	@Autowired
	private ProductService productService;

	@RequestMapping("/goAppStatisticsPageList.action")
	public String goAppStatisticsPageList(HttpServletRequest request) {
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
		List<BaseCompany> sales = new ArrayList<>();
		try {
			sales = companyService.findSalesBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		List<BaseCompany> saleAfter = new ArrayList<>();
		try {
			saleAfter = companyService.findSaleAfterBySort(map);
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
		List<Product> products = new ArrayList<>();
		try {
			products = productService.findAllProduct();
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		
		request.setAttribute("companys", companys);
		request.setAttribute("sales", sales);
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		request.setAttribute("saleAfter", saleAfter);
		request.setAttribute("products", products);
		return PATH + "appStatisticsPageList";
	}

	
	@RequestMapping("/findAppStatisticsList.action")
	@ResponseBody
	public List<Statistics> findAppStatisticsList(HttpServletRequest request,String companyKey, String appId, String sales, String saleAfter, String channelId,
			String startTime, String endTime, String statisticType, String productId) {
		logger.debug("[BINS][APP_STATISTIC] companyKey=" + companyKey + ",appId=" + appId + ",sales=" + sales + ",saleAfter=" + saleAfter
				+ ",channelId=" + channelId + ",startTime=" + startTime + ",endTime=" + endTime + ",statisticType=" + statisticType 
				+ "productId=" + productId);
		
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = DateUtil.convertDate3(new Date());
			endTime = startTime;
		} else {
			try {
				startTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(startTime));
				endTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(endTime));
			} catch (ParseException e) {
				logger.error("[ERROR][APP_STATISTIC] 时间转换异常.", e);
			}
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Statistics> statisticsList = new ArrayList<Statistics>();
		
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		
		paramMap.put("statisticType", statisticType);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		paramMap.put("companyKey", companyKey);
		paramMap.put("appId", appId);
		paramMap.put("sales", sales);
		paramMap.put("saleAfter", saleAfter);
		paramMap.put("channelId", channelId);
		paramMap.put("productId", productId);
		if(ucenterManager != null) {
			paramMap.put("chineseName", ucenterManager.getChineseName());
			paramMap.put("title", ucenterManager.getTitle());
			paramMap.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			paramMap.put("isAllChannel", ucenterManager.getIsAllChannel());
		}

		try {
			if(statisticType.equals("chan")){
				if("销售,客服".contains(String.valueOf(paramMap.get("title"))) && "1".contains(String.valueOf(paramMap.get("isAllCustomer")))){
					
					statisticsList = statisticsService.findAppStatisticsList(paramMap);
				}else if("商务".contains(String.valueOf(paramMap.get("title"))) && "0,1".contains(String.valueOf(paramMap.get("isAllChannel")))){
					
					statisticsList = statisticsService.findAppStatisticsList(paramMap);
				}else{
					statisticsList = statisticsService.findAppChanStatisticsList(paramMap);
				}
			}else{
				statisticsList = statisticsService.findAppStatisticsList(paramMap);
			}
		} catch (Exception e) {
			logger.error("[BINS][APP_STATISTIC] ", e);
		}
		Statistics statistics = null;
		try {
			statistics = statisticsService.findAppStatisticsTotal(paramMap);
			statisticsList.add(statistics);
		} catch (Exception e) {
			logger.error("[BINS][APP_STATISTIC] ", e);
		}
		return statisticsList;
	}
	
	
	@RequestMapping("/goAppProfit.action")
	public String goAppProfit(HttpServletRequest request,HttpServletResponse response){
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
		List<BaseCompany> sales = new ArrayList<>();
		try {
			sales = companyService.findSalesBySort(map);
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
		
		request.setAttribute("companys", companys);
		request.setAttribute("sales", sales);
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "appProfit";
	}

	
	@ResponseBody
	@RequestMapping("/findAppProfitList.action")
	public List<Statistics> findAppProfitList(HttpServletRequest request,String companyId,String appId,String sales,String channelId,String startTime,String endTime,String statisticType,String sort,String sortOrder){
		logger.debug("[BINS][APP_PROFIT] companyId="+companyId +",appId="+appId+",sales="+sales+",channelId="+channelId+",startTime="+startTime+",endTime="+endTime+",statisticType="+statisticType+",sort="+sort+",sortOrder="+sortOrder);
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = DateUtil.convertDate3(new Date());
			endTime = startTime;
		} else {
			try {
				startTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(startTime));
				endTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(endTime));
			} catch (ParseException e) {
				logger.error("[ERROR][APP_STATISTIC] 时间转换异常.", e);
			}
		}
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("companyId", companyId);
		map.put("appId", appId);
		map.put("sales", sales);
		map.put("channelId", channelId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("statisticType", statisticType);
		
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<Statistics> profits=new ArrayList<>();
		try {
			profits=statisticsService.findAppProfitList(map);
		} catch (Exception e) {
			logger.error("[ERROR][APP_PROFIT] EX="+e);
		}
		Integer appReportDelivrdCharge=0;
		Integer channelReportDelivrdCharge=0;
		Double income=0d;
		Double cost=0d;
		for(Statistics stat:profits) {
			Integer appSucc = stat.getAppReportDelivrdCharge();
			Integer channelSucc = stat.getChannelReportDelivrdCharge();
			Double appPrice = stat.getAppPrice();
			Double channelPrice = stat.getChannelPrice();
			Double appIncom=DoubleUtil.formateDouble(appSucc*appPrice/100);
			Double channelCost=DoubleUtil.formateDouble(channelSucc*channelPrice/100);
			stat.setIncome(appIncom);
			stat.setCost(channelCost);
			stat.setProfit(DoubleUtil.formateDouble(appIncom-channelCost));
			stat.setProfitMargin(appIncom==0?0:DoubleUtil.formateDouble((appIncom-channelCost)/appIncom*100));
			appReportDelivrdCharge+=appSucc;
			channelReportDelivrdCharge+=channelSucc;
			income+=appIncom;
			cost+=channelCost;
		}
		Statistics statistics=new Statistics();
		statistics.setIncome(DoubleUtil.formateDouble(income));
		statistics.setCost(DoubleUtil.formateDouble(cost));
		statistics.setProfit(DoubleUtil.formateDouble(income-cost));
		statistics.setProfitMargin(income==0?0:DoubleUtil.formateDouble((income-cost)/income*100));
		statistics.setChannelReportDelivrdCharge(channelReportDelivrdCharge);
		statistics.setAppReportDelivrdCharge(appReportDelivrdCharge);
		statistics.setAppName("总计");
		profits.add(statistics);
		return profits;
	}

	
	@RequestMapping("/findDetailByName.action")
	@ResponseBody
	public Map<String, Object> findDetailByName(String appId){
		
		Map<String, Object> result = new HashMap<>();
		
		try {
			result = applicationService.findDetailByName(appId);
		} catch (Exception e) {
			logger.error("[BINS][APP_STATISTIC] findDetail is error.", e);
		}
		
		return result;
	}
	
	@RequestMapping("/findChanDetailById.action")
	@ResponseBody
	public Map<String, Object> findChanDetailById(String channelId){
		Map<String, Object> result = new HashMap<>();
		try {
			result = statisticsService.findChanDetailById(channelId);
		} catch (Exception e) {
			logger.error("[BINS][APP_STATISTIC] findAppChanDetail is error.", e);
		}
		return result;
	}

}
