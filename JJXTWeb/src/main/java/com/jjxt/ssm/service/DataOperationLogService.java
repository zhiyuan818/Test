package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.DataOperationLog;
import com.jjxt.ssm.utils.DataSource;

public interface DataOperationLogService {
	// 添加数据库日志
	@DataSource("master")
	int addDataOperationLog(DataOperationLog dataOperationLog) throws Exception;

	@DataSource("master")
	int findLogForCount(Map<String, Object> map) throws Exception;

	@DataSource("master")
	List<DataOperationLog> findLogForPage(Map<String, Object> map) throws Exception;
}
