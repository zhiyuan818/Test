package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.utils.MapData;

public interface MapService {

	Map<String,List<MapData>> findMapData(String clazz,String type,String[] data);

	Map<String, Object> findCodeData(String clazz,String type,String[] data);
	
}
