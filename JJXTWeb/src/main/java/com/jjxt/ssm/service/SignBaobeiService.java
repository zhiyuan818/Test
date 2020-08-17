package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.SignBaobei;
import com.jjxt.ssm.utils.DataSource;

public interface SignBaobeiService {
	@DataSource("master")
	public List<SignBaobei> findAllList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Integer findTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public SignBaobei findListById(Integer id) throws Exception;
	@DataSource("master")
	public int delSignBaobei(Integer id) throws Exception;
	@DataSource("master")
	public int updateFlagToNo(Integer id) throws Exception;
	@DataSource("master")
	public int updateFlagToYes(Integer id)throws Exception;
	@DataSource("master")
	public int addBatchSignBaoBei(List<List<Object>> listob, Map<String,String> sp, Integer[] check, String update, String ext,
			String baobeiFlag,String channel) throws Exception;
	@DataSource("master")
	public List<SignBaobei> findSignBaobeiAll() throws Exception ;
	
}
