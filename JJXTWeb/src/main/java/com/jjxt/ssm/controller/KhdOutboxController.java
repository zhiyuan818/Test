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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.KhdOutbox;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.KhdOutboxService;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
/**
 * 
 * @author Administrator
 * 客户端发件箱
 */
@Controller
@RequestMapping("/khdOutbox")
public class KhdOutboxController {
	
	private static Logger logger = Logger.getLogger(KhdOutboxController.class);
	
	private static final String PATH="khdOutbox/";
	@Autowired
	private KhdOutboxService khdOutboxService;
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/goKhdOutboxList.action")
	public String goKhdOutboxList(HttpServletRequest request) {
		List<Application> apps=new ArrayList<>();
		try {
			apps=applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERR][APP] find list err.ex={}",e);
		}
		request.setAttribute("apps", apps);
		
		return PATH+"khdOutboxList";
	}

	
	@RequestMapping("/findKhdOutboxList.action")
	@ResponseBody
	public PageResult<KhdOutbox> findKhdOutboxList(String appId,Integer pageSize,Integer pageIndex,HttpServletRequest request) {
		Map<String, Object> map=new HashMap<>();
		map.put("appId", appId);
		int total=0;
		try {
			total = khdOutboxService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERR][OUTBOX] ex="+e);
		}
		if(total==0){
			return new PageResult<>(0, new ArrayList<KhdOutbox>());
		}
		Page page=new Page(pageSize, total, pageIndex);
		Map<String, Object> param = PageUtil.getDefaultPageMap(page);
		param.put("appId", appId);
		List<KhdOutbox> khdOutboxs=new ArrayList<>();
		try {
			khdOutboxs=khdOutboxService.findKhdOutboxList(param);
		} catch (Exception e) {
			logger.error("[ERR][OUTBOX] ex="+e);
		}
		return new PageResult<>(total, khdOutboxs);	
	}
}
