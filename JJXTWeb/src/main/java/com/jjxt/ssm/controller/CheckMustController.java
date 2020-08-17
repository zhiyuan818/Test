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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.CheckMust;
import com.jjxt.ssm.service.CheckMustService;

@Controller
@RequestMapping("/audit")
public class CheckMustController {

	private static Logger logger = Logger.getLogger(CheckMustController.class);

	private static final String PATH = "audit/";
	@Autowired
	private CheckMustService checkMustService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goCheckMustPage.action")
	public String goCheckMustList() {

		return PATH + "checkMustList";
	}

	/**
	 * 添加必审词
	 * 
	 * @param request
	 * @param response
	 * @param keyWord
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.checkMust, operation = Operate.INSERT)
	@RequestMapping(value = "/addToCheckMust.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToCheckMust(HttpServletRequest request, HttpServletResponse response, String keyWord) {

		Long sTime = System.currentTimeMillis();
		List<String> newList = new ArrayList<String>();
		int add = 0;
		try {
			if (!keyWord.equals("") && keyWord != null) {

				add = checkMustService.addToCheckMust(keyWord);
				newList.add(keyWord);
			}

		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 添加到pre_check_must出错.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][CHECK] addData=" + newList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

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
	public PageResult<CheckMust> findPageList(Integer pageSize, Integer pageIndex, String keyWord, String startTime,
			String endTime, HttpServletRequest request, HttpServletResponse response) {

		Long sTime = System.currentTimeMillis();

		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		try {
			map.put("keyWord", keyWord);
			map.put("startTime", startTime);
			map.put("endTime", endTime);

			total = checkMustService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询pre_check_must总数出错.", e);
		}

		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("keyWord", keyWord);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			List<CheckMust> list = checkMustService.findPageList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][CHECK] pageSize=" + pageSize + "pageIndex=" + pageIndex + "keyWord" + keyWord
					+ "startTime" + startTime + ",time=" + (eTime - sTime) + "ms");

			return new PageResult<>(total, list);
		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询pre_check_must数据出错.", e);
		}

		return new PageResult<>(total, new ArrayList<>());
	}

	/**
	 * 删除必审词
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.checkMust, operation = Operate.DELETE)
	@RequestMapping("/delCheckMust.action")
	@ResponseBody
	public ResponseResult delCheckMust(Integer id) {

		int del = 0;
		CheckMust oldData = null;
		List<CheckMust> oldList = new ArrayList<CheckMust>();
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = checkMustService.findCheckMustById(id);
			oldList.add(oldData);

		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询旧数据出错.", e);
		}
		try {
			del = checkMustService.delCheckMust(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldList);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][CHECK] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][CHECK] 删除pre_check_must数据出错.", e1);
		}

		return result;
	}

	/**
	 * 通过id查询必审词数据
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findCheckMustById.action")
	@ResponseBody
	public CheckMust findCheckMustById(Integer id) {

		CheckMust checkmust = null;
		try {
			checkmust = checkMustService.findCheckMustById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询pre_check_must数据出错.", e);
		}

		return checkmust;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param blackKeyword
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.checkMust, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateCheckMust.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateCheckMust(HttpServletRequest request, HttpServletResponse response,
			CheckMust checkmust) {

		int update = 0;
		ResponseResult result = new ResponseResult();
		Integer id = checkmust.getId();
		CheckMust oldData = null;
		List<CheckMust> newList = new ArrayList<CheckMust>();
		List<CheckMust> oldList = new ArrayList<CheckMust>();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = checkMustService.findCheckMustById(id);
			oldList.add(oldData);
		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询旧数据出错.", e);
		}

		try {
			update = checkMustService.updateCheckMust(checkmust);
			newList.add(checkmust);
			result.setResult(update);
			result.setNewData(newList);
			result.setOldData(oldList);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][CHECK] newData=" + newList + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][CHECK] 修改必审词数据出错.", e1);
		}

		return result;
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackKeyword, operation = Operate.DELETEBATCH)
	@RequestMapping("/delCheckMustBatch.action")
	@ResponseBody
	public ResponseResult delCheckMustBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {

		CheckMust checkmust = null;
		List<CheckMust> oldList = new ArrayList<CheckMust>();
		int del = 0;
		ResponseResult result = null;
		Long sTime = System.currentTimeMillis();
		for (Integer id : ids) {
			try {
				checkmust = checkMustService.findCheckMustById(id);
				oldList.add(checkmust);

			} catch (Exception e) {
				logger.error("[ERROR][CHECK] 查询旧数据出错.", e);
			}
			try {
				del = checkMustService.delCheckMust(id);

			} catch (Exception e) {
				logger.error("[ERROR][CHECK] 删除数据出错.", e);
			}

			if (del != 1) {
				result = new ResponseResult();
				result.setResult(del);
				result.setNewData(null);
				result.setOldData(oldList);
				logger.debug("[BINS][CHECK] 删除数据失败,id=" + id);

				return result;
			}

		}
		result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][CHECK] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		return result;
	}

	
	@RequestMapping("/findByKeyWord.action")
	@ResponseBody
	public ResultData findByKeyWord(String keyWord,String oldKeyWord){
		ResultData result = new ResultData();
		if(keyWord.equals(oldKeyWord)){
			result.setValid(true);
			return result;
		}
		int total = 0;
		try {
			total = checkMustService.findByKeyWord(keyWord);
		} catch (Exception e) {
			logger.error("[ERROR][CHECK] 查询重复审核词出错.", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}
		
		return result;
	}
	

}
