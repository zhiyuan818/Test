package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jjxt.ssm.entity.SignBaobei;

public interface SignBaobeiMapper {

	public List<SignBaobei> findAllList(Map<String, Object> map);
	
	public Integer findTotal(Map<String, Object> map);
	
	public SignBaobei findListById(Integer id);
	
	public int delSignBaobei(Integer id);
	
	public int updateFlagToNo(Integer id);
	
	public int updateFlagToYes(Integer id);

	public int deleteAll();

	public Integer addBatchSignBaobei(@Param("list")List<SignBaobei> signBaobeis);

	public List<SignBaobei> findExistList(Map<String, String> map);

	public void deleteBatchSignBaobei(@Param("ids")String ids);

	public List<SignBaobei> findSignBaobeiAll();
	
	
}
