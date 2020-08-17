package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.utils.DataSource;

public interface BaseCompanyService {
	// 查询
	@DataSource("master")
	public List<BaseCompany> findAllCompany() throws Exception;
	// 分页查询
	@DataSource("master")
	public List<BaseCompany> findCustomerPageList(Map<String, Object> paramMap) throws Exception;
	// 新增
	@DataSource("master")
	public Integer addBaseCompany(BaseCompany baseCompany) throws Exception;
	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;
	@DataSource("master")
	int findTotalByName(Map<String,String> paramMap) throws Exception;
	@DataSource("master")
	public BaseCompany findCustomerById(Integer id) throws Exception;
	@DataSource("master")
	public int updateCustomer(BaseCompany company) throws Exception;
	@DataSource("master")
	public List<Map<String, Object>> findLinkAcc(Integer id) throws Exception;
	@DataSource("master")
	public BaseCompany findCompanyDetails(String appId) throws Exception;
	@DataSource("master")
	public BaseCompany findCustomerByAppId(int parseInt) throws Exception;
	@DataSource("master")
	public List<Map<String, String>> findCompanyMapByAppId(Set<String> addIdList) throws Exception;
	
	@DataSource("master")
	public List<BaseCompany> findAllCompanyKey() throws Exception;
	@DataSource("master")
	public List<BaseCompany> findAllSales() throws Exception;
	@DataSource("master")
	public List<BaseCompany> findAllSaleAfter() throws Exception;
	
	@DataSource("master")
	public List<BaseCompany> findCompanyKeyBySort(Map<String,Object> map) throws Exception;
	@DataSource("master")
	public List<BaseCompany> findSalesBySort(Map<String,Object> map) throws Exception;
	@DataSource("master")
	public List<BaseCompany> findSaleAfterBySort(Map<String,Object> map) throws Exception;
}
