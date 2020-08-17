package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackSummaryConfig;

public interface BlackSummaryConfigMapper {

	/* 根据条件模糊总数 */
	int findTotal(Map<String, Object> map);
	
	/* 查询分页列表 */
	List<BlackSummaryConfig> findBlackSummaryConfigPageList(Map<String, Object> map);
	
	List<BlackSummaryConfig> validatorAppIdAndTypeAndLevel(Map<String, String> map) throws Exception;
	
	/* 添加至黑名单配置 */
	int addBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception;

	/* 查询添加记录 */
	BlackSummaryConfig findAddEnd() throws Exception;
	
	/* 根据id查询对象 */
	List<BlackSummaryConfig> findBlackSummaryConfigById(Integer id) throws Exception;
	
	/* 修改 */
	int updateBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception;
	
	/* 删除单条数据 */
	int deleteBlackSummaryConfig(Integer id) throws Exception;

	/* 根据多个id获取列表 */
	List<BlackSummaryConfig> findBlackSummaryConfigByIds(Integer[] ids) throws Exception;
	
	/* 批量删除 */
	int deleteBlackSummaryConfigBatch(Integer[] ids) throws Exception;
	
	int startSummaryConfig(Integer id) throws Exception;	
	
	int pauseSummaryConfig(Integer id) throws Exception;
	
	int startSummaryConfigBatch(Integer[] ids) throws Exception;	
	
	int pauseSummaryConfigBatch(Integer[] ids) throws Exception;
	
	List<BlackSummaryConfig> findAlllevel() throws Exception;

}
