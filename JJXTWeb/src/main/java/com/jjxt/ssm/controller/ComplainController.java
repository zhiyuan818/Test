package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Complain;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ComplainService;

@Controller
@RequestMapping("/complain")
public class ComplainController {

	private static Logger logger = Logger.getLogger(ComplainController.class);

	private static final String PATH = "complain/";
	@Autowired
	private ComplainService complainService;
	@Value("${changeDate}")
	private String changeDate;
	
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/goComplainPage.action")
	public String goComplainPageList(HttpServletRequest request, HttpServletResponse response) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
        request.setAttribute("apps", apps);
        request.setAttribute("channels", channels);
		return PATH + "complainPageList";
	}

	/**
	 * 查询投诉
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @param destNumber
	 * @param srcNumber
	 * @param content
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping("/findMtList.action")
	@ResponseBody
	public PageResult<Complain> findMtList(String destNumber, String srcNumber, String startTime,
			String endTime, String mtName, HttpServletRequest request, HttpServletResponse response) {

		Date start = null;
		Date end = null;
		List<Date> dateList = new ArrayList<Date>();
		List<Complain> lists = new ArrayList<Complain>();
		if(startTime == null || endTime == null) {
			return new PageResult<Complain>(0, lists);
		}
		Long sTime = System.currentTimeMillis();
		try {
			start = DateUtil.converDateFromStr3(startTime);
			end = DateUtil.converDateFromStr3(endTime);
			dateList = DateUtil.dateSplit(start, end);

		} catch (Exception e) {
			logger.error("[ERROR][DATA] 时间转换异常",e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("destNumber", destNumber);
		map.put("srcNumber", srcNumber);
		List<Complain> onelist = null;
		if (!dateList.isEmpty()) {
			for (Date date : dateList) {
				mtName = DateUtil.convertDate(date);
				map.put("mtName", mtName);
				try {
					if(DateUtil.isCompareTime(mtName, changeDate)) {
						onelist =complainService.findNewMtList(map);
					}else {
						onelist = complainService.findMtList(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][COMPLAIN]",e);
					continue;
				}
				lists.addAll(onelist);
			}
		} else {
				mtName = DateUtil.convertDate(new Date());
				map.put("mtName", mtName);
				try {
					if(DateUtil.isCompareTime(mtName, changeDate)) {
						onelist =complainService.findNewMtList(map);
					}else {
						onelist = complainService.findMtList(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][COMPLAIN]",e);
				}
				lists.addAll(onelist);

		}
		
		if(lists != null && lists.size()>0) {
			Iterator<Complain> iterator = lists.iterator();
			while(iterator.hasNext()) {
				Complain com = iterator.next();
				if(!"DELIVRD".equals(com.getReportStatus())){
					iterator.remove();
					continue;
				}
				if(!StringUtil.isEmpty(srcNumber)){
					if(!srcNumber.equals(com.getSrcNumber())){
						iterator.remove();
						continue;
					}
				}
				if(!StringUtil.isEmpty(com.getContent())) {
					String content = com.getContent();
					content = content.replaceAll("\\s", "");
					content = content.replaceAll("\n", "");
					content = content.replaceAll(" ", "");
					com.setContent(content);
				}
			}
		}
		int total = 0;
		total = lists.size();
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][COMPLAIN] destNumber=" + destNumber + ",srcNumber=" + srcNumber
				+ ",mtName=" + mtName + ",time=" + (eTime - sTime));

		return new PageResult<Complain>(total, lists);

	}

	/**
	 * 添加投诉
	 * 
	 * @param request
	 * @param response
	 * @param appId
	 * @param channelId
	 * @param destNumber
	 *            手机号
	 * @param srcNumber
	 * @param submitTime
	 * @param content
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.complain, operation = Operate.INSERT)
	@RequestMapping(value = "/addToPreComplain.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToPreComplain(HttpServletRequest request, HttpServletResponse response, Integer appId,
			Integer channelId, String destNumber, String srcNumber, Long submitTime, String content) {

		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		int add = 0;
		Map<String, Object> map = new HashMap<String, Object>();

		Date time = new Date(submitTime);
		String str = DateUtil.convertDate2(time);

		int black1 = 0;
		try {
			black1 = complainService.findBlackLevel1(destNumber);
		} catch (Exception e2) {
			logger.error("[ERROR][COMPLAIN] ", e2);
		}
		if (black1 == 0) {
			try {
				int addBlack = complainService.addToBlackLevel1(destNumber);
			} catch (Exception e1) {
				logger.error("[ERROR][COMPLAIN] 添加到一级黑出错.", e1);
			}

		}
		try {
			map.put("appId", appId);
			map.put("channelId", channelId);
			map.put("destNumber", destNumber);
			map.put("srcNumber", srcNumber);
			map.put("content", content);
			map.put("submitTime", str);
			add = complainService.addToPreComplain(map);

		} catch (Exception e) {
			logger.error("[ERROR][COMPLAIN] 添加到pre_complain出错.", e);
		}
		result.setResult(add);
		result.setNewData(map);
		result.setOldData(null);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][COMPLAIN] newData=" + map + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

		return result;
	}
	
	
	
	@RequestMapping("/findComplainPageList.action")
	@ResponseBody
	public PageResult<Complain> findComplainPageList(String appId,String channelId,String destNumber, String srcNumber,String content,
			 HttpServletRequest request, HttpServletResponse response,Integer pageIndex, Integer pageSize) {
		logger.debug("[BINS][COMPLAIN] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",appId="+appId+",channelId="+channelId+",destNumber="+destNumber+",srcNumber="+srcNumber+",content="+content);
		int total = 0;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("appId", appId);
		map1.put("channelId", channelId);
		map1.put("destNumber", destNumber);
		map1.put("srcNumber", srcNumber);
		map1.put("content", content);
		try {
			total = complainService.findComplainTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 查询通道总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appId", appId);
		map.put("channelId", channelId);
		map.put("destNumber", destNumber);
		map.put("srcNumber", srcNumber);
		map.put("content", content);
		List<Complain> complains = new ArrayList<Complain>();
		try {
			complains = complainService.findComplainPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通道列表异常", e);
		}
		return new PageResult<>(total, complains);
	}

}
