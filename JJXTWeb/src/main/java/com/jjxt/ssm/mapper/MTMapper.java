package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.MT;

public interface MTMapper {
	/* 查询所有下行日志 */
	public List<MT> findMT();

	/* 多条件查询总条数 */
	public int findMTTotal(Map<String, Object> map);

	/* 多条件查询分页列表 */
	public List<MT> findMTPageList(Map<String, Object> map);

	/* 根据link_id进行查询日志记录 */
	public MT findMTByLinkId(Map<String, String> map);

	/* 查询重推状态报告 */
	public Integer findPutReportSum(Map<String, Object> map);
	
	/*新查询日志表总条数*/
	public int findNewMtTotal(Map<String, Object> map);
	
	/*新查询日志表记录*/
	public List<MT> findNewMTPageList(Map<String, Object> map);
	
	/*新查询日志表通过LINK_ID*/
	public MT findNewMtByLinkId(Map<String, String> map);
	
	/*新查询重推状态报告数量*/
	public Integer findNewPutReportSum(Map<String, Object> map);
	
	/*新查询日志内容表根据unique_id*/
	public List<MT> findMtContentByUniqueSet(Map<String, Object> uniqueMap);
	
	/*新查询日志内容表根据content模糊查询 */
	public List<MT> findMtContentByMap(Map<String, Object> map);
	
	/*新查询日志表记录根据unique_id*/
	public List<MT> findMtByUniqueSet(Map<String, Object> uniqueSet);
	
}
