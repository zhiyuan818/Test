package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.MT;
import com.jjxt.ssm.utils.DataSource;

public interface MTService {
	/* 查询所有mt */
	@DataSource("slave")
	public List<MT> findMt() throws Exception;

	/* 多条件查询总条数 */
	@DataSource("slave")
	public int findMTTotal(Map<String, Object> map) throws Exception;

	/* 多条件查询分页列表 */
	@DataSource("slave")
	public List<MT> findMTPageList(Map<String, Object> map) throws Exception;

	/* 根据link_id查询日志记录 */
	@DataSource("slave")
	public MT findMTByLinkId(Map<String, String> map) throws Exception;

	@DataSource("slave")
	public Integer findPutReportSum(Map<String, Object> map) throws Exception;
	
	@DataSource("slave")
	public int findNewMtTotal(Map<String, Object> map) throws Exception;
	
	@DataSource("slave")
	public List<MT> findNewMTPageList(Map<String, Object> map) throws Exception;
	
	@DataSource("slave")
	public MT findNewMTByLinkId(Map<String, String> map) throws Exception;
	
	@DataSource("slave")
	public Integer findNewPutReportSum(Map<String, Object> map) throws Exception;

}
