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

import com.jjxt.ssm.entity.BaseChannelSupplier;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ChannelSupplierService;
import com.jjxt.ssm.service.StatisticsService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class ChannelRealStatisticsController {

	private static Logger logger = Logger.getLogger(ChannelRealStatisticsController.class);

	private static final String PATH = "statistics/";
	@Autowired
	private StatisticsService statisticsService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ChannelSupplierService channelSupplierService;

	@RequestMapping("/goChannelRealStatisticsPage.action")
	public String goChannelStatisticsPageList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		List<BaseChannelSupplier> supplierKeys = null;
		List<BaseChannelSupplier> heads = null;
		Map<String, Object> map = new HashMap<>();
		
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		try {
			supplierKeys = channelSupplierService.findSupplierKeyBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		try {
			heads = channelSupplierService.findHeadBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHAN_STATISTIC] ", e);
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("supplierKeys", supplierKeys);
		request.setAttribute("heads", heads);

		return PATH + "channelRealStatisticsList";
	}

	
	@RequestMapping("findChaRealSattistics.action")
	@ResponseBody
	public PageResult<Statistics> findChaRealSattistics(HttpServletRequest request, HttpServletResponse response,
			Integer channelId, String name, String startTime, String endTime, String statisticType, String supplierId, String head) {

		Long sTime = System.currentTimeMillis();
		if (StringUtil.isEmpty(startTime) || StringUtil.isEmpty(endTime)) {
			startTime = DateUtil.convertDate3(new Date());
			endTime = startTime;
		} else {
			try {
				startTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(startTime));
				endTime = DateUtil.convertDate3(DateUtil.converDateFromStr3(endTime));
			} catch (ParseException e) {
				logger.error("[ERROR][CHAN_STATISTIC] 时间转换异常.", e);
			}
		}
		Map<String, Object> map = new HashMap<>();
		int total = 0;
		
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		try {
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("channelId", channelId);
			map.put("supplierId", supplierId);
			map.put("head", head);
			map.put("statisticType", statisticType);
			if(ucenterManager != null) {
				map.put("chineseName", ucenterManager.getChineseName());
				map.put("title", ucenterManager.getTitle());
				map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
				map.put("isAllChannel", ucenterManager.getIsAllChannel());
			}
			if(name != null) {
				map.put("name", name.trim());
			}
			
			List<Statistics> lists = statisticsService.findChaRealSattistics(map);

			List<Statistics> listTotal = statisticsService.findChaRealSattisticsTotal(map);
			lists.addAll(listTotal);
			total = lists.size();
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][CHAN_STATISTIC] startTime=" + startTime + ",endTime=" + endTime + ",channelId="
					+ channelId + ",time=" + (eTime - sTime));
			return new PageResult<Statistics>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][CHAN_STATISTIC] 查询按通道统计列表出错.", e);
		}

		return new PageResult<Statistics>(total, new ArrayList<>());
	}

}
