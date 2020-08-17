package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Dispatch;

public interface MoDispatchMapper {

	public List<Dispatch> findAllList(Map<String, Object> map);
	
	public int addToDispatch(Map<String, Object> map);
	
	public Map<String, Object> findTwoId(String str);
	
	public Map<String, Object> findListById(Integer id);
	
	public int delDispatchById(Integer id);
	
	public int updateDispatch(Map<String, Object> map);
	
	public Dispatch findMoDispatchById(Integer id);
	
	public Map<String, Object> findIdsByName(String str);
	
	public int findMoDispatchData(String str);

	public int findTotal(Map<String, String> paramMap);

	public int deleteDispatch(Map<String, String> map);
	
	
}
