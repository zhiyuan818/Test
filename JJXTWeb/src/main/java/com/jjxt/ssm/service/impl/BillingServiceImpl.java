package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.mapper.BillingMapper;
import com.jjxt.ssm.service.BillingService;
@Service("billingService")
public class BillingServiceImpl implements BillingService{
	
	@Autowired
	private BillingMapper billingMapper;

	@Override
	public List<Statistics> findStatisticSum(Map<String, String> map) {
		return billingMapper.findStatisticSum(map);
	}

	@Override
	public List<Statistics> findStatistic(Map<String, String> param) {
		return billingMapper.findStatistic(param);
	}
	
	@Override
	public int validatorBillAccount(Map<String, Object> param) {
		return billingMapper.validatorBillAccount(param);
	}

}
