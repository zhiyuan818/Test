package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BaseChannelSupplier;

public interface ChannelSupplierMapper {
	
	int findTotalByCondition(Map<String, String> paramMap);
	
	public List<BaseChannelSupplier> findChannelSupplierPageList(Map<String, Object> paramMap);
	
	public Integer addChannelSupplier(BaseChannelSupplier baseChannelSupplier);
	
	public BaseChannelSupplier findChannelSupplierById(Integer id);
	
	public int updateChannelSupplier(BaseChannelSupplier baseChannelSupplier);
	
	public int findTotalByName(Map<String, String> paramMap);
	
	public List<Map<String, Object>> findLinkChannel(Integer id);
	
	public int delChannelSupplierById(Integer id);
	
	public List<BaseChannelSupplier> findChannelSupplierByIds(Integer[] ids);
	
	public int delChannelSupplierBatch(Integer[] ids);
	
	public List<BaseChannelSupplier> findAllChannelSupplier();
	
	public List<BaseChannelSupplier> findAllSupplierKey();
	
	public List<BaseChannelSupplier> findAllHead();
	
	public List<BaseChannelSupplier> findSupplierKeyBySort(Map<String, Object> paramMap);
	
	public List<BaseChannelSupplier> findHeadBySort(Map<String, Object> paramMap);
	

}
