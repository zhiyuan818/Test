package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jjxt.ssm.entity.ChannelTemplate;

public interface ChannelTemplateMapper {
	
	public List<ChannelTemplate> findChannelAppIds();

	public List<ChannelTemplate> findAllList(Map<String, Object> map);
	
	public Integer findTotal(Map<String, Object> map);
	
	public int addChannelTemplate(ChannelTemplate channelTemplate);
	
	public List<ChannelTemplate> findAddEnd();
	
	public List<ChannelTemplate> findChannelTemplateById(Integer id);
	
	public int updateChannelTemplate(ChannelTemplate channelTemplate);
	
	public int deleteChannelTemplate(Integer id);
	
	public List<ChannelTemplate> findChannelTemplateByIds(Integer[] ids);
	
	public int deleteChannelTemplateBatch(Integer[] ids);
	
	public int addChannelTemplateBatch(@Param("list")List<ChannelTemplate> channelTemplate);
	
	public List<ChannelTemplate> findChannelTemplateAll();
	
}
