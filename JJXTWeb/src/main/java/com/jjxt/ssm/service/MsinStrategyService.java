package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.MsinStrategy;
import com.jjxt.ssm.utils.DataSource;

public interface MsinStrategyService {

	@DataSource("master")
	List<MsinStrategy> findAllapp() throws Exception;	
	@DataSource("master")
	List<MsinStrategy> findAlllevel() throws Exception;
	@DataSource("master")
	List<MsinStrategy> findAllData() throws Exception;
	@DataSource("master")
	int findTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	List<MsinStrategy> findMsinList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	int delMsinStrategyById(Integer id) throws Exception;
	@DataSource("master")
	int addMsinStrategy(MsinStrategy msin) throws Exception;
	@DataSource("master")
	int isExistConfig(MsinStrategy msin) throws Exception;
	@DataSource("master")
	List<MsinStrategy> findMsinByIds(Integer[] ids) throws Exception;
	@DataSource("master")
	int delMsinBatch(Integer[] ids) throws Exception;
	@DataSource("master")
	int addMsinBatch(List<MsinStrategy> list);
	
}
