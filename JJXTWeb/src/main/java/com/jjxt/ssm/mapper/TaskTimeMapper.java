package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.TaskTime;

public interface TaskTimeMapper {

	int findTotal(Map<String, Object> map);

	List<TaskTime> findTaskTimePageList(Map<String, Object> param);

}
