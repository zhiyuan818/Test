package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.MO;

public interface MOMapper {

	/* 查询所有上行日志 */
	public List<MO> findMO();

	/* 按条件查询总条数 */
	public int findMOTotal(Map<String, Object> map);

	/* 按条件查询分页列表 */
	public List<MO> findMOPageList(Map<String, Object> map);

	/* 根据id查找下行数据 */
	public MO findMOById(Map<String, Object> map);

	/* 添加二级黑名单 */
	public int addToBlack(BlackLevel2 blackLevel2);

	/* 修改black_flag标记 */
	public int updateFlag(Map<String, Object> map);

	/* 查询二级黑数据 */
	public int selectBlackLevel2(BlackLevel2 blackLevel2);

	/* 批量添加到二级黑 */
	public int addToBlackLevel2Batch(Integer[] ids);

	/* 查询重推上行数量 */
	public Integer findPutMoSum(Map<String, Object> map);

}
