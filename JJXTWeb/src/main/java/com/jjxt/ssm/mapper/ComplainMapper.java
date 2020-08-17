package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Complain;

public interface ComplainMapper {
	
	public List<Complain> findMtList(Map<String, Object> map);
	
	public List<Complain> findNewMtList(Map<String, Object> map);
	
	public int addToPreComplain(Map<String, Object> map);

	public int addToBlackLevel1(String str);
	
	public int findBlackLevel1(String str);

	public int deleteComplain(Map<String, String> map);
	
	public int findComplainTotal(Map<String, String> map);
	
	public List<Complain> findComplainPageList(Map<String, Object> map);
}
