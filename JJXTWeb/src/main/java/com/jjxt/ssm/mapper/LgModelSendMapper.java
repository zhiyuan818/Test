package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LgModelSend;

public interface LgModelSendMapper {

	int findTotal(Map<String, String> map);

	List<LgModelSend> findLgModelSendList(Map<String, Object> map1);

	List<LgModelSend> findAddEnd();

	int addLgModelSend(LgModelSend lgModelSend);

	List<LgModelSend> findLgModelSendById(Integer id);

	int deleteLgModelSend(Integer id);

	int updateLgModelSend(LgModelSend lgModelSend);
	
	List<LgModelSend> findLgModelSendByIds(Integer[] ids);
	
	int delLgModelSendBatch(Integer[] ids);

}
