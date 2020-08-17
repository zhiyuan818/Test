package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Template;

public interface TemplateMapper {

	int findTemplateTotal(Map<String, String> map);

	List<Template> findTemplatePageList(Map<String, Object> param);

	List<Template> validatorAppIdAndRule(String appId, String rule);

	List<Template> validatorAppIdAndRule(Map<String, String> map);

	Integer findIdIndex(Map<String, String> map);

	int addTemplate(Template template);

	Template findTemplateById(String id);

	int updateTemplate(Template template);

	int deleteTemplate(String id);
	
	List<Template> findTemplateByIds(Integer[] ids);
	
	int delTemplateBatch(Integer[] ids);
	
	int pauseTemplateBatch(Integer[] ids);
	
	int startTemplateBatch(Integer[] ids);
	
	int startTemplate(Integer id);
	
	int pauseTemplate(Integer id);

}
