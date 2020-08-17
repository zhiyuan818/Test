package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.CheckMust;

public interface CheckMustMapper {

	public int addToCheckMust(String str);

	/* 按条件查询 */
	public List<CheckMust> findPageList(Map<String, Object> map);

	public int findTotal(Map<String, Object> map);

	public int delCheckMust(Integer id);

	public CheckMust findCheckMustById(Integer id);

	public int updateCheckMust(CheckMust checkmust);
	
	public int findByKeyWord(String str);

}
