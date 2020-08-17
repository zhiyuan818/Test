package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.BaseChannelSupplier;
import com.jjxt.ssm.utils.DataSource;

public interface ChannelSupplierService {
	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findChannelSupplierPageList(Map<String, Object> paramMap) throws Exception;
	
	@DataSource("master")
	public Integer addChannelSupplier(BaseChannelSupplier baseChannelSupplier) throws Exception;
	
	@DataSource("master")
	public BaseChannelSupplier findChannelSupplierById(Integer id) throws Exception;
	
	@DataSource("master")
	public int updateChannelSupplier(BaseChannelSupplier baseChannelSupplier) throws Exception;
	
	@DataSource("master")
	public int findTotalByName(Map<String, String> paramMap) throws Exception;
	
	@DataSource("master")
	public List<Map<String, Object>> findLinkChannel(Integer id) throws Exception;
	
	@DataSource("master")
	public int delChannelSupplierById(Integer id) throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findChannelSupplierByIds(Integer[] ids) throws Exception;
	
	@DataSource("master")
	public int delChannelSupplierBatch(Integer[] ids) throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findAllChannelSupplier() throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findAllSupplierKey() throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findAllHead() throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findSupplierKeyBySort(Map<String, Object> paramMap) throws Exception;
	
	@DataSource("master")
	public List<BaseChannelSupplier> findHeadBySort(Map<String, Object> paramMap) throws Exception;
	
}
