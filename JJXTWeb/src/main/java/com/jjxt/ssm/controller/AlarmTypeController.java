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
public class AlarmTypeController {

	private static Logger logger = Logger.getLogger(AlarmTypeController.class);

	private static final String PATH = "alarm/";
	@Autowired
	private AlarmService alarmService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goAlarmTypePageList.action")
	public String goAlarmPageList() {

		return PATH + "alarmTypeList";
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	
	@RequestMapping(value = "/findAllAlarmType.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<Alarm> findAllAlarmType() {

		List<Alarm> alarmList = null;
		try {
			alarmList = alarmService.findAllAlarmType();
		} catch (Exception e) {
			logger.error("[ALARM] find all type is error.E={}", e);
		}
		return alarmList;
	}

	/**
	 * 添加
	 * @param alarm
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.INSERT)
	@RequestMapping(value = "/addToAlarmType.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToAlarmType(HttpServletRequest request, HttpServletResponse response,
			String type, String typeModel, String isModel,String jsonParams,  String description) {

		Long startTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		int add = 0;
		map.put("type", type);
		map.put("typeModel", typeModel);
		map.put("isModel", isModel);
		map.put("jsonParams", jsonParams);
		map.put("description", description);
		
		try {
			add = alarmService.addToAlarmType(map);
		} catch (Exception e) {
			logger.error("[ALARM] add type is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
				+ add);

		return new ResponseResult(add, map, null);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.DELETE)
	@RequestMapping("/deleteAlarmTypeById.action")
	@ResponseBody
	public ResponseResult deleteAlarmTypeById(Integer typeId) {

		int del = 0;
		Long startTime = System.currentTimeMillis();
		Alarm oldData = null;
		try {
			oldData = alarmService.findAlarmTypeById(typeId);
		} catch (Exception e) {
			logger.error("[ALARM] find old type is error.E={}", e);
		}
		int i = 1;
		try {
			i = alarmService.findAlarmConfigIsExistType(typeId);
		} catch (Exception e) {
			logger.error("[ALARM] findAlarmConfigIsExistType is error.E={}", e);
		}
		if(i > 0) {
			return new ResponseResult(2, null, oldData);
		}
		try {
			del = alarmService.deleteAlarmTypeById(typeId);
		} catch (Exception e) {
			logger.error("[ALARM] del type is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] oldData=" + oldData + ",time=" + (endTime - startTime) + "ms,result="
				+ del);

		return new ResponseResult(del, null, oldData);
	}

	
	@RequestMapping("/findAlarmTypeById.action")
	@ResponseBody
	public Alarm findAlarmTypeById(Integer typeId) {

		Alarm alarm = null;
		try {
			alarm = alarmService.findAlarmTypeById(typeId);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select type error.E={}", e);
		}
		return alarm;
	}

	/**
	 * 修改
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateAlarmType.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateAlarmType(HttpServletRequest request, HttpServletResponse response,
			Integer typeId, String type, String typeModel, String isModel,String jsonParams, String description) {
		int update = 0;
		Alarm oldData = null;
		Map<String, Object> map = new HashMap<String, Object>();
			Long startTime = System.currentTimeMillis();
			map.put("typeId", typeId);
			map.put("type", type);
			map.put("typeModel", typeModel);
			map.put("isModel", isModel);
			map.put("jsonParams", jsonParams);
			map.put("description", description);
			try {
				oldData = alarmService.findAlarmTypeById(typeId);
			} catch (Exception e1) {
				logger.error("[ALARM] select oldtype error.E={}", e1);
			}
			try {
				update = alarmService.updateAlarmType(map);
			} catch (Exception e) {
				logger.error("[ALARM] update type is error.E={}", e);
			}
			Long endTime = System.currentTimeMillis();
			logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
					+ update);
		
		return new ResponseResult(update, map, oldData);
	}

}
