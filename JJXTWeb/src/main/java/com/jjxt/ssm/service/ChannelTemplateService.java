package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ChannelTemplate;
import com.jjxt.ssm.utils.DataSource;

public interface ChannelTemplateService {
	@DataSource("master")
	List<ChannelTemplate> findChannelAppIds() throws Exception;
	@DataSource("master")
	List<ChannelTemplate> findAllList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Integer findTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	int addChannelTemplate(ChannelTemplate channelTemplate) throws Exception;
	@DataSource("master")
	List<ChannelTemplate> findAddEnd() throws Exception;
	@DataSource("master")
	List<ChannelTemplate> findChannelTemplateById(Integer id) throws Exception;
	@DataSource("master")
	int updateChannelTemplate(ChannelTemplate channelTemplate) throws Exception;
	@DataSource("master")
	int deleteChannelTemplate(Integer id) throws Exception;
	@DataSource("master")
	List<ChannelTemplate> findChannelTemplateByIds(Integer[] ids) throws Exception;
	@DataSource("master")
	int deleteChannelTemplateBatch(Integer[] ids) throws Exception;
	@DataSource("master")
	int addChannelTemplateBatch(List<List<Object>> listob, Map<String,String> sp, Integer[] check, String channelId) throws Exception;
	@DataSource("master")
	List<ChannelTemplate> findChannelTemplateAll() throws Exception ;
	
}
