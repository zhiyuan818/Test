package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Role;
import com.jjxt.ssm.mapper.RoleMapper;
import com.jjxt.ssm.service.RoleService;




@Service("RoleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	@Override
	public List<Role> findAllManager() throws Exception {
		return roleMapper.findAllManager();
	}
	
	@Override
	public List<Role> findAllRole() throws Exception {
		return roleMapper.findAllRole();
	}

	@Override
	public int findTotal(Map<String, String> map) throws Exception {
		return roleMapper.findTotal(map);
	}

	@Override
	public List<Role> findRolePageList(Map<String, Object> map) throws Exception {
		return roleMapper.findRolePageList(map);
	}

	@Override
	public int addRoleRelation(Role role) throws Exception {
		return roleMapper.addRoleRelation(role);
	}

	@Override
	public List<Role> findEndAddRoleRelation() throws Exception {
		return roleMapper.findEndAddRoleRelation();
	}

	@Override
	public List<Role> findRoleRelationById(int id) throws Exception {
		return roleMapper.findRoleRelationById(id);
	}

	@Override
	public int updateRoleRelation(Role role) throws Exception {
		return roleMapper.updateRoleRelation(role);
	}

	@Override
	public int deleteRoleRelation(int id) throws Exception {
		return roleMapper.deleteRoleRelation(id);
	}
	
}
