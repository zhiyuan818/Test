package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.KhdOperationLog;
import com.jjxt.ssm.mapper.KhdOperationLogMapper;
import com.jjxt.ssm.service.KhdOperationLogService;

@Transactional
@Service("/khdOperationLogService")
public class KhdOperationLogServiceImpl implements KhdOperationLogService{
	
	@Autowired
	private KhdOperationLogMapper khdOperationLogMapper;
	@Override
	public int findTotal(Map<String, Object> map) {
		
		return khdOperationLogMapper.findTotal(map);
	}

	@Override
	public List<KhdOperationLog> findKhdOperationLogList(Map<String, Object> map1) {
		
		return khdOperationLogMapper.findKhdOperationLogList(map1);
	}

}
