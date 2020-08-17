package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.RandomExt;

public interface RandomExtMapper {

	/* 查询总数 */
	int findRandomExtTotal(Map<String, Object> paramMap);
	
	/* 分页查询list */
	public List<RandomExt> findRandomExtPageList(Map<String, Object> paramMap);

	/* 通过id查询数据 */
	public RandomExt findRandomExtById(Integer id);

	/* 通过id删除数据 */
	public int deleteRandomExtById(Integer id);
	
	List<RandomExt> findRandomExtByIds(Integer[] ids);
	
	int delRandomExtBatch(Integer[] ids);
	
}
