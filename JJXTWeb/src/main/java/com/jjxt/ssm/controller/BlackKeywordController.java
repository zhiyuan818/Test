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

import com.jjxt.ssm.entity.BlackKeyword;
import com.jjxt.ssm.service.BlackKeywordService;

@Controller
@RequestMapping("/keyword")
public class BlackKeywordController {

	private static Logger logger = Logger.getLogger(BlackKeywordController.class);

	private static final String PATH = "keyword/";
	@Autowired
	private BlackKeywordService blackKeywordService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goBlackKeywordPage.action")
	public String goBlackKeywordPageList() {

		return PATH + "blackKeywordList";
	}

	
	@RequestMapping(value = "/findAllList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<BlackKeyword> findAllList() {

		List<BlackKeyword> b = new ArrayList<>();
		try {
			b = blackKeywordService.findAllList();
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] ", e);
		}
		return b;
	}

	/**
	 * 添加敏感词
	 * 
	 * @param request
	 * @param response
	 * @param keyWord
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackKeyword, operation = Operate.INSERT)
	@RequestMapping(value = "/addToBlackKeyword.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToBlackKeyword(HttpServletRequest request, HttpServletResponse response, String keyWord) {
		logger.debug("[BINS][BLKW] keyWord="+keyWord);
		Long sTime = System.currentTimeMillis();
		List<String> newList = new ArrayList<String>();
		int add = 0;
		try {
			if (!keyWord.equals("") && keyWord != null) {

				add = blackKeywordService.addToBlackKeyword(keyWord);
				newList.add(keyWord);
			}

		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 添加到pre_black_keyword表出错.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][BLKW] addData=" + newList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

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
	public PageResult<BlackKeyword> findPageList(Integer pageSize, Integer pageIndex, String keyWord, String startTime,
			String endTime, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][BLKW] pageSize="+pageSize+",pageIndex="+pageIndex+",keyWord="+keyWord+",startTime="+startTime+",endTime="+endTime);
		Long sTime = System.currentTimeMillis();

		Map<String, Object> map = new HashMap<String, Object>();
		List<BlackKeyword> list=new ArrayList<>();
		int total = 0;
		try {
			map.put("keyWord", keyWord);
			map.put("startTime", startTime);
			map.put("endTime", endTime);

			total = blackKeywordService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询pre_black_keyword表总数出错.", e);
		}
		if(total==0){
			return new PageResult<BlackKeyword>(total, list);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("keyWord", keyWord);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			list = blackKeywordService.findPageList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][BLKW] pageSize=" + pageSize + "pageIndex=" + pageIndex + "keyWord" + keyWord
					+ "startTime" + startTime + ",time=" + (eTime - sTime) + "ms");

			return new PageResult<BlackKeyword>(total, list);
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询pre_black_keyword表数据出错.", e);
		}

		return new PageResult<BlackKeyword>(total, list);
	}

	/**
	 * 删除敏感词
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackKeyword, operation = Operate.DELETE)
	@RequestMapping("/delBlackKeyword.action")
	@ResponseBody
	public ResponseResult delBlackKeyword(Integer id) {
		logger.debug("[BINS][BLKW] id="+id);
		int del = 0;
		BlackKeyword oldData = null;
		List<BlackKeyword> oldList = new ArrayList<BlackKeyword>();
		Map<String, Object> map=new HashMap<>();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = blackKeywordService.findBlackKeywordById(id);
			oldList.add(oldData);

		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询旧数据出错.", e);
		}
		if(oldData==null){
			map.put("id", id);
			return new ResponseResult(0,null,map);
		}
		try {
			del = blackKeywordService.delBlackKeyword(id);
			Long eTime = System.currentTimeMillis();
			logger.debug(
					"[BINS][BLKW] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result=" + del);
		} catch (Exception e1) {
			logger.error("[ERROR][BLKW] 删除pre_black_keyword表数据出错.", e1);
		}

		return new ResponseResult(del,null,oldData);
	}

	/**
	 * 通过id查询敏感词数据
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findBlackKeywordById.action")
	@ResponseBody
	public BlackKeyword findBlackKeywordById(Integer id) {
		logger.debug("[BING][BLKW] id="+id);
		BlackKeyword blackKeyword = null;
		try {
			blackKeyword = blackKeywordService.findBlackKeywordById(id);
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询pre_black_keyword表数据出错.", e);
		}

		return blackKeyword;
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @param blackKeyword
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackKeyword, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateBlackKeyword.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateBlackKeyword(HttpServletRequest request, HttpServletResponse response,
			BlackKeyword blackKeyword) {
		logger.debug(
				"[BINS][BLKW] " +blackKeyword);
		if(blackKeyword==null){
			return new ResponseResult(0,null,blackKeyword);
		}
		int update = 0;
		ResponseResult result = new ResponseResult();
		Integer id = blackKeyword.getId();
		BlackKeyword oldData = null;
		List<BlackKeyword> oldList = new ArrayList<BlackKeyword>();
		List<BlackKeyword> newList = new ArrayList<BlackKeyword>();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = blackKeywordService.findBlackKeywordById(id);
			oldList.add(oldData);
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询旧数据出错.", e);
		}
		if(oldData==null){
			return new ResponseResult(0,null,blackKeyword);
		}
		try {
			update = blackKeywordService.updateBlackKeyword(blackKeyword);
			newList.add(blackKeyword);
			result.setResult(update);
			result.setNewData(newList);
			result.setOldData(oldList);
			Long eTime = System.currentTimeMillis();
			logger.debug(
					"[BINS][BLKW] newData=" + newList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][BLKW] 修改敏感词数据出错.", e1);
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
	@RequestMapping("/delBlackKeywordBatch.action")
	@ResponseBody
	public ResponseResult delBlackKeywordBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][BLKW] ids="+ids);
		BlackKeyword blackkeyword = null;
		List<BlackKeyword> oldList = new ArrayList<BlackKeyword>();
		int del = 0;
		ResponseResult result = null;
		Long sTime = System.currentTimeMillis();
		for (Integer id : ids) {
			try {
				blackkeyword = blackKeywordService.findBlackKeywordById(id);
				oldList.add(blackkeyword);

			} catch (Exception e) {
				logger.error("[ERROR][BLKW] 查询旧数据出错.", e);
			}
			try {
				del = blackKeywordService.delBlackKeyword(id);

			} catch (Exception e) {
				logger.error("[ERROR][BLKW] 删除数据出错.", e);
			}

			if (del == 0) {
				result = new ResponseResult();
				result.setResult(del);
				result.setNewData(null);
				result.setOldData(oldList);
				logger.debug("[BINS][BLKW] 删除数据失败,id=" + id);

				return result;
			}

		}
		result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][BLKW] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
		return result;
	}

	
	@RequestMapping("/findByKeyWord.action")
	@ResponseBody
	public ResultData findByKeyWord(String keyWord,String oldKeyWord){
		logger.debug("[BINS][BLKW] keyWord="+keyWord+",oldKeyWord="+oldKeyWord);
		ResultData result = new ResultData();
		if(keyWord.equals(oldKeyWord)){
			result.setValid(true);
			return result;
		}
		int total = 0;
		try {
			total = blackKeywordService.findByKeyWord(keyWord);
		} catch (Exception e) {
			logger.error("[ERROR][BLKW] 查询重复敏感词出错.", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}
		
		return result;
	}
	
	
	

}
