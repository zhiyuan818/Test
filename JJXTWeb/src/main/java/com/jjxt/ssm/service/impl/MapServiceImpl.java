package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.MapService;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.MapData;
import com.jjxt.ssm.utils.QueueUtils;
@Service("mapService")
public class MapServiceImpl implements MapService{
	@Value("${platformFlag}")
	private String platformFlag;
	
	@Override
	public Map<String,List<MapData>> findMapData(String clazz,String type,String[] data) {
		List<MapData> groupList=new ArrayList<MapData>();
		List<MapData> compareList=new ArrayList<MapData>();
		if("平台".equals(clazz)) {
			for(int i=0;i<Constants.Map.Province.province.length;i++) {
				MapData mapData=new MapData();
				String province=Constants.Map.Province.province[i];
				mapData.setName(province);
				byte[] hget = RedisUtil.hget(province, platformFlag+"_count", Constants.Common.Redis.Naming.REDIS60);
				if(hget == null) {
					mapData.setValue("0");
				}else {
					mapData.setValue(new String(hget));
				}
				groupList.add(mapData);
			}
		}else if("acc".equals(clazz)) {
			for(int i=0;i<Constants.Map.Province.province.length;i++) {
				MapData mapData=new MapData();
				String province=Constants.Map.Province.province[i];
				mapData.setName(province);
				int sum=0;
				for(int j=0;j<data.length;j++) {
					byte[] hget = RedisUtil.hget(province, platformFlag+"_count:api:"+data[j], Constants.Common.Redis.Naming.REDIS60);
					sum+=Integer.parseInt(hget==null?"0":new String(hget));
				}
				mapData.setValue(sum);
				groupList.add(mapData);
				if("contrast".equals(type)) {
					MapData comdata=new MapData();
					comdata.setName(province);
					List<MapData> sublist=new ArrayList<>();
					for(int j=0;j<data.length;j++) {
						MapData subdata=new MapData();
						subdata.setName("appId_"+data[j]);
						byte[] hget = RedisUtil.hget(province, platformFlag+"_count:api:"+data[j], Constants.Common.Redis.Naming.REDIS60);
						subdata.setValue(hget==null?"0":new String(hget));
						sublist.add(subdata);
					}
					comdata.setValue(sublist);
					compareList.add(comdata);
				}
			}
		}else if("chan".equals(clazz)) {
			for(int i=0;i<Constants.Map.Province.province.length;i++) {
				MapData mapData=new MapData();
				String province=Constants.Map.Province.province[i];
				mapData.setName(province);
				int sum=0;
				for(int j=0;j<data.length;j++) {
					byte[] hget = RedisUtil.hget(province, platformFlag+"_count:mt:"+data[j], Constants.Common.Redis.Naming.REDIS60);
					sum+=Integer.parseInt(hget==null?"0":new String(hget));
				}
				mapData.setValue(sum);
				groupList.add(mapData);
				if("contrast".equals(type)) {
					MapData coMapData=new MapData();
					coMapData.setName(province);
					List<MapData> sublist=new ArrayList<>();
					for(int j=0;j<data.length;j++) {
						MapData subdata=new MapData();
						subdata.setName("channelId_"+data[j]);
						byte[] hget = RedisUtil.hget(province, platformFlag+"_count:mt:"+data[j], Constants.Common.Redis.Naming.REDIS60);
						subdata.setValue(hget==null?"0":new String(hget));
						sublist.add(subdata);
					}
					coMapData.setValue(sublist);
					compareList.add(coMapData);
				}
			}
		}
		Map<String,List<MapData>> map=new HashMap<>();
		map.put("groupData", groupList);
		map.put("compareData", compareList);
		return map;
	}

