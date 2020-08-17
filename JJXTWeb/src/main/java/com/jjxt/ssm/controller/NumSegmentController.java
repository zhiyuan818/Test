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

import com.jjxt.ssm.entity.NumSegment;
import com.jjxt.ssm.service.NumSegmentService;

@Controller
@RequestMapping("/numSegment")
public class NumSegmentController {

	private static Logger logger = Logger.getLogger(NumSegmentController.class);

	private static final String PATH = "numSegment/";
	@Autowired
	private NumSegmentService numSegmentService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goNumSegmentPageList.action")
	public String goNumSegmentPageList() {

		return PATH + "numSegmentPageList";
	}
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/findNumSegmentList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<NumSegment> findNumSegmentList(Integer pageIndex, Integer pageSize,String seg,String carrier) {
		logger.debug("[BINS][NUM_SEG] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",seg="+seg+",carrier="+carrier);
		int total = 0;
		Map<String, Object> map1=new HashMap<String,Object>();
		map1.put("seg", seg);
		map1.put("carrier", carrier);
		try {
			total = numSegmentService.findNumSegmentTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] 查询总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("seg", seg);
		map.put("carrier", carrier);
		
		List<NumSegment> list = null;
		try {
			list = numSegmentService.findNumSegmentList(map);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG]查询列表异常.", e);
		}
		return new PageResult<NumSegment>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.numSegment, operation = Operate.INSERT)
	@RequestMapping(value = "/addNumSegment.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addNumSegment(HttpServletRequest request, HttpServletResponse response,
			NumSegment numSegment) {
	
		Long startTime = System.currentTimeMillis();
		List<NumSegment> newList = new ArrayList<NumSegment>();
		int add = 0;
		try {
			add = numSegmentService.addNumSegment(numSegment);
			newList.add(numSegment);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] Add data error.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][NUM_SEG] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	
	@RequestMapping("/findNumSegmentByCondition.action")
	@ResponseBody
	public ResultData findNumSegmentByCondition(String seg) {
		ResultData result = new ResultData();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("seg", seg);
		int total = 0;
		try {
			total = numSegmentService.findNumSegmentByCondition(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}
		return result;
	}

	
	@RequestMapping("/findNumSegmentById.action")
	@ResponseBody
	public NumSegment findNumSegmentById(Integer id) {

		NumSegment numSeg = null;
		try {
			numSeg = numSegmentService.findNumSegmentById(id);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] Select data error.", e);
		}
		return numSeg;
	}

	
	@DataOperate(bussiness = Bussiness.numSegment, operation = Operate.UPDATE)
	@RequestMapping(value = "updateNumSegment.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateNumSegment(HttpServletRequest request, HttpServletResponse response, NumSegment newNnumSeg) {
		logger.debug("[BINS][NUM_SEG] numSeg=" + newNnumSeg);
		NumSegment oldNnumSeg=null;
		try {
			oldNnumSeg = numSegmentService.findNumSegmentById(newNnumSeg.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][NUM_SEG] ",e1);
		}
		
		int i = 0;
		try {
			i = numSegmentService.updateNumSegment(newNnumSeg);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] update numSeg exception", e);
		}
		
		return new ResponseResult(i, newNnumSeg, oldNnumSeg);
	}

	
	@DataOperate(bussiness = Bussiness.numSegment, operation = Operate.DELETE)
	@RequestMapping(value = "deleteNumSegById.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteNumSegById(Integer id) {
		
		NumSegment oldNnumSeg=null;
		try {
			oldNnumSeg = numSegmentService.findNumSegmentById(id);
		} catch (Exception e1) {
			logger.error("[ERROR][NUM_SEG] ",e1);
		}
		logger.debug("[BINS][NUM_SEG] del numSeg=" + oldNnumSeg);
		
		int delRes = 0;
		try {
			delRes=numSegmentService.deleteNumSegById(id);
		} catch (Exception e) {
			logger.error("[ERROR][NUM_SEG] ",e);
		}
		return new ResponseResult(delRes, null, oldNnumSeg);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.numSegment, operation = Operate.DELETEBATCH)
	@RequestMapping("/delNumSegmentBatch.action")
	@ResponseBody
	public ResponseResult delLocationSegmentBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<NumSegment> oldList = new ArrayList<NumSegment>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList =numSegmentService.findNumSegByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][NUM_SEG] Error getting old data", e);
			}
			try {
				del = numSegmentService.delNumSegBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][NUM_SEG] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][NUM_SEG] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
}

