package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.BlackSign;
import com.jjxt.ssm.service.BlackSignService;

@Controller
@RequestMapping("/blackSign")
public class BlackSignController {
	
	private static Logger logger = Logger.getLogger(BlackSignController.class);

	private static final String PATH = "blackSign/";
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	@Autowired
	private BlackSignService blackSignService;
	/**
	 * 跳转至黑签名页面
	 * @return
	 */
	@RequestMapping("/goBlackSignList.action")
	public String goBlackSignList(){
		return PATH+"blackSignList";
	}
	/**
	 * 黑签名分页列表
	 * @param pageIndex
	 * @param pageSize
	 * @param sign
	 * @return
	 */
	
	@RequestMapping("/findBlackSignList.action")
	@ResponseBody
	public PageResult<BlackSign> findBlackSignList(Integer pageIndex,Integer pageSize,String sign){
		logger.debug("[BINS][BLACKSIGN] pageIndex="+pageIndex+",pageSize="+pageSize+",sign="+sign);
		Map<String, Object> map=new HashMap<String,Object>();
		List<BlackSign> blackSigns=null;
		map.put("sign", sign);
		int total=0;
		try {
			total = blackSignService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN]",e);
		}
		if(total==0){
			return new PageResult<>(total, new ArrayList<>());
		}
		Page page=new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		map1.put("sign", sign);
		try {
			blackSigns=blackSignService.findBlackSignList(map1);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN]",e);
		}
		return new PageResult<>(total, blackSigns);
	}
	
	/**
	 * 校验是否存在
	 * @param content
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/signValidate.action")
	public ResultData signValidate(String sign ,String oldSign) {
		ResultData result = new ResultData();
		if(sign.equals(oldSign)){
			result.setValid(true);
			return result;
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("validate", sign);
		int total = 0;
		try {
			total = blackSignService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}
	
	/**
	 * 新增黑签名
	 * @param BlackSign
	 * @return
	 */
	
	@DataOperate(bussiness=Bussiness.blackSign,operation=Operate.INSERT)
	@RequestMapping("/addBlackSign.action")
	@ResponseBody
	public ResponseResult addBlackSign(BlackSign blackSign){
		logger.debug("[BINS][BLACKSIGN] blackSign="+blackSign);
		try {
			blackSign.setDataBanned(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e1) {
			logger.error("[ERROR][BINS][BlackSign] date error", e1);
		}
		blackSign.setMessageType(0);
		int result=0;
		try {
			result=blackSignService.addBlackSign(blackSign);
		} catch (Exception e) {
			logger.error("[ERRPR][BLACKSIGN]  ",e);
		}
		List<BlackSign> newData=null;
		try {
			newData = blackSignService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][BlackSign] ",e);
		}
		return new ResponseResult(result,newData,null);
	}
	/**
	 * 删除黑签名
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/deleteBlackSign.action")
	@ResponseBody
	public ResponseResult deleteBlackSign(Integer id){
		logger.debug("[BINS][BLACKSIGN] id="+id);
		List<BlackSign> blackSign = null;
		try {
			blackSign = blackSignService.findBlackSignById(id);
		} catch (Exception e) {
			logger.error("[ERROR][BlackSign]", e);
		}
		int i = 0;
		try {
			i = blackSignService.deleteBlackSign(id);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN] delete error", e);
		}
		return new ResponseResult(i, null, blackSign);
	}
	/**
	 * 更新黑签名
	 * @param blackSign
	 * @return
	 */
	
	@DataOperate(bussiness=Bussiness.blackSign,operation=Operate.UPDATE)
	@RequestMapping("/updateBlackSign.action")
	@ResponseBody
	public ResponseResult updateBlackSign(BlackSign blackSign){
		logger.error("[BINS][BLACKSIGN] blackSign="+blackSign);
		if(blackSign==null){
			return new ResponseResult(0, null, null);
		}
		List<BlackSign> oldData = null;
		try {
			oldData = blackSignService.findBlackSignById(blackSign.getId());
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN] ID查询异常", e);
		}
		int i = 0;
		try {
			i = blackSignService.updateBlackSign(blackSign);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN] UPDATE EXCEPTION", e);
		}
		List<BlackSign> newData = null;
		try {
			newData = blackSignService.findBlackSignById(blackSign.getId());
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN] ID查询异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findBlackSignById.action")
	@ResponseBody
	public BlackSign findBlackSignById(Integer id) {
		logger.debug("[BINS][BLACKSIGN] id=" + id);
		List<BlackSign> blackSigns = null;
		try {
			blackSigns = blackSignService.findBlackSignById(id);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSIGN] id查询异常", e);
		}
		if (!(null==blackSigns || blackSigns.isEmpty())) {
			return blackSigns.get(0);
		}
		return null;
	}
}
