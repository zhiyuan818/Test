package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.DataOperationLog;
import com.jjxt.ssm.mapper.DataOperationLogMapper;
import com.jjxt.ssm.service.DataOperationLogService;

@Service("dataOperationLogService")
@Transactional
public class DataOperationLogServiceImpl implements DataOperationLogService {
	@Autowired
	private DataOperationLogMapper dataOperationLogMapper;

	@Override
	public int addDataOperationLog(DataOperationLog dataOperationLog) throws Exception {

		return dataOperationLogMapper.addDataOperationLog(dataOperationLog);
	}

	@Override
	public List<DataOperationLog> findLogForPage(Map<String, Object> map) throws Exception {
		return dataOperationLogMapper.findLogForPage(map);
	}

	@Override
	public int findLogForCount(Map<String, Object> map) throws Exception {
		return dataOperationLogMapper.findLogForCount(map);
	}

}
