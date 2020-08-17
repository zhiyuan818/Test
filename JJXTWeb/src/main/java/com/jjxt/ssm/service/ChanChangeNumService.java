package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ChanChangeNum;
import com.jjxt.ssm.utils.DataSource;

public interface ChanChangeNumService {

	// 查询总数
	@DataSource("master")
	int findTotal(Map<String, String> map) throws Exception;
	
	// 查询模板分页列表
	@DataSource("master")
	List<ChanChangeNum> findchanChangeNumPageList(Map<String, Object> map1) throws Exception;
	
	@DataSource("master")
	int addChanChangeNum(ChanChangeNum chanChangeNum) throws Exception;
	
	@DataSource("master")
	List<ChanChangeNum> findAddEnd() throws Exception;
	
	@DataSource("master")
	List<ChanChangeNum> findChanChangeNumById(Integer id) throws Exception;
	
	@DataSource("master")
	int updateChanChangeNum(ChanChangeNum chanChangeNum) throws Exception;
	
	@DataSource("master")
	int deleteChanChangeNum(Integer id) throws Exception;
	
	@DataSource("master")
	List<ChanChangeNum> findChanChangeNumByIds(Integer[] ids) throws Exception;
	
	@DataSource("master")
	int deleteChanChangeNumBatch(Integer[] ids) throws Exception;
}
