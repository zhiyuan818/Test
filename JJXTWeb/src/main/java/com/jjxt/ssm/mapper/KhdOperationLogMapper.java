package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.KhdOperationLog;

public interface KhdOperationLogMapper {

	int findTotal(Map<String, Object> map);

	List<KhdOperationLog> findKhdOperationLogList(Map<String, Object> map1);

}
