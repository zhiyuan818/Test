package com.jjxt.ssm.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.jjxt.ssm.entity.AppComplain;
import com.jjxt.ssm.service.AppComplainService;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.PageResult;

@Controller
@RequestMapping("/complain")
public class AppComplainController {

	private static Logger logger = Logger.getLogger(AppComplainController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd);

	private static final String PATH = "complain/";
	@Autowired
	private AppComplainService appComplain;

	
	@RequestMapping("/goAppComplainPage.action")
	public String goAppComplain(HttpServletRequest request) {
		List<String> complainNames = new ArrayList<>();
		try {
			complainNames = appComplain.findComplainNames();
		} catch (Exception e) {
			logger.error("[ERROR][A_COMPLAIN] ", e);
		}
		request.setAttribute("complainNames", complainNames);
		return PATH + "appComplainPageList";
	}

	
	@RequestMapping("/findAllList.action")
	@ResponseBody
	public PageResult<AppComplain> findAllList() {

		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppComplain> lists = new ArrayList<AppComplain>();

		Date date = new Date();
//		String year = DateUtil.convertDate(date);
		Date start = DateUtil.getFirstdayOfMonth(date);
		String bTime = sdf.format(start);
		Date end = DateUtil.getLastdayOfMonth(date);
		String eTime = sdf.format(end);
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
//		map.put("year", year);
		try {
			lists = appComplain.findAllList(map);

		} catch (Exception e) {
			logger.error("[ERROR][A_COMPLAIN] 查询客户投比出错.", e);
		}

		total = lists.size();

		return new PageResult<AppComplain>(total, lists);
	}


	
	@RequestMapping("/findListByName.action")
	@ResponseBody
	public PageResult<AppComplain> findListByName(String companyName, String logDate) {
		logger.debug("[BINS][A_COMPLAIN] companyName="+companyName+",logDate="+logDate);
		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<AppComplain> lists = new ArrayList<AppComplain>();
		List<AppComplain> list = new ArrayList<AppComplain>();

		Double submitCounts = 0d;
		Double succeedCounts = 0d;
		Double complainCounts = 0d;
		Double rate;
		Date date = null;
		try {
			date = DateUtil.converDateFromStr4(logDate);
		} catch (ParseException e1) {
			logger.error("[ERROR][A_COMPLAIN]日期转化出错.", e1);
		}
//		String year = DateUtil.convertDate(date);
		Date start = DateUtil.getFirstdayOfMonth(date);
		String bTime = sdf.format(start);
		Date end = DateUtil.getLastdayOfMonth(date);
		String eTime = sdf.format(end);
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("companyName", companyName);
//		map.put("year", year);
		
		if (!companyName.trim().equals("")) {
			
			try {
				lists = appComplain.findListByName(map);

			} catch (Exception e) {
				logger.error("[ERROR][A_COMPLAIN] 按客户简称查询客户投比出错.", e);
			}
			for (AppComplain appComplain : lists) {

				submitCounts += appComplain.getSubmitCount();
				succeedCounts += appComplain.getSucceedCount();
				complainCounts += appComplain.getComplainCount();

				if (appComplain.getComplainCount() > 0) {
					list.add(appComplain);
				}
			}
			Double d=0d;
			if(succeedCounts!=0d){
				d = ((complainCounts/succeedCounts)*1000000);
			}
			BigDecimal b = new BigDecimal(d);
			rate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			AppComplain appComplains = new AppComplain();
			appComplains.setCompanyName(companyName);
			appComplains.setSubmitCount(submitCounts);
			appComplains.setSucceedCount(succeedCounts);
			appComplains.setComplainCount(complainCounts);
			appComplains.setComplainRate(rate);

			list.add(appComplains);

			total = list.size();

			return new PageResult<AppComplain>(total, list);
			
		} else {
			
			try {
				lists = appComplain.findAllList(map);

			} catch (Exception e) {
				logger.error("[ERROR][A_COMPLAIN] 按日期查询客户投比出错.", e);
			}

			total = lists.size();

			return new PageResult<AppComplain>(total, lists);
			
		}

	}

}
