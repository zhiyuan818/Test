package com.jjxt.ssm.controller;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.TextFormat.ParseException;
import com.jjxt.ssm.common.HttpService;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.BillingService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.ExcelUtils;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.ResultData;
import com.jjxt.ssm.utils.StringUtil;


/**
 * 
 * @author yhhou
 *	出账
 */

@Controller
@RequestMapping("/billing")
public class BillingController {
	
	private static Logger logger = Logger.getLogger(AccountController.class);
	
	private static final String PATH = "billing/";
	
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BillingService billingService;
	@Autowired
	private HttpService httpService;
	@Value("${rrdBillUrl}")
	private String rrdBillUrl;
	@Autowired
	private BaseCompanyService companyService;

	
	@RequestMapping("/goBilling.action")
	public String goBilling(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<Application> apps=new ArrayList<>();
		try {
			apps = applicationService.findAppNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERR][STATISTIC] ", e);
		}
		List<BaseCompany> companys = new ArrayList<>();
		try {
			companys = companyService.findCompanyKeyBySort(map);
		} catch (Exception e) {
			logger.error("[ERR][STATISTIC] ", e);
		}
		request.setAttribute("apps", apps);
		request.setAttribute("companys", companys);
		return PATH+"billing";
	}

	
	@RequestMapping(value="/billing.action",method=RequestMethod.GET)
	@ResponseBody
	public void billing(String companyId,String appId,String startTime,String endTime,String[] settlement,String selectType,HttpServletResponse response) {
		
		logger.debug("[BING][BILLING] companyId="+companyId+",appId="+appId+",startTime="+startTime+",endTime="+endTime+",selectType="+selectType+",settlement="+Arrays.toString(settlement));
		if(StringUtil.isEmpty(companyId)&&StringUtil.isEmpty(appId)) {
			return ;
		}
		boolean isProvider = false;
		if ("2".equals(selectType)) {
			isProvider = true;
		}
		Map<String, String> map=new HashMap<>();
		map.put("appId", appId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("companyId", companyId);
		map.put("selectType", selectType);
		List<Statistics> findStatisticSum = billingService.findStatisticSum(map);
		Set<String> set=new HashSet<>();
		if(StringUtil.isEmpty(appId)) {
			try {
				set=applicationService.findAppIdByCompanyId(companyId);
			} catch (Exception e) {
				logger.error("[ERR][APP] EX="+e);
			}
			
		}else {
			set.add(appId);
		}
		Map<String, String> param=new HashMap<>();
		Map<String, List<Statistics>> statics=new HashMap<>();
		for(String id:set) {
			param.put("startTime", startTime);
			param.put("endTime", endTime);
			param.put("appId", id);
			param.put("selectType", selectType);
			List<Statistics> stats=billingService.findStatistic(param);
			if (stats.size() > 1) {
				logger.debug(stats.get(0).toString());
			}
			if(stats!=null && stats.size()>0) {
				statics.put(stats.get(0).getAppName(), stats);
			}
		}
		//公司名称
		String companyName="";
		if(findStatisticSum.size()>0) {
			companyName=findStatisticSum.get(0).getCompanyName();
		}
		
		for(Statistics stat:findStatisticSum) {
			int sum=0;
			for(String ment:settlement) {
				if("success".equals(ment)) {
					sum+=Integer.parseInt(stat.getReportDelivrdCharge());
				}else if("fail".equals(ment)){
					sum+=Integer.parseInt(stat.getReportUndelivCharge());
				}else if("unknow".equals(ment)) {
					sum+=Integer.parseInt(stat.getReportUnknownCharge());
				}
			}
			if(settlement.length!=0 && !settlement[0].equals("null")) {
				stat.setReportDelivrdCharge(String.valueOf(sum));
			}
		}
		boolean lement=false;
		for(String ment:settlement) {
			if("unknow".equals(ment)) {
				lement=true;
			}
		}
		//日期2018.11.11
		String billingDate=DateUtil.getbillingDate(startTime, endTime);
		//日期2018年11月11日
		String billingTime=DateUtil.getbillingTime(startTime, endTime);
		// 2018年9月账单
		String billingString=DateUtil.getbillingString(startTime, endTime);
		Map<String, Object> parammap=new HashMap<String, Object>();
		parammap.put("companyName", companyName);
		parammap.put("billingTime", billingTime);
		parammap.put("billingDate", billingDate);
		parammap.put("billingString", billingString);
		parammap.put("lement", lement);
		parammap.put("isProvider", isProvider);
		XSSFWorkbook workbook = null;
		try {
			workbook=ExcelUtils.createExcelBillFile(findStatisticSum,statics,parammap,"对账单");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| ParseException | IntrospectionException e) {
			e.printStackTrace();
		}
		OutputStream output;  
        try {  
        	response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(("久佳信通.xlsx"),"UTF-8"));  
	        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setDateHeader("Expires", 0);  
            output = response.getOutputStream();  
          
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
            bufferedOutPut.flush();  
            workbook.write(bufferedOutPut);  
            bufferedOutPut.close();  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	
	
	@RequestMapping("/goSubmitBill.action")
	public String goSubmitBill(HttpServletRequest request) {
		List<Application> apps=new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERR][STATISTIC] ", e);
		}
		request.setAttribute("apps", apps);
		return PATH+"submitBill";
	}
	
	
	@ResponseBody
	@RequestMapping("/validatorBillAccount.action")
	public ResultData validatorBillAccount(Integer companyId,String appName) {
		ResultData result = new ResultData();

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("companyId", companyId);
		paramMap.put("appName", appName);
		int total = 0;
		try {
			total = billingService.validatorBillAccount(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][validatorBillAccount] ", e);
		}
		if (total > 0) {
			result.setValid(true);
		} else {
			result.setValid(false);
		}
		return result;
	}

	@DataOperate(bussiness = Bussiness.bill, operation = Operate.SUBMIT)
	@RequestMapping(value="/submitBill.action")
	@ResponseBody
	public ResponseResult submitBill(Integer companyId,String appName,String providerName,String billDate,Long successCount,Long unknownCount,Long count,Double cost) {
		
		logger.debug("[BING][SUBMITBILL] companyId="+companyId+",appName="+appName+",providerName="+providerName+",billDate="+billDate+",successCount="+successCount+",unknownCount="+unknownCount+",count="+count+",cost="+cost);
		if (companyId == null||appName==null||billDate == null||successCount == null||unknownCount == null||count == null||cost == null) {
			return new ResponseResult(3,null,null);
			
		}
		billDate = billDate.trim();
		appName = appName.trim();
		providerName = providerName.trim();
		Integer status = -1;
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("companyId", companyId);
		paramMap.put("appName", appName);
		int total = 0;
		try {
			total = billingService.validatorBillAccount(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][validatorBillAccount] ", e);
		}
		JSONObject json = new JSONObject();
		json.put("account", appName);
		json.put("providerName", providerName);
		json.put("month", billDate);
		json.put("successCount", successCount);
		json.put("unknownCount", unknownCount);
		json.put("count", count);
		json.put("cost", cost);
		if (total == 0) {
			return new ResponseResult(2,null,json.toJSONString());
		}
		String httpResult = httpService.httpClientPostJson(rrdBillUrl, json.toJSONString());
		if (httpResult == null) {
			return new ResponseResult(4,null,json.toJSONString());
		}
		JSONObject jsonObject = JSONObject.parseObject(httpResult);
		
		ResponseResult result = new ResponseResult();
		result.setNewData(jsonObject.toJSONString());
		result.setOldData(json.toJSONString());
		status = jsonObject.get("status") == null ? -1 :Integer.valueOf(String.valueOf(jsonObject.get("status")));
		if(status == 0){
			result.setResult(0);
		}else {
			result.setResult(1);
		}
		return result;
	}
	
}
