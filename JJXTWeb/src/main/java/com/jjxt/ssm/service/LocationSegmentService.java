package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LocationSegment;
import com.jjxt.ssm.utils.DataSource;

public interface LocationSegmentService {
	
	/* 查询总数 */
	@DataSource("master")
	int findLocationSegmentTotal(Map<String, Object> paramMap) throws Exception;
	
	/* 分页查询list */
	@DataSource("master")
	public List<LocationSegment> findLocationSegmentList(Map<String, Object> paramMap) throws Exception;

	/* 判断号段是否存在 */
	@DataSource("master")
	public int findLocationSegmentByCondition(Map<String, Object> paramMap) throws Exception;
	
	/* 添加数据 */
	@DataSource("master")
	public int addLocationSegment(LocationSegment locationSegment) throws Exception;
	
	/* 通过id查询数据 */
	@DataSource("master")
	public LocationSegment findLocationSegmentById(Integer id) throws Exception;

	/* 修改配置 */
	@DataSource("master")
	public int updateLocationSegment(LocationSegment locationSegment) throws Exception;

	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteLocationSegById(Integer id) throws Exception;
	
	@DataSource("master")
	public List<LocationSegment> findLocationSegByIds(Integer[] ids) throws Exception;
	
	@DataSource("master")
	public int delLocationSegBatch(Integer[] ids) throws Exception;
}
