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
public class AlarmUserController {

	private static Logger logger = Logger.getLogger(AlarmUserController.class);

	private static final String PATH = "alarm/";
	@Autowired
	private AlarmService alarmuserService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goAlarmUserPageList.action")
	public String goAlarmPageList(HttpServletRequest request) {
		
		return PATH + "alarmUserList";
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/findAllAlarmUser.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<Alarm> findAllAlarmUser() {

		List<Alarm> alarmList = null;
		try {
			alarmList = alarmuserService.findAllAlarmUser();
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select user error.E={}", e);
		}
		return alarmList;
	}

	/**
	 * 添加
	 * @param alarm
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.INSERT)
	@RequestMapping(value = "/addToAlarmUser.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToAlarmUser(HttpServletRequest request, HttpServletResponse response,
			String userName, String email, String phoneNumber, String describe, String wechat) {

		Long startTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Alarm> configList = new ArrayList<>();
		int add = 0;
		int userId = 0;
		int addRelation = 0;
		
		map.put("userName", userName);
		map.put("email", email);
		map.put("phoneNumber", phoneNumber);
		map.put("describe", describe);
		map.put("wechat", wechat);
		
		try {
			add = alarmuserService.addToAlarmUser(map);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] add user error.E={}", e);
		}
		try {
			userId = alarmuserService.findUserIdByName(userName);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find userId by name error.E={}", e);
		}
		try {
			configList = alarmuserService.findStrategyConfigIdList();
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find typeId list is error.E={}", e);
		}
		ResponseResult result = new ResponseResult();
		if(configList != null && configList.size() > 0) {
		for (Alarm alarm : configList) {
			alarm.setUserId(userId);
		}
		try {
			addRelation = alarmuserService.insertStrategySend(configList);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] insert listBean is error.E={}", e);
		}
		result.setResult(addRelation);
		}else {
		result.setResult(add);
		}
		result.setNewData(map);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.DELETE)
	@RequestMapping("/deleteAlarmUserById.action")
	@ResponseBody
	public ResponseResult deleteAlarmUserById(Integer id) {

		int del = 0;
		int delRelation = 0;
		Long startTime = System.currentTimeMillis();
		Alarm oldData = null;
		try {
			oldData = alarmuserService.findAlarmUserById(id);

		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select user by id is error.E={}", e);
		}
		try {
			del = alarmuserService.deleteAlarmUserById(id);

		} catch (Exception e) {
			logger.error("[ERROR][ALARM] delete user is error.E={}", e);
		}
		try {
			//删除strategy_send数据
			delRelation = alarmuserService.delRelationByuserId(id);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] delete relation by userId is error.E={}", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] oldData=" + oldData + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	
	@RequestMapping("/findAlarmUserById.action")
	@ResponseBody
	public Alarm findAlarmUserById(Integer id) {

		Alarm alarm = null;
		try {
			alarm = alarmuserService.findAlarmUserById(id);

		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find alarmuser by id is error.E={}", e);
		}
		return alarm;
	}

	/**
	 * 修改
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateAlarmUser.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateAlarmUser(HttpServletRequest request, HttpServletResponse response,
			Integer userId, String userName, String email, String phoneNumber, String describe,String wechat) {
		int update = 0;
		ResponseResult result = new ResponseResult();
		Alarm oldData = null;
		Map<String, Object> map = new HashMap<String, Object>();
			Long startTime = System.currentTimeMillis();
			map.put("userId", userId);
			map.put("userName", userName);
			map.put("email", email);
			map.put("phoneNumber", phoneNumber);
			map.put("describe", describe);
			map.put("wechat", wechat);
			try {
				oldData = alarmuserService.findAlarmUserById(userId);
			} catch (Exception e1) {
				logger.error("[ERROR][ALARM] find user by id error.E={}", e1);
			}
			try {
				update = alarmuserService.updateAlarmUser(map);
					result.setResult(update);
					result.setNewData(map);
					result.setOldData(oldData);
					Long endTime = System.currentTimeMillis();
					logger.debug("[BINS][ALARM] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
							+ result.getResult());

			} catch (Exception e) {
				logger.error("[ERROR][ALARM] update user is error.E={}", e);
			}

		return result;

	}


	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.INSERT)
	@RequestMapping(value = "/addStrategySend.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addStrategySend(String userId, String typeId, String isPhone, String isEmail, String isWechat){
		int add = 0;
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("userId", userId);
			map.put("typeId", typeId);
			map.put("isPhone", isPhone);
			map.put("isEmail", isEmail);
			map.put("isWechat", isWechat);
			add = alarmuserService.addStrategySend(map);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] add StrategySend is error.E={}", e);
		}
		logger.info("[BINS][ALARM] newData=" + map + ",result=" + add);
		return new ResponseResult(add, map, null);
	}

	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.DELETE)
	@RequestMapping("/delSendById.action")
	@ResponseBody
	public ResponseResult delSendById(Integer sendId) {

		int del = 0;
		Long startTime = System.currentTimeMillis();
		try {
			del = alarmuserService.delSendById(sendId);

		} catch (Exception e) {
			logger.error("[ERROR][ALARM] delete strategy_send is error.E={}", e);
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][ALARM] sendId=" + sendId + ",time=" + (endTime - startTime) + "ms,result="
				+ del);

		return new ResponseResult(del, null, sendId);
	}
	
	@RequestMapping("/validatorUserName.action")
	@ResponseBody
	public ResultData validatorUserName(String userName){
		ResultData result = new ResultData();
		int total = 0;
		try {
			total = alarmuserService.validatorUserName(userName);
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
