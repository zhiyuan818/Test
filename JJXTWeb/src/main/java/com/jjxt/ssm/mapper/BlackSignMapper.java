package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackSign;

public interface BlackSignMapper {

	int findTotal(Map<String, Object> map);

	List<BlackSign> findBlackSignList(Map<String, Object> map);

	int addBlackSign(BlackSign blackSign);

	List<BlackSign> findAddEnd();

	List<BlackSign> findBlackSignById(Integer id);

	int deleteBlackSign(Integer id);

	int updateBlackSign(BlackSign blackSign);
	
}
