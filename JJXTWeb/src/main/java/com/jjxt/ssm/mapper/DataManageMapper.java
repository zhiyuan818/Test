package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackLevel1;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.PhoneStatus;
import com.jjxt.ssm.entity.WhiteLevel;

public interface DataManageMapper {

	// 根据条件查询白名单
	List<WhiteLevel> findPhoneByWhite(String phoneNumber);
	// 根据条件查询黑名单1
	List<BlackLevel1> findPhoneByBlack1(String phoneNumber);
	// 根据条件查询黑名单2
	List<BlackLevel2> findPhoneByBlack2(List<String> phone);
	

	// 添加白名单
	int addWhite(WhiteLevel white);
	
	
	// 添加投诉黑名单
	int addBlackLevel1(BlackLevel1 black1);
	// 添加回复黑名单
	int addBlackLevel2(BlackLevel2 black2);
	// 添加实号库
	int addShunt(Map<String, String> map);
	// 查询实名库总量
	int findShuntCount();

	// 删除黑白名单
	int deletePhone(Map<String, String> map);
	
	//联合查询
	List<PhoneStatus> findPhone(List<String> phone);
	//批量新增投诉黑
	int addBlackLevel1Batch(List<PhoneStatus> phoneStatusList);
	//批量新增白名单
	int addWhiteBatch(List<PhoneStatus> list);
	

}
