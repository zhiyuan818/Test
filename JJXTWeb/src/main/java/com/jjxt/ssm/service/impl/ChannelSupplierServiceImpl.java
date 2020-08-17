package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.BaseChannelSupplier;
import com.jjxt.ssm.mapper.ChannelSupplierMapper;
import com.jjxt.ssm.service.ChannelSupplierService;

@Service("channelSupplierService")
public class ChannelSupplierServiceImpl implements ChannelSupplierService {
	@Autowired
	private ChannelSupplierMapper channelSupplierMapper;
	
	
	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		return channelSupplierMapper.findTotalByCondition(paramMap);
	}
	
	@Override
	public List<BaseChannelSupplier> findChannelSupplierPageList(Map<String, Object> paramMap) throws Exception {
		return channelSupplierMapper.findChannelSupplierPageList(paramMap);
	}
	
	@Override
	public Integer addChannelSupplier(BaseChannelSupplier baseChannelSupplier) throws Exception {
		return channelSupplierMapper.addChannelSupplier(baseChannelSupplier);
	}
	
	@Override
	public BaseChannelSupplier findChannelSupplierById(Integer id) throws Exception {
		return channelSupplierMapper.findChannelSupplierById(id);
	}
	
	@Override
	public int updateChannelSupplier(BaseChannelSupplier baseChannelSupplier) throws Exception {
		return channelSupplierMapper.updateChannelSupplier(baseChannelSupplier);
	}
	
	@Override
	public int findTotalByName(Map<String, String> paramMap) throws Exception {
		return channelSupplierMapper.findTotalByName(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> findLinkChannel(Integer id) throws Exception {
		return channelSupplierMapper.findLinkChannel(id);
	}

	@Override
	public int delChannelSupplierById(Integer id) throws Exception {
		return channelSupplierMapper.delChannelSupplierById(id);
	}

	@Override
	public List<BaseChannelSupplier> findChannelSupplierByIds(Integer[] ids) throws Exception {
		return channelSupplierMapper.findChannelSupplierByIds(ids);
	}

	@Override
	public int delChannelSupplierBatch(Integer[] ids) throws Exception {
		return channelSupplierMapper.delChannelSupplierBatch(ids);
	}

	@Override
	public List<BaseChannelSupplier> findAllChannelSupplier() throws Exception {
		return channelSupplierMapper.findAllChannelSupplier();
	}

	@Override
	public List<BaseChannelSupplier> findAllSupplierKey() throws Exception {
		return channelSupplierMapper.findAllSupplierKey();
	}

	@Override
	public List<BaseChannelSupplier> findAllHead() throws Exception {
		return channelSupplierMapper.findAllHead();
	}

	@Override
	public List<BaseChannelSupplier> findSupplierKeyBySort(Map<String, Object> paramMap) throws Exception {
		return channelSupplierMapper.findSupplierKeyBySort(paramMap);
	}

	@Override
	public List<BaseChannelSupplier> findHeadBySort(Map<String, Object> paramMap) throws Exception {
		return channelSupplierMapper.findHeadBySort(paramMap);
	}

}
