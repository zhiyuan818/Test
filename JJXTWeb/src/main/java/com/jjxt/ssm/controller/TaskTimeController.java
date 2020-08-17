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
import com.jjxt.ssm.entity.TaskTime;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.TaskTimeService;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;

@Controller
@RequestMapping("/taskTime")
public class TaskTimeController {

	private static Logger logger = Logger.getLogger(TaskTimeController.class);
	
	private static final String PATH = "taskTime/";
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private TaskTimeService taskTimeService;
	
	@RequestMapping("/goTaskTimePageList.action")
	public String goTaskTime(HttpServletRequest request, HttpServletResponse response){
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		request.setAttribute("apps", apps);
		return PATH + "taskTime";
	}
	
	@RequestMapping("/findTaskTimeList.action")
	@ResponseBody
	public PageResult<TaskTime> findTaskTimeList(String appId,Integer pageSize,Integer pageIndex,String status,HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		Long sTime = System.currentTimeMillis();
		logger.debug("[BINS][TASKTIME] appId="+appId+",pageSize="+pageSize+",pageIndex="+pageIndex+",status="+status);
		map.put("appId", appId);
		map.put("status", status);
		int total=0;
		try {
			total = taskTimeService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERR][TASKTIME] ex="+e);
		}
		if(total==0) {
			return new PageResult<>(total, new ArrayList<>());
		}
		Page page=new Page(pageSize, total, pageIndex);
		Map<String, Object> param = PageUtil.getDefaultPageMap(page);
		param.put("appId", appId);
		param.put("status", status);
		List<TaskTime> mtList=new ArrayList<>();
		try {
			mtList=taskTimeService.findTaskTimePageList(param);
		} catch (Exception e) {
			logger.error("[ERR][TASKTIME] ex="+e);
		}
		logger.debug("[BINS][TASKTIME] TASKTIME size ="+mtList.size()+",time="+(System.currentTimeMillis()-sTime));
		return new PageResult<TaskTime>(total, mtList);
	}
	
}
