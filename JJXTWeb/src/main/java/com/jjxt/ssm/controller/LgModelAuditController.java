package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelAudit;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.LgModelAuditService;

@Controller
@RequestMapping("/lgModelAudit")
public class LgModelAuditController {

	private static Logger logger = Logger.getLogger(LgModelAuditController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	private static final String PATH = "lgModelAudit/";
	@Autowired
	private LgModelAuditService lgModelAuditService;
	@Autowired
	private ApplicationService applicationService;
	/**
	 * 跳转页面
	 * 
	 * @return
	 */
	@RequestMapping("/goLgModelAuditPageList.action")
	public String goLgModelAuditPageList(HttpServletRequest request,HttpServletResponse response) {
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][APP] ", e);
		}
		request.setAttribute("apps", apps);
		return PATH + "lgModelAuditPageList";
	}

	/**
	 * 分页查询
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @param content
	 * @return
	 */
	
	@RequestMapping("/findLgModelAudit.action")
	@ResponseBody
	public PageResult<LgModelAudit> findLgModelAudit(Integer pageSize, Integer pageIndex, String content,String appId,String auditFlag) {
		logger.debug("[BINS][LOGMODELAUDIT] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",content=" + content+",auditFlag="+auditFlag);
		int total = 0;
		Map<String, String> param=new HashMap<>();
		param.put("appId",appId);
		param.put("content", content);
		param.put("auditFlag", auditFlag);
		try {
			total = lgModelAuditService.findTotal(param);
		} catch (Exception e) {
			logger.error("[ERROR][LOGMODELAUDIT]", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("content", content);
		map.put("appId", appId);
		map.put("auditFlag", auditFlag);
		List<LgModelAudit> lg = null;
		;
		try {
			lg = lgModelAuditService.findLgModleAuditPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]", e);
		}
		return new PageResult<>(total, lg);
	}

	/**
	 * 新增
	 * 
	 * @param lgModelAudit
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.lgModelAudit, operation = Operate.INSERT)
	@RequestMapping("addLgModelAudit.action")
	@ResponseBody
	public ResponseResult addLgModelAudit(LgModelAudit lgModelAudit) {
		logger.debug("[BINS][LGMODELAUDIT] " + lgModelAudit);
		Long startTime = System.currentTimeMillis();
		try {
			lgModelAudit.setCreateTime(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e1) {
			logger.error("[ERROR][BINS][LGMODELAUDIT] date error", e1);
		}
		int i = 0;
		try {
			i = lgModelAuditService.addLgModelAudit(lgModelAudit);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]", e);
		}
		// 查询最后添加的数据
		LgModelAudit model = null;
		try {
			model = lgModelAuditService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]put last add menu is error", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(i);
		result.setNewData(model);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][LGMODELAUDIT]  " + lgModelAudit + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.lgModelAudit, operation = Operate.DELETE)
	@RequestMapping("/deleteLgModelAudit.action")
	@ResponseBody
	public ResponseResult deleteLgModelAudit(Integer id) {
		logger.debug("[BINS][LGMODELAUDIT] id=" + id);
		List<LgModelAudit> lgModelAudit = null;
		try {
			lgModelAudit = lgModelAuditService.findLgModelAuditById(id);
		} catch (Exception e) {
			logger.error("[error][BINS][LGMODELAUDIT]", e);
		}
		int i = 0;
		try {
			i = lgModelAuditService.deleteLgModelAudit(id);
		} catch (Exception e) {
			logger.error("[error][BINS][LGMODELAUDIT] delete error", e);
		}
		return new ResponseResult(i, null, lgModelAudit);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.lgModelAudit, operation = Operate.DELETEBATCH)
	@ResponseBody
	@RequestMapping("/deleteLgModelAuditBatch.action")
	public ResponseResult deleteLgModelAuditBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][LGMODELAUDIT] ids[]=" + ids);
		Long startTime = System.currentTimeMillis();
		List<LgModelAudit> oldData = null;
		try {
			oldData = lgModelAuditService.findLgModelAuditByIds(ids);
		} catch (Exception e1) {
			logger.error("[LGMODELAUDIT] select by ids error,E={}", e1);
		}
		int i = 0;
		try {
			i = lgModelAuditService.deleteLgModelAuditBatch(ids);
		} catch (Exception e) {
			logger.error("[BINS][LGMODELAUDIT] delete batch is error.E={}", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][LGMODELAUDIT] ids[]=" + ids + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 更新必审词模板
	 * 
	 * @param request
	 * @param response
	 * @param lgModelAudit
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.lgModelAudit, operation = Operate.UPDATE)
	@ResponseBody
	@RequestMapping(value = "updateLgModelAudit.action", method = RequestMethod.POST)
	public ResponseResult updateLgModelAudit(HttpServletRequest request, HttpServletResponse response,
			LgModelAudit lgModelAudit) {
		logger.debug("[BINS][LGMODELAUDIT] " + lgModelAudit);
		Long startTime = System.currentTimeMillis();
		List<LgModelAudit> oldData = null;
		try {
			oldData = lgModelAuditService.findLgModelAuditById(lgModelAudit.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]", e1);
		}
		
		try {
			lgModelAudit.setCreateTime(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e1) {
			logger.error("[ERROR][BINS][LGMODELAUDIT] date error", e1);
		}
		int i = 0;
		try {
			i = lgModelAuditService.updateLgModelAudit(lgModelAudit);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] update menu is error", e);
		}
		List<LgModelAudit> newData = null;
		try {
			newData = lgModelAuditService.findLgModelAuditById(lgModelAudit.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]", e1);
		}
		ResponseResult result = new ResponseResult(i, newData, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][LGMODELAUDIT] " + lgModelAudit + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("findLgModelAuditById.action")
	@ResponseBody
	public LgModelAudit findLgModelAuditById(Integer id) {
		logger.debug("[BINS][LGMODELAUDIT] id=" + id);
		LgModelAudit lgModelAuditById = null;
		try {
			lgModelAuditById = lgModelAuditService.findLgModelAuditById(id).get(0);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][LGMODELAUDIT]", e);
		}
		return lgModelAuditById;
	}

	
	@RequestMapping("checkAuditContent.action")
	public @ResponseBody ResultData checkAuditContent(String content,String oldContent) throws Exception {
		logger.debug("[BINS][AUDIT] content=" + content+",oldContent="+oldContent);
		ResultData resultData = new ResultData();
		if(content.equals(oldContent)){
			resultData.setValid(true);
			return resultData;
		}
		LgModelAudit lgModelAudit = lgModelAuditService.findByContent(content);
		// System.out.println("用户登录用户名为："+ucenterMan);
		if (lgModelAudit == null) {
			resultData.setValid(true);
		} else {
			resultData.setValid(false);
		}
		logger.debug("[BINS][AUDIT] content=" + content + ", result=" + resultData.getValid());
		return resultData; // 注：ajax方式不能返回页面，只能返回数据
	}
}
