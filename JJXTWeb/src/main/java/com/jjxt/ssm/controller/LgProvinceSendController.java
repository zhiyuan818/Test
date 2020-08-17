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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.entity.Product;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.LgProvinceSendService;
import com.jjxt.ssm.service.ProductService;

/**
 *  分省发送
 * @author yhhou
 *
 * 2018年8月29日上午11:17:20
 */
@Controller
@RequestMapping("/lgProvinceSend")
public class LgProvinceSendController {
	private static Logger logger = Logger.getLogger(LgProvinceSendController.class);

	private static final String PATH = "lgProvinceSend/";
	
	@Autowired
	private LgProvinceSendService lgProvinceSendService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/goLgProvinceSendPageList.action")
	public String goLgProvinceSendPageList(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new HashMap<String,Object>();
		List<Channel> findChannel = null;
		List<Product> products=null;
		try {
			findChannel = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		try {
			products = productService.findAllProduct();
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] ", e);
		}
		request.setAttribute("channels", findChannel);
		request.setAttribute("products", products);
		return PATH+"lgProvincesSend";
	}

	
	@RequestMapping("/findLgProvinceSendPageList.action")
	@ResponseBody
	public PageResult<LgProvincesSend> findLgProvinceSendPageList(Integer pageSize, Integer pageIndex,Integer productId, Integer channelId,String province,String carriers,String status){
		logger.debug("[BINS][PROVINCE] pageSize="+pageSize+",pageIndex="+pageIndex+",channelId="+channelId+",productId="+productId+",province="+province+",carriers="+carriers+",status="+status);
		Map<String, Object> map=new HashMap<>();
		map.put("channelId", channelId);
		map.put("productId", productId);
		map.put("province", province);
		map.put("carriers", carriers);
		map.put("status", status);
		int total=lgProvinceSendService.findTotal(map);
		if(total==0){
			return new PageResult<>(total, new ArrayList<LgProvincesSend>());
		}
		Page page=new Page(pageSize, total, pageIndex);
		Map<String, Object> pageMap = PageUtil.getDefaultPageMap(page);
		pageMap.put("channelId", channelId);
		pageMap.put("productId", productId);
		pageMap.put("province", province);
		pageMap.put("carriers", carriers);
		pageMap.put("status", status);
		List<LgProvincesSend> sends=lgProvinceSendService.findLgProvinceSendPageList(pageMap);
		return new PageResult<LgProvincesSend>(total,sends);
	}

	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.DELETEBATCH)
	@RequestMapping("/delLgProvincesSendBatch.action")
	@ResponseBody
	public ResponseResult delLgProvincesSendBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendByIds(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			del = lgProvinceSendService.delLgProvincesSendBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(del, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}

	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.PAUSEBATCH)
	@RequestMapping("/pauseLgProvincesSendBatch.action")
	@ResponseBody
	public ResponseResult pauseLgProvincesSendBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendByIds(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			pau = lgProvinceSendService.pauseLgProvincesSendBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.PAUSEBATCH)
	@RequestMapping("/startLgProvincesSendBatch.action")
	@ResponseBody
	public ResponseResult startLgProvincesSendBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendByIds(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			str = lgProvinceSendService.startLgProvincesSendBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(str, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return new ResponseResult(str,null,oldList);
	}
	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.DELETEBATCH)
	@RequestMapping("/deleteLgProvincesSend.action")
	@ResponseBody
	public ResponseResult deleteLgProvincesSend(Integer id){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			del = lgProvinceSendService.delLgProvincesSend(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(del, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return new ResponseResult(del,null,oldList);
	}
	
	
	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.PAUSE)
	@RequestMapping("/pauseLgProvincesSend.action")
	@ResponseBody
	public ResponseResult pauseLgProvincesSend(Integer id){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			pau = lgProvinceSendService.pauseLgProvincesSend(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return new ResponseResult(pau,null,oldList);
	}
	
	
	@DataOperate(bussiness=Bussiness.lgProvinceSend,operation=Operate.START)
	@RequestMapping("/startLgProvincesSend.action")
	@ResponseBody
	public ResponseResult startLgProvincesSend(Integer id){
		List<LgProvincesSend> oldList = new ArrayList<>();
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = lgProvinceSendService.findLgProvincesSendById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Error getting old data", e);
		}
		try {
			str = lgProvinceSendService.startLgProvincesSend(id);
		} catch (Exception e) {
			logger.error("[ERROR][PROVINCE] Delete data error", e);
		}
		ResponseResult result = new ResponseResult(str, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][PROVINCE] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return new ResponseResult(str,null,oldList);
	}
}
