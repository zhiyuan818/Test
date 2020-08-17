package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.LocationSegment;

public interface LocationSegmentMapper {

	/* 查询总数 */
	int findLocationSegmentTotal(Map<String, Object> paramMap);
	
	/* 分页查询list */
	public List<LocationSegment> findLocationSegmentList(Map<String, Object> paramMap);

	/* 判断号段是否存在 */
	public int findLocationSegmentByCondition(Map<String, Object> paramMap);
	
	/* 添加数据 */
	public int addLocationSegment(LocationSegment locationSegment);
	
	/* 通过id查询数据 */
	public LocationSegment findLocationSegmentById(Integer id);

	/* 修改配置 */
	public int updateLocationSegment(LocationSegment locationSegment);

	/* 通过id删除数据 */
	public int deleteLocationSegById(Integer id);
	
	List<LocationSegment> findLocationSegByIds(Integer[] ids);
	
	int delLocationSegBatch(Integer[] ids);
	
}
