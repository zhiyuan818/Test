package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelAudit;
import com.jjxt.ssm.utils.DataSource;

public interface LgModelAuditService {
	// 添加至模板表
	@DataSource("master")
	int addLgModelAudit(LgModelAudit lgModelAudit) throws Exception;

	// 查询添加记录
	@DataSource("master")
	LgModelAudit findAddEnd() throws Exception;

	// 根据content查询模板
	@DataSource("master")
	LgModelAudit findByContent(String string) throws Exception;

	/* 查询分页列表 */
	@DataSource("master")
	List<LgModelAudit> findLgModleAuditPageList(Map<String, Object> map) throws Exception;

	/* 根据id查询对象 */
	@DataSource("master")
	List<LgModelAudit> findLgModelAuditById(Integer id) throws Exception;

	/* 单条删除 */
	@DataSource("master")
	int deleteLgModelAudit(Integer id) throws Exception;

	/* 根据多个id获取列表 */
	@DataSource("master")
	List<LgModelAudit> findLgModelAuditByIds(Integer[] ids) throws Exception;

	/* 批量删除 */
	@DataSource("master")
	int deleteLgModelAuditBatch(Integer[] ids) throws Exception;

	/* 修改 */
	@DataSource("master")
	int updateLgModelAudit(LgModelAudit lgModelAudit) throws Exception;
	/*-------------------删除-----------------------*/
	/* 查询app */
	@DataSource("master")
	Application findApplicationById(int parseInt) throws Exception;
	
	@DataSource("master")
	String findCompanyNameById(int parseInt) throws Exception;
	
	@DataSource("master")
	int findTotal(Map<String, String> param) throws Exception;
	@DataSource("master")
	LgModelAudit findByContent(String content, String string) throws Exception;

}
