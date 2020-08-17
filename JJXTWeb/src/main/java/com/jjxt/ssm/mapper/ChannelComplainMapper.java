package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ChannelComplain;

public interface ChannelComplainMapper {

	public List<ChannelComplain> findAllLists(Map<String, Object> map);
	
	public List<ChannelComplain> findListByChannelId(Map<String, Object> map);
	
	public List<Integer> findListChannelId();
}
