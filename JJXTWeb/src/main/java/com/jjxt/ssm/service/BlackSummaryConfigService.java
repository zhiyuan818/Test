package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackSummaryConfig;
import com.jjxt.ssm.utils.DataSource;

public interface BlackSummaryConfigService {
	
	/* 根据条件模糊总数 */
	@DataSource("master")
	int findTotal(Map<String, Object> param) throws Exception;
	
	/* 查询分页列表 */
	@DataSource("master")
	List<BlackSummaryConfig> findBlackSummaryConfigPageList(Map<String, Object> map) throws Exception;
	
	@DataSource("master")
	List<BlackSummaryConfig> validatorAppIdAndTypeAndLevel(Map<String, String> map) throws Exception;
	
	/* 添加至黑名单配置 */
	@DataSource("master")
	int addBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception;

	/* 查询添加记录 */
	@DataSource("master")
	BlackSummaryConfig findAddEnd() throws Exception;
	
	/* 根据id查询对象 */
	@DataSource("master")
	List<BlackSummaryConfig> findBlackSummaryConfigById(Integer id) throws Exception;

	/* 修改 */
	@DataSource("master")
	int updateBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception;

	/* 删除单条数据 */
	@DataSource("master")
	int deleteBlackSummaryConfig(Integer id) throws Exception;

	/* 根据多个id获取列表 */
	@DataSource("master")
	List<BlackSummaryConfig> findBlackSummaryConfigByIds(Integer[] ids) throws Exception;
	
	/* 批量删除 */
	@DataSource("master")
	int deleteBlackSummaryConfigBatch(Integer[] ids) throws Exception;
	
	@DataSource("master")	
	int startSummaryConfig(Integer id) throws Exception;	
	
	@DataSource("master")	
	int pauseSummaryConfig(Integer id) throws Exception;
	
	@DataSource("master")	
	int startSummaryConfigBatch(Integer[] ids) throws Exception;	
	
	@DataSource("master")	
	int pauseSummaryConfigBatch(Integer[] ids) throws Exception;	
	
	@DataSource("master")
	List<BlackSummaryConfig> findAlllevel() throws Exception;
	
}
