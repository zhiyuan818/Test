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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Alarm;
import com.jjxt.ssm.service.AlarmService;

@Controller
@RequestMapping("/alarm")
public class AlarmConfigController {

	private static Logger logger = Logger.getLogger(AlarmConfigController.class);

	private static final String PATH = "alarm/";
	@Autowired
	private AlarmService alarmService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goAlarmConfigPageList.action")
	public String goAlarmPageList(HttpServletRequest request) {
		List<Alarm> models = new ArrayList<>();
		List<Alarm> types = new ArrayList<>();
		try {
			models = alarmService.findAllModels();
		} catch (Exception e) {
			logger.error("[ALARM] select allModel error.E={}", e);
		}
		try {
			types = alarmService.findAllTypes();
		} catch (Exception e) {
			logger.error("[ALARM] select allType error.E={}", e);
		}
		request.setAttribute("models", models);
		request.setAttribute("types", types);
		return PATH + "alarmConfigList";
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	
	@RequestMapping(value = "/findAllStrategyConfig.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<Alarm> findAllStrategyConfig(Integer typeId,Integer modelId) {

		List<Alarm> alarmList = null;
		Map<String ,Integer> map = new HashMap<>();
		map.put("typeId", typeId);
		map.put("modelId", modelId);
		try {
			alarmList = alarmService.findAllStrategyConfig(map);
		} catch (Exception e) {
			logger.error("[ALARM] find all config is error.E={}", e);
		}
		return alarmList;
	}

	/**
	 * 添加
	 * @param alarm
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.INSERT)
	@RequestMapping(value = "/addStrategyConfig.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addStrategyConfig(HttpServletRequest request, HttpServletResponse response,
			Integer modelId, Integer typeId, Integer level, String startTime, String endTime, String cycle, String ignore, String strategy,String status,String description ) {

		logger.debug("[BINS][ALARM] modelId=" + modelId + ",typeId=" + typeId + "startTime="+startTime+
				"endTime="+endTime+ "cycle="+cycle+ "ignore="+ignore+ "strategy="+strategy+ "status="+status+ "description=" + description);
		Long sTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Integer> userList = new ArrayList<>();
		List<Alarm> listBean = new ArrayList<>();
		Alarm bean = null;
		int add = 0;
		int i = 0;
		int addRelation = 0;
		map.put("modelId", modelId);
		map.put("typeId", typeId);
		map.put("level", level);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("cycle", cycle);
		map.put("ignore", ignore);
		map.put("strategy", strategy);
		map.put("status", status);
		map.put("description", description);
		try {
			i = alarmService.findIsExistStrategyConfig(map);
			if (i > 0) {
				return new ResponseResult(2, map, null);
			}
		} catch (Exception e) {
			logger.error("[ALARM] find config is error.E={}", e);
		}
		try {
			add = alarmService.addStrategyConfig(map);
		} catch (Exception e) {
			logger.error("[ALARM] add config is error.E={}", e);
		}
		try {
			i = alarmService.findIsExistStrategySend(map);
			
		} catch (Exception e) {
			logger.error("[ALARM] find config is error.E={}", e);
		}
		ResponseResult result = new ResponseResult();
		result.setNewData(map);
		if(i == 0) {
		try {
			userList = alarmService.findUserIdList();
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find userId list is error.E={}", e);
		}
		if(userList != null && userList.size() > 0) {
		for (Integer userId : userList) {
		bean = new Alarm();
		bean.setTypeId(typeId);
		bean.setUserId(userId);
		bean.setLevel(level);
		listBean.add(bean);
		}
		try {
		addRelation = alarmService.insertStrategySend(listBean);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] insert listBean is error.E={}", e);
		}
		if(addRelation > 0) {
			result.setResult(1);
		}
		}else {
			result.setResult(add);
		}
	}else {
		result.setResult(add);
	}
		
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (eTime - sTime) + "ms,result="
				+ add);

		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.DELETE)
	@RequestMapping("/delConfigById.action")
	@ResponseBody
	public ResponseResult delConfigById(Integer configId) {

		int del = 0;
		int delRelation = 0;
		Long startTime = System.currentTimeMillis();
		Alarm oldData = null;
		Map<String,Integer> map = new HashMap<>();
		try {
			oldData = alarmService.findConfigById(configId);
			map.put("typeId", oldData.getTypeId());
			map.put("level", oldData.getLevel());
		} catch (Exception e) {
			logger.error("[ALARM] find old config is error.E={}", e);
		}
		
		int count = 0;
		try {
			count = alarmService.findConfigByTypeLevel(map);
		} catch (Exception e) {
			logger.error("[ALARM] findConfigByTypeLevel error.E={}", e);
		}
		if(count == 1) {
			try {
				delRelation = alarmService.delRelationBytypeId(configId);
			} catch (Exception e) {
				logger.error("[ALARM] del relation by typeId is error.E={}", e);
			}
		}
			
		try {
			del = alarmService.delConfigById(configId);
		} catch (Exception e) {
			logger.error("[ALARM] del config is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] oldData=" + oldData + ",time=" + (endTime - startTime) + "ms,result="
				+ del);

		return new ResponseResult(del, null, oldData);
	}

	
	@RequestMapping("/findConfigById.action")
	@ResponseBody
	public Alarm findConfigById(Integer configId) {

		Alarm alarm = null;
		try {
			alarm = alarmService.findConfigById(configId);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select config error.E={}", e);
		}
		return alarm;
	}

	/**
	 * 修改
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.UPDATE)
	@RequestMapping("/updateConfigById.action")
	@ResponseBody
	public ResponseResult updateConfigById(HttpServletRequest request, HttpServletResponse response,
			Integer configId, String startTime, String endTime, String cycle, String ignore, String strategy,String status,String description) {
		int update = 0;
		Alarm oldData = null;
		Map<String, Object> map = new HashMap<String, Object>();
			Long sTime = System.currentTimeMillis();
			map.put("configId", configId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("cycle", cycle);
			map.put("ignore", ignore);
			map.put("strategy", strategy);
			map.put("status", status);
			map.put("description", description);
			try {
				oldData = alarmService.findConfigById(configId);
			} catch (Exception e1) {
				logger.error("[ALARM] select oldconfig error.E={}", e1);
			}
			try {
				update = alarmService.updateConfigById(map);
			} catch (Exception e) {
				logger.error("[ALARM] update config is error.E={}", e);
			}
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (eTime - sTime) + "ms,result="
					+ update);
		
		return new ResponseResult(update, map, oldData);
	}

	
	@RequestMapping("/findTypeDetail.action")
	@ResponseBody
	public Alarm findTypeDetail(Integer typeId){
		Alarm alarm = new Alarm();
		try {
			alarm = alarmService.findTypeDetail(typeId);
		} catch (Exception e) {
			logger.error("[ALARM] select is error.E={}", e);
		}
		return alarm;
	}

}
