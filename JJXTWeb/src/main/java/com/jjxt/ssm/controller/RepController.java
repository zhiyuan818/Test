package com.jjxt.ssm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.common.HttpService;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.PushReport;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.MOService;
import com.jjxt.ssm.service.MTService;
import com.jjxt.ssm.service.RedisService;

@RequestMapping("/rep")
@Controller
public class RepController {

	private static Logger logger = Logger.getLogger(RepController.class);

	private static final String PATH = "rep/";

	@Autowired
	private HttpService httpService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private MTService mtService;
	@Autowired
	private MOService moService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BaseCompanyService baseCompanyService;
	@Value("${AD_APPID}")
	private String AD_APPID;
	@Value("${repService1}")
	private String repService1;
	@Value("${repService2}")
	private String repService2;
	@Value("${putRepService1}")
	private String putRepService1;
	@Value("${putRepService2}")
	private String putRepService2;
	@Value("${putMoService1}")
	private String putMoService1;
	@Value("${putMoService2}")
	private String putMoService2;
	@Value("${changeDate}")
	private String changeDate;
	@Autowired
	private RedisService redisService;
	/**
	 * 跳转状态报告监控页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goPushReportList.action")
	public String goToBeSubmittedList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][STATISTIC] ", e);
		}
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "pushReportList";
	}

	/**
	 * 状态报告列表
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	@RequestMapping("/findPushReportList.action")
	@ResponseBody
	public PageResult<PushReport> findPushReportList(Integer pageIndex, Integer pageSize,String appId) {
		logger.debug("[BINS][REP] pageIndex=" + pageIndex + ",pageSize=" + pageSize);
		Long startTime = System.currentTimeMillis();
		List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.REPORT);
		List<PushReport> slps = new ArrayList<>();
		for(String redisName:redisNameList) {
			Set<String> keys = RedisUtil.keys(AD_APPID,redisName);
			Set<String> appIdList = getAppIdList(keys);
			int total = appIdList.size();
			Page page = new Page(pageSize, total, pageIndex);
			Integer start = (page.getCurrPageNo() - 1) * page.getPageSize();
			Integer end = page.getCurrPageNo() * page.getPageSize();
			slps.addAll(setPushReportList(appIdList, start, end,appId,redisName));
		}
		Collections.sort(slps,new Comparator<PushReport>() {
			 
			@Override
			public int compare(PushReport o1, PushReport o2) {
				int i = o2.getAdNumber() - o1.getAdNumber();
				if(i==0){
					i = o2.getMoadNumber() - o1.getMoadNumber();
				}
				return i;
			}
		});
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][REP] pageIndex=" + pageIndex + ",pageSize=" + pageSize + ",time=" + (endTime - startTime));
		return new PageResult<PushReport>(slps.size(), slps);
	}

	/**
	 * 清理
	 * 
	 * @param key
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.rep, operation = Operate.INSERT)
	@RequestMapping("/cleanPushReport.action")
	@ResponseBody
	public ResponseResult cleanPushReport(String key) {
		logger.debug("[BINS][REP] key=" + key);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", key);
		String result = null;
		try {
			result = httpService.doPost(repService1, map);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][SERVICE] repService1 异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(repService2, map);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][SERVICE] repService2 异常", e);
			}
		}
		if (result == null) {
			map.put("error","urgentService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("success","success");
			logger.debug("[BINS][REP][SERVICE] 0 success");
		} else if ("-2006".equals(result)) {
			map.put("error","ip地址出错");
			logger.debug("[BINS][REP][SERVICE] -2006 ip地址出错");
		} else if ("-2009".equals(result)) {
			map.put("error","key is null");
			logger.debug("[BINS][REP][SERVICE] -2006 key is null");
		} else if ("-4444".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][REP][SERVICE] -4444 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	/**
	 * 跳转重推页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goReplayState.action")
	public String goReplayState(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String,Object>();
		List<Channel> findChannel = null;
		try {
			findChannel = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		request.setAttribute("channels", findChannel);
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		request.setAttribute("apps", apps);
		return PATH + "replayState";
	}

	/**
	 * 查看符合重推条件的数量
	 * 
	 * @param logDate
	 * @param appId
	 * @param provider
	 * @param beginTime
	 * @param endTime
	 * @param sendFlag
	 * @param uniqueId
	 * @param channelMsgId
	 * @param channelId
	 * @param destNumber
	 * @param sourceSegment
	 * @param content
	 * @return
	 */
	
