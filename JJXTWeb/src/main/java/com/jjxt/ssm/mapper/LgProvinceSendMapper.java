package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.LgProvincesSend;

@Repository
public interface LgProvinceSendMapper {

	int findTotal(Map<String, Object> map);

	List<LgProvincesSend> findLgProvinceSendPageList(Map<String, Object> pageMap);

	List<LgProvincesSend> findLgProvincesSendByIds(Integer[] ids);

	int delLgProvincesSendBatch(Integer[] ids);

	int delLgProvincesSend(Integer id);

	List<LgProvincesSend> findLgProvincesSendById(Integer id);
	
	int pauseLgProvincesSendBatch(Integer[] ids);
	
	int pauseLgProvincesSend(Integer id);
	
	int startLgProvincesSendBatch(Integer[] ids);
	
	int startLgProvincesSend(Integer id);

}
