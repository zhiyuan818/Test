package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.DataFinance;

public interface DataFinanceMapper {

	int addDataFinance(DataFinance dataFinance);
	
    int findTotalByCondition(Map<String, String> paramMap);
	
	List<DataFinance> findFinancePageList(Map<String, Object> paramMap);
	
	DataFinance findDataFinanceById(Integer id);
	
	int updateDataFinance(DataFinance dataFinance);
}
