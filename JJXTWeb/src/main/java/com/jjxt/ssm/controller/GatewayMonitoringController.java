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
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.SubmitToGW;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.RedisService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/gatewayMonitoring")
public class GatewayMonitoringController {

	private static Logger logger = Logger.getLogger(GatewayMonitoringController.class);

	private static final String PATH = "gatewayMonitoring/";

	@Autowired
	private HttpService httpService;
	@Value("${GWLOG_CHANNELID}")
	private String GWLOG_CHANNELID;
	@Value("${gwService1}")
	private String gwService1;
	@Value("${gwService2}")
	private String gwService2;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private RedisService redisService;

	@RequestMapping("/goToBeSubmittedList.action")
	public String goAuditPageList(HttpServletRequest request) {
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
	public PageResult<SubmitToGW> findToBeSubmittedList(Integer pageIndex, Integer pageSize) {
		logger.debug("[BINS][GW] pageIndex=" + pageIndex + ",pageSize=" + pageSize);
		Long startTime = System.currentTimeMillis();
		List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.GW);
		List<SubmitToGW> sGWs = new ArrayList<SubmitToGW>();
		for(String redisName:redisNameList) {
			Set<String> keys = RedisUtil.keys(GWLOG_CHANNELID,redisName);
			int total = keys.size();
			if(total>0) {
				sGWs.addAll(setSubmitToGWList(keys,redisName));
			}
		}
		if(sGWs.size()==0) {
			return new PageResult<>(0, new ArrayList<>());
		}
		Collections.sort(sGWs,new Comparator<SubmitToGW>() {
			 
			@Override
			public int compare(SubmitToGW o1, SubmitToGW o2) {
				int i = (o2.getCodeAmount()==null?0:o2.getCodeAmount()) - (o1.getCodeAmount()==null?0:o1.getCodeAmount());
				if(i==0){
					i=(o2.getAmount()==null?0:o2.getAmount()) - (o1.getAmount()==null?0:o1.getAmount());
				}
				return i;
			}
		});
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][GW]   result size="+sGWs.size()+",time=" + (endTime - startTime));
		return new PageResult<>(sGWs.size(), sGWs);
	}

	private List<SubmitToGW> setSubmitToGWList(Set<String> Keys,String redisName) {
		List<SubmitToGW> list = new ArrayList<SubmitToGW>();
		if(Keys==null){
			return list;
		}
		Iterator<String> iterator = Keys.iterator();
		String st = null;
		String[] sp = null;
		Integer channelId = null;
		SubmitToGW sGw = null;
		Set<String> set=new HashSet<String>();
		while (iterator.hasNext()) {
				st = iterator.next();
				sp=st.split(":");
				if(sp.length!=2){
					break;
				}
				channelId = Integer.parseInt(sp[1]);
				set.add(channelId.toString());
		}
		Iterator<String> chanSet = set.iterator();
		while (chanSet.hasNext()) {
			sGw = new SubmitToGW();
			st = chanSet.next();
			sGw.setKey("GWLOG:"+st);
			sGw.setCodeKey("GWCODELOG:"+st);
			Long gwSize = RedisUtil.lLen("GWLOG:"+st,redisName);
			Long codeSize = RedisUtil.lLen("GWCODELOG:"+st,redisName);
			sGw.setAmount(gwSize.intValue());
			sGw.setCodeAmount(codeSize.intValue());
			sGw.setChannelId(Integer.parseInt(st));
			list.add(sGw);
		}
		
		List<Channel> channels=new ArrayList<Channel>();
		try {
			channels = channelService.findChannelById(set);
		} catch (Exception e) {
			logger.error("[ERROR][CHA] ex="+e);
		}
		for(SubmitToGW sub:list){
			for(Channel cha :channels){
				if(sub.getChannelId().equals(cha.getChannelId())){
					sub.setChannelName(cha.getName()==null?"-":cha.getName());
				}
			}
		}
		return list;
	}

	
	@ResponseBody
	@RequestMapping("/findAmountByKey.action")
	public Map<String, Object> findAmountByKey(String key) {
		logger.debug("[BING][GW] key=" + key);
		SubmitToGW gw = new SubmitToGW();
		String[] split = key.split(":");
		if(split.length!=2) {
			return new HashMap<String,Object>();
		}
		Integer channelId = Integer.parseInt(key.split(":")[1]);
		if(channelId==0) {
			return new HashMap<String,Object>();
		}
		Channel channel=null;
		try {
			channel = channelService.findChannelByChannelId(channelId);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL] 查询通道列表异常",e);
		}
		String redisName=Constants.Common.Redis.Naming.GW;
		if(channel!=null) {
			List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.GW);
			redisName="chan"+channel.getRedisFlag()+":"+Constants.Common.Redis.Naming.GW;
			if(!redisNameList.contains(redisName)) {
				redisName = Constants.Common.Redis.Naming.GW;
			}
		}
		Long size = RedisUtil.lLen(key,redisName);
		gw.setKey(key);
		gw.setAmount(size.intValue());
		Map<String, String> carriers = new HashMap<>();
		carriers.put("cmcc", channel.getToCmcc());
		carriers.put("unicom", channel.getToUnicom());
		carriers.put("telecom", channel.getToTelecom());
		gw.setCarriers(carriers);
		int i=0;
		for(String str : carriers.values()) {
			if("yes".equals(str)) {
				i++;
			}
		}
		
		if(i>1) {
			gw.setFlag(true);
		}
		gw.setChannelName(channel==null?null:channel.getName());
		gw.setChannelId(channelId);
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
				}else
				if("yes".equals(channel.getToUnicom())&&channel.getToUnicom().equals(chan.getToUnicom())) {
					set.add(chan);
				}else
				if("yes".equals(channel.getToTelecom())&&channel.getToTelecom().equals(chan.getToTelecom())) {
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
		Map<String, Object> result=new HashMap<String,Object>();
		result.put("gw", gw);
		result.put("channels", list);
		result.put("cmccChannels", cmcclist);
		result.put("unicomChannels", unicomlist);
		result.put("telecomChannels", telecomlist);
		return result;
	}

	
	@DataOperate(bussiness = Bussiness.gwlog, operation = Operate.INSERT)
	@ResponseBody
	@RequestMapping("/switchChannel.action")
	public ResponseResult switchChannel(String src_channelId, String dest_channelId, Integer amount,String flag,String channelFlag,String cmcc_channelId,String unicom_channelId,String telecom_channelId) {
		logger.debug("[BINS][GW] src_channelId=" + src_channelId + ",dest_channelId=" + dest_channelId + ",amount="
				+ amount+",flag="+flag+",channelFlag="+channelFlag+",cmcc_channelId="+cmcc_channelId+",unicom_channelId="+unicom_channelId+",telecom_channelId="+telecom_channelId);
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
		map.put("src_channel_id", src_channelId);
		map.put("dest_channel_id", dest_channelId);
		map.put("cmcc_channel_id", cmcc_channelId);
		map.put("unicom_channel_id", unicom_channelId);
		map.put("telecom_channel_id", telecom_channelId);
		map.put("num", amount.toString());
		map.put("priority", flag);
		map.put("channel_flag", channelFlag);
		String result = null;
		try {
			result = httpService.doPost(gwService1, map);
			logger.debug("[BINS][SERVICE]访问gwService1：" + result);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][GW] gwService1 连接异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(gwService2, map);
				logger.debug("[BINS][SERVICE]访问gwService2：" + result);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][GW] gwService2 连接失败", e);
			}
		}
		if (result == null) {
			map.put("error","gwService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("error","success");
			logger.debug("[BINS][GW][SERVICE] 0 success");
		} else if ("-2001".equals(result)) {
			map.put("error","src_channel_id is null");
			logger.debug("[BINS][GW][SERVICE] -2001 src_channel_id is null");
		} else if ("-2002".equals(result)) {
			map.put("error","dest_channel_id is null");
			logger.debug("[BINS][GW][SERVICE] -2002 dest_channel_id is null");
		} else if ("-2003".equals(result)) {
			map.put("error","num is null");
			logger.debug("[BINS][GW][SERVICE] -2003 num is null");
		} else if ("-2006".equals(result)) {
			map.put("error","ip鉴权失败");
			logger.debug("[BINS][GW][SERVICE] -2006 ip鉴权失败");
		} else if ("-2007".equals(result)) {
			map.put("error","num小于等于0");
			logger.debug("[BINS][GW][SERVICE] -2007 num小于等于0");
		} else if ("-2222".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][GW][SERVICE] -2222 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}
}
