package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.RandomExt;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.RandomExtService;

@Controller
@RequestMapping("/randomExt")
public class RandomExtController {

	private static Logger logger = Logger.getLogger(RandomExtController.class);

	private static final String PATH = "randomExt/";
	@Autowired
	private RandomExtService randomExtService;
	@Autowired
	private ApplicationService applicationService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goRandomExtPageList.action")
	public String goLocationSegmentPageList(HttpServletRequest request) {
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][FIND_APP]e=", e);
		}
		request.setAttribute("apps", apps);
		return PATH + "randomExtPageList";
	}
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/findRandomExtPageList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<RandomExt> findRandomExtPageList(Integer pageIndex, Integer pageSize,String appId,String randomExt,String msgext) {
		logger.debug("[BINS][RANDOM_EXT] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",appId="+appId+",randomExt="+randomExt+",msgext="+msgext);
		int total = 0;
		Map<String, Object> map1=new HashMap<String,Object>();
		map1.put("appId", appId);
		map1.put("randomExt", randomExt);
		map1.put("msgext", msgext);
		try {
			total = randomExtService.findRandomExtTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][RANDOM_EXT] 查询总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appId", appId);
		map.put("randomExt", randomExt);
		map.put("msgext", msgext);
		
		List<RandomExt> list = null;
		try {
			list = randomExtService.findRandomExtPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][RANDOM_EXT]查询列表异常.", e);
		}
		return new PageResult<RandomExt>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.randomExt, operation = Operate.DELETE)
	@RequestMapping(value = "deleteRandomExtById.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteRandomExtById(Integer id) {
		
		RandomExt oldRandomExt=null;
		try {
			oldRandomExt = randomExtService.findRandomExtById(id);
		} catch (Exception e1) {
			logger.error("[ERROR][RANDOM_EXT] ",e1);
		}
		logger.debug("[BINS][RANDOM_EXT] del randomExt=" + oldRandomExt);
		
		int delRes = 0;
		try {
			delRes=randomExtService.deleteRandomExtById(id);
		} catch (Exception e) {
			logger.error("[ERROR][RANDOM_EXT] ",e);
		}
		return new ResponseResult(delRes, null, oldRandomExt);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.randomExt, operation = Operate.DELETEBATCH)
	@RequestMapping("/delRandomExtBatch.action")
	@ResponseBody
	public ResponseResult delRandomExtBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<RandomExt> oldList = new ArrayList<RandomExt>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList = randomExtService.findRandomExtByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][RANDOM_EXT] Error getting old data", e);
			}
			try {
				del = randomExtService.delRandomExtBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][RANDOM_EXT] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][RANDOM_EXT] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
}

