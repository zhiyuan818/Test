package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelAudit;

public interface LgModelAuditMapper {
	/* 添加至模板表 */
	int addLgModelAudit(LgModelAudit lgModelAudit);

	/* 查询最后添加模板 */
	LgModelAudit findAddEnd();

	/* 根据content查询对象 */
	LgModelAudit findByContent(@Param("content")String content);

	/* 根据条件模糊总数 */
	int findTotal(Map<String, String> map);

	/* 查询分页列表 */
	List<LgModelAudit> findLgModleAuditPageList(Map<String, Object> map);

	/* 根据id查询对象 */
	List<LgModelAudit> findLgModelAuditById(Integer id);

	/* 单条删除 */
	int deleteLgModelAudit(Integer id);

	/* 根据多个id获取列表 */
	List<LgModelAudit> findLgModelAuditByIds(Integer[] ids);

	/* 批量删除 */
	int deleteLgModelAuditBatch(Integer[] ids);

	/* 修改 */
	int updateLgModelAudit(LgModelAudit lgModelAudit);

	Application findApplicationByAppId(int id);

	String findCompanyNameById(int id);

	LgModelAudit findByConMap(Map<String, String> map);

}
