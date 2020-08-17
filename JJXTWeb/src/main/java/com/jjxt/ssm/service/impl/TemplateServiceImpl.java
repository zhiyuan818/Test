package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.mapper.TemplateMapper;
import com.jjxt.ssm.service.TemplateService;
@Service("/templateService")
public class TemplateServiceImpl implements TemplateService{
	
	@Autowired
	private TemplateMapper templateMapper;
	@Override
	public int findTemplateTotal(Map<String, String> map) {
		return templateMapper.findTemplateTotal(map);
	}
	@Override
	public List<Template> findTemplatePageList(Map<String, Object> param) throws Exception {
		return templateMapper.findTemplatePageList(param);
	}
	@Override
	public List<Template> validatorAppIdAndRule(Map<String, String> map) throws Exception {
		return templateMapper.validatorAppIdAndRule(map);
	}
	@Override
	public Integer findIdIndex(Map<String, String> map) throws Exception {
		return templateMapper.findIdIndex(map);
	}
	@Override
	public int addTemplate(Template template) throws Exception {
		return templateMapper.addTemplate(template);
	}
	@Override
	public Template findTemplateById(String id) throws Exception {
		return templateMapper.findTemplateById(id);
	}
	@Override
	public int updateTemplate(Template template) throws Exception {
		return templateMapper.updateTemplate(template);
	}
	@Override
	public int deleteTemplate(String id) throws Exception {
		return templateMapper.deleteTemplate(id);
	}
	@Override
	public List<Template> findTemplateByIds(Integer[] ids) throws Exception {
		return templateMapper.findTemplateByIds(ids);
	}
	@Override
	public int delTemplateBatch(Integer[] ids) throws Exception {
		return templateMapper.delTemplateBatch(ids);
	}
	@Override
	public int pauseTemplateBatch(Integer[] ids) throws Exception {
		return templateMapper.pauseTemplateBatch(ids);
	}
	@Override
	public int startTemplateBatch(Integer[] ids) throws Exception {
		return templateMapper.startTemplateBatch(ids);
	}
	@Override
	public int startTemplate(Integer id) throws Exception {
		return templateMapper.startTemplate(id);
	}
	@Override
	public int pauseTemplate(Integer id) throws Exception {
		return templateMapper.pauseTemplate(id);
	}

}
