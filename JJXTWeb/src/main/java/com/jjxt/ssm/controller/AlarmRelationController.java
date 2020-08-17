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

import net.sf.json.JSONArray;



@Controller
@RequestMapping("/alarm")
public class AlarmRelationController {

	private static Logger logger = Logger.getLogger(AlarmRelationController.class);
	private static final String PATH = "alarm/";
	@Autowired
	private AlarmService alarmServer;
	
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/goAlarmRelation.action")
	public String goAlarmRelation(HttpServletRequest request){
		List<Alarm> types = new ArrayList<>();
		List<Alarm> users = new ArrayList<>();
		try {
			types = alarmServer.findAllTypes();
			users = alarmServer.findAllUsers();
		} catch (Exception e) {
			logger.error("[ALARM] select types err.E={}", e);
		}
		request.setAttribute("types", types);
		request.setAttribute("users", users);
		return PATH + "alarmRelationList";
	}
	
	/**
	 * 用户列表
	 * @param typeId
	 * @return
	 */
	
	@RequestMapping(value = "/findUserRelation.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<Alarm> findUserRelation(Integer pageIndex, Integer pageSize,String level, Integer typeId, Integer selectType){
		int total = 0;
		List<Alarm> userList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		try {
			paramMap.put("typeId", typeId);
			paramMap.put("level", level);
			paramMap.put("selectType", selectType);
			total = alarmServer.findUserRelationTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find userRelation total err.E={}", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("typeId", typeId);
		map.put("level", level);
		map.put("selectType", selectType);
		try {
			userList = alarmServer.findUserRelation(map);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select userList error.E={}", e);
		}
		return new PageResult<>(total, userList);
	}
	
	
	/**
	 * 类型列表
	 * @param userId
	 * @return
	 */
	
	@RequestMapping(value = "/findTypeRelation.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<Alarm> findTypeRelation(Integer pageIndex, Integer pageSize, String level,Integer userId, Integer selectType){
		int total = 0;
		List<Alarm> typeList = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		
		try {
			paramMap.put("userId", userId);
			paramMap.put("selectType", selectType);
			paramMap.put("level", level);
			total = alarmServer.findTypeRelationTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] find typeRelation total err.E={}", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("userId", userId);
		map.put("selectType", selectType);
		map.put("level", level);
		try {
			typeList = alarmServer.findTypeRelation(map);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] select typeList error.E={}", e);
		}
		return new PageResult<>(total, typeList);
	}
	
	
	/**
	 * 批量更新
	 * @param request
	 * @param response
	 * @param json
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.alarm, operation = Operate.UPDATEBATCH)
	@RequestMapping(value = "/updateRelationBatch.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateRelationBatch(HttpServletRequest request, HttpServletResponse response, String json){
		JSONArray jsonArray = JSONArray.fromObject(json);
		@SuppressWarnings("unchecked")
		List<Alarm> list = (List<Alarm>) JSONArray.toCollection(jsonArray, Alarm.class);
		ResponseResult result = new ResponseResult();
		int update = 0;
		try {
			update = alarmServer.updateStrategySend(list);
			result.setResult(update);
			result.setNewData(list);
			result.setOldData(null);
		} catch (Exception e) {
			logger.error("[ERROR][ALARM] update relationBatch is error.E={}", e);
		}
		logger.debug("[BINS][ALARM] newData=" + list + ",result=" + result.getResult());
		
		return result;
	}


	
	@RequestMapping(value = "/validatorRelation.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult validatorRelation(HttpServletRequest requests, HttpServletResponse response, String json){
		
		int result = 1;
		List<Integer> res = new ArrayList<>();
		JSONArray jsonArray = JSONArray.fromObject(json);
		@SuppressWarnings("unchecked")
		List<Alarm> list = (List<Alarm>) JSONArray.toCollection(jsonArray, Alarm.class);
		try {
			res = alarmServer.validatorRelation(list);
		} catch (Exception e) {
			
		}
		for (Integer r : res) {
			if(r == 0){
				result = 0;
				break;
			}
		}
		
		return new ResponseResult(result, null, null);
	}
	
	
}
