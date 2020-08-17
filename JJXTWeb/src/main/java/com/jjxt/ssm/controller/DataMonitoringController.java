package com.jjxt.ssm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.common.HttpService;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.RedisInfo;
import com.jjxt.ssm.entity.SubmitToGW;
import com.jjxt.ssm.entity.Sync;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.DataManageService;
import com.jjxt.ssm.service.DataMonitoringService;
import com.jjxt.ssm.service.RedisService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.EchartData;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.Series;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/dataMonitoring")
public class DataMonitoringController {

	private static Logger logger = Logger.getLogger(DataMonitoringController.class);
	private static final String PATH = "dataMonitoring/";
	@Value("${priority_channelId}")
	private String priority_channelId;
	@Value("${priority1}")
	private String priority1;
	@Value("${priority2}")
	private String priority2;
	@Value("${platformFlag}")
	private String platformFlag;
	@Autowired
	private HttpService httpService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private DataMonitoringService dataMonitoringService;
	@Autowired
	private DataManageService dataManageService;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 数据监控汇总页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDataMonitoringList.action")
	public String goDataMonitoringList(HttpServletRequest request) {
		List<String> redisNameList = redisService.getRedisNameList(Constants.Common.Redis.Naming.SYNC);
		Long syncMo = 0l;
		Long syncMt = 0l;
		Long syncRpt = 0l;
		if(!StringUtil.isEmpty(redisNameList) && redisNameList.size()>0) {
			for(String redisName:redisNameList) {
				syncMo += RedisUtil.lLen("SYNC:MO", redisName);
				syncMt += RedisUtil.lLen("SYNC:MT", redisName);
				syncRpt += RedisUtil.lLen("SYNC:RPT", redisName);
			}
		}
		Long black1 = RedisUtil.hlen("logic:black1",Constants.Common.Redis.Naming.BLACK);
		Long black2 = RedisUtil.hlen("logic:black2",Constants.Common.Redis.Naming.BLACK);
		Long black3 = RedisUtil.hlen("logic:black3",Constants.Common.Redis.Naming.BLACK);
		Long black5 = RedisUtil.hlen("logic:black5",Constants.Common.Redis.Naming.BLACK);
		Long white = RedisUtil.hlen("logic:white",Constants.Common.Redis.Naming.WHITE);
		Long shunt = RedisUtil.hlen("logic:shuntphone",Constants.Common.Redis.Naming.SHUNT_PHONE);
		Long numPortability = RedisUtil.hlen("logic:num:portability",Constants.Common.Redis.Naming.WHITE);
		List<SubmitToGW> list = new ArrayList<SubmitToGW>();
		SubmitToGW sg = null;
		Set<String> priKeys = RedisUtil.keys(priority_channelId,Constants.Common.Redis.Naming.PRIORITY);
		Iterator<String> iterator = priKeys.iterator();
		String key = null;
		Long size = null;
		String channelId = null;
		Channel channel = null;
		try {
			while (iterator.hasNext()) {
				key = iterator.next();
				channelId = key.split(":")[2];
				sg = new SubmitToGW();
				size = RedisUtil.zSetLen(key,Constants.Common.Redis.Naming.PRIORITY);
				sg.setChannelId(Integer.parseInt(channelId));
				channel = channelService.findChannelByChannelId(Integer.parseInt(channelId));
				sg.setChannelName(channel == null ? "" : channel.getName());
				sg.setAmount(size.intValue());
				list.add(sg);
			}
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 查询通道异常",e);
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
			e.printStackTrace();
		}
		request.setAttribute("apps", apps);
		request.setAttribute("syncMo", syncMo);
		request.setAttribute("syncMt", syncMt);
		request.setAttribute("syncRpt", syncRpt);
		request.setAttribute("black1", black1);
		request.setAttribute("black2", black2);
		request.setAttribute("black3", black3);
		request.setAttribute("black5", black5);
		request.setAttribute("white", white);
		request.setAttribute("shunt", shunt);
		request.setAttribute("list", list);
		request.setAttribute("numPortability", numPortability);
		return PATH + "dataMonitoringList";
	}

	/**
	 * 查询黑名单，白名单，实库等手机号时候存在
	 * 
	 * @param phone
	 * @param searchType
	 * @return
	 */
	
