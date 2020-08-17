package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.jjxt.ssm.entity.Application;

public interface ApplicationMapper {

	Application findApplicationById(Integer id);
	
	int addAccount(Application account);
	
	int addAccountExt(Application account);
	
	int deleteAccountById(Integer id);
	
	int updateAccount(Application account);
	
	int updateAccountExt(Application account);
	
	int findTotalByCondition(Map<String,String> paramMap);
	
	List<Application> findAccoutPageList(Map<String, Object> paramMap);

	List<Application> findAppName();
	/*查找账户名是否存在*/
	int findAppByName(String name);
	
	List<Application> findAllParent();

	String findCompanyNameById(int id);
	
	int chargeAccount(Application account);

	Application findEndAdd();
	
	List<Application> findChildByParentId(int id);

	List<Application> findAccountSub(Integer id);

	int updateSubAccountCompany(Application account);
	
	int chargeAccountShuntPolicies(Map<String, Object> paramMap);

	List<Application> findAccountSet(@Param("set")Set<String> appIdSet);
	
	Map<String, Object> findDetailByName(String appId);

	List<Map<String, Object>> findAccount();

	Set<String> findAppIdByCompanyId(String companyId);
	
	List<Application> findAppNameBySort(Map<String, Object> map);
}
