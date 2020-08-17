package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.NumSegment;

public interface NumSegmentMapper {

	/* 查询总数 */
	int findNumSegmentTotal(Map<String, Object> paramMap);
	
	/* 分页查询list */
	public List<NumSegment> findNumSegmentList(Map<String, Object> paramMap);

	/* 判断号段是否存在 */
	public int findNumSegmentByCondition(Map<String, Object> paramMap);
	
	/* 添加数据 */
	public int addNumSegment(NumSegment numSegment);
	
	/* 通过id查询数据 */
	public NumSegment findNumSegmentById(Integer id);

	/* 修改配置 */
	public int updateNumSegment(NumSegment numSegment);

	/* 通过id删除数据 */
	public int deleteNumSegById(Integer id);
	
	public List<NumSegment> findNumSegByIds(Integer[] ids);
	
	public int delNumSegBatch(Integer[] ids);

}
