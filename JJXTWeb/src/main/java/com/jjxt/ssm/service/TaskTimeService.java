package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.TaskTime;

public interface TaskTimeService {

	int findTotal(Map<String, Object> map) throws Exception;

	List<TaskTime> findTaskTimePageList(Map<String, Object> param) throws Exception;
	
	
}