	@RequestMapping("queryExist.action")
	@ResponseBody
	public Map<String, Object> queryExist(String phone, String searchType,String appId) {
		logger.debug("[BINS][DATAMONITORING] phone=" + phone + ",searchType=" + searchType+",appId="+appId);
		phone = phone.trim();
		boolean result = false;
		String npResult = null;
		String prividerResult = "未知";
		List<BlackLevel2> black2 = null;
		List<BlackLevel2> b2Result = new ArrayList<BlackLevel2>();
		BlackLevel2 blackLevel2 = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Application app = null;
		switch (searchType) {
		case "white":
			result = RedisUtil.hexist("logic:white", phone,Constants.Common.Redis.Naming.WHITE);
			break;
		case "black1":
			result = RedisUtil.hexist("logic:black1", phone,Constants.Common.Redis.Naming.BLACK);
			break;
		case "black2":
			List<String> phones = new ArrayList<>();
			phones.add(phone);
			try {
				black2 = dataManageService.findPhoneByBlack2(phones,appId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (BlackLevel2 b2 : black2) {
				blackLevel2 = new BlackLevel2();
				blackLevel2.setAppId(b2.getAppId());
				try {
					app = applicationService.findApplicationById(b2.getAppId());
				} catch (Exception e) {
					e.printStackTrace();
				}
				blackLevel2.setAppName(app == null ? "" : app.getAppName());
				blackLevel2.setPhoneNumber(b2.getPhoneNumber());
				b2Result.add(blackLevel2);
			}
			break;
		case "black3":
			result =  RedisUtil.hexist("logic:black3", phone,Constants.Common.Redis.Naming.BLACK);
			break;
		case "black5":
			result =  RedisUtil.hexist("logic:black5", phone,Constants.Common.Redis.Naming.BLACK);
			break;
		case "shunt":
			result =  RedisUtil.hexist("logic:shuntphone", phone,Constants.Common.Redis.Naming.SHUNT_PHONE);
			break;
		case "numPortability":
			result =  RedisUtil.hexist("logic:num:portability", phone,Constants.Common.Redis.Naming.WHITE);
			byte[] hget = RedisUtil.hget("logic:num:portability", phone, Constants.Common.Redis.Naming.WHITE);
			if(!StringUtil.isEmpty(hget)) {
			npResult = new String(hget);
			result = true;
			List<Map<String, String>> maps = dataMonitoringService.findProviderList();
			for (Map<String, String> mapp :maps) {
				if (!StringUtil.isEmpty(mapp.get("seg")) && phone.startsWith(mapp.get("seg")) && !StringUtil.isEmpty(mapp.get("carrier"))) {
					prividerResult = mapp.get("carrier");
					break;
				}
			}
			}
			break;
		}
		if (b2Result.size() > 0) {
			result = true;
		}
		map.put("result", result);
		map.put("b2list", b2Result);
		map.put("npResult", npResult);
		map.put("prividerResult", prividerResult);
		return map;
	}

	/**
	 * 查询需要清理的账户list
	 * 
	 * @param channelId
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/findCleanAppList.action")
	public List<Application> findCleanAppList(Integer channelId) {
		
		Set<String> set=RedisUtil.zRangeWithScores("LOMT:PRIORITY:" + channelId,Constants.Common.Redis.Naming.PRIORITY);
		List<Application> apps = new ArrayList<Application>();
		Application app = null;
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			try {
				app = applicationService.findApplicationById(Integer.parseInt(iterator.next()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (app != null) {
				apps.add(app);
			}
		}
		return apps;
	}

	/**
	 * 清理优先级
	 * 
	 * @param channelId
	 * @param appIds
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.priority, operation = Operate.CLEAN)
	@ResponseBody
	@RequestMapping("/cleanSubmit.action")
	public ResponseResult cleanSubmit(String channelId, String appIds) {
		logger.debug("[BINS][PRIORITY] channelId=" + channelId + ",appIds=" + appIds);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channel_id", channelId);
		map.put("flag", "D");
		if (!StringUtil.isEmpty(appIds)) {
			map.put("app_ids", appIds);
		}
		String result = null;
		try {
			result = httpService.doPost(priority1, map);
			logger.debug("[BINS][SERVICE]访问priority1：" + result);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][PRIORITY] priority1 连接异常", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(priority2, map);
				logger.debug("[BINS][SERVICE]访问priority2：" + result);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][PRIORITY] priority2 连接失败", e);
			}
		}
		if (result == null) {
			map.put("error","priorityService 连接异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("success","success");
			logger.debug("[BINS][PRIORITY][SERVICE] 0 success");
		} else if ("-4001".equals(result)) {
			map.put("error","channel_id is null");
			logger.debug("[BINS][PRIORITY][SERVICE] -4001 src_channel_id is null");
		} else if ("-4002".equals(result)) {
			map.put("error","ip鉴权失败");
			logger.debug("[BINS][PRIORITY][SERVICE] -4002 ip鉴权失败");
		} else if ("-7777".equals(result)) {
			map.put("error","UNKNOWN");
			logger.debug("[BINS][PRIORITY][SERVICE] -7777 UNKNOWN");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);

	}
	
	
	@RequestMapping("/goRedisList.action")
	public String goRedisList(HttpServletRequest request,HttpServletResponse response){
		return PATH+"redisList";
	}

	
	@RequestMapping("/findRedisList.action")
	@ResponseBody
	public List<RedisInfo> findRedisList(){
		logger.debug("[BINS][REDIS] find redis list");
		List<RedisInfo> redisLists=new ArrayList<>();
		try {
			redisLists = dataMonitoringService.findRedisList();
		} catch (Exception e) {
			logger.error("[ERROR][REDIS] ex="+e);
		}
		return redisLists;
	}

	
	@RequestMapping("/findRedisResult.action")
	@ResponseBody
	public Map<String, Object> findRedisResult(String value,String key,String name){
		logger.debug("[BINS][REDIS] value="+value+",key="+key+",name="+name);
		Map<String, Object> map=new HashMap<String,Object>();
		if("logic".equals(name)){
			if("u:version".equals(key)){
				byte[] hget = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(hget)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(hget));
			}else if("id:match:name".equals(key)){
				byte[] hget = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(hget)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(hget));
			}else if("logic:province".equals(key)){
				Sync.LgProvinces provinces=null;
				Sync.LgProvinces.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					provinces = Sync.LgProvinces.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.LgProvinces.newBuilder(provinces);
				sync.build();
				map.put("result", "通道ID="+sync.getChannelId()+",产品ID="+sync.getProductId()	+",省份="+new String(sync.getProvince().getBytes())+",运营商="+new String(sync.getCarriers().getBytes())+",内容规则："+new String(sync.getContentRule().getBytes()));
			}else if("logic:blacksign".equals(key)){
				Boolean hexists = RedisUtil.hexist(key, value, Constants.Common.Redis.Naming.LOGIC);
				map.put("result", hexists);
			}else if("logic:policies".equals(key)){
				Sync.AccountShuntPolicies accountShuntPolicies=null;
				Sync.AccountShuntPolicies.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					accountShuntPolicies = Sync.AccountShuntPolicies.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.AccountShuntPolicies.newBuilder(accountShuntPolicies);
				sync.build();
				map.put("result", "账号ID="+sync.getAppId()+",忽略省份="+new String(sync.getIgnoredProvinces().getBytes())+",忽略运营商="+new String(sync.getIgnoredCarriers().getBytes())+",包基数="+sync.getIgnoredPackMin()+",前基数="+sync.getIgnoredPackHead()+",后基数="+sync.getIgnoredPackTail()+",优享内容="+sync.getContent()+",省份/内容比例="+sync.getPercent());
			}else if("logic:extsign".equals(key)){
				byte[] hget = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(hget)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(hget));
			}else if("logic:modispatch".equals(key)){
				byte[] hget = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(hget)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(hget));
			}else if("logic:model".equals(key)){
				Sync.LgModelSend lgModelSend=null;
				Sync.LgModelSend.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					lgModelSend = Sync.LgModelSend.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.LgModelSend.newBuilder(lgModelSend);
				sync.build();
				map.put("result", "移动通道ID="+sync.getCmccChannelId()+",联通通道ID="+sync.getUnicomChannelId()+",电信通道ID="+sync.getTelecomChannelId()+",内容="+new String(sync.getContent().getBytes()));
			}else if("logic:resend".equals(key)){
				Sync.ResendConfig resendConfig=null;
				Sync.ResendConfig.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					resendConfig = Sync.ResendConfig.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.ResendConfig.newBuilder(resendConfig);
				sync.build();
				map.put("result", "重发ID="+sync.getId()+"状态码="+sync.getStatus()+",账户或通道Id="+sync.getAppChanId()
				+"跳转后的通道Id="+sync.getToChannel()+",id的类型="+sync.getIdType()+",短信类型="+sync.getMsgType()+",创建时间="+sync.getCreateTime());
			}else if("logic:channel:template:(channelId)".equals(key)){
				long hlen = RedisUtil.hlen(key.substring(0,key.lastIndexOf(":")+1) + value,Constants.Common.Redis.Naming.LOGIC);
				if(StringUtil.isEmpty(hlen)){
					map.put("result", "0");
					return map;
				}
				map.put("result", hlen);
			}
			

		}else if("rep".equals(name)){
			
		}else if("log".equals(name)){
			
		}else if("sync".equals(name)){
			if("SYNC:MT".equals(key)){
				long hget = RedisUtil.lLen(key, Constants.Common.Redis.Naming.SYNC);
				map.put("result", hget);
			}else if("SYNC:MO".equals(key)){
				long hget = RedisUtil.lLen(key, Constants.Common.Redis.Naming.SYNC);
				map.put("result", hget);
			}else if("SYNC:RPT".equals(key)){
				long hget = RedisUtil.lLen(key, Constants.Common.Redis.Naming.SYNC);
				map.put("result", hget);
			}
		}else if("dynamic".equals(name)){
			String dd=new SimpleDateFormat("dd").format(new Date());
			if("acc:balance".equals(key)){
				byte[] hget = RedisUtil.hget(key, value, Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(hget)){
					map.put("result", "");
					return map;
				}
				map.put("result", "账户余额:"+new String(hget));
			}else if("logic:minlimit".equals(key)){
				String[] st=value.split("_");
				String bs = RedisUtil.get("minlimit_"+"_"+st[0]+"_"+st[1] , Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(bs)){
					map.put("result", "");
					return map;
				}
				map.put("result", "秒限："+bs);
			}else if("logic:daylimit".equals(key)){
				String[] st=value.split("_");
				byte[] bs = RedisUtil.hget((key+":"+dd), (st[0]+"_"+st[1]), Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(bs)){
					map.put("result", "");
					return map;
				}
				map.put("result", "日限："+new String(bs));
			}else if("logic:daylimit:content".equals(key)){
				String[] st=value.split("_");
				byte[] bs = RedisUtil.hget((key+":"+dd), (st[0]+"_"+st[1]+"_"+st[2]), Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(bs)){
					map.put("result", "");
					return map;
				}
				map.put("result", "内容限："+new String(bs));
			}else if("ranext".equals(key)){
				//String bs = RedisUtil.get(key,Constants.Common.Redis.Naming.DYNAMIC);
				//map.put("result", "内容限："+bs);
			}else if("msgext:ranext".equals(key)){
				byte[] bs = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(bs)){
					map.put("result", "");
					return map;
				}
				map.put("result", "随机数："+new String(bs));
			}else if("ranext:msgext".equals(key)){
				byte[] bs = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.DYNAMIC);
				if(StringUtil.isEmpty(bs)){
					map.put("result", "");
					return map;
				}
				map.put("result", "长号："+new String(bs));
			}
		}else if("load".equals(name)){
			if("logic:account".equals(key)){
				Sync.Account account=null;
				Sync.Account.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					account = Sync.Account.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.Account.newBuilder(account);
				sync.build();
				map.put("result", "账户ID="+sync.getId()+",账户名称="+new String(sync.getName().getBytes())+",密码="+new String(sync.getPassword().getBytes())
						+",产品ID="+sync.getProductId()+",产品类型="+sync.getProductType()+",产品状态="+sync.getProductStatus()+",测试模式="+sync.getTestMode()+",付款方式="+sync.getPaymentType()+",开始时间："+sync.getServiceTimeBegin()+",结束时间："+sync.getServiceTimeEnd()+",默认签名="+new String(sync.getDefaultSign().getBytes())+",必须带签名="+sync.getIsSign()
						+",移动通道ID="+sync.getCmccChannelId()+",联通通道ID="+sync.getUnicomChannelId()+",电信通道ID="+sync.getTelecomChannelId()+",账户扩展="+sync.getAppExtSrc()+",余额="+sync.getLimitCount()+",发送量="+sync.getSentCount()
						+"按长号提交="+sync.getIsExt()+",长号="+sync.getSourceSegment()+",黑名单级别="+sync.getBlackLevels()+",跳过必审词="+sync.getSkipMustWords()+",优先级="+sync.getPriority()+",是否时限="+sync.getIsMinLimit()+",时限长度="+sync.getMinLimitCount()
						+",日限="+sync.getIsDayLimit()+",日限长度="+sync.getDayLimitCount()+",内容限="+sync.getIsDayContentLimit()+",内容限长度="+sync.getDayLimitConCount()+",优享比例="+sync.getGroupShuntLevel()+",状态报告获取方式="+sync.getRptSyncAddress()+",状态报告地址="+sync.getRptSyncModel()
						+"上行获取方式="+sync.getMoSyncAddress()+",上行地址="+sync.getMoSyncModel()+",最大连接数="+sync.getMaxConnection()+",是否优享="+sync.getIsShuntPhone()+",过必审词="+sync.getCheckWordsType()+",使用模板="+sync.getIsModel()+",进内容审核="+sync.getIsContAudit()+",超过条数="+sync.getContAuditCount()+",上行扩展="+sync.getMoFlag()+",月限="+sync.getIsMonthLimit()
						+",月限条数="+sync.getMonthLimitCount()+",使用策略模板="+sync.getIsTemplate()+",开启日限审核="+sync.getIsDayLimitCheck()+",非默认签名拦截="+sync.getIsDefaultSignSubmit()+",平台标识="+sync.getPlatformFlag()+",账户扩展参数="+sync.getAppExtras()+",是否推送状态报告="+sync.getIsSgip());
			}else if("logic:channel".equals(key)){
				Sync.Channel channel=null;
				Sync.Channel.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					channel = Sync.Channel.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.Channel.newBuilder(channel);
				sync.build();
				map.put("result", "通道ID="+sync.getChannelId()+"通道名称="+new String(sync.getName().getBytes())+",长号="+sync.getSourceSegment()+",支持移动="+sync.getToCmcc()+",支持联通="+sync.getToUnicom()+",支持电信="+sync.getToTelecom()+",状态报告="+sync.getHaveReport()+",上行="+sync.getHaveMo()
				+"自动扩展="+sync.getAutoExtSrc()+",用户扩展="+sync.getUserExtSrc()+",通道状态="+sync.getChannelStatus()+",报备状态="+sync.getBaobeiBeforePjExtSrc()+",网关类型="+sync.getGwType()+",网关地址="+sync.getSvcAddr()+",端口="+sync.getSvcPort()+",账号="+sync.getAccount()+",密码="+sync.getPassword()
				+",最大连接数="+sync.getLinkMax()+"最大发送速度="+sync.getLinkSpeed()+",服务代码="+sync.getServiceCode()+",企业代码="+sync.getEnterpriseCode()+",通道特定参数="+sync.getExtras()+",通道单价="+sync.getChannelPrice()+"通道类="+sync.getVariant()+",网关模块="+sync.getModel()+",逻辑模块="+sync.getLogicModel()+",匹配方式="+sync.getMoMatch()+",通道月限="+sync.getIsMonthLimit()
				+",月限条数="+sync.getMonthLimitCount()+",平台标识="+sync.getPlatformFlag()+",是否启用双平台="+sync.getAllPlatformUsed());
			}else if("logic:presegment".equals(key)){
				Sync.PreSegment preSegment=null;
				Sync.PreSegment.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					preSegment = Sync.PreSegment.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.PreSegment.newBuilder(preSegment);
				sync.build();
				map.put("result", "号段="+sync.getSegment()+",省市="+new String(sync.getProvince().getBytes())+",地市="+sync.getCity());
			}else if("logic:numsegment".equals(key)){
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(msg));
			}else if("logic:blackkeyword".equals(key)){
				Sync.PreBlackKeyword blackKeyword=null;
				Sync.PreBlackKeyword.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					blackKeyword = Sync.PreBlackKeyword.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.PreBlackKeyword.newBuilder(blackKeyword);
				sync.build();
				map.put("result", "敏感词="+new String(sync.getKeyword().getBytes())+",等级="+sync.getLevel());
			}else if("logic:chanforward".equals(key)){
				
			}else if("logic:glsetting".equals(key)){
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				map.put("result", new String(msg));
			}else if("logic:report:config".equals(key)) {
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				Sync.ReportConfig reportConfig=null;
				Sync.ReportConfig.Builder sync=null;
				try {
					reportConfig = Sync.ReportConfig.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.ReportConfig.newBuilder(reportConfig);
				sync.build();
				map.put("result", "状态报告URI="+new String(sync.getUri().getBytes())+",通道ID="+new String(sync.getChannelId().getBytes())+",类型="+new String(sync.getType().getBytes()));
			}else if("logic:timer:task".equals(key)){
				Sync.TimerTask timerTask=null;
				Sync.TimerTask.Builder sync=null;
				byte[] msg = RedisUtil.hget(key, value,Constants.Common.Redis.Naming.LOAD);
				if(StringUtil.isEmpty(msg)){
					map.put("result", "");
					return map;
				}
				try {
					timerTask = Sync.TimerTask.parseFrom(msg);
				} catch (InvalidProtocolBufferException e) {
					logger.error("[ERROR]序列化异常", e);
				}
				sync=Sync.TimerTask.newBuilder(timerTask);
				sync.build();
				map.put("result", "任务="+sync.getTask()+"首次时间="+sync.getFirstTime()+",单位="+sync.getUnit()
				+"周期="+sync.getCycle());
			}
		}else if("black".equals(name)){
			if("sync:black2".equals(key)){
				Long llen = RedisUtil.hlen(key,Constants.Common.Redis.Naming.BLACK);
				map.put("result", llen);
			}
		}else if("white".equals(name)){
			
		}else if("shuntphone".equals(name)){
			if("sync:shuntphone".equals(key)){
				Long llen = RedisUtil.lLen(key,Constants.Common.Redis.Naming.SHUNT_PHONE);
				map.put("result", llen);
			}
		}else if("longMsg".equals(name)){
			if("msgkey:msgid".equals(key)){
				Long llen = RedisUtil.hlen(key,Constants.Common.Redis.Naming.LONGMSG);
				map.put("result", llen);
			}
		}else if("match".equals(name)){
			if("logic:sign:ext:max".equals(key)) {
				String result = "";
				List<String> redisNameList = redisService.getRedisNameList("match");
				for(String redisName:redisNameList) {
					String string = RedisUtil.get(key, redisName);
					if(!StringUtil.isEmpty(string)) {
						result = string;
						break;
					}
				}
				map.put("result", result);
			}else if("logic:sign:ext".equals(key)) {
				String result = "";
				List<String> redisNameList = redisService.getRedisNameList("match");
				for(String redisName:redisNameList) {
					byte[] hget = RedisUtil.hget(key, value, redisName);
					if(!StringUtil.isEmpty(hget)) {
						result = new String(hget);
						break;
					}
				}
				map.put("result", result);
			}	
		}else if("gw".equals(name)){
			
		}else if("audit".equals(name)){
			if("audit:appId:channelId:近似度Hash".equals(key)){
				Set<String> keys = RedisUtil.keys("audit:*:*:*", Constants.Common.Redis.Naming.AUDIT);
				if(StringUtil.isEmpty(keys)){
					map.put("result", "0");
					return map;
				}
				map.put("result",keys.size());
			}else if("logic:audit:cont".equals(key)){
				Long llen = RedisUtil.hlen("logic:audit:cont", Constants.Common.Redis.Naming.AUDIT);
				map.put("result", llen);
			}else if("auditModel:(appId)".equals(key)){
				Set<String> keys = RedisUtil.keys("auditModel:*", Constants.Common.Redis.Naming.AUDIT);
				int sum=0;
				for(String str:keys){
					sum+=RedisUtil.hlen(str, Constants.Common.Redis.Naming.AUDIT);
				}
				map.put("result",sum);
				return map;
			}else if("msgtemplate:(appId)".equals(key)) {
				Set<String> keys = RedisUtil.keys("msgtemplate:*", Constants.Common.Redis.Naming.AUDIT);
				int sum=0;
				for(String str:keys){
					sum+=RedisUtil.hlen(str, Constants.Common.Redis.Naming.AUDIT);
				}
				map.put("result",sum);
			}
		}else if("monthlimit".equals(name)) {
			if("logic:monthlimit:(appId)".equals(key)) {
				Set<String> keys = RedisUtil.keys("logic:monthlimit:*", Constants.Common.Redis.Naming.MONTHLIMIT);
				int sum=0;
				for(String str:keys){
					sum+=RedisUtil.hlen(str, Constants.Common.Redis.Naming.MONTHLIMIT);
				}
				map.put("result",sum);
			}else if("logic:channel:monthlimit:(channelId)".equals(key)) {
				Set<String> keys = RedisUtil.keys("logic:channel:monthlimit:*", Constants.Common.Redis.Naming.MONTHLIMIT);
				int sum=0;
				for(String str:keys){
					sum+=RedisUtil.hlen(str, Constants.Common.Redis.Naming.MONTHLIMIT);
				}
				map.put("result",sum);
			}
		}
		return map;
	}
	
	
	@RequestMapping("/goStatisticsList.action")
	public String goStatisticsList(HttpServletRequest request) {
		return PATH+"statisticMonit";
	}

	
	@ResponseBody
	@RequestMapping("/findCountStatistic.action")
	public Map<String, String> findCountStatistic(){
		Set<Entry<byte[], byte[]>> setByHscan = RedisUtil.getSetByHscan("count",Constants.Common.Redis.Naming.REDIS60);
		Map<String, String> countMap=new HashMap<>();
		for(Entry<byte[], byte[]> ent:setByHscan) {
			countMap.put(new String(ent.getKey()), new String(ent.getValue()));
		}
		logger.debug("[Statistics] size="+countMap.size());
		return countMap;
	}

	
	@ResponseBody
	@RequestMapping("/findAccStatisticView.action")
	public Map<String, Object> findAccStatisticView(String accId) {
		if(StringUtil.isEmpty(accId)) {
			return null;
		}
		//账号统计
		List<String> category = new ArrayList<String>();
        List<Long> serisData=new ArrayList<Long>();
        Map<String,String> map = dataMonitoringService.findAccStatistic(accId);
        Set<String> set=new HashSet<>();
        for (Entry<String, String> totalNum : map.entrySet()) {
            category.add(totalNum.getKey());
            set.add(totalNum.getKey());
            serisData.add(Long.valueOf(totalNum.getValue()));
        }
        List<String> legend = new ArrayList<String>(set);// 数据分组
        List<Series> series = new ArrayList<Series>();// 纵坐标
        series.add(new Series("总数", "bar", serisData,"50","true"));
        EchartData data = new EchartData(legend, category, series);
        //账号分钟统计
        Map<String, Map<String,List<String>>> minStatistics=dataMonitoringService.findAccMinStatistic(accId);
        List<Series> minSeries = new ArrayList<Series>();
        List<String> minCategory = new ArrayList<String>();
        Set<String> paramSet=new HashSet<>();
        for(Entry<String, List<String>> minStatistic :minStatistics.get("result").entrySet()) {
        	minCategory.add(minStatistic.getKey());
        }
        Collections.sort(minCategory,new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int i=Integer.parseInt(o1)-Integer.parseInt(o2);
				return i;
			}
		});
        for(Entry<String, List<String>> minStatistic :minStatistics.get("param").entrySet()) {
        	List<String> minSerisData=new ArrayList<String>();
        	minSerisData.addAll(minStatistic.getValue());
        	paramSet.add(minStatistic.getKey());
        	minSeries.add(new Series(minStatistic.getKey(), "line", minSerisData, "10","true"));
        }
        List<String> minlegend = new ArrayList<String>(paramSet);// 数据分组
        EchartData datas = new EchartData(minlegend, minCategory, minSeries);
        //到达率
        String apiReportSucc= map.get("平台状态推送成功");//回执成功
        String apiReportfail= map.get("平台状态推送失败");//回执失败
        String apiSubmit= map.get("账号提交总数");//平台提交
        float succSum=0;
        if(apiReportSucc != null) {
        	succSum=Float.parseFloat(apiReportSucc);
        }
        float failSum=0;
        if(apiReportfail != null) {
        	failSum=Float.parseFloat(apiReportfail);
        }
        float sum=0;
        if(apiSubmit != null) {
        	sum=Float.parseFloat(apiSubmit);
        }
        float arrive=0f;
        float succ=0f;
        if(sum!=0) {
        	arrive=(succSum+failSum)/sum*100;
        	succ=(succSum)/sum*100;
        }
        Map<String,Object> param=new HashMap<>();
        param.put("data", data);
        param.put("datas", datas);
        param.put("succ", succ);
        param.put("arrive", arrive);
	    return param;
	}

	
	@ResponseBody
	@RequestMapping("/findChannelStatisticView.action")
	public Map<String,Object> findChannelStatisticView(String channelId) {
		if(StringUtil.isEmpty(channelId)) {
			return null;
		}
		List<String> category = new ArrayList<String>();
		List<Long> serisData=new ArrayList<Long>();
		Map<String,String> map = dataMonitoringService.findChannelStatistic(channelId);
		Set<String> set=new HashSet<>();
		for (Entry<String, String> totalNum : map.entrySet()) {
			set.add(totalNum.getKey());
			category.add(totalNum.getKey());
			serisData.add(Long.valueOf(totalNum.getValue()));
		}
		List<String> legend = new ArrayList<String>(set);// 数据分组
		List<Series> series = new ArrayList<Series>();// 纵坐标
		series.add(new Series("总数", "bar", serisData,"50","true"));
		EchartData data = new EchartData(legend, category, series);
		//通道分钟统计
        Map<String, Map<String,List<String>>> minStatistics=dataMonitoringService.findChanMinStatistic(channelId);
        List<Series> minSeries = new ArrayList<Series>();
        List<String> minCategory = new ArrayList<String>();
        Set<String> paramSet=new HashSet<>();
        for(Entry<String, List<String>> minStatistic :minStatistics.get("result").entrySet()) {
        	minCategory.add(minStatistic.getKey());
        }
        Collections.sort(minCategory,new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int i=Integer.parseInt(o1)-Integer.parseInt(o2);
				return i;
			}
		});
        for(Entry<String, List<String>> minStatistic :minStatistics.get("param").entrySet()) {
        	List<String> minSerisData=new ArrayList<String>();
        	minSerisData.addAll(minStatistic.getValue());
        	paramSet.add(minStatistic.getKey());
        	minSeries.add(new Series(minStatistic.getKey(), "line", minSerisData, "10","true"));
        }
        List<String> minlegend = new ArrayList<String>(paramSet);// 数据分组
        EchartData datas = new EchartData(minlegend, minCategory, minSeries);
        //到达率
        String gwReportSucc= map.get("平台状态回执成功");//回执成功
        String gwReportfail= map.get("平台状态回执失败");//回执失败
        String gwSubmit= map.get("平台提交总数");//平台提交
        float succSum=0;
        if(gwReportSucc != null) {
        	succSum=Float.parseFloat(gwReportSucc);
        }
        float failSum=0;
        if(gwReportfail != null) {
        	failSum=Float.parseFloat(gwReportfail);
        }
        float sum=0;
        if(gwSubmit != null) {
        	sum=Float.parseFloat(gwSubmit);
        }
        float arrive=0f;
        float succ=0f;
        if(sum!=0) {
        	arrive=((succSum+failSum)/sum)*100;
        	succ=(succSum/sum)*100;
        }
        
        Map<String,Object> param=new HashMap<>();
        param.put("data", data);
        param.put("datas", datas);
        param.put("arrive", arrive);
        param.put("succ", succ);
		return param;
	}
	
	@RequestMapping("/goStatisticChart.action")
	public String goStatisticChart() {
		return PATH+"statisticChart";
	}

	
	@ResponseBody
	@RequestMapping("/findLoadData.action")
	public List<Map<String,Object>> findLoadData(String cla){
		logger.debug("[BINS][MONITOR] cla="+cla);
		List<Map<String, Object>> datas=new ArrayList<>();
		if(StringUtil.isEmpty(cla)) {
			logger.error("[ERROR][MONITOR] cla="+cla);
			return null;
		}else {
			if("acc".equals(cla)) {
				try {
					datas=applicationService.findAccount();
				} catch (Exception e) {
					logger.error("[ERROR][APP] find acc ex.",e);
				}
			}else if("chan".equals(cla)) {
				try {
					datas=channelService.findChannel();
				} catch (Exception e) {
					logger.error("[ERROR][CHAN] find chan ex.",e);
				}
			}
		}
		logger.debug("[BINS] data size ="+datas.size());
		return datas;
	}

	
	@ResponseBody
	@RequestMapping("/findDataChart.action")
	public Map<String, Object> findDataChart(String cla,String type,String[] data,String[] category,String selectTime){
		logger.debug("[BINS][MONITOR] cla="+cla+",type="+type+",data="+Arrays.toString(data)+"category="+Arrays.toString(category)+",selectTime="+selectTime);
		//折线图数据
		long startTime=System.currentTimeMillis();
		Map<String, Object> map = dataMonitoringService.findDataChart(cla,type,data,category,selectTime);
		Map<String, List<String>> object = (Map<String, List<String>>) map.get("result");
		Map<String, List<String>> result=new HashMap<>();
		if("group".equals(type)) {
			for(Entry<String, List<String>> ent:object.entrySet()) {
				String key = ent.getKey();
				List<String> value = ent.getValue();
				if("api_submit".equals(key)) {
					result.put("账号提交", value);
				}else if("gw_submit".equals(key)) {
					result.put("平台提交", value);
				}else if("api_report_succ".equals(key)) {
					result.put("账号状态成功", value);
				}else if("api_report_fail".equals(key)) {
					result.put("账号状态失败", value);
				}else if("gw_report_succ".equals(key)) {
					result.put("平台状态成功", value);
				}else if("gw_report_fail".equals(key)) {
					result.put("平台状态失败", value);
				}else if("api_mo".equals(key)) {
					result.put("账号上行", value);
				}else if("gw_mo".equals(key)) {
					result.put("平台上行", value);
				}else if("gw_submit_speed".equals(key)) {
					result.put("平台提交速度(/s)", value);
				}
			}
		}else if("contrast".equals(type)) {
			if("acc".equals(cla)) {
				Set<String> keySet = object.keySet();
				List<Application> accs=new ArrayList<>();
				try {
					accs = applicationService.findAccountById(keySet);
				} catch (Exception e) {
					logger.error("[ERROR][APP] find app by set ex.",e);
				}
				for(Entry<String, List<String>> ent:object.entrySet()) {
					String key = ent.getKey();
					List<String> value = ent.getValue();
					for(Application acc:accs) {
						if(key.equals(String.valueOf(acc.getId()))) {
							result.put(acc.getId()+":"+acc.getAppName(), value);
						}
					}
				}
			}else if("chan".equals(cla)) {
				Set<String> keySet = object.keySet();
				List<Channel> chans=new ArrayList<>();
				try {
					chans=channelService.findChannelById(keySet);
				} catch (Exception e) {
					logger.error("[ERROR][CHAN] find chan by set ex.",e);
				}
				for(Entry<String, List<String>> ent:object.entrySet()) {
					String key = ent.getKey();
					List<String> value = ent.getValue();
					for(Channel chan:chans) {
						if(key.equals(String.valueOf(chan.getChannelId()))) {
							result.put(chan.getChannelId()+":"+chan.getName(), value);
						}
					}
				}
			}
			
		}
		
		Set<String> minCategory = (Set<String>) map.get("minCategory");
		List<String> cate=new ArrayList<>(minCategory);
		Collections.sort(cate, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				int i=Integer.parseInt(o1)-Integer.parseInt(o2);
				return i;
			}
		});
		List<Series> minSeries = new ArrayList<Series>();
        Set<String> paramSet=new HashSet<>();
		for(Entry<String, List<String>> minStatistic :result.entrySet()) {
        	paramSet.add(minStatistic.getKey());
        	minSeries.add(new Series(minStatistic.getKey(), "line", minStatistic.getValue(), "10","true","true"));
        }
		List<String> minlegend = new ArrayList<String>(paramSet);// 数据分组
        EchartData datas = new EchartData(minlegend, cate, minSeries);
		//统计汇总
        Map<String, Long> sumMap=dataMonitoringService.findStatisticSum(cla,data);
        Map<String, Object> objMap=new HashMap<>();
        objMap.put("sumMap", sumMap);
        objMap.put("datas", datas);
        logger.debug("[BINS][MONITOR] time="+(System.currentTimeMillis()-startTime));
		return objMap;
	}
	
	@RequestMapping("/goFailLeaderboard.action")
	public String goFailLeaderboard(HttpServletRequest request) {
		List<Application> apps=new ArrayList<>();
		try {
			apps=applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERR][APP] EX="+e);
		}
		Map<String, Object> map=new HashMap<>();
		List<Channel> chans=new ArrayList<>();
		try {
			chans=channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERR][CHAN] EX="+e);
		}
		request.setAttribute("apps", apps);
		request.setAttribute("chans", chans);
		return PATH+"failLeaderboard";
	}

	
	@RequestMapping("/findAppFailLeaderboard.action")
	@ResponseBody
	public List<Map<String, Object>> findAppFailLeaderboard(String appId){
		logger.debug("[BINS][FAIL_MONITOR] appId="+appId);
		String appKey=platformFlag+"_api:"+appId+":report:fail:code";
		logger.debug("[BINS][FAIL_MONITOR] KEY="+appKey);
		List<Map<String, Object>> list=new ArrayList<>();
		Set<String> zRevrange = RedisUtil.zRevrange(appKey, 0, 20, Constants.Common.Redis.Naming.REDIS60);
		for(String s:zRevrange) {
			Map<String, Object> map=new HashMap<>();
			map.put("key", s);
			Double zScore = RedisUtil.zScore(appKey, s, Constants.Common.Redis.Naming.REDIS60);
			map.put("score", zScore);
			list.add(map);
		}
		return list;
	}

	
	@RequestMapping("/findChannelFailLeaderboard.action")
	@ResponseBody
	public List<Map<String, Object>> findChannelFailLeaderboard(String channelId){
		logger.debug("[BINS][FAIL_MONITOR] channelId="+channelId);
		String channelKey=platformFlag+"_mt:"+channelId+":report:fail:code";
		logger.debug("[BINS][FAIL_MONITOR] KEY="+channelKey);
		List<Map<String, Object>> list=new ArrayList<>();
		Set<String> zRevrange = RedisUtil.zRevrange(channelKey, 0, 20, Constants.Common.Redis.Naming.REDIS60);
		for(String s:zRevrange) {
			Map<String, Object> map=new HashMap<>();
			map.put("key", s);
			Double zScore = RedisUtil.zScore(channelKey, s, Constants.Common.Redis.Naming.REDIS60);
			map.put("score", zScore);
			list.add(map);
		}
		return list;
	}
}