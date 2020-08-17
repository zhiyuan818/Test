package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackLevel1;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.PhoneStatus;
import com.jjxt.ssm.entity.WhiteLevel;
import com.jjxt.ssm.utils.DataSource;

public interface DataManageService {
	
	// 根据条件查询黑名单2
	List<BlackLevel2> findPhoneByBlack2(List<String> phone, String appId) throws Exception;

	// 添加白名单
	int addWhite(WhiteLevel white) throws Exception;
	// 添加普通黑名单
	int addBlackLevel3(String phone) throws Exception;
	// 添加关键黑名单
	int addBlackLevel5(String phone) throws Exception;
	// 添加投诉黑名单
	int addBlackLevel1(BlackLevel1 black1) throws Exception;
	// 添加回复黑名单
	int addBlackLevel2(BlackLevel2 black2) throws Exception;
	// 添加实号库
	int addShunt(String phone) throws Exception;
	

	
	// 删除黑白名单
	@DataSource("master")
	int deletePhone(Map<String, String> map) throws Exception;
	
	//联合查询
	@DataSource("master")
	List<PhoneStatus> findPhone(List<String> phone) throws Exception;
	
	//批量添加投诉黑
	@DataSource("master")
	int addBlackLevel1Batch(List<PhoneStatus> phoneStatusList) throws Exception;
	//批量添加白名单
	@DataSource("master")
	int addWhiteBatch(List<PhoneStatus> list) throws Exception;
}
