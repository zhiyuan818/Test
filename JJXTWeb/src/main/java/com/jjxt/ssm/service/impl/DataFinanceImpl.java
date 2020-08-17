package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.DataFinance;
import com.jjxt.ssm.mapper.DataFinanceMapper;
import com.jjxt.ssm.service.DataFinanceService;

@Service("dataFinanceService")
@Transactional
public class DataFinanceImpl implements DataFinanceService{
	
	@Autowired
	DataFinanceMapper dataFinanceMapper;

	@Override
	public int addDataFinance(DataFinance dataFinance) throws Exception {
		return 0;
	}

	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		return dataFinanceMapper.findTotalByCondition(paramMap);
	}

	@Override
	public List<DataFinance> findDataFinancePageList(Map<String, Object> paramMap) throws Exception {
		return dataFinanceMapper.findFinancePageList(paramMap);
	}

	@Override
	public DataFinance findDataFinanceById(Integer id) throws Exception {
		return dataFinanceMapper.findDataFinanceById(id);
	}

	@Override
	public int updateDataFinance(DataFinance dataFinance) throws Exception {
		return dataFinanceMapper.updateDataFinance(dataFinance);
	}

	
}
