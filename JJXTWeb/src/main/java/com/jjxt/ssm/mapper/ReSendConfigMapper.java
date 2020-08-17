package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ReSendConfig;

public interface ReSendConfigMapper {
	
	int findTotalByApp(Map<String, String> map);
	
	List<ReSendConfig> findReSendConfigByAppPageList(Map<String, Object> paramMap);
	
	int findTotalByChannel(Map<String, String> map);
	
	List<ReSendConfig> findReSendConfigByChannelPageList(Map<String, Object> paramMap);
	
	ReSendConfig findReSendConfigByAppId(Integer id);
	
	ReSendConfig findReSendConfigByChannelId(Integer id);
	
	int updateReSendConfigByAppId(Map<String, Object> map);
	
	int updateReSendConfigByChannelId(Map<String, Object> map);
	
	int findReSendConfigIsRepeat(Map<String, Object> map);
	
	int addReSendConfig(Map<String, Object> map);
	
	ReSendConfig findEndAddReSendConfig(String idType);
	
	int delReSendConfig(Integer id);
	
	List<ReSendConfig> findResendConfigByIds(Integer[] ids);
	
	int delResendConfigByIdBatch(Integer[] ids);
}
