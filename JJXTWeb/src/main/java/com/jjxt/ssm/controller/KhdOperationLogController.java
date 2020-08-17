package com.jjxt.ssm.controller;

import java.util.ArrayList;
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
import com.jjxt.ssm.entity.KhdOperationLog;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.KhdOperationLogService;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;

@Controller
@RequestMapping("/khdOperationLog")
public class KhdOperationLogController {
	private static Logger logger = Logger.getLogger(KhdOperationLogController.class);
	private static final String PATH = "khdOperationLog/";
	@Autowired
	private KhdOperationLogService khdOperationLogservice;
	@Autowired
	private ApplicationService applicationService;
	/**
	 * 跳转菜单分页页面
	 * 
	 * @return
	 */
	@RequestMapping("/findKhdOperationLogList.action")
	public String goMTPageList(HttpServletRequest request) {
		List<Application> apps=new ArrayList<>();
		try {
			apps=applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERR][APP] find list err.ex={}",e);
		}
		request.setAttribute("apps", apps);
		return PATH + "khdOperationLogList";
	}

	/**
	 * 查询所有的菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "/findKhdOperationLogList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<KhdOperationLog> findMTPageList(Integer pageSize, Integer pageIndex, String sort,
			String sortOrder, String bussiness, String operation, String remarks, String appId,
			String startTime, String endTime, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[BINS][LOG] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",sort=" + sort + ",sortOrder="
				+ sortOrder + ",bussiness=" + bussiness + ",operation=" + operation + ",remarks=" + remarks
				+ ",appId=" + appId + ",startTime=" + startTime + ",endTime=" + endTime);

		Map<String, Object> map = new HashMap<String, Object>();
		List<KhdOperationLog> logs=new ArrayList<KhdOperationLog>();
		map.put("bussiness", bussiness);
		map.put("operation", operation);
		map.put("remarks", remarks);
		map.put("appId", appId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		int total = 0;
		try {
			total = khdOperationLogservice.findTotal(map);
		} catch (Exception e1) {
			logger.error("[ERROR:PUT_MT_TOTAL] put mt_total is error", e1);
		}
		if(total==0){
			return new PageResult<KhdOperationLog>(total, logs);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("bussiness", bussiness);
			map1.put("operation", operation);
			map1.put("remarks", remarks);
			map1.put("appId", appId);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);

			logs = khdOperationLogservice.findKhdOperationLogList(map1);
			return new PageResult<KhdOperationLog>(total, logs);
		} catch (Exception e) {
			logger.error("[ERROR:PUT_MT_PAGElIST] put mt pageList is error", e);
		}
		return new PageResult<KhdOperationLog>(total, logs);
	}
}
