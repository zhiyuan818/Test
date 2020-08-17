package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackKeyword;

public interface BlackKeywordMapper {

	public List<BlackKeyword> findAllList();

	public int addToBlackKeyword(String str);

	/* 按条件查询分页数据 */
	public List<BlackKeyword> findPageList(Map<String, Object> map);

	public int findTotal(Map<String, Object> map);

	public int delBlackKeyword(Integer id);

	public BlackKeyword findBlackKeywordById(Integer id);

	public int updateBlackKeyword(BlackKeyword blackKeyword);
	
	public int findByKeyWord(String str);

}
