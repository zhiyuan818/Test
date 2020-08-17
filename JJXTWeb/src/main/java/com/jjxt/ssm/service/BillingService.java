package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Statistics;
public interface BillingService {
	
	List<Statistics> findStatisticSum(Map<String, String> map);

	List<Statistics> findStatistic(Map<String, String> param);
	
	int validatorBillAccount(Map<String, Object> param);

}
