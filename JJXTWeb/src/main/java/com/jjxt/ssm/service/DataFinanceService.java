package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.DataFinance;
import com.jjxt.ssm.utils.DataSource;

public interface DataFinanceService {

	@DataSource("master")
	int addDataFinance(DataFinance dataFinance) throws Exception;
	
	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;
	
	@DataSource("master")
	List<DataFinance> findDataFinancePageList(Map<String, Object> paramMap) throws Exception;
	@DataSource("master")
	DataFinance findDataFinanceById(Integer id) throws Exception;
	@DataSource("master")
	int updateDataFinance(DataFinance dataFinance) throws Exception;
	
}
