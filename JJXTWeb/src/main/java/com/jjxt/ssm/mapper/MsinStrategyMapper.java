package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.MsinStrategy;

public interface MsinStrategyMapper {

	List<MsinStrategy> findAllapp();
	List<MsinStrategy> findAlllevel();
	
	List<MsinStrategy> findAllData();
	
	int findTotal(Map<String, Object> map);
	
	List<MsinStrategy> findMsinList(Map<String, Object> map);
	
	int delMsinStrategyById(Integer id);
	
	int addMsinStrategy(MsinStrategy msin);
	
	int isExistConfig(MsinStrategy msin);
	
	List<MsinStrategy> findMsinByIds(Integer[] ids);
	
	int delMsinBatch(Integer[] ids);
	
	int addMsinBatch(List<MsinStrategy> list);
	
}
