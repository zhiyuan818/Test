package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackSign;
import com.jjxt.ssm.utils.DataSource;

public interface BlackSignService {
	@DataSource("master")
	int findTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	List<BlackSign> findBlackSignList(Map<String, Object> map1) throws Exception;
	@DataSource("master")
	int addBlackSign(BlackSign blackSign) throws Exception;
	@DataSource("master")
	List<BlackSign> findAddEnd() throws Exception;
	@DataSource("master")
	List<com.jjxt.ssm.entity.BlackSign> findBlackSignById(Integer id) throws Exception;
	@DataSource("master")
	int deleteBlackSign(Integer id) throws Exception;
	@DataSource("master")
	int updateBlackSign(BlackSign blackSign) throws Exception;

}