	@Override
	public Map<String, Object> findCodeData(String clazz,String type,String[] data) {
		int gwSubmitSum=0;
		int gwReportSuccSum=0;
		int gwArrivalSum=0;
		String successRate=null;
		String arrivalRate=null;
		double speed=0.0;
		int parseDouble=0;
		if("平台".equals(clazz)) {
			byte[] gwSubmit = RedisUtil.hget(platformFlag+"_count","gw_submit",Constants.Common.Redis.Naming.REDIS60);
			byte[] gwSuccess = RedisUtil.hget(platformFlag+"_count", "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
			byte[] gwFail = RedisUtil.hget(platformFlag+"_count", "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
			byte[] gwMinuteSubmit = RedisUtil.hget(platformFlag+"_count:minute:gw:submit", DateUtil.getTimeByMinute(-1), Constants.Common.Redis.Naming.REDIS60);
			gwSubmitSum = Integer.parseInt(gwSubmit==null?"0":new String(gwSubmit));
			gwArrivalSum = Integer.parseInt(gwSuccess==null?"0":new String(gwSuccess)) + Integer.parseInt(gwFail==null?"0":new String(gwFail));
			gwReportSuccSum = Integer.parseInt(gwSuccess==null?"0":new String(gwSuccess));
			successRate = QueueUtils.percentage(gwReportSuccSum, gwSubmitSum);
			arrivalRate = QueueUtils.percentage(gwArrivalSum, gwSubmitSum);
			parseDouble = Integer.valueOf(gwMinuteSubmit==null?"0":new String(gwMinuteSubmit));
			speed = Math.round(parseDouble/6.0*10.0)/100.0 ; 
			
		}else if("acc".equals(clazz)) {
			for(int i=0;i<data.length;i++) {
				byte[] apiSubmit = RedisUtil.hget(platformFlag+"_api:"+data[i], "api_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiSuccess = RedisUtil.hget(platformFlag+"_api:"+data[i], "api_report_succ", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiFail = RedisUtil.hget(platformFlag+"_api:"+data[i], "api_report_fail", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiMinuteSubmit = RedisUtil.hget(platformFlag+"_api:minute:"+data[i]+":apiSubmit", DateUtil.getTimeByMinute(-1), Constants.Common.Redis.Naming.REDIS60);
				gwSubmitSum += Integer.parseInt(apiSubmit==null?"0":new String(apiSubmit));
				gwArrivalSum += Integer.parseInt(apiSuccess==null?"0":new String(apiSuccess)) + Integer.parseInt(apiFail==null?"0":new String(apiFail));
				gwReportSuccSum += Integer.parseInt(apiSuccess==null?"0":new String(apiSuccess));
				parseDouble += Integer.valueOf(apiMinuteSubmit==null?"0":new String(apiMinuteSubmit));
			}
			successRate = QueueUtils.percentage(gwReportSuccSum, gwSubmitSum);
			arrivalRate = QueueUtils.percentage(gwArrivalSum, gwSubmitSum);
			speed = Math.round(parseDouble/6.0*10.0)/100.0 ; 
		}else if("chan".equals(clazz)) {
			for(int i=0;i<data.length;i++) {
				byte[] apiSubmit = RedisUtil.hget(platformFlag+"_mt:"+data[i], "gw_submit", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiSuccess = RedisUtil.hget(platformFlag+"_mt:"+data[i], "gw_report_succ", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiFail = RedisUtil.hget(platformFlag+"_mt:"+data[i], "gw_report_fail", Constants.Common.Redis.Naming.REDIS60);
				byte[] apiMinuteSubmit = RedisUtil.hget(platformFlag+"_mt:minute:"+data[i]+":gwSubmit", DateUtil.getTimeByMinute(-1), Constants.Common.Redis.Naming.REDIS60);
				gwSubmitSum += Integer.parseInt(apiSubmit==null?"0":new String(apiSubmit));
				gwArrivalSum += Integer.parseInt(apiSuccess==null?"0":new String(apiSuccess)) + Integer.parseInt(apiFail==null?"0":new String(apiFail));
				gwReportSuccSum += Integer.parseInt(apiSuccess==null?"0":new String(apiSuccess));
				parseDouble += Integer.valueOf(apiMinuteSubmit==null?"0":new String(apiMinuteSubmit));
			}
			successRate = QueueUtils.percentage(gwReportSuccSum, gwSubmitSum);
			arrivalRate = QueueUtils.percentage(gwArrivalSum, gwSubmitSum);
			speed = Math.round(parseDouble/6.0*10.0)/100.0 ; 
		}
		
		Map<String, Object> map=new HashMap<>();
		map.put("gwSubmitSum", gwSubmitSum);
		map.put("gwArrivalSum", gwArrivalSum);
		map.put("gwReportSuccSum", gwReportSuccSum);
		map.put("successRate", successRate);
		map.put("arrivalRate", arrivalRate);
		map.put("speed", speed);
		return map;
	}

}
