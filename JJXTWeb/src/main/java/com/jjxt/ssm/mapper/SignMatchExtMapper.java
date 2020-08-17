package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.SignMatchExt;

public interface SignMatchExtMapper {

	List<SignMatchExt> findPageList(Map<String, Object> map);

	Integer findTotal(Map<String, Object> map);

	Integer findMaxExtByAppIdAndExtLength(SignMatchExt signMExt);

	Integer addSignMatchExt(SignMatchExt signMExt);

	int deleteSignMatchExt(String id);

	SignMatchExt findListId(String id);

	List<SignMatchExt> findAllList(Map<String, Object> map);
	
}
