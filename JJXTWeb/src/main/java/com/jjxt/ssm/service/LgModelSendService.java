package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.utils.DataSource;

public interface LgModelSendService {
	// 查询总数
	@DataSource("master")
	int findTotal(Map<String, String> map) throws Exception;

	// 查询模板分页列表
	@DataSource("master")
	List<LgModelSend> findLgModelSendList(Map<String, Object> map1) throws Exception;
	//新增短信模板
	@DataSource("master")
	int addLgModelSend(LgModelSend lgModelSend) throws Exception;
	//查询最后新增的数据
	@DataSource("master")
	List<LgModelSend> findAddEnd() throws Exception;
	//根据ID查询
	@DataSource("master")
	List<LgModelSend> findLgModelSendById(Integer id) throws Exception;
	//删除短信模板
	@DataSource("master")
	int deleteLgModelSend(Integer id) throws Exception;
	//更新短信模板
	@DataSource("master")
	int updateLgModelSend(LgModelSend lgModelSend) throws Exception;
	@DataSource("master")
	List<LgModelSend> findLgModelSendByIds(Integer[] ids) throws Exception;
	@DataSource("master")
	int delLgModelSendBatch(Integer[] ids) throws Exception;
}
