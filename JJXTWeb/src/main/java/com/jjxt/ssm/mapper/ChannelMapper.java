package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Model;
import com.jjxt.ssm.entity.Variant;

public interface ChannelMapper {

	Channel findChannelByChannelId(Integer channelId);

	List<Channel> findChannelPageList(Map<String, Object> map);

	List<Channel> findEndAddChannel();

	int addChannel(Channel channel);

	Integer findMaxChannelId();

	int findTotal(Map<String, String> map);

	List<Channel> findChannelById(Integer id);

	int updateChannel(Channel channel);

	List<Variant> findVariant();

	List<Model> findModel(Map<String, Object> map);

	List<Channel> findChannelSet(@Param("set")Set<String> channelIdSet);

	List<Channel> findChannelByMap(Map<String, Object> map);

	List<Map<String, Object>> findChannel();

	List<Channel> findChannelByCarrierMap(Map<String, Object> map);

	List<Channel> findNormalChannel();
	
	List<Channel> findChannelAllConfigByChannelId(Integer id);
	
	int findConfirmProvider(Map<String,Object> map);
	
	int updateProductCongifgByChannelId(Map<String,Object> map);
	
	int updateAppResendCongifgByChannelId(Map<String,Object> map);
	
	int updateChannelResendCongifgByChannelId(Map<String,Object> map);
	
	int updateProvincesSendByChannelId(Map<String,Object> map);
	
	int updateModelSendByChannelId(Map<String,Object> map);
	
	int addChannelExt(Channel channel);
	
	int updateChannelExt(Channel channel);
	
	List<Channel> findChannelNameBySort(Map<String, Object> map);
	
	String findStrategyFromTemplate(Map<String, Object> map);
	
	int updateMsgTemplateByChannelId(Map<String, Object> map);
	
	Channel findMainData(Map<String, Object> map);
	
	Channel findAccResendData(Map<String, Object> map);
	
	Channel findChanResendData(Map<String, Object> map);
	
	Channel findProvincesSendData(Map<String, Object> map);
	
	Channel findModelSendData(Map<String, Object> map);
	
	Channel findTemplateData(Map<String, Object> map);

}
