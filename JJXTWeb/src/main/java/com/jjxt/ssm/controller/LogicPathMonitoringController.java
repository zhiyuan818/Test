package com.jjxt.ssm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.jjxt.ssm.entity.SubmitToLogicPath;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.LgModelAuditService;
import com.jjxt.ssm.service.RedisService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.StringUtil;

@RequestMapping("/logicPathMonitoring")
@Controller
public class LogicPathMonitoringController {

	private static Logger logger = Logger.getLogger(LogicPathMonitoringController.class);

	private static final String PATH = "logicPathMonitoring/";

	@Autowired
	private HttpService httpService;

	@Autowired
	private ChannelService channelService;
	@Autowired
	private LgModelAuditService lgModelAuditService;
	@Value("${LOG_CHANNELID_PRIORITY_APPID}")
	private String LOG_CHANNELID_PRIORITY_APPID;
	@Value("${logService1}")
	private String logService1;
	@Value("${logService2}")
	private String logService2;
	@Value("${urgentService1}")
	private String urgentService1;
	@Value("${urgentService2}")
	private String urgentService2;
	@Value("${platformFlag}")
	private String platformFlag;
	@Autowired
	private RedisService redisService;

	@RequestMapping("/goToBeSubmittedList.action")
	public String goToBeSubmittedList(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String,Object>();
		List<Channel> findChannel = null;
		try {
			findChannel = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		request.setAttribute("channels", findChannel);
		return PATH + "toBeSubmittedList";
	}

	
	@RequestMapping("/findToBeSubmittedList.action")
	@ResponseBody
	public PageResult<SubmitToLogicPath> findToBeSubmittedList(Integer pageIndex, Integer pageSize) {
		logger.debug("[BINS][LOGIC] pageIndex=" + pageIndex + ",pageSize=" + pageSize);
		Long startTime = System.currentTimeMillis();
		List<SubmitToLogicPath> slps = new ArrayList<SubmitToLogicPath>();
		List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.LOG);
		for(String redisName:redisNameList) {
				Set<String> keys = RedisUtil.keys(LOG_CHANNELID_PRIORITY_APPID,redisName);
				Set<String> channelIdList = getChannelIdList(keys);
				int total = channelIdList.size();
				Page page = new Page(pageSize, total, pageIndex);
				Integer start = (page.getCurrPageNo() - 1) * page.getPageSize();
				Integer end = page.getCurrPageNo() * page.getPageSize();
				slps .addAll(setSubmitToLogicPathList(channelIdList, DateUtil.getTimeByMinute(-1), start, end,redisName));
		}
		Collections.sort(slps,new Comparator<SubmitToLogicPath>() {
			 
			@Override
			public int compare(SubmitToLogicPath o1, SubmitToLogicPath o2) {
				int i = o2.getCodeNumber() - o1.getCodeNumber();
				if(i==0){
					i=o2.getIndustryNumber()-o1.getIndustryNumber();
				}
				if(i==0){
					i=o2.getUrgentNumber()-o1.getUrgentNumber();
				}
				return i;
			}
		});
		Long endTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][LOGIC] pageIndex=" + pageIndex + ",pageSize=" + pageSize + ",time=" + (endTime - startTime));
		return new PageResult<SubmitToLogicPath>(slps.size(), slps);
	}

	
	@RequestMapping("/findMarketDetail.action")
	@ResponseBody
	public List<SubmitToLogicPath> findMarketDetail(Integer channelId) {
		logger.debug("[BINS][LOGIC] channelId=" + channelId);
		Long startTime = System.currentTimeMillis();
		String redisName = Constants.Common.Redis.Naming.LOG;
		Channel channel = null;
		try {
			channel = channelService.findChannelByChannelId(channelId);
		} catch (Exception e) {
			logger.error("ERR:CHANNEL] EX="+e);
		}
		if(channel != null) {
			redisName = "chan"+channel.getRedisFlag()+":"+Constants.Common.Redis.Naming.LOG;
			List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.LOG);
			if(!redisNameList.contains(redisName)) {
				redisName = Constants.Common.Redis.Naming.LOG; 
			}
		}
		Set<String> keys = RedisUtil.keys("LOG:" + channelId + ":2:*",redisName);
		List<SubmitToLogicPath> slps = setMarketDetail(keys,redisName);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][LOGIC] channelId=" + channelId + ",time=" + (endTime - startTime));
		return slps;
	}

	
	@DataOperate(bussiness = Bussiness.logiclog, operation = Operate.INSERT)
	@RequestMapping("/switchChannel.action")
	@ResponseBody
	public ResponseResult switchChannel(String src_channelId, String dest_channelId, Integer amount, Integer appId,
			String priority,String cmcc_channelId,String unicom_channelId,String telecom_channelId, String channelFlag) {
		logger.debug("[BINS][LOG] src_channelId=" + src_channelId + ",dest_channelId=" + dest_channelId + ",amount="
				+ amount + ",appId=" + appId + ",priority=" + priority+",cmcc_channel="+cmcc_channelId+",unicom_channelId="
				+unicom_channelId+",telecom_channelId="+telecom_channelId+",channelFlag="+channelFlag);
		if("1".equals(channelFlag)) {
			if(StringUtil.isEmpty(cmcc_channelId)) {
				cmcc_channelId = "0";
			}
			if(StringUtil.isEmpty(unicom_channelId)) {
				unicom_channelId = "0";
			}
			if(StringUtil.isEmpty(telecom_channelId)) {
				telecom_channelId = "0";
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if (appId != null) {
			map.put("app_id", appId);
		}
		map.put("src_channel_id", src_channelId);
		map.put("dest_channel_id", dest_channelId);
		map.put("num", amount.toString());
		map.put("priority", priority);
		map.put("cmcc_channel_id", cmcc_channelId);
		map.put("unicom_channel_id", unicom_channelId);
		map.put("telecom_channel_id", telecom_channelId);
		map.put("channel_flag", channelFlag);
		String result = null;
		try {
			result = httpService.doPost(logService1, map);
			logger.debug("[BINS][SERVICE]访问logService1：" + result);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][LOGIC] logService1 连接异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(logService2, map);
				logger.debug("[BINS][SERVICE]访问logService2：" + result);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][LOGIC] logService2 连接失败", e);
			}
		}
		if (result == null) {
			map.put("error","logService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("error","success");
			logger.debug("[BINS][LOGIC][SERVICE] 0 success");
		} else if ("-2001".equals(result)) {
			map.put("error","src_channel_id is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2001 src_channel_id is null");
		} else if ("-2002".equals(result)) {
			map.put("error","dest_channel_id is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2002 dest_channel_id is null");
		} else if ("-2003".equals(result)) {
			map.put("error","num is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2003 num is null");
		} else if ("-2004".equals(result)) {
			map.put("error","当priority为2时,app_id为");
			logger.debug("[BINS][LOGIC][SERVICE] -2004 当priority为2时,app_id为");
		} else if ("-2005".equals(result)) {
			map.put("error","priority为空");
			logger.debug("[BINS][LOGIC][SERVICE] -2005 priority为空");
		} else if ("-2006".equals(result)) {
			map.put("error","ip鉴权失败");
			logger.debug("[BINS][LOGIC][SERVICE] -2006 ip鉴权失败");
		} else if ("-2007".equals(result)) {
			map.put("error","num小于等于0");
			logger.debug("[BINS][LOGIC][SERVICE] -2007 num小于等于0");
		} else if ("-2008".equals(result)) {
			map.put("error","priority格式不正确");
			logger.debug("[BINS][LOGIC][SERVICE] -2008 priority格式不正确");
		} else if ("-3333".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][LOGIC][SERVICE] -3333 UNKNOWN");
		} else if ("-2014".equals(result)) {
			map.put("error","提交队列数据不能切换到其他平台");
			logger.debug("[BINS][LOGIC][SERVICE] -3333 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	
	@DataOperate(bussiness = Bussiness.logiclog, operation = Operate.INSERT)
	@RequestMapping("/urgentPriority.action")
	@ResponseBody
	public ResponseResult urgentPriority(String src_channelId, Integer amount, Integer appId) {
		logger.debug("[BINS][LOG] schannelId=" + src_channelId + ",amount=" + amount + ",appId=" + appId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel_id", src_channelId);
		map.put("num", amount.toString());
		map.put("app_id", appId.toString());
		String result = null;
		try {
			result = httpService.doPost(urgentService1, map);
			logger.debug("[BINS][SERVICE]访问 urgentService1：" + result);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][LOGIC] urgentService1 连接异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(urgentService2, map);
				logger.debug("[BINS][SERVICE]访问urgentService2：" + result);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][LOGIC] urgentService2 连接失败", e);
			}
		}
		if (result == null) {
			map.put("error","urgentService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("error","success");
			logger.debug("[BINS][LOGIC][SERVICE] 0 success");
		} else if ("-2009".equals(result)) {
			map.put("error","channel_id is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2009 channel_id is null");
		} else if ("-2004".equals(result)) {
			map.put("error","app_id is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2004 app_id is null");
		} else if ("-2003".equals(result)) {
			map.put("error","num is null");
			logger.debug("[BINS][LOGIC][SERVICE] -2003 num is null");
		} else if ("-2006".equals(result)) {
			map.put("error","ip鉴权失败");
			logger.debug("[BINS][LOGIC][SERVICE] -2006 ip鉴权失败");
		} else if ("-2007".equals(result)) {
			map.put("error","num小于等于0");
			logger.debug("[BINS][LOGIC][SERVICE] -2007 num小于等于0");
		} else if ("-3333".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][LOGIC][SERVICE] -3333 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	
	@RequestMapping("/findChannel.action")
	@ResponseBody
	public Map<String, Object> findChannel(String channelId){
		Channel channel=null;
		try {
			channel = channelService.findChannelByChannelId(Integer.parseInt(channelId));
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL] 查询通道列表异常",e);
		}
		List<Channel> channels = new ArrayList<Channel>();
		Set<Channel> set=new HashSet<>();
		Set<Channel> cmccSet = new HashSet<>();
		Set<Channel> unicomSet = new HashSet<>();
		Set<Channel> telecomSet = new HashSet<>();
		if(channel!=null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelStatus", "paused");
			try {
				channels=channelService.findChannel(map);
			} catch (Exception e) {
				logger.error("[ERR][CHAN] EX=",e);
			}
			
			for(Channel chan:channels) {
				if("yes".equals(channel.getToCmcc())&&channel.getToCmcc().equals(chan.getToCmcc())) {
					set.add(chan);
				}else if("yes".equals(channel.getToUnicom())&&channel.getToUnicom().equals(chan.getToUnicom())) {
					set.add(chan);
				}else if("yes".equals(channel.getToTelecom())&&channel.getToTelecom().equals(chan.getToTelecom())) {
					set.add(chan);
				}
			}
			
			for(Channel chan : channels) {
				if("yes".equals(channel.getToCmcc())&&channel.getToCmcc().equals(chan.getToCmcc())) {
					cmccSet.add(chan);
				}
				if("yes".equals(channel.getToUnicom())&&channel.getToUnicom().equals(chan.getToUnicom())) {
					unicomSet.add(chan);
				}
				if("yes".equals(channel.getToTelecom())&&channel.getToTelecom().equals(chan.getToTelecom())) {
					telecomSet.add(chan);
				}
			}
		}
		boolean channelFlag = false;
		Map<String, String> carriers = new HashMap<>();
		carriers.put("cmcc", channel.getToCmcc());
		carriers.put("unicom", channel.getToUnicom());
		carriers.put("telecom", channel.getToTelecom());
		int i=0;
		for(String str : carriers.values()) {
			if("yes".equals(str)) {
				i++;
			}
		}
		
		if(i>1) {
			channelFlag = true;
		}
		List<Channel> list=new ArrayList<Channel>(set);
			Collections.sort(list, new Comparator<Channel>(){

			@Override
			public int compare(Channel o1, Channel o2) {
				return o1.getChannelId()-o2.getChannelId();
			}
			
		});
		List<Channel> cmcclist=new ArrayList<Channel>(cmccSet);
		Collections.sort(cmcclist, new Comparator<Channel>(){
			
			@Override
			public int compare(Channel o1, Channel o2) {
				return o1.getChannelId()-o2.getChannelId();
			}
			
		});
		List<Channel> unicomlist=new ArrayList<Channel>(unicomSet);
		Collections.sort(unicomlist, new Comparator<Channel>(){
			
			@Override
			public int compare(Channel o1, Channel o2) {
				return o1.getChannelId()-o2.getChannelId();
			}
			
		});
		List<Channel> telecomlist=new ArrayList<Channel>(telecomSet);
		Collections.sort(telecomlist, new Comparator<Channel>(){
			
			@Override
			public int compare(Channel o1, Channel o2) {
				return o1.getChannelId()-o2.getChannelId();
			}
			
		});	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channels", list);
		map.put("carriers", carriers);
		map.put("channelFlag", channelFlag);
		map.put("cmccChannels", cmcclist);
		map.put("unicomChannels", unicomlist);
		map.put("telecomChannels", telecomlist);
		return map;
	}
	private Set<String> getChannelIdList(Set<String> set) {
		Iterator<String> it = set.iterator();
		Set<String> channelIds = new HashSet<String>();
		String st = null;
		String channelId = null;
		while (it.hasNext()) {
			st = it.next();
			channelId = st.split(":")[1];
			channelIds.add(channelId);
		}
		return channelIds;
	}

	private List<SubmitToLogicPath> setSubmitToLogicPathList(Set<String> set, String time, Integer start, Integer end,String redisName) {
		List<SubmitToLogicPath> list = new ArrayList<SubmitToLogicPath>();
		if(StringUtil.isEmpty(set)) {
			return list;
		}
		Iterator<String> iterator = set.iterator();
		String st = null;
		Long codeNumber = null;
		Long industryNumber = null;
		Long urgentNumber = null;
		Integer marketNumber = null;
		SubmitToLogicPath slps = null;
		Set<String> keys = null;
		byte[] hgetGwSubmitSpeed ;
		try {
			while (iterator.hasNext()) {
					slps = new SubmitToLogicPath();
					st = iterator.next();
					codeNumber = RedisUtil.lLen("LOG:" + st + ":0",redisName);
					industryNumber = RedisUtil.lLen("LOG:" + st + ":1",redisName);
					urgentNumber = RedisUtil.lLen("LOG:" + st + ":3",redisName);
					keys = RedisUtil.keys("LOG:" + st + ":2:*",redisName);
					hgetGwSubmitSpeed = RedisUtil.hget(platformFlag + "_mt:minute:"+st+":gwSubmit", time, Constants.Common.Redis.Naming.REDIS60);
					marketNumber = getMarketNumber(keys,redisName);
					slps.setChannelId(Integer.parseInt(st));
					slps.setCodeNumber(codeNumber.intValue());
					slps.setIndustryNumber(industryNumber.intValue());
					slps.setMarketNumber(marketNumber);
					slps.setUrgentNumber(urgentNumber.intValue());
					if(!StringUtil.isEmpty(hgetGwSubmitSpeed)) {
						slps.setGwSubmitSpeed(Math.round(Integer.valueOf(new String(hgetGwSubmitSpeed))/6.0*10.0)/100.0);
					}else {
						slps.setGwSubmitSpeed(0.0);
					}
					list.add(slps);
			}
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL]", e);
		}
		List<Channel> channels=new ArrayList<>();
		try {
			channels = channelService.findChannelById(set);
		} catch (Exception e) {
			logger.error("[ERROR][CHA] ex="+e);
		}
		for(SubmitToLogicPath sub :list){
			for(Channel cha:channels){
				if(sub.getChannelId().equals(cha.getChannelId())){
					sub.setChannelName(cha.getName()==null?"-":cha.getName());
				}
			}
		}
		return list;
	}

	private int getMarketNumber(Set<String> keys,String redisName) {
		if (keys == null || keys.size()==0) {
			return 0;
		}
		int number = 0;
		Iterator<String> iterator = keys.iterator();
		String st = null;
		while (iterator.hasNext()) {
			st = iterator.next();
			number += RedisUtil.lLen(st,redisName);
		}
		return number;
	}

	private List<SubmitToLogicPath> setMarketDetail(Set<String> key,String redisName) {
		List<SubmitToLogicPath> list = new ArrayList<SubmitToLogicPath>();
		if(key==null || key.size()==0){
			return list;
		}
		Iterator<String> iterator = key.iterator();
		String st = null;
		Integer appId = null;
		Integer channelId = null;
		SubmitToLogicPath slps = null;
		Long marketNumber = null;
		Application app = null;
		Channel channel = null;
		try {
			while (iterator.hasNext()) {
				st = iterator.next();
				slps = new SubmitToLogicPath();
				appId = Integer.parseInt(st.split(":")[3]);
				channelId = Integer.parseInt(st.split(":")[1]);
				app = lgModelAuditService.findApplicationById(appId);
				channel = channelService.findChannelByChannelId(channelId);
				slps.setAppId(appId);
				slps.setAppName(app==null?"":app.getAppName());
				slps.setPriority(app==null?"":app.getPriority());
				slps.setChannelId(channelId);
				slps.setChannelName(channel==null?"":channel.getName());
				marketNumber = RedisUtil.lLen(st,redisName);
				slps.setMarketNumber(marketNumber.intValue());
				list.add(slps);
			}
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL]",e);
		}
		return list;
	}
}
