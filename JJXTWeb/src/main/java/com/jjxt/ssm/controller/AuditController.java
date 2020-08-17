package com.jjxt.ssm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.jjxt.ssm.entity.Audit;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.LgModelAudit;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.LgModelAuditService;

import net.sf.json.JSONObject;
import redis.clients.jedis.ScanResult;

@Controller
@RequestMapping("/audit")
public class AuditController {

	private static Logger logger = Logger.getLogger(AuditController.class);

	private static final String PATH = "audit/";
	@Autowired
	private ChannelService channelService;
	@Autowired
	private HttpService httpService;
	@Autowired
	private LgModelAuditService lgModelAuditService;
	@Autowired
	private ApplicationService applicationService;
	@Value("${auditService1}")
	private String auditService1;
	@Value("${auditService2}")
	private String auditService2;
	@Value("${logic_audit_cont}")
	private String logic_audit_cont;
	@Value("${logic_check_must}")
	private String logic_check_must;
	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	@RequestMapping("/goAuditPageList.action")
	public String goAuditPageList(HttpServletRequest request) {
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
		return PATH + "auditCheckPageList";
	}

	
	@RequestMapping("/findAuditPageList.action")
	@ResponseBody
	public PageResult<Audit> findAuditPageList(String appId,String channelId,Integer pageSize, Integer pageIndex) {
		logger.debug("[BINS][AUDIT] appId="+appId+",channelId="+channelId+",pageSize=" + pageSize + ",pageIndex=" + pageIndex);
		long total = RedisUtil.hlen(logic_audit_cont, Constants.Common.Redis.Naming.AUDIT);
		if(total==0){
			return new PageResult<>(0, new ArrayList<Audit>());
		}
		List<Audit> audits = setChangePageList(appId,channelId,pageSize);
		Collections.sort(audits, new Comparator<Audit>() {
			@Override
			public int compare(Audit o1, Audit o2) {
				int i = (int) (o2.getAmount() - o1.getAmount());
				return i;
			}
		});
		return new PageResult<>((int)total, audits);
	}

	
	@RequestMapping("/findAuditDetail.action")
	@ResponseBody
	public Audit findAuditDetail(String key) {
		logger.debug("[BINS][AUDIT] key=" + key);
		Audit audit = new Audit();
		String[] str = key.split(":");
		audit.setFlag(key);
		audit.setAppId(Integer.parseInt(str[1]));
		audit.setChannelId(Integer.parseInt(str[2]));
		audit.setAmount(RedisUtil.lLen(key,Constants.Common.Redis.Naming.AUDIT));
		Channel channel = null;
		Application app=null;
		String content=null;
		try {
			channel = channelService.findChannelByChannelId(Integer.parseInt(str[2]));
		} catch (Exception e1) {
			logger.error("[ERROR][CHANNEL] ", e1);
		}
		try {
			app = applicationService.findApplicationById(Integer.parseInt(str[1]));
		} catch (Exception e) {
			logger.error("[ERROR][ACC] ", e);
		}
		audit.setAppName(app == null ? null : app.getAppName());
		audit.setChannelName(channel == null ? null : channel.getName());
		byte[] hget = RedisUtil.hget(logic_audit_cont, key, Constants.Common.Redis.Naming.AUDIT);
		if(!StringUtil.isEmpty(hget)){
			content=new String(hget);
		}else{
			content="";
		}
		audit.setContent(content);
		audit.setAuditResult(auditResult(content));
		String[] spli=content.split(Constants.HttpApi.Config.RequestParse.PERSONALIZED_CONTENT_DELIMITER_REG);
		if(spli.length==2) {
			audit.setContent(spli[0]);
			if(spli[1].equals(Constants.Gateway.SendFlag.CHECK_MUST_AUDIT)) {//审核词
				audit.setAuditResult("审核词："+auditResult(spli[0]));
			}else if(spli[1].equals(Constants.Gateway.SendFlag.COUNTENT_AUDIT)) {//相同内容
				audit.setAuditResult("相同内容审核");
			}else if(spli[1].indexOf(Constants.Gateway.SendFlag.RULE_AUDIT)!=-1) {//策略模板
				String[] split = spli[1].split(":");
				if(split.length==2) {
					audit.setAuditResult("策略模板:"+split[1]);
				}else {
					audit.setAuditResult("策略模板");
				}
			}else if(spli[1].equals(Constants.Gateway.SendFlag.DAY_LIMIT_AUDIT)) {//日限
				audit.setAuditResult("日限审核");
			}
		}else {
			audit.setContent(spli[0]);
			audit.setAuditResult(auditResult(spli[0]));
		}
		return audit;
	}
	
