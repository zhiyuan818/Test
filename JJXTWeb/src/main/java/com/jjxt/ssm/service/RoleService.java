package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;


import com.jjxt.ssm.entity.Role;
import com.jjxt.ssm.utils.DataSource;

public interface RoleService {

	
	/* 查询全部 用户*/
	@DataSource("master")
	public List<Role> findAllManager() throws Exception;
	
	/* 查询全部 角色*/
	@DataSource("master")
	public List<Role> findAllRole() throws Exception;
	
	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;
	
	@DataSource("master")
	List<Role> findRolePageList(Map<String, Object> map) throws Exception;
	
	
	@DataSource("master")
	int addRoleRelation(Role role) throws Exception;
	
	@DataSource("master")
	List<Role> findEndAddRoleRelation() throws Exception;
	
	@DataSource("master")
	List<Role> findRoleRelationById(int id) throws Exception;
	
	@DataSource("master")
	int updateRoleRelation(Role role) throws Exception;
	
	@DataSource("master")
	int deleteRoleRelation(int id) throws Exception;
	
	
}
