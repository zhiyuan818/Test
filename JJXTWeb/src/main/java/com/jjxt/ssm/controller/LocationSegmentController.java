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

import com.jjxt.ssm.entity.LocationSegment;
import com.jjxt.ssm.service.LocationSegmentService;

@Controller
@RequestMapping("/locationSegment")
public class LocationSegmentController {

	private static Logger logger = Logger.getLogger(LocationSegmentController.class);

	private static final String PATH = "locationSegment/";
	@Autowired
	private LocationSegmentService locationSegmentService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goLocationSegmentPageList.action")
	public String goLocationSegmentPageList() {
		return PATH + "locationSegmentPageList";
	}
	
	/**
	 * 查询全部数据
	 * 
	 * @return
	 */
	
	@RequestMapping(value = "/findLocationSegmentList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<LocationSegment> findLocationSegmentList(Integer pageIndex, Integer pageSize,String segment,String province,String city) {
		logger.debug("[BINS][LOCATION_SEG] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",segment="+segment+",province="+province+",city="+city);
		int total = 0;
		Map<String, Object> map1=new HashMap<String,Object>();
		map1.put("segment", segment);
		map1.put("province", province);
		map1.put("city", city);
		try {
			total = locationSegmentService.findLocationSegmentTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] 查询总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("segment", segment);
		map.put("province", province);
		map.put("city", city);
		
		List<LocationSegment> list = null;
		try {
			list = locationSegmentService.findLocationSegmentList(map);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG]查询列表异常.", e);
		}
		return new PageResult<LocationSegment>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.locationSegment, operation = Operate.INSERT)
	@RequestMapping(value = "/addLocationSegment.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addLocationSegment(HttpServletRequest request, HttpServletResponse response,
			LocationSegment locationSegment) {
	
		Long startTime = System.currentTimeMillis();
		List<LocationSegment> newList = new ArrayList<LocationSegment>();
		int add = 0;
		try {
			add = locationSegmentService.addLocationSegment(locationSegment);
			newList.add(locationSegment);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] Add data error.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][LOCATION_SEG] newData=" + newList + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}

	
	@RequestMapping("/findLocationSegmentByCondition.action")
	@ResponseBody
	public ResultData findLocationSegmentByCondition(String segment) {
		ResultData result = new ResultData();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("segment", segment);
		int total = 0;
		try {
			total = locationSegmentService.findLocationSegmentByCondition(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}
		return result;
	}

	
	@RequestMapping("/findLocationSegmentById.action")
	@ResponseBody
	public LocationSegment findLocationSegmentById(Integer id) {

		LocationSegment locationSegment = null;
		try {
			locationSegment = locationSegmentService.findLocationSegmentById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] Select data error.", e);
		}
		return locationSegment;
	}

	
	@DataOperate(bussiness = Bussiness.locationSegment, operation = Operate.UPDATE)
	@RequestMapping(value = "updateLocationSegment.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateLocationSegment(HttpServletRequest request, HttpServletResponse response, LocationSegment newLocationSegment) {
		logger.debug("[BINS][LOCATION_SEG] numSeg=" + newLocationSegment);
		LocationSegment oldLocationSegment=null;
		try {
			oldLocationSegment = locationSegmentService.findLocationSegmentById(newLocationSegment.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][LOCATION_SEG] ",e1);
		}
		
		int i = 0;
		try {
			i = locationSegmentService.updateLocationSegment(newLocationSegment);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] update numSeg exception", e);
		}
		
		return new ResponseResult(i, newLocationSegment, oldLocationSegment);
	}

	
	@DataOperate(bussiness = Bussiness.locationSegment, operation = Operate.DELETE)
	@RequestMapping(value = "deleteLocationSegById.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult deleteLocationSegById(Integer id) {
		
		LocationSegment oldLocationSegment=null;
		try {
			oldLocationSegment = locationSegmentService.findLocationSegmentById(id);
		} catch (Exception e1) {
			logger.error("[ERROR][LOCATION_SEG] ",e1);
		}
		logger.debug("[BINS][LOCATION_SEG] del locationSeg=" + oldLocationSegment);
		
		int delRes = 0;
		try {
			delRes=locationSegmentService.deleteLocationSegById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LOCATION_SEG] ",e);
		}
		return new ResponseResult(delRes, null, oldLocationSegment);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.locationSegment, operation = Operate.DELETEBATCH)
	@RequestMapping("/delLocationSegmentBatch.action")
	@ResponseBody
	public ResponseResult delLocationSegmentBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<LocationSegment> oldList = new ArrayList<LocationSegment>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList = locationSegmentService.findLocationSegByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][LOCATION_SEG] Error getting old data", e);
			}
			try {
				del = locationSegmentService.delLocationSegBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][LOCATION_SEG] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][LOCATION_SEG] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
}

