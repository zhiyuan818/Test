package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ChannelComplain;
import com.jjxt.ssm.utils.DataSource;

public interface ChannelComplainService {
	@DataSource("master")
	public List<ChannelComplain> findAllLists(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<ChannelComplain> findListByChannelId(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Integer> findListChannelId() throws Exception;

}
