package com.jjxt.ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.DataFinance;
import com.jjxt.ssm.mapper.ApplicationMapper;
import com.jjxt.ssm.mapper.DataFinanceMapper;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ComplainService;
import com.jjxt.ssm.service.ExtSignService;
import com.jjxt.ssm.service.MoDispatchService;
import com.jjxt.ssm.utils.StringUtil;
@Service("applicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService{
	
	@Autowired
	private ApplicationMapper applicationMapper;
	@Autowired
	private DataFinanceMapper dataFinanceMapper;
	@Autowired
	private ExtSignService extsignService;
	@Autowired
	private MoDispatchService moDispatchService;
	@Autowired
	private ComplainService complainService;
	@Override
	public Application findApplicationById(Integer id) throws Exception {
		return applicationMapper.findApplicationById(id);
	}
	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		return applicationMapper.findTotalByCondition(paramMap);
	}
	
	@Override
	public List<Application> findAccoutPageList(Map<String, Object> paramMap) throws Exception {
		return applicationMapper.findAccoutPageList(paramMap);
	}
	
	@Override
	public int updateAccount(Application account) throws Exception {
		Map<String, String> map=new HashMap<>();
		map.put("appId", account.getId().toString());
		if("deleted".equals(account.getAppStatus())){
			int delExtSign = extsignService.delExtSign(map);
			int deleteDispatch = moDispatchService.deleteDispatch(map);
			int deleteComplain = complainService.deleteComplain(map);
		}
		int result =  applicationMapper.updateAccount(account);
		int updateAccountExt = applicationMapper.updateAccountExt(account);
		if(!StringUtil.isEmpty(account.getCompanyId())){
			int updateSubAccountCompany = applicationMapper.updateSubAccountCompany(account);
		}
		return result;
	}
	@Override
	public int addAccount(Application account) throws Exception {
		int result = applicationMapper.addAccount(account);
		applicationMapper.addAccountExt(account);
		return result;
	}
	@Override
	public List<Application> findAppName() throws Exception {
		return applicationMapper.findAppName();
	}
	@Override
	public int findAppByName(String name) throws Exception {
		return applicationMapper.findAppByName(name);
	}
	@Override
	public List<Application> findAllParent() throws Exception {
		return applicationMapper.findAllParent();
	}
	@Override
	public String findCompanyNameById(int id) throws Exception {
		return applicationMapper.findCompanyNameById(id);
	}
	
	@Override
	public int chargeAccount(DataFinance dataFinance) throws Exception {
		Application app = new Application();
		app.setId(dataFinance.getAppId());
		app.setPayCount(dataFinance.getChangeNum());
		int appRes = applicationMapper.chargeAccount(app);
		dataFinanceMapper.addDataFinance(dataFinance);
		return appRes;
	}
	@Override
	public Application findEndAdd() throws Exception {
		
		return applicationMapper.findEndAdd();
	}
	@Override
	public List<Application> findChildByParentId(int id) throws Exception {
		return applicationMapper.findChildByParentId(id);
	}
	@Override
	public List<Application> findAccountSub(Integer id) throws Exception {
		
		return applicationMapper.findAccountSub(id);
	}
	
	@Override
	public int chargeAccountShuntPolicies(Map<String, Object> paramMap) throws Exception {
		
		return applicationMapper.chargeAccountShuntPolicies(paramMap);
	}
	@Override
	public List<Application> findAccountById(Set<String> appIdSet) throws Exception {
		return applicationMapper.findAccountSet(appIdSet);
	}
	@Override
	public Map<String, Object> findDetailByName(String appId) throws Exception {
		return applicationMapper.findDetailByName(appId);
	}
	@Override
	public List<Map<String, Object>> findAccount() throws Exception {
		return applicationMapper.findAccount();
	}
	@Override
	public Set<String> findAppIdByCompanyId(String companyId) throws Exception {
		
		return applicationMapper.findAppIdByCompanyId(companyId);
	}
	
	@Override
	public List<Application> findAppNameBySort(Map<String, Object> map) throws Exception {
		return applicationMapper.findAppNameBySort(map);
	}
	
}
