package com.jjxt.ssm.controller;

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

import com.jjxt.ssm.entity.Alarm;
import com.jjxt.ssm.service.AlarmService;

@Controller
@RequestMapping("/alarm")
public class AlarmModelController {

	private static Logger logger = Logger.getLogger(AlarmModelController.class);

	private static final String PATH = "alarm/";
	@Autowired
	private AlarmService alarmService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goAlarmModelPageList.action")
	public String goAlarmPageList() {

		return PATH + "alarmModelList";
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	
	@RequestMapping(value = "/findAllAlarmModel.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<Alarm> findAllAlarmModel() {

		List<Alarm> alarmList = null;
		try {
			alarmList = alarmService.findAllAlarmModel();
		} catch (Exception e) {
			logger.error("[ALARM] find all model is error.E={}", e);
		}
		return alarmList;
	}

	/**
	 * 添加
	 * @param alarm
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.INSERT)
	@RequestMapping(value = "/addToAlarmModel.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToAlarmModel(HttpServletRequest request, HttpServletResponse response,
			String modelName, String modelPwd, String authIp, String sign) {

		Long startTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		int add = 0;
		map.put("modelName", modelName);
		map.put("modelPwd", modelPwd);
		map.put("authIp", authIp);
		map.put("sign", sign);
		
		try {
			add = alarmService.addToAlarmModel(map);
		} catch (Exception e) {
			logger.error("[ALARM] add model is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
				+ add);

		return new ResponseResult(add, map, null);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.DELETE)
	@RequestMapping("/deleteAlarmModelById.action")
	@ResponseBody
	public ResponseResult deleteAlarmModelById(Integer modelId) {

		int del = 0;
		Long startTime = System.currentTimeMillis();
		Alarm oldData = null;
		try {
			oldData = alarmService.findAlarmModelById(modelId);
		} catch (Exception e) {
			logger.error("[ALARM] find old model is error.E={}", e);
		}
		int i = 1;
		try {
			i = alarmService.findAlarmConfigIsExistModel(modelId);
		} catch (Exception e) {
			logger.error("[ALARM] findAlarmConfigIsExistModel is error.E={}", e);
		}
		if(i > 0) {
			return new ResponseResult(2, null, oldData);
		}
		try {
			del = alarmService.deleteAlarmModelById(modelId);
		} catch (Exception e) {
			logger.error("[ALARM] del model is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] oldData=" + oldData + ",time=" + (endTime - startTime) + "ms,result="
				+ del);

		return new ResponseResult(del, null, oldData);
	}

	
	@RequestMapping("/findAlarmModelById.action")
	@ResponseBody
	public Alarm findAlarmModelById(Integer modelId) {

		Alarm alarm = null;
		try {
			alarm = alarmService.findAlarmModelById(modelId);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select model error.E={}", e);
		}
		return alarm;
	}

	/**
	 * 修改
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateAlarmModel.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateAlarmModel(HttpServletRequest request, HttpServletResponse response,
			Integer modelId, String modelName, String modelPwd, String authIp, String sign) {
		int update = 0;
		Alarm oldData = null;
		Map<String, Object> map = new HashMap<String, Object>();
			Long startTime = System.currentTimeMillis();
			map.put("modelId", modelId);
			map.put("modelName", modelName);
			map.put("modelPwd", modelPwd);
			map.put("authIp", authIp);
			map.put("sign", sign);
			try {
				oldData = alarmService.findAlarmModelById(modelId);
			} catch (Exception e1) {
				logger.error("[ALARM] select oldmodel error.E={}", e1);
			}
			try {
				update = alarmService.updateAlarmModel(map);
			} catch (Exception e) {
				logger.error("[ALARM] update model is error.E={}", e);
			}
			Long endTime = System.currentTimeMillis();
			logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
					+ update);
		
		return new ResponseResult(update, map, oldData);
	}

	
	@RequestMapping("/validatorAuthIp.action")
	@ResponseBody
	public ResultData validatorAuthIp(String authIp){
		ResultData result = new ResultData();
		Boolean res = true;
		String[] ips = authIp.split(",");
		for (String ip : ips) {
			String[] l = ip.trim().split("\\.");
			if(l.length != 4){
				res = false;
				break;
			}
		}
		result.setValid(res);
		return result;
	}

	
	@RequestMapping("/validatorModelName.action")
	@ResponseBody
	public ResultData validatorModelName(String modelName){
		ResultData result = new ResultData();
		int total = 0;
		try {
			total = alarmService.validatorModelName(modelName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(total > 0){
			result.setValid(false);
		}else{
			result.setValid(true);			
		}
		return result;
	}
	
	

}
