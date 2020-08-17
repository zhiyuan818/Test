package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ExtSign;
import com.jjxt.ssm.entity.SignExtTemplate;

public interface ExtSignMapper {

	public List<ExtSign> findAllList(Map<String, Object> map);
	
	public Integer findTotal(Map<String, String> map);
	
	public int addToExtSign(Map<String, Object> map);
	
	public int delExtSign(Map<String, String> map);
	
	public ExtSign findExtSignById(Integer id);
	
	public int updateExtSign(Map<String, Object> map);
	//根据账户名查询id
	public int findAppId(String str);
	//判断数据是否存在
	public int existData(Map<String, Object> map);

	public List<ExtSign> findExistList(Map<String, String> map);

	public void deleteBatchExtSign(String substring);

	public int addBatchExtSign(List<ExtSign> signBaobeis);

	public int deleteAll();
	
	
	List<SignExtTemplate> findSignExtBySigns(List<String> list);
	
	Integer findTemplateTotal(Map<String, String> map);
	
	List<SignExtTemplate> findTemplateList(Map<String, Object> map);
	
	SignExtTemplate findTemplateById(Integer id);
	
	int addToSignExtTemplate(Map<String, Object> map);
	
	int isExistTemplate(Map<String, Object> map);
	
	int updateSignExtTemplate(Map<String, Object> map);
	
	int delSignExtTemplate(Integer id);
	
	int delTemplateBatch(Integer[] ids);
	
}
