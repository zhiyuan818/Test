package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.NumSegment;
import com.jjxt.ssm.utils.DataSource;

public interface NumSegmentService {
	
	/* 查询总数 */
	@DataSource("master")
	int findNumSegmentTotal(Map<String, Object> paramMap) throws Exception;
	
	/* 分页查询list */
	@DataSource("master")
	public List<NumSegment> findNumSegmentList(Map<String, Object> paramMap) throws Exception;

	/* 判断号段是否存在 */
	@DataSource("master")
	public int findNumSegmentByCondition(Map<String, Object> paramMap) throws Exception;
	
	/* 添加数据 */
	@DataSource("master")
	public int addNumSegment(NumSegment numSegment) throws Exception;
	
	/* 通过id查询数据 */
	@DataSource("master")
	public NumSegment findNumSegmentById(Integer id) throws Exception;

	/* 修改配置 */
	@DataSource("master")
	public int updateNumSegment(NumSegment numSegment) throws Exception;

	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteNumSegById(Integer id) throws Exception;
	
	@DataSource("master")
	public List<NumSegment> findNumSegByIds(Integer[] ids) throws Exception;
	
	@DataSource("master")
	public int delNumSegBatch(Integer[] ids) throws Exception;
}
