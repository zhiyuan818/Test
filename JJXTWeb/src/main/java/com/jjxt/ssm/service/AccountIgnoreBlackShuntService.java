package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AccountIgnoreBlackShunt;
import com.jjxt.ssm.utils.DataSource;

public interface AccountIgnoreBlackShuntService {
	
	@DataSource("master")
	public int findTotal(Map<String, String> paramMap) throws Exception;
	@DataSource("master")
	public List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntPageList(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int addAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account) throws Exception;
	@DataSource("master")
	public AccountIgnoreBlackShunt findEndAdd() throws Exception;
	@DataSource("master")
	public AccountIgnoreBlackShunt findAccountIgnoreBlackShuntById(Integer id) throws Exception;
	@DataSource("master")
	public int updateAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account) throws Exception;
	@DataSource("master")
	public int delAccountIgnoreBlackShuntById(Integer id) throws Exception;
	@DataSource("master")
	public List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntByIds(Integer[] ids) throws Exception;
	@DataSource("master")
	public int delAccountIgnoreBlackShuntByIdBatch(Integer[] ids) throws Exception;
	@DataSource("master")
	public List<AccountIgnoreBlackShunt> findShuntLevels() throws Exception;
}
