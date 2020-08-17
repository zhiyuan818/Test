package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.LgModelSendService;

/**
 * 模板管理（模板短信）
 * 
 * @author yhhou
 *
 *         2018年6月25日上午10:56:01
 */
@Controller
@RequestMapping("/lgModelSend")
public class LgModelSendController {
	private static Logger logger = Logger.getLogger(LgModelSendController.class);

	private static final String PATH = "lgModelSend/";
	
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	
	@Autowired
	private LgModelSendService lgModelSendService;
	
	@Autowired
	private ChannelService channelService;
	/**
	 * 跳转模板列表
	 * 
	 * @return
	 */
	@RequestMapping("/goLgModelSendList.action")
	public String goLgModelSendList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<Channel>();
		List<Channel> cmccChannels =  new ArrayList<Channel>();
		List<Channel> unicomChannels =  new ArrayList<Channel>();
		List<Channel> telecomChannels =  new ArrayList<Channel>();
		Map<String, Object> map=new HashMap<String,Object>();
		
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL]", e);
		}
		for(Channel cha:channels){
			if("yes".equals(cha.getToCmcc())){
				cmccChannels.add(cha);
			}
			if("yes".equals(cha.getToUnicom())){
				unicomChannels.add(cha);
			}
			if("yes".equals(cha.getToTelecom())){
				telecomChannels.add(cha);
			}
		}
		request.setAttribute("channels", channels);
		request.setAttribute("cmccChannels", cmccChannels);
		request.setAttribute("unicomChannels", unicomChannels);
		request.setAttribute("telecomChannels", telecomChannels);
		return PATH + "lgModelSendList";
	}
	/**
	 * 查询短信模板分页列表
	 * @param pageIndex
	 * @param pageSize
	 * @param content
	 * @return
	 */
	
	@RequestMapping("/findLgModelSendList.action")
	@ResponseBody
	public PageResult<LgModelSend> findLgModelSendList(Integer pageIndex, Integer pageSize,String content,String channelId,String id) {
		logger.debug("[BINS][LGMODELSEND] pageSize=" + pageSize + ",pageIndex=" + pageIndex+",content="+content+",channelId="+channelId+",id="+id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("content", content);
		map.put("channelId", channelId);
		int total=0;
		try {
			total = lgModelSendService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERRPR][LGMODELSEND]  ",e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		map1.put("id", id);
		map1.put("content", content);
		map1.put("channelId", channelId);
		List<LgModelSend> list=null;
		try {
			list = lgModelSendService.findLgModelSendList(map1);
		} catch (Exception e) {
			logger.error("[ERRPR][LGMODELSEND]  ",e);
		}
		return new PageResult<>(total, list);
	}
	/**
	 * 新增短信模板
	 * @param lgModelSend
	 * @return
	 */
	
	@DataOperate(bussiness=Bussiness.lgModelSend,operation=Operate.INSERT)
	@RequestMapping("/addLgModelSend.action")
	@ResponseBody
	public ResponseResult addLgModelSend(LgModelSend lgModelSend){
		logger.debug("[BINS][LGMODELSEND] lgmodel="+lgModelSend);
		lgModelSend.setCmccChannelId(lgModelSend.getCmccChannelId()==null?0:lgModelSend.getCmccChannelId());
		lgModelSend.setUnicomChannelId(lgModelSend.getUnicomChannelId()==null?0:lgModelSend.getUnicomChannelId());
		lgModelSend.setTelecomChannelId(lgModelSend.getTelecomChannelId()==null?0:lgModelSend.getTelecomChannelId());
		try {
			lgModelSend.setCreateTime(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e1) {
			logger.error("[ERROR][BINS][LGMODELSEND] date error", e1);
		}
		int result=0;
		try {
			result=lgModelSendService.addLgModelSend(lgModelSend);
		} catch (Exception e) {
			logger.error("[ERRPR][LGMODELSEND]  ",e);
		}
		List<LgModelSend> newData=null;
		try {
			newData = lgModelSendService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] ",e);
		}
		return new ResponseResult(result,newData,null);
	}
	/**
	 * 校验是否存在
	 * @param content
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/contentValidate.action")
	public ResultData contentValidate(String content,String oldContent) {
		ResultData result = new ResultData();
		if(content.equals(oldContent)){
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("validate", content);
		int total = 0;
		try {
			total = lgModelSendService.findTotal(paramMap);
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
	 * 删除短信模板
	 * @param id
	 * @return
	 */
	@DataOperate(bussiness = Bussiness.lgModelSend, operation = Operate.DELETE)
	@RequestMapping("/deleteLgModelSend.action")
	@ResponseBody
	public ResponseResult deleteLgModelSend(Integer id){
		logger.debug("[BINS][LGMODELSEND] id="+id);
		List<LgModelSend> lgModelSend = null;
		try {
			lgModelSend = lgModelSendService.findLgModelSendById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND]", e);
		}
		int i = 0;
		try {
			i = lgModelSendService.deleteLgModelSend(id);
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] delete error", e);
		}
		return new ResponseResult(i, null, lgModelSend);
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.lgModelSend, operation = Operate.DELETEBATCH)
	@RequestMapping("/delLgModelSendBatch.action")
	@ResponseBody
	public ResponseResult delLgModelSendBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		
		List<LgModelSend> oldList = new ArrayList<>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
			try {
				oldList = lgModelSendService.findLgModelSendByIds(ids);
			} catch (Exception e) {
				logger.error("[ERROR][LGMODELSEND] Error getting old data", e);
			}
			try {
				del = lgModelSendService.delLgModelSendBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][LGMODELSEND] Delete data error", e);
			}
			ResponseResult result = new ResponseResult(del, null, oldList);
			Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][LGMODELSEND] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	/**
	 * 更新短信模板
	 * @param lgModelSend
	 * @return
	 */
	
	@DataOperate(bussiness=Bussiness.lgModelSend,operation=Operate.UPDATE)
	@RequestMapping("/updateLgModelSend.action")
	@ResponseBody
	public ResponseResult updateLgModelSend(LgModelSend lgModelSend){
		logger.debug("[BINS][LGMODELSEND] lgModelSend="+lgModelSend);
		lgModelSend.setCmccChannelId(lgModelSend.getCmccChannelId()==null?0:lgModelSend.getCmccChannelId());
		lgModelSend.setUnicomChannelId(lgModelSend.getUnicomChannelId()==null?0:lgModelSend.getUnicomChannelId());
		lgModelSend.setTelecomChannelId(lgModelSend.getTelecomChannelId()==null?0:lgModelSend.getTelecomChannelId());
		List<LgModelSend> oldData = null;
		try {
			oldData = lgModelSendService.findLgModelSendById(lgModelSend.getId());
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] ID查询异常", e);
		}
		int i = 0;
		try {
			i = lgModelSendService.updateLgModelSend(lgModelSend);
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] UPDATE EXCEPTION", e);
		}
		List<LgModelSend> newData = null;
		try {
			newData = lgModelSendService.findLgModelSendById(lgModelSend.getId());
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] ID查询异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findLgModelSendById.action")
	@ResponseBody
	public LgModelSend findLgModelSendById(Integer id) {
		logger.debug("[BINS][LGMODELSEND] id=" + id);
		List<LgModelSend> lgModelSends = null;
		try {
			lgModelSends = lgModelSendService.findLgModelSendById(id);
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND] id查询异常", e);
		}
		if (!(null==lgModelSends || lgModelSends.isEmpty())) {
			return lgModelSends.get(0);
		}
		return null;
	}
}
