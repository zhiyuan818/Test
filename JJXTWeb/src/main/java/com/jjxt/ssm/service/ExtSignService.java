package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ExtSign;
import com.jjxt.ssm.entity.SignExtTemplate;
import com.jjxt.ssm.utils.DataSource;

public interface ExtSignService {
	@DataSource("master")
	public List<ExtSign> findAllList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public Integer findTotal(Map<String, String> map) throws Exception;
	@DataSource("master")
	public int addToExtSign(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int delExtSign(Map<String, String> map) throws Exception;
	@DataSource("master")
	public ExtSign findExtSignById(Integer id) throws Exception;
	@DataSource("master")
	public int updateExtSign(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int findAppId(String str) throws Exception;
	@DataSource("master")
	public int existData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int addBatchExtSign(List<List<Object>> listob, Map<String, String> map, Integer[] check, String update) throws Exception;
	@DataSource("master")
	public List<SignExtTemplate> findSignExtBySigns(List<String> list) throws Exception;
	@DataSource("master")
	public Integer findTemplateTotal(Map<String, String> map) throws Exception;
	@DataSource("master")
	public List<SignExtTemplate> findTemplateList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public SignExtTemplate findTemplateById(Integer id) throws Exception;
	@DataSource("master")
	public int addToSignExtTemplate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int isExistTemplate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int updateSignExtTemplate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int delSignExtTemplate(Integer id) throws Exception;
	@DataSource("master")
	public int delTemplateBatch(Integer[] ids) throws Exception;
	
	
}
