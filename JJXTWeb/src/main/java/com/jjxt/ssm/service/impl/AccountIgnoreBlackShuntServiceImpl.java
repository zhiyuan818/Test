package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.entity.AccountIgnoreBlackShunt;
import com.jjxt.ssm.mapper.AccountIgnoreBlackShuntMapper;
import com.jjxt.ssm.service.AccountIgnoreBlackShuntService;

@Service("accountIgnoreBlackShuntService")
public class AccountIgnoreBlackShuntServiceImpl implements AccountIgnoreBlackShuntService {
	
	@Autowired
	private AccountIgnoreBlackShuntMapper accountIgnoreBlackShuntMapper;
	
	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findTotal(paramMap);
	}

	@Override
	public List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntPageList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findAccountIgnoreBlackShuntPageList(map);
	}

	@Override
	public int addAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.addAccountIgnoreBlackShunt(account);
	}

	@Override
	public AccountIgnoreBlackShunt findEndAdd() throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findEndAdd();
	}

	@Override
	public AccountIgnoreBlackShunt findAccountIgnoreBlackShuntById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findAccountIgnoreBlackShuntById(id);
	}

	@Override
	public int updateAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.updateAccountIgnoreBlackShunt(account);
	}

	@Override
	public int delAccountIgnoreBlackShuntById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.delAccountIgnoreBlackShuntById(id);
	}

	@Override
	public List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntByIds(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findAccountIgnoreBlackShuntByIds(ids);
	}

	@Override
	public int delAccountIgnoreBlackShuntByIdBatch(Integer[] ids) throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.delAccountIgnoreBlackShuntByIdBatch(ids);
	}

	@Override
	public List<AccountIgnoreBlackShunt> findShuntLevels() throws Exception {
		// TODO Auto-generated method stub
		return accountIgnoreBlackShuntMapper.findShuntLevels();
	}

}
