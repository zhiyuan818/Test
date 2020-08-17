package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.utils.DataSource;

public interface TemplateService {
	@DataSource("master")
	int findTemplateTotal(Map<String, String> map) throws Exception;
	@DataSource("master")
	List<Template> findTemplatePageList(Map<String, Object> param) throws Exception;
	@DataSource("master")
	List<Template> validatorAppIdAndRule(Map<String, String> map) throws Exception;
	@DataSource("master")
	Integer findIdIndex(Map<String, String> map) throws Exception;
	@DataSource("master")
	int addTemplate(Template template) throws Exception;
	@DataSource("master")
	Template findTemplateById(String id) throws Exception;
	@DataSource("master")
	int updateTemplate(Template template) throws Exception;
	@DataSource("master")
	int deleteTemplate(String id) throws Exception;
	@DataSource("master")
	List<Template> findTemplateByIds(Integer[] ids) throws Exception;
	@DataSource("master")	
	int delTemplateBatch(Integer[] ids) throws Exception;
	@DataSource("master")	
	int pauseTemplateBatch(Integer[] ids) throws Exception;	
	@DataSource("master")	
	int startTemplateBatch(Integer[] ids) throws Exception;	
	@DataSource("master")	
	int startTemplate(Integer id) throws Exception;	
	@DataSource("master")	
	int pauseTemplate(Integer id) throws Exception;

}
