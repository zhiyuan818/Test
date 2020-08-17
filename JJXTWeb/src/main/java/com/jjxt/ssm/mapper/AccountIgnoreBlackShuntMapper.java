package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.AccountIgnoreBlackShunt;

public interface AccountIgnoreBlackShuntMapper {

	int findTotal(Map<String, String> paramMap);

	List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntPageList(Map<String, Object> map);

	int addAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account);

	AccountIgnoreBlackShunt findEndAdd();

	AccountIgnoreBlackShunt findAccountIgnoreBlackShuntById(Integer id);

	int updateAccountIgnoreBlackShunt(AccountIgnoreBlackShunt account);

	int delAccountIgnoreBlackShuntById(Integer id);

	List<AccountIgnoreBlackShunt> findAccountIgnoreBlackShuntByIds(Integer[] ids);

	int delAccountIgnoreBlackShuntByIdBatch(Integer[] ids);
	
	List<AccountIgnoreBlackShunt> findShuntLevels();

}
