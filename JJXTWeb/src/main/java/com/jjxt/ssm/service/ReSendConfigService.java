package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;
import com.jjxt.ssm.entity.ReSendConfig;
import com.jjxt.ssm.utils.DataSource;

public interface ReSendConfigService {


	@DataSource("master")
	List<ReSendConfig> findReSendConfigByAppPageList(Map<String, Object> paramMap);
	@DataSource("master")
	int findTotalByApp(Map<String, String> map);
	@DataSource("master")
	List<ReSendConfig> findReSendConfigByChannelPageList(Map<String, Object> paramMap);
	@DataSource("master")
	int findTotalByChannel(Map<String, String> map);
	@DataSource("master")
	ReSendConfig findReSendConfigByAppId(Integer id);
	@DataSource("master")
	ReSendConfig findReSendConfigByChannelId(Integer id);
	@DataSource("master")
	int updateReSendConfigByAppId(Map<String, Object> map);
	@DataSource("master")
	int updateReSendConfigByChannelId(Map<String, Object> map);
	@DataSource("master")
	int findReSendConfigIsRepeat(Map<String, Object> map);
	@DataSource("master")
	int addReSendConfig(Map<String, Object> map);
	@DataSource("master")
	ReSendConfig findEndAddReSendConfig(String idType);
	@DataSource("master")
	int delReSendConfig(Integer id);
	@DataSource("master")
	List<ReSendConfig> findResendConfigByIds(Integer[] ids);
	@DataSource("master")
	int delResendConfigByIdBatch(Integer[] ids);


}
