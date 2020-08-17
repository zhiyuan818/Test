package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.DataFinance;
import com.jjxt.ssm.utils.DataSource;

public interface ApplicationService {
	@DataSource("master")
	Application findApplicationById(Integer id) throws Exception;

	@DataSource("master")
	int addAccount(Application account) throws Exception;

	@DataSource("master")
	int updateAccount(Application account) throws Exception;

	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;

	@DataSource("master")
	List<Application> findAccoutPageList(Map<String, Object> paramMap) throws Exception;

	@DataSource("master")
	List<Application> findAppName() throws Exception;

	/* 查找账户名是否存在 */
	@DataSource("master")
	int findAppByName(String name) throws Exception;

	@DataSource("master")
	List<Application> findAllParent() throws Exception;

	@DataSource("master")
	String findCompanyNameById(int id) throws Exception;

	@DataSource("master")
	int chargeAccount(DataFinance dataFinance) throws Exception;

	@DataSource("master")
	Application findEndAdd() throws Exception;
	
	//根据主账号,查找其下的子账户
	@DataSource("master")
	List<Application> findChildByParentId(int id) throws Exception;
	
	@DataSource("master")
	List<Application> findAccountSub(Integer id) throws Exception;
	
	@DataSource("master")
	int chargeAccountShuntPolicies(Map<String, Object> paramMap) throws Exception;
	
	@DataSource("master")
	List<Application> findAccountById(Set<String> appIdSet) throws Exception;
	
	@DataSource("master")
	Map<String, Object> findDetailByName(String appId) throws Exception;
	
	@DataSource("master")
	List<Map<String, Object>> findAccount() throws Exception;
	
	@DataSource("master")
	Set<String> findAppIdByCompanyId(String companyId) throws Exception;
	
	@DataSource("master")
	List<Application> findAppNameBySort(Map<String, Object> map) throws Exception;

}
