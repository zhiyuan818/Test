package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Model;
import com.jjxt.ssm.entity.Variant;
import com.jjxt.ssm.utils.DataSource;

public interface ChannelService {
	@DataSource("master")
	Channel findChannelByChannelId(Integer id) throws Exception;

	@DataSource("master")
	List<Channel> findChannel(Map<String, Object> map) throws Exception;

	@DataSource("master")
	List<Channel> findChannelPageList(Map<String, Object> map) throws Exception;

	@DataSource("master")
	int addChannel(Channel channel) throws Exception;

	@DataSource("master")
	List<Channel> findEndAddChannel() throws Exception;

	@DataSource("master")
	Integer findMaxChannelId() throws Exception;

	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;

	@DataSource("master")
	List<Channel> findChannelById(Integer id) throws Exception;

	@DataSource("master")
	int updateChannel(Channel channel) throws Exception;
	
	@DataSource("master")
	List<Variant> findVariant() throws Exception;
	@DataSource("master")
	List<Model> findModel(Map<String, Object> map) throws Exception;
	@DataSource("master")
	List<Channel> findChannelById(Set<String> channelIdSet) throws Exception;
	@DataSource("master")
	List<Map<String, Object>> findChannel() throws Exception;
	@DataSource("master")
	List<Channel> findNormalChannel() throws Exception;
	@DataSource("master")
	List<Channel> findChannelAllConfigByChannelId(Integer id) throws Exception;
	@DataSource("master")
	int findConfirmProvider(Map<String,Object> map) throws Exception;
	@DataSource("master")
	int updateProductCongifgByChannelId(Map<String,Object> map) throws Exception;
	@DataSource("master")
	int updateAppResendCongifgByChannelId(Map<String,Object> map) throws Exception;
	@DataSource("master")
	int updateChannelResendCongifgByChannelId(Map<String,Object> map) throws Exception;
	@DataSource("master")
	int updateProvincesSendByChannelId(Map<String,Object> map) throws Exception;
	@DataSource("master")
	int updateModelSendByChannelId(Map<String,Object> map) throws Exception;
	@DataSource("master")
	List<Channel> findChannelNameBySort(Map<String, Object> map) throws Exception;
	@DataSource("master")
	String findStrategyFromTemplate(Map<String, Object> map) throws Exception;
	@DataSource("master")
	int updateMsgTemplateByChannelId(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findMainData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findAccResendData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findChanResendData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findProvincesSendData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findModelSendData(Map<String, Object> map) throws Exception;
	@DataSource("master")
	Channel findTemplateData(Map<String, Object> map) throws Exception;
	
}
