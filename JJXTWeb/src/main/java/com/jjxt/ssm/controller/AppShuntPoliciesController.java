package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.AppShuntPolicies;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.AppShuntPoliciesService;
import com.jjxt.ssm.service.ApplicationService;

@Controller
@RequestMapping("/appShuntPolicies")
public class AppShuntPoliciesController {

	private static Logger logger = Logger.getLogger(AppShuntPoliciesController.class);

	private static final String PATH = "appShuntPolicies/";

	@Autowired
	AppShuntPoliciesService appShuntPoliciesService;
	@Autowired
	private ApplicationService applicationService;

	@RequestMapping("/goAppShuntPoliciesList.action")
	public String goComplainPageList(HttpServletRequest request) {
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
			logger.error("[ERROR][FIND_APP]e=", e);
		}
		request.setAttribute("apps", apps);
		return PATH + "appShuntPoliciesList";
	}

	
	@RequestMapping("/findAppShuntPoliciesPageList.action")
	@ResponseBody
	public PageResult<AppShuntPolicies> findAppShuntPoliciesPageList(HttpServletRequest request, Integer pageSize, Integer pageIndex,
			String appName, String ignoredProvinces, String ignoredCarriers, String ignoredPackMin,
			String ignoredPackHead, String ignoredPackTail) {

		logger.debug("[BINS][AppShuntPolicies] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appName=" + appName
				+ ",ignoredProvinces=" + ignoredProvinces + ",ignoredCarriers=" + ignoredCarriers + ",ignoredPackMin="
				+ ignoredPackMin+",ignoredPackHead="+ignoredPackHead+",ignoredPackTail="+ignoredPackTail);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appName", appName);
		paramMap.put("ignoredProvinces", ignoredProvinces);
		paramMap.put("ignoredCarriers", ignoredCarriers);
		paramMap.put("ignoredPackMin", ignoredPackMin);
		paramMap.put("ignoredPackHead", ignoredPackHead);
		paramMap.put("ignoredPackTail", ignoredPackTail);
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			paramMap.put("chineseName", ucenterManager.getChineseName());
			paramMap.put("title", ucenterManager.getTitle());
			paramMap.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			paramMap.put("isAllChannel", ucenterManager.getIsAllChannel());
		}

		int total = 0;
		try {
			total = appShuntPoliciesService.findAppShuntPoliciesTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][FIND_ASP_TOTAL]e=", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appName", appName);
		map.put("ignoredProvinces", ignoredProvinces);
		map.put("ignoredCarriers", ignoredCarriers);
		map.put("ignoredPackMin", ignoredPackMin);
		map.put("ignoredPackHead", ignoredPackHead);
		map.put("ignoredPackTail", ignoredPackTail);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<AppShuntPolicies> list = new ArrayList<>();
		try {
			list = appShuntPoliciesService.findAppShuntPoliciesPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][FIND_ASP_LIST]e=", e);
		}

		return new PageResult<>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.appShuntPolicies, operation = Operate.INSERT)
	@RequestMapping(value = "/addAppShuntPolicies.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addAppShuntPolicies(HttpServletRequest request, HttpServletResponse response,
			AppShuntPolicies appShuntPolicies) {
		appShuntPolicies=checkASP(appShuntPolicies);
		logger.debug("[BINS][INSERT_ASP] " + appShuntPolicies);
		List<AppShuntPolicies> list = new ArrayList<AppShuntPolicies>();
		
		list.add(appShuntPolicies);
		if(appShuntPolicies==null){
			return new ResponseResult(0,null,null);
		}
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", appShuntPolicies.getAppId().toString());
		map.put("appShuntLevel", appShuntPolicies.getAppShuntLevel().toString());
		int i = 0;
		try {
			i = appShuntPoliciesService.addAppShuntPolicies(appShuntPolicies);
			if(i==0){
				return new ResponseResult(i, list, null);
			}
			
			//修改账户表的扣量比例字段
			i=applicationService.chargeAccountShuntPolicies(map);
			if(i==0){
				return new ResponseResult(i, list, null);
			}
			
			if(Boolean.parseBoolean(appShuntPolicies.getIsSyncSubApp())){
				//同步到子账户
				List<Application> appList = applicationService.findChildByParentId(appShuntPolicies.getAppId());
				for (Application app : appList) {
					AppShuntPolicies asp = new AppShuntPolicies();
					asp.setAppId(app.getId());
					asp.setIgnoredProvinces(appShuntPolicies.getIgnoredProvinces());
					asp.setIgnoredCarriers(appShuntPolicies.getIgnoredCarriers());
					asp.setIgnoredPackMin(appShuntPolicies.getIgnoredPackMin());
					asp.setIgnoredPackHead(appShuntPolicies.getIgnoredPackHead());
					asp.setIgnoredPackTail(appShuntPolicies.getIgnoredPackTail());
					asp.setContent(appShuntPolicies.getContent());
					asp.setPercent(appShuntPolicies.getPercent());
					list.add(asp);
					int result = appShuntPoliciesService.addAppShuntPolicies(asp);
					if(result<0){
						return new ResponseResult(result, list, null);
					}
					
					map.put("id", app.getId());
					//修改账户表的扣量比例字段
					i=applicationService.chargeAccountShuntPolicies(map);
					if(i==0){
						return new ResponseResult(i, list, null);
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("[ERROR][INSERT_ASP]e=", e);
		}
		
		return new ResponseResult(i, list, null);
	}

	
	@RequestMapping("/findAppShuntPoliciesByAppId.action")
	@ResponseBody
	public AppShuntPolicies findAppShuntPoliciesById(Integer appId) {
		logger.debug("[BINS][INSERT_ASP] appId=" + appId);
		AppShuntPolicies appShuntPolicies = null;
		try {
			appShuntPolicies = appShuntPoliciesService.findAppShuntPoliciesByAppId(appId);
		} catch (Exception e) {
			logger.error("[ERROR][FIND_ASP]e=", e);
		}
		return appShuntPolicies;
	}

	
	@DataOperate(bussiness = Bussiness.appShuntPolicies, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateAppShuntPolicies.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateAppShuntPolicies(HttpServletRequest request, HttpServletResponse response,
			AppShuntPolicies appShuntPolicies) {
		
		appShuntPolicies=checkASP(appShuntPolicies);
		logger.debug("[BINS][UPDATE_ASP] " + appShuntPolicies);
		if(appShuntPolicies.getId()==null) {
			int i=0;
			try {
				i = appShuntPoliciesService.addAppShuntPolicies(appShuntPolicies);
			} catch (Exception e) {
				logger.error("[ERR][ASP] EX="+e);
			}
			return new ResponseResult(i, appShuntPolicies, null);
		}
		List<AppShuntPolicies> list = new ArrayList<AppShuntPolicies>();
		list.add(appShuntPolicies);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", appShuntPolicies.getAppId().toString());
		map.put("appShuntLevel", appShuntPolicies.getAppShuntLevel().toString());
		int i = 0;
		try {
			i = appShuntPoliciesService.updateAppShuntPolicies(appShuntPolicies);
			if(i==0){
				return new ResponseResult(i, list, null);
			}
			
			//修改账户表的扣量比例字段
			i=applicationService.chargeAccountShuntPolicies(map);
			if(i==0){
				return new ResponseResult(i, list, null);
			}
			
			if(Boolean.parseBoolean(appShuntPolicies.getIsSyncSubApp())){
				//同步到子账户
				List<Application> appList = applicationService.findChildByParentId(appShuntPolicies.getAppId());
				for (Application app : appList) {
					AppShuntPolicies asp = appShuntPoliciesService.findAppShuntPoliciesByAppId(app.getId());
					asp.setAppId(app.getId());
					asp.setIgnoredProvinces(appShuntPolicies.getIgnoredProvinces());
					asp.setIgnoredCarriers(appShuntPolicies.getIgnoredCarriers());
					asp.setIgnoredPackMin(appShuntPolicies.getIgnoredPackMin());
					asp.setIgnoredPackHead(appShuntPolicies.getIgnoredPackHead());
					asp.setIgnoredPackTail(appShuntPolicies.getIgnoredPackTail());
					asp.setContent(appShuntPolicies.getContent());
					asp.setPercent(appShuntPolicies.getPercent());
					list.add(asp);
					int result = appShuntPoliciesService.addAppShuntPolicies(asp);
					if(result<0){
						return new ResponseResult(result, list, null);
					}
					
					map.put("id", app.getId());
					//修改账户表的扣量比例字段
					i=applicationService.chargeAccountShuntPolicies(map);
					if(i==0){
						return new ResponseResult(i, list, null);
					}
				}
				
			}
		} catch (Exception e) {
			logger.error("[ERROR][UPDATE_ASP]e=", e);
		}
		return new ResponseResult(i, list, null);
	}

	
	@DataOperate(bussiness = Bussiness.appShuntPolicies, operation = Operate.DELETE)
	@RequestMapping(value = "/deleteAppShuntPoliciesById.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult deleteAppShuntPoliciesById(Integer appId) {
		logger.debug("[BINS][DEL_ASP] appId=" + appId);
		AppShuntPolicies appShuntPolicies = null;
		try {
			appShuntPolicies = appShuntPoliciesService.findAppShuntPoliciesByAppId(appId);
		} catch (Exception e1) {
			logger.error("[ERROR][DEL_ASP_ID]e=", e1);
		}
		int i = 0; 
		try {
			i = appShuntPoliciesService.deleteAppShuntPoliciesByAppId(appId);
		} catch (Exception e) {
			logger.error("[ERROR][DEL_ASP]e=", e);
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("id", appId);
		map.put("appShuntLevel", "0");
		try {
			i = applicationService.chargeAccountShuntPolicies(map);
		} catch (Exception e) {
			logger.error("[ERROR][DEL_ASP]e=", e);
		}
		
		return new ResponseResult(i, appShuntPolicies, null);
	}

	
	@DataOperate(bussiness = Bussiness.appShuntPolicies, operation = Operate.DELETEBATCH)
	@RequestMapping(value = "/delAppShuntPoliciesBatch.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult delAppShuntPoliciesBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][DELBATCH_ASP] appids="+Arrays.toString(ids));
		AppShuntPolicies appShuntPolicies = null;
		List<AppShuntPolicies> oldList = new ArrayList<AppShuntPolicies>();
		int i = 0;
		ResponseResult result = null;
		Long sTime = System.currentTimeMillis();
		for (Integer id : ids) {
			try {
				appShuntPolicies = appShuntPoliciesService.findAppShuntPoliciesByAppId(id);
				oldList.add(appShuntPolicies);
			} catch (Exception e) {
				logger.error("[ERROR][DELBATCH_ASP]query old data,e=.", e);
			}
			
			try {
				i =appShuntPoliciesService.deleteAppShuntPoliciesByAppId(id);
			} catch (Exception e) {
				logger.error("[ERROR][DELBATCH_ASP] delete data,e=.", e);
			}
			
			//修改账户表的扣量比例字段
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", appShuntPolicies.getAppId().toString());
			map.put("appShuntLevel", "0");
			try {
				i = applicationService.chargeAccountShuntPolicies(map);
			} catch (Exception e) {
				logger.error("[ERROR][DEL_ASP]e=", e);
			}
			
			if (i == 0) {
				result = new ResponseResult();
				result.setResult(i);
				result.setNewData(null);
				result.setOldData(oldList);
				logger.debug("[BINS][DELBATCH_ASP]deletion failed,id=" + id);
				return result;
			}
			
		}
		result = new ResponseResult();
		result.setResult(i);
		result.setNewData(null);
		result.setOldData(oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][DELBATCH_ASP] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		return result;
	}
	
	public AppShuntPolicies checkASP(AppShuntPolicies appShuntPolicies){
		if(StringUtil.isEmpty(appShuntPolicies.getIgnoredCarriers())){
			appShuntPolicies.setIgnoredCarriers("");
		}
		if(StringUtil.isEmpty(appShuntPolicies.getIgnoredProvinces())){
			appShuntPolicies.setIgnoredProvinces("");
		}
		if(StringUtil.isEmpty(appShuntPolicies.getIgnoredPackMin())){
			appShuntPolicies.setIgnoredPackMin(0);
		}
		if(StringUtil.isEmpty(appShuntPolicies.getIgnoredPackHead())){
			appShuntPolicies.setIgnoredPackHead(0);
		}
		if(StringUtil.isEmpty(appShuntPolicies.getIgnoredPackTail())){
			appShuntPolicies.setIgnoredPackTail(0);
		}
		if(StringUtil.isEmpty(appShuntPolicies.getAppShuntLevel())){
			appShuntPolicies.setAppShuntLevel(0);
		}
		if(StringUtil.isEmpty(appShuntPolicies.getContent())){
			appShuntPolicies.setContent("");;
		}
		if(StringUtil.isEmpty(appShuntPolicies.getPercent())){
			appShuntPolicies.setPercent(0);;
		}
		
		return appShuntPolicies;
	}

}
