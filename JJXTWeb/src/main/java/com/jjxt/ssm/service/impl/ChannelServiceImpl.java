package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Model;
import com.jjxt.ssm.entity.Variant;
import com.jjxt.ssm.mapper.ChannelMapper;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.utils.StringUtil;

@Service("channelService")
@Transactional
public class ChannelServiceImpl implements ChannelService {

	@Autowired
	private ChannelMapper channelMapper;

	@Override
	public Channel findChannelByChannelId(Integer id) throws Exception {
		return channelMapper.findChannelByChannelId(id);
	}

	@Override
	public List<Channel> findChannel(Map<String, Object> map) throws Exception {
		return channelMapper.findChannelByMap(map);
	}

	@Override
	public List<Channel> findChannelPageList(Map<String, Object> map) throws Exception {
		return channelMapper.findChannelPageList(map);
	}

	@Override
	public int addChannel(Channel channel) throws Exception {
		int result = channelMapper.addChannel(channel);
		if(!StringUtil.isEmpty(channel.getSupplierId())) {
			channelMapper.addChannelExt(channel);
		}
		return result;
	}

	@Override
	public List<Channel> findEndAddChannel() throws Exception {
		return channelMapper.findEndAddChannel();
	}

	@Override
	public Integer findMaxChannelId() throws Exception {
		return channelMapper.findMaxChannelId();
	}

	@Override
	public int findTotal(Map<String, String> map) throws Exception {
		return channelMapper.findTotal(map);
	}

	@Override
	public List<Channel> findChannelById(Integer id) throws Exception {
		return channelMapper.findChannelById(id);
	}

	@Override
	public int updateChannel(Channel channel) throws Exception {
		int result = channelMapper.updateChannel(channel);
		if(!StringUtil.isEmpty(channel.getSupplierId())) {
			channelMapper.updateChannelExt(channel);
		}
		return result;
	}

	@Override
	public List<Variant> findVariant() throws Exception {
		return channelMapper.findVariant();
	}

	@Override
	public List<Model> findModel(Map<String, Object> map) throws Exception {
		return channelMapper.findModel(map);
	}

	@Override
	public List<Channel> findChannelById(Set<String> channelIdSet) throws Exception {
		return channelMapper.findChannelSet(channelIdSet);
	}

	@Override
	public List<Map<String, Object>> findChannel() throws Exception {
		return channelMapper.findChannel();
	}

	@Override
	public List<Channel> findNormalChannel() throws Exception {
		return channelMapper.findNormalChannel();
	}
	
	@Override
	public List<Channel> findChannelAllConfigByChannelId(Integer id) throws Exception {
		return channelMapper.findChannelAllConfigByChannelId(id);
	}
	
	@Override
	public int findConfirmProvider(Map<String,Object> map) throws Exception {
		return channelMapper.findConfirmProvider(map);
	}
	
	@Override
	public int updateProductCongifgByChannelId(Map<String,Object> map) throws Exception {
		return channelMapper.updateProductCongifgByChannelId(map);
	}
	
	@Override
	public int updateAppResendCongifgByChannelId(Map<String,Object> map) throws Exception {
		return channelMapper.updateAppResendCongifgByChannelId(map);
	}
	
	@Override
	public int updateChannelResendCongifgByChannelId(Map<String,Object> map) throws Exception {
		return channelMapper.updateChannelResendCongifgByChannelId(map);
	}
	
	@Override
	public int updateProvincesSendByChannelId(Map<String,Object> map) throws Exception {
		return channelMapper.updateProvincesSendByChannelId(map);
	}
	
	@Override
	public int updateModelSendByChannelId(Map<String,Object> map) throws Exception {
		return channelMapper.updateModelSendByChannelId(map);
	}

	@Override
	public List<Channel> findChannelNameBySort(Map<String, Object> map) throws Exception {
		return channelMapper.findChannelNameBySort(map);
	}

	@Override
	public String findStrategyFromTemplate(Map<String, Object> map) throws Exception {
		return channelMapper.findStrategyFromTemplate(map);
	}

	@Override
	public int updateMsgTemplateByChannelId(Map<String, Object> map) throws Exception {
		return channelMapper.updateMsgTemplateByChannelId(map);
	}

	@Override
	public Channel findMainData(Map<String, Object> map) throws Exception {
		return channelMapper.findMainData(map);
	}

	@Override
	public Channel findAccResendData(Map<String, Object> map) throws Exception {
		return channelMapper.findAccResendData(map);
	}

	@Override
	public Channel findChanResendData(Map<String, Object> map) throws Exception {
		return channelMapper.findChanResendData(map);
	}

	@Override
	public Channel findProvincesSendData(Map<String, Object> map) throws Exception {
		return channelMapper.findProvincesSendData(map);
	}

	@Override
	public Channel findModelSendData(Map<String, Object> map) throws Exception {
		return channelMapper.findModelSendData(map);
	}

	@Override
	public Channel findTemplateData(Map<String, Object> map) throws Exception {
		return channelMapper.findTemplateData(map);
	}
}
