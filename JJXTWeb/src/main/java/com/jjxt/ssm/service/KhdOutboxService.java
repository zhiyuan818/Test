package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.KhdOutbox;
import com.jjxt.ssm.utils.DataSource;

public interface KhdOutboxService {
	
	@DataSource("master")
	int findTotal(Map<String, Object> map);
	@DataSource("master")
	List<KhdOutbox> findKhdOutboxList(Map<String, Object> param);

}
