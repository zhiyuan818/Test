package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.entity.RedisInfo;
import com.jjxt.ssm.mapper.DataMonitoringMapper;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.DataMonitoringService;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.StringUtil;
@Service("dataMonitoring")
@Transactional
public class DataMonitoringServiceImpl implements DataMonitoringService{
	@Value("${platformFlag}")
	private String platformFlag;
	@Autowired
	private DataMonitoringMapper dataMonitoringMapper;
	@Override
	public List<RedisInfo> findRedisList() throws Exception {
		return dataMonitoringMapper.findRedisList();
	}
	@Override
	public Map<String,String> findAccStatistic(String accId) {
		String accKey=platformFlag+"_api:"+accId;
		byte[] api_submit = RedisUtil.hget(accKey, "api_submit", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_submit = RedisUtil.hget(accKey, "gw_submit", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_report_succ = RedisUtil.hget(accKey, "api_report_succ", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_report_fail = RedisUtil.hget(accKey, "api_report_fail", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_report_succ = RedisUtil.hget(accKey, "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_report_fail = RedisUtil.hget(accKey, "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_mo = RedisUtil.hget(accKey, "api_mo", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_mo = RedisUtil.hget(accKey, "gw_mo", Constants.Common.Redis.Naming.REDIS60);
		List<String> list=new ArrayList<>();
		String apiSubmit="0";
		Map<String, String> map=new HashMap<>();
		if(api_submit !=null) {
			apiSubmit=new String(api_submit);
		}
		list.add(apiSubmit);
		map.put("账号提交总数", apiSubmit);
		String gwSubmit="0";
		if(gw_submit !=null) {
			gwSubmit=new String(gw_submit);
		}
		list.add(gwSubmit);
		map.put("平台提交总数", gwSubmit);
		String apiReportSucc="0";
		if(api_report_succ !=null) {
			apiReportSucc=new String(api_report_succ);
		}
		list.add(apiReportSucc);
		map.put("平台状态推送成功", apiReportSucc);
		String apiReportFail="0";
		if(api_report_fail !=null) {
			apiReportFail=new String(api_report_fail);
		}
		list.add(apiReportFail);
		map.put("平台状态推送失败", apiReportFail);
		String gwReportSucc="0";
		if(gw_report_succ !=null) {
			gwReportSucc=new String(gw_report_succ);
		}
		list.add(gwReportSucc);
		map.put("平台状态回执成功", gwReportSucc);
		String gwReportFail="0";
		if(gw_report_fail !=null) {
			gwReportFail=new String(gw_report_fail);
		}
		list.add(gwReportFail);
		map.put("平台状态回执失败", gwReportFail);
		String apiMo="0";
		if(api_mo !=null) {
			apiMo=new String(api_mo);
		}
		list.add(apiMo);
		map.put("平台推送上行", apiMo);
		String gwMo="0";
		if(gw_mo !=null) {
			gwMo=new String(gw_mo);
		}
		list.add(gwMo);
		map.put("平台接收上行", gwMo);
		return map;
	}
	
	@Override
	public Map<String,String> findChannelStatistic(String channelId) {
		String accKey=platformFlag+"_mt:"+channelId;
		byte[] api_submit = RedisUtil.hget(accKey, "api_submit", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_submit = RedisUtil.hget(accKey, "gw_submit", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_report_succ = RedisUtil.hget(accKey, "api_report_succ", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_report_fail = RedisUtil.hget(accKey, "api_report_fail", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_report_succ = RedisUtil.hget(accKey, "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_report_fail = RedisUtil.hget(accKey, "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
		byte[] api_mo = RedisUtil.hget(accKey, "api_mo", Constants.Common.Redis.Naming.REDIS60);
		byte[] gw_mo = RedisUtil.hget(accKey, "gw_mo", Constants.Common.Redis.Naming.REDIS60);
		List<String> list=new ArrayList<>();
		String apiSubmit="0";
		Map<String, String> map=new HashMap<>();
		if(api_submit !=null) {
			apiSubmit=new String(api_submit);
		}
		list.add(apiSubmit);
		map.put("账号提交总数", apiSubmit);
		String gwSubmit="0";
		if(gw_submit !=null) {
			gwSubmit=new String(gw_submit);
		}
		list.add(gwSubmit);
		map.put("平台提交总数", gwSubmit);
		String apiReportSucc="0";
		if(api_report_succ !=null) {
			apiReportSucc=new String(api_report_succ);
		}
		list.add(apiReportSucc);
		map.put("平台状态推送成功", apiReportSucc);
		String apiReportFail="0";
		if(api_report_fail !=null) {
			apiReportFail=new String(api_report_fail);
		}
		list.add(apiReportFail);
		map.put("平台状态推送失败", apiReportFail);
		String gwReportSucc="0";
		if(gw_report_succ !=null) {
			gwReportSucc=new String(gw_report_succ);
		}
		list.add(gwReportSucc);
		map.put("平台状态回执成功", gwReportSucc);
		String gwReportFail="0";
		if(gw_report_fail !=null) {
			gwReportFail=new String(gw_report_fail);
		}
		list.add(gwReportFail);
		map.put("平台状态回执失败", gwReportFail);
		String apiMo="0";
		if(api_mo !=null) {
			apiMo=new String(api_mo);
		}
		list.add(apiMo);
		map.put("平台推送上行", apiMo);
		String gwMo="0";
		if(api_mo !=null) {
			gwMo=new String(gw_mo);
		}
		list.add(gwMo);
		map.put("平台接收上行", gwMo);
		return map;
	}
	@Override
	public Map<String, Map<String,List<String>>> findAccMinStatistic(String accId) {
		String apiMinuteApiSubmit=platformFlag+"_api:minute:"+accId+":apiSubmit";
		String apiMinuteGWSubmit=platformFlag+"_api:minute:"+accId+":gwSubmit";
		String apiMinuteReportSucc=platformFlag+"_api:minute:"+accId+":report:succ";
		String apiMinuteReportFail=platformFlag+"_api:minute:"+accId+":report:fail";
		String apiMinuteMo=platformFlag+"_api:minute:"+accId+":mo";
		String redisFileld="";
		Map<String, List<String>> result=new HashMap<String, List<String>>();
		List<String> apiSubmitList=new ArrayList<>();
		List<String> gwSubmitList=new ArrayList<>();
		List<String> reportSuccList=new ArrayList<>();
		List<String> reportFailList=new ArrayList<>();
		List<String> moList=new ArrayList<>();
		List<String> list=null;
		
		for(int i=-30;i<0;i++) {
			list=new ArrayList<String>();
			redisFileld = DateUtil.getTimeByMinute(i);
			byte[] apiSubmit = RedisUtil.hget(apiMinuteApiSubmit, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] gwSubmit = RedisUtil.hget(apiMinuteGWSubmit, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] reportSucc = RedisUtil.hget(apiMinuteReportSucc, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] reportFail = RedisUtil.hget(apiMinuteReportFail, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] mo = RedisUtil.hget(apiMinuteMo, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			if(apiSubmit != null) {
				list.add(new String(apiSubmit));
				apiSubmitList.add(new String(apiSubmit));
			}else {
				list.add("0");
				apiSubmitList.add("0");
			}
			if(gwSubmit != null) {
				list.add(new String(gwSubmit));
				gwSubmitList.add(new String(gwSubmit));
			}else {
				list.add("0");
				gwSubmitList.add("0");
			}
			if(reportSucc != null) {
				list.add(new String(reportSucc));
				reportSuccList.add(new String(reportSucc));
			}else {
				list.add("0");
				reportSuccList.add("0");
			}
			if(reportFail != null) {
				list.add(new String(reportFail));
				reportFailList.add(new String(reportFail));
			}else {
				list.add("0");
				reportFailList.add("0");
			}
			if(mo != null) {
				list.add(new String(mo));
				moList.add(new String(mo));
			}else {
				list.add("0");
				moList.add("0");
			}
			result.put(String.valueOf(redisFileld), list);
		}
		Map<String, List<String>> param=new HashMap<>();
		param.put("账号提交总数", apiSubmitList);
		param.put("平台提交总数", gwSubmitList);
		param.put("平台状态推送成功", reportSuccList);
		param.put("平台状态推送失败", reportFailList);
		param.put("平台推送上行", moList);
		Map<String,Map<String,List<String>>> map=new HashMap<>();
		map.put("result", result);
		map.put("param", param);
		return map;
	}
	@Override
	public Map<String, Map<String, List<String>>> findChanMinStatistic(String channelId) {
		String mtMinuteApiSubmit=platformFlag+"_mt:minute:"+channelId+":apiSubmit";
		String mtMinuteGWSubmit=platformFlag+"_mt:minute:"+channelId+":gwSubmit";
		String mtMinuteReportSucc=platformFlag+"_mt:minute:"+channelId+":report:succ";
		String mtMinuteReportFail=platformFlag+"_mt:minute:"+channelId+":report:fail";
		String mtMinuteMo=platformFlag+"_mt:minute:"+channelId+":mo";
		String redisFileld="";
		Map<String, List<String>> result=new HashMap<String, List<String>>();
		List<String> apiSubmitList=new ArrayList<>();
		List<String> gwSubmitList=new ArrayList<>();
		List<String> reportSuccList=new ArrayList<>();
		List<String> reportFailList=new ArrayList<>();
		List<String> moList=new ArrayList<>();
		List<String> list=null;
		
		for(int i=-30;i<0;i++) {
			list=new ArrayList<String>();
			redisFileld = DateUtil.getTimeByMinute(i);
			byte[] apiSubmit = RedisUtil.hget(mtMinuteApiSubmit, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] gwSubmit = RedisUtil.hget(mtMinuteGWSubmit, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] reportSucc = RedisUtil.hget(mtMinuteReportSucc, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] reportFail = RedisUtil.hget(mtMinuteReportFail,  String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			byte[] mo = RedisUtil.hget(mtMinuteMo,  String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
			if(apiSubmit != null) {
				list.add(new String(apiSubmit));
				apiSubmitList.add(new String(apiSubmit));
			}else {
				list.add("0");
				apiSubmitList.add("0");
			}
			if(gwSubmit != null) {
				list.add(new String(gwSubmit));
				gwSubmitList.add(new String(gwSubmit));
			}else {
				list.add("0");
				gwSubmitList.add("0");
			}
			if(reportSucc != null) {
				list.add(new String(reportSucc));
				reportSuccList.add(new String(reportSucc));
			}else {
				list.add("0");
				reportSuccList.add("0");
			}
			if(reportFail != null) {
				list.add(new String(reportFail));
				reportFailList.add(new String(reportFail));
			}else {
				list.add("0");
				reportFailList.add("0");
			}
			if(mo != null) {
				list.add(new String(mo));
				moList.add(new String(mo));
			}else {
				list.add("0");
				moList.add("0");
			}
			result.put(String.valueOf(redisFileld), list);
		}
		Map<String, List<String>> param=new HashMap<>();
		param.put("账号提交总数", apiSubmitList);
		param.put("平台提交总数", gwSubmitList);
		param.put("平台状态回执成功", reportSuccList);
		param.put("平台回执失败", reportFailList);
		param.put("平台接收上行", moList);
		Map<String,Map<String,List<String>>> map=new HashMap<>();
		map.put("result", result);
		map.put("param", param);
		return map;
	}
	@Override
	public Map<String, Object> findDataChart(String cla, String type, String[] data, String[] category,String selectTime) {
//		//查看下发总数
//		Set<Entry<byte[], byte[]>> setByHscan = RedisUtil.getSetByHscan("count",Constants.Common.Redis.Naming.REDIS60);
//		Map<String, String> countMap=new HashMap<>();
//		for(Entry<byte[], byte[]> ent:setByHscan) {
//			countMap.put(new String(ent.getKey()), new String(ent.getValue()));
//		}
		///获取selectTime分钟数据
		int time=Integer.parseInt(selectTime);
		if(category.length==0) {
			if("acc".equals(cla)) {
				category =new String[] {"api_submit","gw_submit","api_report_succ","api_report_fail","api_mo"};
			}else if("chan".equals(cla)) {
				category =new String[] {"api_submit","gw_submit","gw_report_succ","gw_report_fail","gw_mo"};
			}else if("count".equals(cla)){
				category =new String[] {"api_submit","gw_submit","api_report_succ","api_report_fail","gw_report_succ","gw_report_fail","api_mo","gw_mo"};
			}
		}
		String redisFileld = "";
		Map<String, Object> result=new HashMap<>();
		Map<String, List<String>> map=new HashMap<>();
		Set<String> minCategory=new HashSet<>();
		if("acc".equals(cla)) {
			if("group".equals(type)) {
				Map<String, List<String>> keysMap=new HashMap<>();
				for(String s:category) {
					List<String> list=new ArrayList<>();
					for(String accId:data) {
						if("api_submit".equals(s)) {
							list.add(platformFlag+"_api:minute:"+accId+":apiSubmit");
						}else if("gw_submit".equals(s)) {
							list.add(platformFlag+"_api:minute:"+accId+":gwSubmit");
						}else if("api_report_succ".equals(s)) {
							list.add(platformFlag+"_api:minute:"+accId+":report:succ");
						}else if("api_report_fail".equals(s)) {
							list.add(platformFlag+"_api:minute:"+accId+":report:fail");
						}else if("api_mo".equals(s)) {
							list.add(platformFlag+"_api:minute:"+accId+":mo");
						}
					}
					keysMap.put(s, list);
				}
				for(Entry<String, List<String>> ent:keysMap.entrySet()) {
					map.put(ent.getKey(), new ArrayList<>());
				}
				for(int i=-time;i<0;i++) {
					redisFileld = DateUtil.getTimeByMinute(i);
					for(Entry<String, List<String>> ent:keysMap.entrySet()) {
						String key = ent.getKey();
						List<String> list=ent.getValue();
						int sum=0;
						for(String str:list) {
							byte[] hget = RedisUtil.hget(str, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
							if(!StringUtil.isEmpty(hget)) {
								sum+=Integer.parseInt(new String(hget));
							}
						}
						List<String> list2 = map.get(key);
						list2.add(String.valueOf(sum));
					}
					minCategory.add(redisFileld);
				}
				
			}else if("contrast".equals(type)) {
				Map<String, String> Keysmap=new HashMap<String,String>();
				for(String accId:data) {
					if("api_submit".equals(category[0])) {
						Keysmap.put(accId, platformFlag+"_api:minute:"+accId+":apiSubmit");
					}else if("gw_submit".equals(category[0])) {
						Keysmap.put(accId, platformFlag+"_api:minute:"+accId+":gwSubmit");
					}else if("api_report_succ".equals(category[0])) {
						Keysmap.put(accId, platformFlag+"_api:minute:"+accId+":report:succ");
					}else if("api_report_fail".equals(category[0])) {
						Keysmap.put(accId, platformFlag+"_api:minute:"+accId+":report:fail");
					}else if("api_mo".equals(category[0])) {
						Keysmap.put(accId, platformFlag+"_api:minute:"+accId+":mo");
					}
				}
				for(Entry<String, String> ent:Keysmap.entrySet()) {
					String key = ent.getKey();
					String value = ent.getValue();
					List<String> list=new ArrayList<>();
					for(int i=-time;i<0;i++) {
						redisFileld = DateUtil.getTimeByMinute(i);
						byte[] hget = RedisUtil.hget(value, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
						if(!StringUtil.isEmpty(hget)) {
							list.add(new String(hget));
						}else {
							list.add("0");
						}
						minCategory.add(redisFileld);
					}
					map.put(key, list);
				}
			}
		}else if("chan".equals(cla)) {
			if("group".equals(type)) {
				Map<String, List<String>> keysMap=new HashMap<>();
				for(String s:category) {
					List<String> list=new ArrayList<>();
					for(String chanId:data) {
						if("api_submit".equals(s)) {
							list.add(platformFlag+"_mt:minute:"+chanId+":apiSubmit");
						}else if("gw_submit".equals(s)) {
							list.add(platformFlag+"_mt:minute:"+chanId+":gwSubmit");
						}else if("gw_report_succ".equals(s)) {
							list.add(platformFlag+"_mt:minute:"+chanId+":report:succ");
						}else if("gw_report_fail".equals(s)) {
							list.add(platformFlag+"_mt:minute:"+chanId+":report:fail");
						}else if("gw_mo".equals(s)) {
							list.add(platformFlag+"_mt:minute:"+chanId+":mo");
						}
					}
					keysMap.put(s, list);
				}
				for(Entry<String, List<String>> ent:keysMap.entrySet()) {
					map.put(ent.getKey(), new ArrayList<>());
				}
				map.put("gw_submit_speed", new ArrayList<>());
				for(int i=-time;i<0;i++) {
					redisFileld = DateUtil.getTimeByMinute(i);
					for(Entry<String, List<String>> ent:keysMap.entrySet()) {
						String key = ent.getKey();
						List<String> list=ent.getValue();
						int sum=0;
						for(String str:list) {
							byte[] hget = RedisUtil.hget(str, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
							if(!StringUtil.isEmpty(hget)) {
								sum+=Integer.parseInt(new String(hget));
							}
						}
						List<String> list2 = map.get(key);
						list2.add(String.valueOf(sum));
						if(key != null && "gw_submit".equals(key)) {
							map.get("gw_submit_speed").add(String.valueOf(Math.round(sum/6.0*10.0)/100.0));
						}
					}
					minCategory.add(redisFileld);
				}
				
			}else if("contrast".equals(type)) {
				Map<String, String> Keysmap=new HashMap<String,String>();
				for(String channelId:data) {
					if("api_submit".equals(category[0])) {
						Keysmap.put(channelId, platformFlag+"_mt:minute:"+channelId+":apiSubmit");
					}else if("gw_submit".equals(category[0])) {
						Keysmap.put(channelId, platformFlag+"_mt:minute:"+channelId+":gwSubmit");
					}else if("gw_report_succ".equals(category[0])) {
						Keysmap.put(channelId, platformFlag+"_mt:minute:"+channelId+":report:succ");
					}else if("gw_report_fail".equals(category[0])) {
						Keysmap.put(channelId, platformFlag+"_mt:minute:"+channelId+":report:fail");
					}else if("gw_mo".equals(category[0])) {
						Keysmap.put(channelId, platformFlag+"_mt:minute:"+channelId+":mo");
					}
				}
				for(Entry<String, String> ent:Keysmap.entrySet()) {
					String key = ent.getKey();
					String value = ent.getValue();
					List<String> list=new ArrayList<>();
					for(int i=-time;i<0;i++) {
						redisFileld = DateUtil.getTimeByMinute(i);
						byte[] hget = RedisUtil.hget(value, String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
						if(!StringUtil.isEmpty(hget)) {
							list.add(new String(hget));
						}else {
							list.add("0");
						}
						minCategory.add(redisFileld);
					}
					map.put(key, list);
				}
			}
			
		}else if("count".equals(cla)){
			Map<String,String> mapkey=new HashMap<String,String>();
			for(String s:category) {
					if("api_submit".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:api:submit");
					}else if("gw_submit".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:gw:submit");
					}else if("api_report_succ".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:api:report:succ");
					}else if("api_report_fail".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:api:report:fail");
					}else if("gw_report_succ".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:gw:report:succ");
					}else if("gw_report_fail".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:gw:report:fail");
					}else if("api_mo".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:api:mo");
					}else if("gw_mo".equals(s)) {
						mapkey.put(s,platformFlag+"_count:minute:gw:mo");
					}
			}
			for(Entry<String, String> ent:mapkey.entrySet()) {
				List<String> list=new ArrayList<>();
				for(int i=-time;i<0;i++) {
					redisFileld = DateUtil.getTimeByMinute(i);
					byte[] hget = RedisUtil.hget(ent.getValue(), String.valueOf(redisFileld), Constants.Common.Redis.Naming.REDIS60);
					list.add(hget==null?"0":new String(hget));
					minCategory.add(redisFileld);
				}
				map.put(ent.getKey(), list);
			}
		}
		result.put("result", map);
		result.put("minCategory", minCategory);
		return result;

	}
	@Override
	public Map<String, Long> findStatisticSum(String cla, String[] data) {
		Map<String, Long> sumMap=new HashMap<>();
		if("acc".equals(cla)) {
			long apiSubmitSum=0;
			long gwSubmitSum=0;
			long apiReportSuccSum=0;
			long apiReportFailSum=0;
			long apiMoSum=0;
			for(String accId:data) {
				String accKey=platformFlag+"_api:"+accId;
				byte[] api_submit = RedisUtil.hget(accKey, "api_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] gw_submit = RedisUtil.hget(accKey, "gw_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] api_report_succ = RedisUtil.hget(accKey, "api_report_succ", Constants.Common.Redis.Naming.REDIS60);
				byte[] api_report_fail = RedisUtil.hget(accKey, "api_report_fail", Constants.Common.Redis.Naming.REDIS60);
				byte[] api_mo = RedisUtil.hget(accKey, "api_mo", Constants.Common.Redis.Naming.REDIS60);
				apiSubmitSum+=Integer.parseInt(api_submit==null?"0":new String(api_submit));
				gwSubmitSum+=Integer.parseInt(gw_submit==null?"0":new String(gw_submit));
				apiReportSuccSum+=Integer.parseInt(api_report_succ==null?"0":new String(api_report_succ));
				apiReportFailSum+=Integer.parseInt(api_report_fail==null?"0":new String(api_report_fail));
				apiMoSum+=Integer.parseInt(api_mo==null?"0":new String(api_mo));
			}
			
			sumMap.put("apiSubmit", apiSubmitSum);
			sumMap.put("gwSubmit", gwSubmitSum);
			sumMap.put("apiReportSucc", apiReportSuccSum);
			sumMap.put("apiReportFail", apiReportFailSum);
			sumMap.put("apiMo", apiMoSum);
		}else if("chan".equals(cla)) {
			long apiSubmitSum=0;
			long gwSubmitSum=0;
			long gwReportSuccSum=0;
			long gwReportFailSum=0;
			long gwMoSum=0;
			for(String chaId:data) {
				String chanKey=platformFlag+"_mt:"+chaId;
				byte[] api_submit = RedisUtil.hget(chanKey, "api_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] gw_submit = RedisUtil.hget(chanKey, "gw_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] gw_report_succ = RedisUtil.hget(chanKey, "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
				byte[] gw_report_fail = RedisUtil.hget(chanKey, "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
				byte[] gw_mo = RedisUtil.hget(chanKey, "gw_mo", Constants.Common.Redis.Naming.REDIS60);
				apiSubmitSum+=Integer.parseInt(api_submit==null?"0":new String(api_submit));
				gwSubmitSum+=Integer.parseInt(gw_submit==null?"0":new String(gw_submit));
				gwReportSuccSum+=Integer.parseInt(gw_report_succ==null?"0":new String(gw_report_succ));
				gwReportFailSum+=Integer.parseInt(gw_report_fail==null?"0":new String(gw_report_fail));
				gwMoSum+=Integer.parseInt(gw_mo==null?"0":new String(gw_mo));
			}
			
			sumMap.put("apiSubmit", apiSubmitSum);
			sumMap.put("gwSubmit", gwSubmitSum);
			sumMap.put("gwReportSucc", gwReportSuccSum);
			sumMap.put("gwReportFail", gwReportFailSum);
			sumMap.put("gwMo", gwMoSum);
		}else if("count".equals(cla)) {
			String redisKey=platformFlag+"_count";
			byte[] api_submit = RedisUtil.hget(redisKey, "api_submit", Constants.Common.Redis.Naming.REDIS60);
			byte[] gw_submit = RedisUtil.hget(redisKey, "gw_submit", Constants.Common.Redis.Naming.REDIS60);
			byte[] api_report_succ = RedisUtil.hget(redisKey, "api_report_succ", Constants.Common.Redis.Naming.REDIS60);
			byte[] api_report_fail = RedisUtil.hget(redisKey, "api_report_fail", Constants.Common.Redis.Naming.REDIS60);
			byte[] gw_report_succ = RedisUtil.hget(redisKey, "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
			byte[] gw_report_fail = RedisUtil.hget(redisKey, "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
			byte[] api_mo = RedisUtil.hget(redisKey, "api_mo", Constants.Common.Redis.Naming.REDIS60);
			byte[] gw_mo = RedisUtil.hget(redisKey, "gw_mo", Constants.Common.Redis.Naming.REDIS60);
			long apiSubmitSum=Long.parseLong(api_submit==null?"0":new String(api_submit));
			long gwSubmitSum=Long.parseLong(gw_submit==null?"0":new String(gw_submit));
			long apiReportSuccSum=Long.parseLong(api_report_succ==null?"0":new String(api_report_succ));
			long apiReportFailSum=Long.parseLong(api_report_fail==null?"0":new String(api_report_fail));
			long gwReportSuccSum=Long.parseLong(gw_report_succ==null?"0":new String(gw_report_succ));
			long gwReportFailSum=Long.parseLong(gw_report_fail==null?"0":new String(gw_report_fail));
			long apiMoSum=Long.parseLong(api_mo==null?"0":new String(api_mo));
			long gwMoSum=Long.parseLong(gw_mo==null?"0":new String(gw_mo));
			sumMap.put("apiSubmit", apiSubmitSum);
			sumMap.put("gwSubmit", gwSubmitSum);
			sumMap.put("apiReportSucc", apiReportSuccSum);
			sumMap.put("apiReportFail", apiReportFailSum);
			sumMap.put("gwReportSucc", gwReportSuccSum);
			sumMap.put("gwReportFail", gwReportFailSum);
			sumMap.put("apiMo", apiMoSum);
			sumMap.put("gwMo", gwMoSum);
		}
		return sumMap;
	}
	@Override
	public List<Map<String, String>> findProviderList() {
		return dataMonitoringMapper.findProviderList();
	}

}
