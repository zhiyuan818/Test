package com.jjxt.ssm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.BaseChannelSupplier;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ChannelSupplierService;
import com.jjxt.ssm.service.UcenterManagerService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.ResultData;

@Controller
@RequestMapping("/channelSupplier")
public class ChannelSupplierController {
	private static Logger logger = Logger.getLogger(ChannelSupplierController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);

	private static final String PATH = "channelSupplier/";

	@Autowired
	private ChannelSupplierService channelSupplierService;
	@Autowired
	private UcenterManagerService ucenterManagerService;

	@RequestMapping("/goChannelSupplierList.action")
	public String goChannelSupplierList(HttpServletRequest request) {
		List<UcenterManager> heads = new ArrayList<>();
		try {
			heads = ucenterManagerService.findHeadManager();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_SUPPLIER] ", e);
		}
		request.setAttribute("heads", heads);
		return PATH + "channelSupplier";
	}

	
	@RequestMapping("findChannelSupplierList.action")
	@ResponseBody
	public PageResult<BaseChannelSupplier> findChannelSupplierList(Integer pageSize, Integer pageIndex, String supplierKey,String head) {
		logger.debug("[BINS][CHANNEL_SUPPLIER] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",supplierKey=" + supplierKey + ",head=" + head);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("supplierKey", supplierKey);
		paramMap.put("head", head);
		int total = 0;
		try {
			total = channelSupplierService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("supplierKey", supplierKey);
		map.put("head", head);
		List<BaseChannelSupplier> list = new ArrayList<>();
		try {
			list = channelSupplierService.findChannelSupplierPageList(map);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		return new PageResult<>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.channelSupplier, operation = Operate.INSERT)
	@RequestMapping("/addChannelSupplier.action")
	@ResponseBody
	public ResponseResult addChannelSupplier(BaseChannelSupplier baseChannelSupplier) {
		logger.debug("[BINS][CHANNEL_SUPPLIER] baseChannelSupplier=" + baseChannelSupplier);
		int addRes = 0;
		try {
			baseChannelSupplier.setCreateTime(sdf.parse(sdf.format(new Date())));
			baseChannelSupplier.setUpdateTime(sdf.parse(sdf.format(new Date())));
			addRes = channelSupplierService.addChannelSupplier(baseChannelSupplier);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][CHANNEL_SUPPLIER] exce=", e);
		}

		return new ResponseResult(addRes, baseChannelSupplier, null);
	}
	
	@RequestMapping("/findChannelSupplierById.action")
	@ResponseBody
	public BaseChannelSupplier findChannelSupplierById(Integer id) {
		logger.debug("[BINS][CHANNEL_SUPPLIER] id=" + id);
		BaseChannelSupplier channelSupplier = null;
		try {
			channelSupplier = channelSupplierService.findChannelSupplierById(id);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		return channelSupplier;
	}

	
	@DataOperate(bussiness = Bussiness.channelSupplier, operation = Operate.UPDATE)
	@RequestMapping(value = "updateChannelSupplier.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateChannelSupplier(HttpServletRequest request, HttpServletResponse response,
			BaseChannelSupplier channelSupplier) {
		logger.debug("[BINS][CHANNEL_SUPPLIER] channelSupplier=" + channelSupplier);
		BaseChannelSupplier oldChannelSupplier = null;
		try {
			oldChannelSupplier = channelSupplierService.findChannelSupplierById(channelSupplier.getId());
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		int updateRes = 0;
		try {
			updateRes = channelSupplierService.updateChannelSupplier(channelSupplier);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}

		return new ResponseResult(updateRes, channelSupplier, oldChannelSupplier);
	}

	
	@RequestMapping("/findLinkChannel.action")
	@ResponseBody
	public List<Map<String,Object>> findLinkChannel(Integer id) {
		logger.debug("[BINS][CHANNEL_SUPPLIER] id=" + id);
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = channelSupplierService.findLinkChannel(id);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		return result;
	}
	
	
	@RequestMapping("/validatorSupplierName.action")
	@ResponseBody
	public ResultData validatorSupplierName(String supplierName,String oldSupplierName) {
		ResultData result = new ResultData();
		if(supplierName.equals(oldSupplierName)) {
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("supplierName", supplierName);
		int total = 0;
		try {
			total = channelSupplierService.findTotalByName(paramMap);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}
	
	@RequestMapping("/validatorSupplierKey.action")
	@ResponseBody
	public ResultData validatorSupplierKey(String supplierKey,String oldSupplierKey) {
		ResultData result = new ResultData();
		if(supplierKey.equals(oldSupplierKey)) {
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("supplierKey", supplierKey);
		int total = 0;
		try {
			total = channelSupplierService.findTotalByName(paramMap);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_SUPPLIER] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}
	
	@DataOperate(bussiness = Bussiness.channelSupplier, operation = Operate.DELETE)
	@RequestMapping("/delChannelSupplierById.action")
	@ResponseBody
	public ResponseResult delChannelSupplierById(Integer id) {
		
		BaseChannelSupplier oldChannelSupplier=null;
		try {
			oldChannelSupplier = channelSupplierService.findChannelSupplierById(id);
		} catch (Exception e1) {
			logger.error("[ERROR][CHANNEL_SUPPLIER] ",e1);
		}
		logger.debug("[BINS][CHANNEL_SUPPLIER] del channelSupplier=" + oldChannelSupplier);
		
		int delRes = 0;
		try {
			delRes=channelSupplierService.delChannelSupplierById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_SUPPLIER] ",e);
		}
		return new ResponseResult(delRes, null, oldChannelSupplier);
	}
	
	@DataOperate(bussiness = Bussiness.channelSupplier, operation = Operate.DELETEBATCH)
	@RequestMapping("/delChannelSupplierBatch.action")
	@ResponseBody
	public ResponseResult delChannelSupplierBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<BaseChannelSupplier> oldList = new ArrayList<BaseChannelSupplier>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList = channelSupplierService.findChannelSupplierByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][CHANNEL_SUPPLIER] Error getting old data", e);
			}
			try {
				del = channelSupplierService.delChannelSupplierBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][CHANNEL_SUPPLIER] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][CHANNEL_SUPPLIER] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
}
