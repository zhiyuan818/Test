package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.utils.DataSource;

public interface LgProvinceSendService {
	
	@DataSource("master")
	int findTotal(Map<String, Object> map);
	@DataSource("master")
	List<LgProvincesSend> findLgProvinceSendPageList(Map<String, Object> pageMap);
	@DataSource("master")
	List<LgProvincesSend> findLgProvincesSendByIds(Integer[] ids);
	@DataSource("master")
	int delLgProvincesSendBatch(Integer[] ids);
	@DataSource("master")
	int delLgProvincesSend(Integer id);
	@DataSource("master")
	int pauseLgProvincesSendBatch(Integer[] ids);
	@DataSource("master")
	int pauseLgProvincesSend(Integer id);
	@DataSource("master")
	int startLgProvincesSendBatch(Integer[] ids);
	@DataSource("master")
	int startLgProvincesSend(Integer id);
	@DataSource("master")
	List<LgProvincesSend> findLgProvincesSendById(Integer id);

}
