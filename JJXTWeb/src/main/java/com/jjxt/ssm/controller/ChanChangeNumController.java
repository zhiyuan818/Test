package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.ChanChangeNum;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChanChangeNumService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;

/**
 * @author shangcp
 * 补发空格组成长短信
 */

@Controller
@RequestMapping("/chanChangeNum")
public class ChanChangeNumController {
	private static Logger logger = Logger.getLogger(ChanChangeNumController.class);
	
	private static final String PATH = "chanChangeNum/";

	@Autowired
	private ApplicationService applicationMapper;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ChanChangeNumService chanChangeNumService;
	
	@RequestMapping("/gochanChangeNumPageList.action")
	public String gochanChangeNumPageList(HttpServletRequest request){
		Map<String, Object> map=new HashMap<>();
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationMapper.findAppNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][MO] ",e);
		}
		
		List<Channel> channels = new ArrayList<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][MO] ",e);
		}
		
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "chanChangeNumList";
	}
	
	
	@RequestMapping(value="findchanChangeNumPageList.action",method=RequestMethod.POST)
	@ResponseBody
	public PageResult<ChanChangeNum> findchanChangeNumPageList(HttpServletRequest request, Integer pageIndex, Integer pageSize,String appId,String channelId,String id) {
		logger.debug("[BINS][CHANCHANGENUM] pageSize=" + pageSize + ",pageIndex=" + pageIndex+",appId="+appId+",channelId="+channelId+",id="+id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("appId", appId);
		map.put("channelId", channelId);
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		int total=0;
		try {
			total = chanChangeNumService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERRPR][CHANCHANGENUM]  ",e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		map1.put("id", id);
		map1.put("appId", appId);
		map1.put("channelId", channelId);
		if(ucenterManager != null) {
			map1.put("chineseName", ucenterManager.getChineseName());
			map1.put("title", ucenterManager.getTitle());
			map1.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map1.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<ChanChangeNum> list=null;
		try {
			list = chanChangeNumService.findchanChangeNumPageList(map1);
		} catch (Exception e) {
			logger.error("[ERRPR][CHANCHANGENUM]  ",e);
		}
		return new PageResult<>(total, list);
	}
	
	
	@DataOperate(bussiness=Bussiness.chanChangeNum,operation=Operate.INSERT)
	@RequestMapping("/addChanChangeNum.action")
	@ResponseBody
	public ResponseResult addChanChangeNum(ChanChangeNum chanChangeNum){
		logger.debug("[BINS][CHANCHANGENUM] chanChangeNum="+chanChangeNum);
		chanChangeNum.setChannelId(chanChangeNum.getChannelId()==null? 0 : chanChangeNum.getChannelId());
		chanChangeNum.setAppId(chanChangeNum.getAppId()==null? 0 : chanChangeNum.getAppId());
		int result=0;
		try {
			result=chanChangeNumService.addChanChangeNum(chanChangeNum);
		} catch (Exception e) {
			logger.error("[ERRPR][CHANCHANGENUM]  ",e);
		}
		List<ChanChangeNum> newData=null;
		try {
			newData = chanChangeNumService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] ",e);
		}
		return new ResponseResult(result,newData,null);
	}
	
	@RequestMapping("/findChanChangeNumById.action")
	@ResponseBody
	public ChanChangeNum findChanChangeNumById(Integer id) {
		logger.debug("[BINS][CHANCHANGENUM] id=" + id);
		List<ChanChangeNum> chanChangeNums = null;
		try {
			chanChangeNums = chanChangeNumService.findChanChangeNumById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANCHANGENUM] id查询异常", e);
		}
		if (!(null==chanChangeNums || chanChangeNums.isEmpty())) {
			return chanChangeNums.get(0);
		}
		return null;
	}
	
	@DataOperate(bussiness=Bussiness.chanChangeNum,operation=Operate.UPDATE)
	@RequestMapping("/updateChanChangeNum.action")
	@ResponseBody
	public ResponseResult updateChanChangeNum(ChanChangeNum chanChangeNum){
		logger.debug("[BINS][CHANCHANGENUM] chanChangeNum="+chanChangeNum);
		chanChangeNum.setChannelId(chanChangeNum.getChannelId()==null? 0 : chanChangeNum.getChannelId());
		chanChangeNum.setAppId(chanChangeNum.getAppId()==null? 0 : chanChangeNum.getAppId());
		List<ChanChangeNum> oldData = null;
		try {
			oldData = chanChangeNumService.findChanChangeNumById(chanChangeNum.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANCHANGENUM] ID查询异常", e);
		}
		int i = 0;
		try {
			i = chanChangeNumService.updateChanChangeNum(chanChangeNum);
		} catch (Exception e) {
			logger.error("[ERROR][CHANCHANGENUM] UPDATE EXCEPTION", e);
		}
		List<ChanChangeNum> newData = null;
		try {
			newData = chanChangeNumService.findChanChangeNumById(chanChangeNum.getId());
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] ID查询异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}
	

	@DataOperate(bussiness = Bussiness.chanChangeNum, operation = Operate.DELETE)
	@RequestMapping("/deleteChanChangeNum.action")
	@ResponseBody
	public ResponseResult deleteChanChangeNum(Integer id){
		logger.debug("[BINS][CHANCHANGENUM] id="+id);
		List<ChanChangeNum> chanChangeNum = null;
		try {
			chanChangeNum = chanChangeNumService.findChanChangeNumById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANCHANGENUM]", e);
		}
		int i = 0;
		try {
			i = chanChangeNumService.deleteChanChangeNum(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANCHANGENUM] delete error", e);
		}
		return new ResponseResult(i, null, chanChangeNum);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.chanChangeNum, operation = Operate.DELETEBATCH)
	@RequestMapping("/delChanChangeNumBatch.action")
	@ResponseBody
	public ResponseResult delChanChangeNumBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<ChanChangeNum> oldList = new ArrayList<>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList = chanChangeNumService.findChanChangeNumByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][CHANCHANGENUM] Error getting old data", e);
			}
			try {
				del = chanChangeNumService.deleteChanChangeNumBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][CHANCHANGENUM] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][CHANCHANGENUM] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
}
