package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.TaskTime;
import com.jjxt.ssm.mapper.TaskTimeMapper;
import com.jjxt.ssm.service.TaskTimeService;

@Service("taskTimeService")
public class TaskTimeServiceImpl implements TaskTimeService{

	@Autowired
	private TaskTimeMapper taskTimeMapper;
	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		return taskTimeMapper.findTotal(map);
	}

	@Override
	public List<TaskTime> findTaskTimePageList(Map<String, Object> param) throws Exception {
		return taskTimeMapper.findTaskTimePageList(param);
	}

}
