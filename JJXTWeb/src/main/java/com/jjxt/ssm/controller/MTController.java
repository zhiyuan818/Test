package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.MT;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.MTService;

@Controller
@RequestMapping("/mt")
public class MTController {

	private static Logger logger = Logger.getLogger(MTController.class);

	private static final String PATH = "mt/";
	@Autowired
	private MTService mtService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Value("${changeDate}")
	private String changeDate;
	/**
	 * 跳转菜单分页页面
	 * 
	 * @return
	 */
	@RequestMapping("/goMTPageList.action")
	public String goMTPageList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "mtList";
	}

	/**
	 * 查询所有的菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 
	 */
	
	@RequestMapping(value = "/findMTPageList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<MT> findMTPageList(Integer pageSize, Integer pageIndex, String sort, String sortOrder,
			String logDate, String appId, String destNumber, String channelMsgId, String reportStatus, String sendFlag,
			String sign, String channelId, String province, String submitStatus, String reportStatus1,
			String startTime, String endTime, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][MT]  pageSize=" + pageSize + ", pageIndex=" + pageIndex + " ,sort=" + sort + " ,sortOrder="
				+ sortOrder + " ,logDate=" + logDate + " ,appId=" + appId + " ,destNumber=" + destNumber
				+ ",channelMsgId=" + channelMsgId + " ,reportStatus=" + reportStatus + " ,sendFlag=" + sendFlag
				+ " ,sign=" + sign + " ,channelId=" + channelId + " ,province=" + province + " ,submitStatus="
				+ submitStatus + " ,reportStatus1=" + reportStatus1 + " ,startTime=" + startTime + " ,endTime="
				+ endTime);
		Long sTime = System.currentTimeMillis();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[error]时间转换异常", e);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logDate", logDate);
		map.put("appId", appId);
		map.put("destNumber", destNumber);
		map.put("channelMsgId", channelMsgId);
		map.put("reportStatus", reportStatus);
		map.put("sendFlag", sendFlag);
		map.put("sign", sign);
		map.put("channelId", channelId);
		map.put("province", province);
		map.put("submitStatus", submitStatus);
		map.put("reportStatus1", reportStatus1);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		int total = 0;
		try {
			if(DateUtil.isCompareTime(logDate, changeDate)) {
				total = mtService.findNewMtTotal(map);
			}else {
				total = mtService.findMTTotal(map);
			}
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MT] put mt_total is error", e1);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		List<MT> ucenterMenuLists=null;
		try {
			map1.put("logDate", logDate);
			map1.put("appId", appId);
			map1.put("destNumber", destNumber);
			map1.put("channelMsgId", channelMsgId);
			map1.put("reportStatus", reportStatus);
			map1.put("sendFlag", sendFlag);
			map1.put("sign", sign);
			map1.put("channelId", channelId);
			map1.put("province", province);
			map1.put("submitStatus", submitStatus);
			map1.put("reportStatus1", reportStatus1);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			if(DateUtil.isCompareTime(logDate, changeDate)) {
				ucenterMenuLists = mtService.findNewMTPageList(map1);
			} else {
				ucenterMenuLists = mtService.findMTPageList(map1);
			}
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][MT]  pageSize=" + pageSize + ", pageIndex=" + pageIndex + " ,sort=" + sort
					+ " ,sortOrder=" + sortOrder + " ,logDate=" + logDate + " ,appId=" + appId + " ,destNumber="
					+ destNumber + ",channelMsgId=" + channelMsgId + " ,reportStatus=" + reportStatus + " ,sendFlag="
					+ sendFlag + " ,sign=" + sign + " ,channelId=" + channelId + " ,province=" + province
					+ " ,submitStatus=" + submitStatus + " ,reportStatus1=" + reportStatus1 + " ,startTime=" + startTime
					+ " ,endTime=" + endTime + ",time=" + (eTime - sTime));
			return new PageResult<MT>(total, ucenterMenuLists);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MT] put mt pageList is error", e);
		}
		return new PageResult<MT>(0, new ArrayList<MT>());
	}

	/**
	 * 日志条件查询中的所有通道
	 * 
	 * @return
	 */
	
	@RequestMapping("/findChannel.action")
	@ResponseBody
	public List<Channel> findChannelName() {
		logger.debug("[BINS][MT][CHANNELNAME]");
		List<Channel> list = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			list = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		return list;
	};

	/**
	 * 根据link_id进行查询所有记录
	 * 
	 * @param link_id
	 * @return
	 */
	
	@RequestMapping("/findMTByLinkId.action")
	@ResponseBody
	public MT findMTByLinkId(String linkId, String logDate) {
		logger.debug("[BINS][MT] put mt  linkId=" + linkId + ",logDate=" + logDate);
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[error]时间转换异常", e);
			}
		}
		map.put("linkId", linkId);
		map.put("logDate", logDate);
		MT mt = null;
		try {
			if(DateUtil.isCompareTime(logDate, changeDate)) {
				mt = mtService.findNewMTByLinkId(map);
			}else {
				mt = mtService.findMTByLinkId(map);
			}
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		return mt;
	}

	
	@RequestMapping("/findCompanyDetails.action")
	@ResponseBody
	public BaseCompany findCompanyDetails(String appId){
		logger.debug("[BINS][MT] appId="+appId);
		BaseCompany baseCompany=null;
		try {
			baseCompany=baseCompanyService.findCompanyDetails(appId);
		} catch (Exception e) {
			logger.error("[ERROR][MT] e=",e);
		}
		return baseCompany;
	}
}
