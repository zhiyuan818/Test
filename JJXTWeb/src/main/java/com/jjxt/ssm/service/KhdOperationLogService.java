package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.KhdOperationLog;

public interface KhdOperationLogService {

	int findTotal(Map<String, Object> map);

	List<KhdOperationLog> findKhdOperationLogList(Map<String, Object> map1);

}