	//通过或者驳回
	
	@DataOperate(bussiness = Bussiness.audit, operation = Operate.INSERT)
	@RequestMapping("/auditOperation.action")
	@ResponseBody
	public ResponseResult auditOperation(String key,String flag,String template) {
		logger.debug("[BINS][AUDIT] key="+key+",flag="+flag+",template="+template);
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtil.isEmpty(key)){
			map.put("error", "key 为空");
			return new ResponseResult(-1, map, null);
		}
		if(StringUtil.isEmpty(flag)){
			map.put("error", "flag 为空");
			return new ResponseResult(-1, map, null);
		}
		JSONObject jasonObject = JSONObject.fromObject(key);
		@SuppressWarnings("unchecked")
		Map<String, String> param = (Map<String, String>) jasonObject;
		if(param.size()==0){
			map.put("error", "param 为空");
			return new ResponseResult(-1, map, null);
		}
		if(!StringUtil.isEmpty(template)){
			LgModelAudit audit=new LgModelAudit();
			LgModelAudit lg=null;
			if("black".equals(template)){
				audit.setAuditFlag("black");
			}else if("white".equals(template)){
				audit.setAuditFlag("white");
			}
			Set<String> keySet = param.keySet();
			byte[] hget=null;
			String content = null;
			for(String auditKey:keySet){
				String[] split = auditKey.split(":");
				if(split.length!=4){
					continue;
				}
				hget = RedisUtil.hget("logic:auditcontent", split[3],Constants.Common.Redis.Naming.AUDIT);
				if(!StringUtil.isEmpty(hget)){
					try {
						content = new String(hget, "UTF-8");
					} catch (UnsupportedEncodingException e3) {
						logger.error("[error][AUDITMODEL]字符集格式转换异常", e3);
					}
				}
				if(StringUtil.isEmpty(content)){
					continue;
				}
				audit.setContent(content);
				audit.setAppId(split[1]);
				try {
					lg = lgModelAuditService.findByContent(content,split[1]);
				} catch (Exception e2) {
					logger.error("[error][AUDITMODEL]", e2);
				}
				try {
					audit.setCreateTime(sdf.parse(sdf.format(new Date())));
				} catch (java.text.ParseException e) {
					logger.error("[ERROR][AUDIT][ADD_MODEL]", e);
				}
				if(StringUtil.isEmpty(lg)){
					try {
						int i = lgModelAuditService.addLgModelAudit(audit);
					} catch (Exception e) {
						logger.error("[ERROR][AUDIT][AUDITMODEL]", e);
					}
				}
			}
		}
		map.put("param", param);
		map.put("flag", flag);
		if(StringUtil.isEmpty(template)) {
			template="";
		}
		map.put("template", template);
		String result = null;
		try {
			result = httpService.doPost(auditService1, map);
			logger.debug("[BINS][SERVICE]访问service1：" + result);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][AUDIT] service1 连接失败", e);
		}
		if (result == null) {
			try {
				result = httpService.doPost(auditService2, map);
				logger.debug("[BINS][SERVICE]访问service2：" + result);
			} catch (URISyntaxException | IOException e) {
				logger.error("[ERROR][AUDIT] service2 连接失败", e);
			}
		}
		if (result == null) {
			map.put("error","service 连接异常");
			return new ResponseResult(-1, map, null);
		}
		logger.debug("[BINS][AUDIT][SERVICE] result="+result);
		if(result.split(":").length!=2){
			map.put("error","回执结果异常");
			return new ResponseResult(-1, map, null);
		}
		result = result.split(":")[1];
		if ("0".equals(result)) {
			map.put("success","success");
			logger.debug("[BINS][AUDIT][SERVICE] 0 success");
		} else if ("-1001".equals(result)) {
			map.put("error","flag为空");
			logger.debug("[BINS][AUDIT][SERVICE] -1001 flag为空");
		} else if ("-1002".equals(result)) {
			map.put("error","amount为空");
			logger.debug("[BINS][AUDIT][SERVICE] -1002 amount为空");
		} else if ("-1003".equals(result)) {
			map.put("error","param为空");
			logger.debug("[BINS][AUDIT][SERVICE] -1003 param为空");
		} else if ("-1004".equals(result)) {
			map.put("error","ip鉴权失败");
			logger.debug("[BINS][AUDIT][SERVICE] -1004 ip鉴权失败");
		} else if ("-1005".equals(result)) {
			map.put("error","amount长度小于等于0");
			logger.debug("[BINS][AUDIT][SERVICE] -1005 amount长度小于等于0");
		} else if ("-1006".equals(result)) {
			map.put("error","param长度错误");
			logger.debug("[BINS][AUDIT][SERVICE] -1006 param长度错误");
		} else if ("-1007".equals(result)) {
			map.put("error","flag错误 T,F大小写都可以");
			logger.debug("[BINS][AUDIT][SERVICE] -1007 flag错误 T,F大小写都可以");
		} else if ("-1008".equals(result)) {
			map.put("error"," 对某个key执行太过频繁");
			logger.debug("[BINS][AUDIT][SERVICE] -1008  对某个key执行太过频繁");
		} else if ("-1009".equals(result)) {
			map.put("error","线程数超过5个");
			logger.debug("[BINS][AUDIT][SERVICE] -1009  线程数超过5个");
		} else if ("-1010".equals(result)) {
			map.put("error","线程数超过5个");
			logger.debug("[BINS][AUDIT][SERVICE] -1010  当前审核队列处理正在排队，请重试");
		} else if ("-1111".equals(result)) {
			map.put("error","未知");
			logger.debug("[BINS][AUDIT][SERVICE] -1111 未知");
		} else if ("-9999".equals(result)) {
			map.put("error","-9999");
			logger.debug("[BINS][AUDIT][SERVICE] -9999");
		} else if ("-9998".equals(result)) {
			map.put("error","pross方法未处理的异常");
			logger.debug("[BINS][AUDIT][SERVICE] -9998 pross方法未处理的异常");
		} else if ("-9997".equals(result)) {
			map.put("error","写入流异常，网络异常");
			logger.debug("[BINS][AUDIT][SERVICE] -9997   写入流异常，网络异常");
		}
		return new ResponseResult(Integer.parseInt(result), map, null);
	}

	private List<Audit> setChangePageList(String appId, String channelId, Integer pageSize) {
		Set<Entry<byte[], byte[]>> entrySet = new HashSet<Entry<byte[], byte[]>>();
		List<Audit> audits=new ArrayList<Audit>();
		String cursor="0";
		while(entrySet.size()<pageSize){
			ScanResult<Map.Entry<byte[], byte[]>> hscan = RedisUtil.hscan(logic_audit_cont,cursor, Constants.Common.Redis.Naming.AUDIT);
			List<Entry<byte[], byte[]>> result = hscan.getResult();
			for(Entry<byte[], byte[]> entry:result) {
				byte[] key = entry.getKey();
				String auditKey=new String(key);
				if( !StringUtil.isEmpty(channelId) && !StringUtil.isEmpty(appId)) {
					String[] split = auditKey.split(":");
					if(split.length==4 && appId.equals(split[1]) && channelId.equals(split[2])) {
						entrySet.add(entry);
					}
				}else if(!StringUtil.isEmpty(appId)) {
					String[] split = auditKey.split(":");
					if(split.length==4 && appId.equals(split[1])) {
						entrySet.add(entry);
					}
				}else if(!StringUtil.isEmpty(channelId)) {
					String[] split = auditKey.split(":");
					if(split.length==4 && channelId.equals(split[2])) {
						entrySet.add(entry);
					}
				}else {
					entrySet.add(entry);
				}
			}
			if("0".equals(hscan.getCursor())){
				break;
			}
			cursor=hscan.getCursor();
		}
		
		if(entrySet.size()==0){
			return new ArrayList<Audit>();
		}
		List<Entry<byte[], byte[]>> result=new ArrayList<Entry<byte[], byte[]>>(entrySet);
		
		if(result.size()>pageSize){
			result=result.subList(0, pageSize);
		}else{
			result=result.subList(0, result.size());
		}
		byte[] key=null;
		byte[] value=null;
		String[] str = new String[4];
		String auditKey=null;
		Audit audit = null;
		String content=null;
		Set<String> appIdSet=new HashSet<>();
		Set<String> channelIdSet=new HashSet<>();
		for(Entry<byte[], byte[]> ent: result){
			key=ent.getKey();
			audit=new Audit();
			if(key==null){
				break;
			}
			value=ent.getValue();
			if(value==null){
				break;
			}
			auditKey=new String(key);
			str=auditKey.split(":");
			if(str.length!=4){
				logger.error("[ERROR][AUDIT] key="+str);
				continue;
			}
			audit.setFlag(auditKey);
			audit.setAppId(Integer.parseInt(str[1]));
			audit.setChannelId(Integer.parseInt(str[2]));
			long lLen = RedisUtil.lLen(auditKey,Constants.Common.Redis.Naming.AUDIT);
			audit.setAmount(lLen);
			appIdSet.add(str[1]);
			channelIdSet.add(str[2]);
			content=new String(value);
			String[] spli=content.split(Constants.HttpApi.Config.RequestParse.PERSONALIZED_CONTENT_DELIMITER_REG);
			if(spli.length==2) {
				audit.setContent(spli[0]);
				if(spli[1].equals(Constants.Gateway.SendFlag.CHECK_MUST_AUDIT)) {//审核词
					audit.setAuditResult("审核词："+auditResult(spli[0]));
				}else if(spli[1].equals(Constants.Gateway.SendFlag.COUNTENT_AUDIT)) {//相同内容
					audit.setAuditResult("相同内容审核");
				}else if(spli[1].indexOf(Constants.Gateway.SendFlag.RULE_AUDIT)!=-1) {//策略模板
					String[] split = spli[1].split(":");
					if(split.length==2) {
						audit.setAuditResult("策略模板:"+split[1]);
					}else {
						audit.setAuditResult("策略模板");
					}
				}else if(spli[1].equals(Constants.Gateway.SendFlag.DAY_LIMIT_AUDIT)) {//日限
					audit.setAuditResult("日限审核");
				}
			}else {
				audit.setContent(spli[0]);
				audit.setAuditResult(auditResult(spli[0]));
			}
			if(lLen>0){
				audits.add(audit);
			}
		}
		List<Application> apps=new ArrayList<>();
		List<Channel> channels=new ArrayList<>();
		try {
			apps=applicationService.findAccountById(appIdSet);
		} catch (Exception e) {
			logger.error("[ERROR][ACC] ex="+e);
		}
		try {
			channels=channelService.findChannelById(channelIdSet);
		} catch (Exception e) {
			logger.error("[ERROR][CHA] ex="+e);
		}
		Map<String, Application> appMap=new HashMap<>();
		Map<String, Channel> chaMap=new HashMap<>();
		for(Application app:apps){
			appMap.put(String.valueOf(app.getId()), app);
		}
		for(Channel cha:channels){
			chaMap.put(String.valueOf(cha.getChannelId()),cha);
		}
		Application app=null;
		Channel cha=null;
		for(Audit au:audits){
			app=appMap.get(String.valueOf(au.getAppId()));
			cha=chaMap.get(String.valueOf(au.getChannelId()));
			au.setAppName(app==null?"-":app.getAppName());
			au.setChannelName(cha==null?"-":cha.getName());
		}
		return audits;
	}

	private String auditResult(String str) {
		Map<String, String> map = RedisUtil.hgetAll(logic_check_must, Constants.Common.Redis.Naming.AUDIT);
		Collection<String> values = map.values();
		Iterator<String> iterator = values.iterator();
		StringBuffer sb = new StringBuffer();
		String s = null;
		while (iterator.hasNext()) {
			s = iterator.next();
			if (str.contains(s)) {
				sb.append(",").append(s);
			}
		}
		if(sb.length()>0){
			return sb.substring(1);
		}
		return sb.toString();
	}
	
}
