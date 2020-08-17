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

import com.jjxt.ssm.entity.LoadConfig;
import com.jjxt.ssm.service.LoadConfigService;

@Controller
@RequestMapping("/loadConfig")
public class LoadConfigController {

	private static Logger logger = Logger.getLogger(LoadConfigController.class);

	private static final String PATH = "loadConfig/";
	@Autowired
	private LoadConfigService loadconfigservice;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goLoadConfigPageList.action")
	public String goLoadConfigPageList() {

		return PATH + "loadConfigPageList";
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @param keyWord
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.loadConfig, operation = Operate.INSERT)
	@RequestMapping(value = "/addToLoadConfig.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToLoadConfig(HttpServletRequest request, HttpServletResponse response, LoadConfig loadconfig) {

		Long sTime = System.currentTimeMillis();
		int add = 0;
		try {
				add = loadconfigservice.addToLoadConfig(loadconfig);

		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 添加到load_router_config出错.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(loadconfig);
		result.setOldData(null);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][LOAD] addData=" + loadconfig + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

		return result;
	}

	/**
	 * 按条件查询
	 * 
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value = "/findPageList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<LoadConfig> findPageList(Integer pageSize, Integer pageIndex, String redisKey, String modelName,
			 HttpServletRequest request, HttpServletResponse response) {

		Long sTime = System.currentTimeMillis();

		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		try {
			map.put("redisKey", redisKey);
			map.put("modelName", modelName);

			total = loadconfigservice.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 查询load_router_config总数出错.", e);
		}

		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("redisKey", redisKey);
			map1.put("modelName", modelName);
			List<LoadConfig> list = loadconfigservice.findPageList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][LOAD] pageSize=" + pageSize + "pageIndex=" + pageIndex + "redisKey" + redisKey
					+ "modelName" + modelName + ",time=" + (eTime - sTime) + "ms");

			return new PageResult<>(total, list);
		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 查询load_router_config数据出错.", e);
		}

		return null;
	}

	/**
	 * 删除必审词
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.loadConfig, operation = Operate.DELETE)
	@RequestMapping("/delLoadConfig.action")
	@ResponseBody
	public ResponseResult delLoadConfig(Integer id) {

		int del = 0;
		LoadConfig oldData = null;
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = loadconfigservice.findLoadConfigById(id);

		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 查询旧数据出错.", e);
		}
		try {
			del = loadconfigservice.delLoadConfig(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][LOAD] oldData=" + oldData + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][LOAD] 删除load_router_config数据出错.", e1);
		}

		return result;
	}

	/**
	 * 通过id查询必审词数据
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findLoadConfigById.action")
	@ResponseBody
	public LoadConfig findLoadConfigById(Integer id) {

		LoadConfig loadconfig = null;
		try {
			loadconfig = loadconfigservice.findLoadConfigById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 查询load_router_config数据出错.", e);
		}

		return loadconfig;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param blackKeyword
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.loadConfig, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateLoadConfig.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateLoadConfig(HttpServletRequest request, HttpServletResponse response,
			LoadConfig loadconfig) {

		int update = 0;
		ResponseResult result = new ResponseResult();
		Integer id = loadconfig.getId();
		LoadConfig oldData = null;
		Long sTime = System.currentTimeMillis();
		try {
			oldData = loadconfigservice.findLoadConfigById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LOAD] 查询旧数据出错.", e);
		}

		try {
			update = loadconfigservice.updateLoadConfig(loadconfig);
			result.setResult(update);
			result.setNewData(loadconfig);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][LOAD] newData=" + loadconfig + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][LOAD] 修改数据出错.", e1);
		}

		return result;
	}

	

}