	@RequestMapping("/searchMTNumber.action")
	@ResponseBody
	public Integer searchMTNumber(String logDate, String appId, String provider, String beginTime, String endTime,
			String sendFlag, String uniqueId, String channelMsgId, String channelId, String destNumber,String sourceSegment,
			String content) {
		logger.debug("[BINS][REP] search logDate=" + logDate + ",appId=" + appId + ",provider=" + provider
				+ ",beginTime=" + beginTime + ",endTime=" + endTime + ",uniqueId=" + uniqueId + ",sendFlag=" + sendFlag
				+ ",channelMsgId=" + channelMsgId + ",channelId=" + channelId + ",destNumber=" + destNumber + ",sourceSegment=" + sourceSegment
				+ ",content=" + content);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[error]时间转换异常", e);
			}
		}
		provider = "";
		map.put("logDate", logDate);
		map.put("appId", appId);
		map.put("provider", provider);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("sendFlag", sendFlag);
		map.put("uniqueId", uniqueId);
		map.put("channelMsgId", channelMsgId);
		map.put("channelId", channelId);
		map.put("destNumber", destNumber);
		map.put("sourceSegment", sourceSegment);
		map.put("content", content);
		Integer num = 0;
		try {
			if(DateUtil.isCompareTime(logDate, changeDate)) {
				num = mtService.findNewPutReportSum(map);
			}else {
				num = mtService.findPutReportSum(map);
			}
		} catch (Exception e) {
			logger.error("[BINS][REP] 查询重推数量异常", e);
		}
		return num;
	}

	
	@RequestMapping("/searchMONumber.action")
	@ResponseBody
	public Integer searchMONumber(String logDate, String appId, String provider, String beginTime, String endTime,
			String sendFlag, String uniqueId, String channelMsgId, String channelId, String destNumber, String sourceSegment,
			String content) {
		logger.debug("[BINS][REP] search logDate=" + logDate + ",appId=" + appId + ",provider=" + provider
				+ ",beginTime=" + beginTime + ",endTime=" + endTime + ",uniqueId=" + uniqueId + ",sendFlag=" + sendFlag
				+ ",channelMsgId=" + channelMsgId + ",channelId=" + channelId + ",destNumber=" + destNumber + ",sourceSegment=" + sourceSegment
				+ ",content=" + content);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate1(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate1(DateUtil.converDateFromStr4(logDate));
			} catch (ParseException e) {
				logger.error("[ERROR][MO] 时间转换异常.", e);
			}
		}
		map.put("logDate", logDate);
		map.put("appId", appId);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		map.put("channelMsgId", channelMsgId);
		map.put("channelId", channelId);
		map.put("srcNumber", destNumber);
		map.put("sourceSegment", sourceSegment);
		map.put("content", content);
		Integer num = 0;
		try {
			num = moService.findPutMoSum(map);
		} catch (Exception e) {
			logger.error("[BINS][REP] 查询重推上行数量异常", e);
		}
		return num;
	}

	
	@DataOperate(bussiness = Bussiness.rep, operation = Operate.INSERT)
	@RequestMapping("/pushRepState.action")
	@ResponseBody
	public ResponseResult pushRepState(String logDate, String appId, String provider, String beginTime, String endTime,
			String sendFlag, String uniqueId, String channelMsgId, String channelId, String destNumber, String sourceSegment,
			String content,String destSendFlag, String number, String seconds, String reportStatus) {
		logger.debug("[BINS][REP] search logDate=" + logDate + ",appId=" + appId + ",provider=" + provider
				+ ",beginTime=" + beginTime + ",endTime=" + endTime + ",uniqueId=" + uniqueId + ",sendFlag=" + sendFlag
				+ ",channelMsgId=" + channelMsgId + ",channelId=" + channelId + ",destNumber=" + destNumber + ",sourceSegment=" + sourceSegment
				+ ",content=" + content + ",destSendFlag=" + destSendFlag + ",reportStatus=" + reportStatus
				+ ",seconds=" + seconds + ",number=" + number);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate(DateUtil.converDateFromStr3(logDate));
			} catch (ParseException e) {
				logger.error("[error]时间转换异常", e);
			}
		}
		map.put("log_date", logDate);
		map.put("app_id", appId);
		map.put("provider", provider);
		map.put("begin_time", beginTime);
		map.put("end_time", endTime);
		map.put("send_flag", sendFlag);
		map.put("unique_id", uniqueId);
		map.put("channel_msg_id", channelMsgId);
		map.put("channel_id", channelId);
		map.put("dest_number", destNumber);
		map.put("src_number", sourceSegment);
		map.put("content", content);
		map.put("dest_send_flag", destSendFlag);// 目标标记
		map.put("report_status", reportStatus);// 状态报告
		map.put("seconds", seconds);// 秒数
		map.put("number", number);// 数量
		String result = null;
		try {
			result = httpService.doPost(putRepService1, map);
		} catch (URISyntaxException | IOException e) {
			logger.error("[BINS][PUTREPSERVICE] putRepService1 异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(putRepService2, map);
			} catch (URISyntaxException | IOException e) {
				logger.error("[BINS][PUTREPSERVICE] putRepService2 异常", e);
			}
		}
		if (result == null) {
			map.put("error","putRepService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("success","success");
			logger.debug("[BINS][PUTREPSERVICE] 0 success");
		} else if ("-2006".equals(result)) {
			map.put("error","ip地址出错");
			logger.debug("[BINS][PUTREPSERVICE] -2006 ip地址出错");
		} else if ("-2009".equals(result)) {
			map.put("error","key is null");
			logger.debug("[BINS][PUTREPSERVICE] -2006 key is null");
		} else if ("-4444".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][PUTREPSERVICE] -4444 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	
	@DataOperate(bussiness = Bussiness.rep, operation = Operate.INSERT)
	@RequestMapping("/putMo.action")
	@ResponseBody
	public ResponseResult putMo(String logDate, String appId, String beginTime, String endTime, String channelMsgId,
			String channelId, String destNumber, String sourceSegment, String content, String number) {
		logger.debug("[BINS][REP] search logDate=" + logDate + ",appId=" + appId + ",beginTime=" + beginTime
				+ ",endTime=" + endTime + ",channelMsgId=" + channelMsgId + ",channelId=" + channelId + ",destNumber="
				+ destNumber + ",sourceSegment="+ sourceSegment + ",content=" + content + ",number=" + number);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate1(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate1(DateUtil.converDateFromStr4(logDate));
			} catch (ParseException e) {
				logger.error("[ERROR][MO] 时间转换异常.", e);
			}
		}
		map.put("log_date", logDate);
		map.put("app_id", appId);
		map.put("begin_time", beginTime);
		map.put("end_time", endTime);
		map.put("channel_msg_id", channelMsgId);
		map.put("channel_id", channelId);
		map.put("src_number", destNumber);
		map.put("dest_number", sourceSegment);
		map.put("content", content);
		map.put("number", number);// 数量
		String result = null;
		try {
			result = httpService.doPost(putMoService1, map);
		} catch (URISyntaxException | IOException e) {
			logger.error("[BINS][PUTREPSERVICE] putMoService1 异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(putMoService2, map);
			} catch (URISyntaxException | IOException e) {
				logger.error("[BINS][PUTREPSERVICE] putMoService2 异常", e);
			}
		}
		if (result == null) {
			map.put("error","putMoService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("success","success");
			logger.debug("[BINS][PUTREPSERVICE] 0 success");
		} else if ("-2006".equals(result)) {
			map.put("error","ip地址出错");
			logger.debug("[BINS][PUTREPSERVICE] -2006 ip地址出错");
		} else if ("-2009".equals(result)) {
			map.put("error","key is null");
			logger.debug("[BINS][PUTREPSERVICE] -2006 key is null");
		} else if ("-4444".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][PUTREPSERVICE] -4444 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	private Set<String> getAppIdList(Set<String> set) {
		Iterator<String> iterator = set.iterator();
		Set<String> appIds = new HashSet<String>();
		String st = null;
		while (iterator.hasNext()) {
			st = iterator.next();
			appIds.add(st.split(":")[1]);
		}
		return appIds;
	}

	private List<PushReport> setPushReportList(Set<String> addIdList, Integer start, Integer end,String appId,String redisName) {
		List<PushReport> list = new ArrayList<PushReport>();
		Iterator<String> iterator = addIdList.iterator();
		String st = null;
		Long adNumber = null;
		Long moadNumber = null;
		PushReport pr = null;
		try {
			while (iterator.hasNext()) {
				st = iterator.next();
				if(!StringUtil.isEmpty(appId)) {
					if(appId.equals(st)) {
						pr = new PushReport();
						adNumber = RedisUtil.lLen("AD:" + st,redisName);
						moadNumber = RedisUtil.lLen("MOAD:" + st,redisName);
						pr.setAppId(Integer.parseInt(st));
						pr.setAdNumber(adNumber.intValue());
						pr.setMoadNumber(moadNumber.intValue());
						list.add(pr);
					}
				}else {
					pr = new PushReport();
					adNumber = RedisUtil.lLen("AD:" + st,redisName);
					moadNumber = RedisUtil.lLen("MOAD:" + st,redisName);
					pr.setAppId(Integer.parseInt(st));
					pr.setAdNumber(adNumber.intValue());
					pr.setMoadNumber(moadNumber.intValue());
					list.add(pr);
				}
			}
		} catch (Exception e) {
			logger.error("[ERROR][REP] EX= "+e);
		}
		List<Map<String,String>> comps=new ArrayList<>();//map key appId,value companyName
		try {
			comps = baseCompanyService.findCompanyMapByAppId(addIdList);
		} catch (Exception e) {
			logger.error("[ERROR][COM] EX="+e);
		}
		for(PushReport put:list){
			for(Map<String,String> map:comps){
				if(String.valueOf(put.getAppId()).equals(String.valueOf(map.get("appId")))){
					put.setCompanyName(map.get("companyName"));
					put.setAppName(map.get("appName"));
				}
			}
		}
		return list;
	}
}
