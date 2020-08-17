package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.DataOperationLog;

@Repository("dataOperationLogMapper")
public interface DataOperationLogMapper {
	// 添加数据库日志
	int addDataOperationLog(DataOperationLog dataOperationLog);

	// 查询日志数据总条数
	int findDataOperationLogTotal();

	// 查询日志分页列表
	List<DataOperationLog> findDataOperationLogList(Map<String, Object> map);

	int findLogForCount(Map<String, Object> map);

	List<DataOperationLog> findLogForPage(Map<String, Object> map);

}
