package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.RandomExt;
import com.jjxt.ssm.utils.DataSource;

public interface RandomExtService {
	
	/* 查询总数 */
	@DataSource("master")
	int findRandomExtTotal(Map<String, Object> paramMap) throws Exception;
	
	/* 分页查询list */
	@DataSource("master")
	public List<RandomExt> findRandomExtPageList(Map<String, Object> paramMap) throws Exception;

	/* 通过id查询数据 */
	@DataSource("master")
	public RandomExt findRandomExtById(Integer id) throws Exception;

	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteRandomExtById(Integer id) throws Exception;
	
	@DataSource("master")
	public List<RandomExt> findRandomExtByIds(Integer[] ids) throws Exception;
	
	@DataSource("master")
	public int delRandomExtBatch(Integer[] ids) throws Exception;
}
