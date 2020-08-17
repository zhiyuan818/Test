package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.DataFinance;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.DataFinanceService;
import com.jjxt.ssm.service.UcenterManagerService;

@Controller
@RequestMapping("/finance")
public class DataFinanceController {
	private static Logger logger = Logger.getLogger(DataFinanceController.class);
	
	private static final String PATH = "finance/";
	
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	
	@Autowired
	private DataFinanceService dataFinanceService;
	@Autowired
	private UcenterManagerService ucenterManagerService;
	@RequestMapping("/goFinanceList.action")
	public String goFinanceList(HttpServletRequest request) {
		Map<String, String> map=new HashMap<String,String>();
		map.put("title", "销售");
		List<UcenterManager> managers=null;
		try {
			managers = ucenterManagerService.findUcenterManager(map);
		} catch (Exception e) {
			logger.error("[ERROR][MANAGER]" ,e);
		}
		request.setAttribute("managers", managers);
		return PATH + "financeList";
	}

	
	@RequestMapping("/findFinance.action")
	@ResponseBody
	public PageResult<DataFinance> findFinance(Integer pageSize, Integer pageIndex, String appName,String addName,String changeType,String isBill,String startTime,String endTime) {
		logger.debug("[BINS][FINANCE] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appName=" + appName+",addName="+addName+",changeType="+changeType+",isBill="+isBill+",startTime="+startTime+",endTime="+endTime);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appName", appName);
		paramMap.put("addName", addName);
		paramMap.put("changeType", changeType);
		paramMap.put("isBill", isBill);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		int total = 0;
		try {
			total = dataFinanceService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][FINANCE] ", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appName", appName);
		map.put("addName", addName);
		map.put("changeType", changeType);
		map.put("isBill", isBill);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<DataFinance> list = new ArrayList<>();
		try {
			list = dataFinanceService.findDataFinancePageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][FINANCE] ", e);
		}
		return new PageResult<>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.finance, operation = Operate.UPDATE)
	@RequestMapping(value="writeOff.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult writeOff(HttpServletRequest request, HttpServletResponse response,Integer id){
		logger.debug("[BINS][FINANCE] id=" + id);
		DataFinance finance = null;
		int updateRes = 0;
		DataFinance oldFinance = null;
		try {
			finance = new DataFinance();
			finance.setId(id);
			UcenterManager ucenterManager = (UcenterManager) request.getSession()
					.getAttribute(Constant.SERVER_USER_SESSION);
			finance.setBillName(ucenterManager.getManagerName());
			finance.setBillTime(sdf.parse(sdf.format(new Date())));
			finance.setIsBill("yes");
			try {
				updateRes = dataFinanceService.updateDataFinance(finance);
			} catch (Exception e) {
				logger.error("[ERROR][BINS][FINANCE] ", e);
			}
			try {
				oldFinance = dataFinanceService.findDataFinanceById(id);
			} catch (Exception e) {
				logger.error("[ERROR][BINS][FINANCE] ", e);
			}
		} catch (ParseException e) {
			logger.error("[ERROR][BINS][FINANCE] exce=", e);
		}
		return new ResponseResult(updateRes, finance, oldFinance);
	}
}
