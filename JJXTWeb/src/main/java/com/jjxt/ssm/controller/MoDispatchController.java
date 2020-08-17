package com.jjxt.ssm.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Dispatch;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.MoDispatchService;

@Controller
@RequestMapping("/dispatch")
public class MoDispatchController {
	
	private static Logger logger = Logger.getLogger(MoDispatchController.class);
	
	private static final String PATH = "dispatch/";
	@Autowired
	private MoDispatchService dispatchService;
	@Autowired
	private ApplicationService appService;
	
	@RequestMapping("/goMoDispatchPage.action")
	public String goMoDispatchPage(HttpServletRequest request){
		List<Application> names = new ArrayList<>();
		try {
			names = appService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] ", e);
		}
		request.setAttribute("names", names);
		return PATH + "moDispatchList";
	}

	
	@RequestMapping("/findAllList.action")
	@ResponseBody
	public PageResult<Dispatch> findAllList(HttpServletRequest request, HttpServletResponse response, String appName, String longNum){
		
		List<Dispatch> lists = new ArrayList<Dispatch>();
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		try {
			map.put("appName", appName);
			map.put("longNum", longNum);
			lists = dispatchService.findAllList(map);
			total = lists.size();
			
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] 查询全部上行分拣数据出错.", e);
		}
		
		return new PageResult<Dispatch>(total, lists);
	}

	
	@DataOperate(bussiness = Bussiness.modispatch, operation = Operate.INSERT)
	@RequestMapping("/addToDispatch.action")
	@ResponseBody
	public ResponseResult addToDispatch(HttpServletRequest request, HttpServletResponse response, String appName, String longNum,String command){
		
		int res = 0;
		ResponseResult result = new ResponseResult();
		Map<String, Object> map = new HashMap<String, Object>();
		
		int data = 0;
		try {
			data = dispatchService.findMoDispatchData(longNum);
		} catch (Exception e1) {
			logger.error("[ERROR][DISPATCH] ", e1);
		}
		if (data == 1) {
			
			result.setResult(res);
		} else {
			
			int selectResult = 0;
			try {
				selectResult = appService.findAppByName(appName);
			} catch (Exception e1) {
				logger.error("[ERROR][DISPATCH] ", e1);
			}
			if(selectResult == 1 && longNum.length()>=5 && longNum.length()<=21){
				Long sTime = System.currentTimeMillis();
				try {
					map = dispatchService.findTwoId(appName);
					map.put("longNum", longNum);
					
					res = dispatchService.addToDispatch(map);
					
				} catch (Exception e) {
					logger.error("[ERROR][DISPATCH] 查询全部上行分拣数据出错.", e);
				}
				result.setResult(res);
				result.setNewData(map);
				result.setOldData(null);
				Long eTime = System.currentTimeMillis();
				logger.debug(
						"[BINS][DISPATCH] addData=" + map + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

				
			}else{
				
				if(selectResult != 1){
					res = 2;
					
				}else{
					res = 3;
				}
				
				result.setResult(res);
			}
			

		}
		

		
		
		return result;
	}

	
	@DataOperate(bussiness = Bussiness.modispatch, operation = Operate.DELETE)
	@RequestMapping("/delDispatch.action")
	@ResponseBody
	public ResponseResult delDispatch(Integer id){
		
		int del = 0;
		Map<String, Object> oldData = new HashMap<String, Object>();
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = dispatchService.findListById(id);
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] 查询旧数据出错.", e);
		}
		try {
			del = dispatchService.delDispatchById(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug(
					"[BINS][DISPATCH] oldData=" + oldData + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][DISPATCH] 删除args_dispatch_mo表数据出错.", e1);
		}
		
		return result;
	}

	
	@RequestMapping("/findMoDispatchById.action")
	@ResponseBody
	public Dispatch findMoDispatchById(Integer id){
		
		Dispatch dispatch = null;
		try {
			dispatch = dispatchService.findMoDispatchById(id);
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] 查询args_dispatch_mo表数据出错.", e);
		}
		
		return dispatch;
	}

	
	@DataOperate(bussiness = Bussiness.modispatch, operation = Operate.UPDATE)
	@RequestMapping("/updateDispatch.action")
	@ResponseBody
	public ResponseResult updateDispatch(HttpServletRequest request, HttpServletResponse response, Dispatch dispatch){
		
		int res = 0;
		ResponseResult result = new ResponseResult(0,null,null);
		Integer id = dispatch.getId();
		Map<String, Object> oldData = new HashMap<String, Object>();
		List<Object> newData = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = dispatchService.findListById(id);
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] 查询旧数据出错.", e);
		}
		try {
			
			map = dispatchService.findIdsByName(dispatch.getAppName());
			map.put("id", dispatch.getId());
			map.put("longNum", dispatch.getLongNum());
			res = dispatchService.updateDispatch(map);
			newData.add(map);
			result.setResult(res);
			result.setNewData(newData);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug(
					"[BINS][DISPATCH] newData=" + newData + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][DISPATCH] 修改上行分拣数据出错.", e1);
		}
				
		return result;
	}

	
	@RequestMapping("/validatorLongNum.action")
	@ResponseBody
	public ResultData validatorLongNum(String longNum,String oldNum) {
		logger.debug("[BINS][DSIPATCH] longNum="+longNum+",oldNum="+oldNum);
		ResultData result = new ResultData();
		if(longNum.equals(oldNum)){
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("num", longNum);
		int total = 0;
		try {
			total = dispatchService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][DISPATCH] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}

	
	@DataOperate(bussiness = Bussiness.modispatch, operation = Operate.DELETEBATCH)
	@RequestMapping("/delMoDispatchBatch.action")
	@ResponseBody
	public ResponseResult delMoDispatchBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		
		int del = 0;
		ResponseResult result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Long sTime = System.currentTimeMillis();
		for (Integer id : ids) {
			
			try {
				map = dispatchService.findListById(id);
				list.add(map);
				
			} catch (Exception e) {
				logger.error("[ERROR][DISPATCH] 查询旧数据出错.", e);
			}
			
			try {
				del = dispatchService.delDispatchById(id);
			} catch (Exception e) {
				logger.error("[ERROR][DISPATCH] 删除批量数据出错.", e);
			}
			
			if(del != 1){
				result = new ResponseResult();
				result.setResult(del);
				result.setNewData(null);
				result.setOldData(list);
				logger.debug("[BINS][DISPATCH] 删除数据失败,id=" + id);
				
				return result;
			}
			
			
		}
		result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(list);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][CHECK] oldData=" + list + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		
		return result;
	}
	
	
	

}
