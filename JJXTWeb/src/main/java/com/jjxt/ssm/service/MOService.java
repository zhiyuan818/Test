package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.MO;
import com.jjxt.ssm.utils.DataSource;

public interface MOService {

	/* 查询所有上行日志 */
	@DataSource("slave")
	public List<MO> findMO() throws Exception;

	/* 按条件查询总条数 */
	@DataSource("slave")
	public int findMOTotal(Map<String, Object> map) throws Exception;

	/* 按条件查询分页列表 */
	@DataSource("slave")
	public List<MO> findMOPageList(Map<String, Object> map) throws Exception;

	/* 根据id查找MO */
	@DataSource("slave")
	public MO findMOById(Map<String, Object> map) throws Exception;

	/* 添加二级黑名单 */
	@DataSource("master")
	public int addToBlack(BlackLevel2 blackLevel2) throws Exception;

	/* 修改black_flag标记 */
	@DataSource("master")
	public int updateFlag(Map<String, Object> map) throws Exception;

	/* 查询二级黑数据 */
	@DataSource("master")
	public int selectBlackLevel2(BlackLevel2 blackLevel2) throws Exception;

	/* 批量添加到二级黑 */
	@DataSource("master")
	public int addToBlackLevel2Batch(Integer[] ids) throws Exception;

	@DataSource("slave")
	public Integer findPutMoSum(Map<String, Object> map) throws Exception;

}
