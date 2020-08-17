package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.GlobalSettingService;

@Controller
@RequestMapping("/global")
public class GlobalSettingController {

	private static Logger logger = Logger.getLogger(GlobalSettingController.class);

	private static final String PATH = "global/";
	@Autowired
	private GlobalSettingService globalService;
	@Autowired
	private ApplicationService appService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goGlobalSettingPage.action")
	public String goMOPageList() {

		return PATH + "globalPageList";
	}

	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/findAll.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<GlobalSetting> findAll() {

		int total = 0;
		List<GlobalSetting> globalList = null;
		try {
			globalList = globalService.findAll();
			total = globalList.size();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new PageResult<GlobalSetting>(total, globalList);
	}

	/**
	 * 添加配置
	 * 
	 * @param global
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.global, operation = Operate.INSERT)
	@RequestMapping(value = "/addToGlobalSetting.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public synchronized ResponseResult addToGlobalSetting(HttpServletRequest request, HttpServletResponse response,
			GlobalSetting global) {

		Long startTime = System.currentTimeMillis();
		List<GlobalSetting> newList = new ArrayList<GlobalSetting>();
		int add = 0;
		try {
			add = globalService.addToGlobalSetting(global);
			newList.add(global);
		} catch (Exception e) {
			logger.error("[ERROR][GLOB] Add data error.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][GLOB] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	/**
	 * 删除配置
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.global, operation = Operate.DELETE)
	@RequestMapping("/deleteGlobalById.action")
	@ResponseBody
	public ResponseResult deleteGlobalById(Integer id) {

		int del = 0;
		Long startTime = System.currentTimeMillis();
		GlobalSetting oldData = null;
		List<GlobalSetting> oldList = new ArrayList<GlobalSetting>();
		try {
			oldData = globalService.findGlobalSettingById(id);
			oldList.add(oldData);

		} catch (Exception e) {
			logger.error("[ERROR][GLOB] Error in finding old data.", e);
		}
		try {
			del = globalService.deleteGlobalById(id);

		} catch (Exception e) {
			logger.error("[ERROR][GLOB] Delete data error.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(oldList);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][GLOB] oldData=" + oldList + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	
	@RequestMapping("/findGlobalSettingById.action")
	@ResponseBody
	public GlobalSetting findGlobalSettingById(Integer id) {

		GlobalSetting global = null;
		try {
			global = globalService.findGlobalSettingById(id);

		} catch (Exception e) {
			logger.error("[ERROR][GLOB] Select data error.", e);
		}
		return global;
	}

	/**
	 * 修改配置参数并校验
	 */
	
	@DataOperate(bussiness = Bussiness.global, operation = Operate.UPDATE)
	@RequestMapping(value = "/dataValidation.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult dataValidation(HttpServletRequest request, HttpServletResponse response,
			GlobalSetting global) {
		int sum = 0;
		int res = 0;
		int update = 0;
		ResponseResult result = new ResponseResult();
		List<GlobalSetting> newList = new ArrayList<GlobalSetting>();
		List<GlobalSetting> oldList = new ArrayList<GlobalSetting>();
		Integer id = global.getId();
		String key = global.getKey().trim();
		String val = global.getVal().trim();
		GlobalSetting oldData = null;
		newList.add(global);

		if (key.equals(Constant.GlobalSetting.WEB_AUTH_IP)) {
			try {
				Long startTime = System.currentTimeMillis();
				String[] ips = val.split(",");
				for (int j = 0; j < ips.length; j++) {
					String[] num = ips[j].trim().split("\\.");
					if (num.length == 4) {
						sum++;
					}
				}
				if (sum != ips.length) {
					res = 1;
					result.setResult(res);

				} else {
					try {
						oldData = globalService.findGlobalSettingById(id);
						oldList.add(oldData);
					} catch (Exception e1) {
						logger.error("[ERROR][GLOB] 查询旧数据出错.", e1);
					}
					try {
						update = globalService.updateGlobal(global);
						if (update == 1) {
							result.setResult(res);
							result.setNewData(newList);
							result.setOldData(oldList);
							Long endTime = System.currentTimeMillis();
							logger.debug("[BINS][GLOB] newData=" + newList + ",time=" + (endTime - startTime)
									+ "ms,result=" + result.getResult());

						} else {
							res = 4;
							result.setResult(res);
							logger.debug("[BINS][GLOB] 修改配置失败. newData=" + newList + ",oldData=" + oldList);
						}

					} catch (Exception e2) {
						logger.error("[ERROR][GLOB] 修改web_auth_ip配置出错.", e2);
					}

				}

			} catch (Exception e) {
				logger.error("[ERROR][GLOB] 修改配置出错.", e);
			}

		} else if (key.equals(Constant.GlobalSetting.OVERRIDE_MO_DEST_ACCOUNTS)) {

			Long startTime = System.currentTimeMillis();
			try {
				String[] str = val.split(",");
				for (String s : str) {
					int selectResult = appService.findAppByName(s);
					if (selectResult == 0) {
						res = 2;
						break;
					}
				}
			} catch (Exception e) {
				logger.error("[ERROR][GLOB] 查找账号出错.", e);
			}
			if (res == 0) {
				try {
					oldData = globalService.findGlobalSettingById(id);
					oldList.add(oldData);
				} catch (Exception e1) {
					logger.error("[ERROR][GLOB] 查询旧数据出错.", e1);
				}
				try {
					update = globalService.updateGlobal(global);
					if (update == 1) {
						result.setResult(res);
						result.setNewData(newList);
						result.setOldData(oldList);
						Long endTime = System.currentTimeMillis();
						logger.debug("[BINS][GLOB] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
								+ result.getResult());

					} else {
						res = 4;
						result.setResult(res);
						logger.debug("[BINS][GLOB] 修改配置失败. newData=" + newList + ",oldData=" + oldList);
					}

				} catch (Exception e2) {
					logger.error("[ERROR][GLOB] 修改override_mo_dest_accounts配置出错.", e2);
				}
			} else {
				result.setResult(res);
			}

		} else if (key.equals(Constant.GlobalSetting.ENABLE_SHUNT)
				|| key.equals(Constant.GlobalSetting.IS_SYNC_SHUNT)) {

			if (val.equals("true") || val.equals("false") || val.equals("deleted")) {
				Long startTime = System.currentTimeMillis();
				try {
					oldData = globalService.findGlobalSettingById(id);
					oldList.add(oldData);
				} catch (Exception e) {
					logger.error("[ERROR][GLOB] 查询旧数据出错.", e);
				}
				try {
					update = globalService.updateGlobal(global);
					if (update == 1) {
						result.setResult(res);
						result.setNewData(newList);
						result.setOldData(oldList);
						Long endTime = System.currentTimeMillis();
						logger.debug("[BINS][GLOB] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
								+ result.getResult());

					} else {
						res = 4;
						result.setResult(res);
						logger.debug("[BINS][GLOB] 修改配置失败. newData=" + newList + ",oldData=" + oldList);
					}

				} catch (Exception e) {
					logger.error("[ERROR][GLOB] 修改配置参数出错.", e);
				}

			} else {
				res = 3;
				result.setResult(res);
			}

		} else {
			Long startTime = System.currentTimeMillis();
			try {
				oldData = globalService.findGlobalSettingById(id);
				oldList.add(oldData);
			} catch (Exception e1) {
				logger.error("[ERROR][GLOB] 查询旧数据出错.", e1);
			}
			try {
				update = globalService.updateGlobal(global);
				if (update == 1) {
					result.setResult(res);
					result.setNewData(newList);
					result.setOldData(oldList);
					Long endTime = System.currentTimeMillis();
					logger.debug("[BINS][GLOB] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
							+ result.getResult());

				} else {
					res = 4;
					result.setResult(res);
					logger.debug("[BINS][GLOB] 修改配置失败. newData=" + newList + ",oldData=" + oldList);
				}

			} catch (Exception e) {
				logger.error("[ERROR][GLOB] 修改配置参数出错.", e);
			}
		}

		return result;

	}

}
