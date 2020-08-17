package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.MapService;
import com.jjxt.ssm.utils.MapData;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/map")
public class MapController {
	private static Logger logger = Logger.getLogger(MapController.class);

	private static final String PATH = "map/";
	@Autowired
	private MapService mapService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/goMap.action")
	public String goMap() {
		return PATH+"chinaMap";
	}

	
	@ResponseBody
	@RequestMapping("/findMapCount.action")
	public Map<String, Object> findMapCount(String clazz,String type,String[] data){
		logger.debug("[BINS][CHINA_MAP] class="+clazz+",type="+type+",data="+Arrays.toString(data));
		Map<String, Object> map=new HashMap<String,Object>();
		Map<String, List<MapData>> mapData = mapService.findMapData(clazz,type,data);
		Map<String, Object> codeData = mapService.findCodeData(clazz,type,data);
		map.put("mapData", mapData);
		map.put("codeData", codeData);
		return map;
	}

	
	@ResponseBody
	@RequestMapping("/findLoadData.action")
	public List<Map<String,Object>> findLoadData(String cla){
		logger.debug("[BINS][CHINA_MAP] cla="+cla);
		List<Map<String, Object>> datas=new ArrayList<>();
		if(StringUtil.isEmpty(cla)) {
			logger.error("[ERROR][CHINA_MAP] cla="+cla);
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
}
