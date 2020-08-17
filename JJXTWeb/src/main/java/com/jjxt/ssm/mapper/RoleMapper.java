package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Role;

public interface RoleMapper {
	
	/* 查询全部用户 */
	public List<Role> findAllManager();
	
	/* 查询全部角色 */
	public List<Role> findAllRole();
	
	/* 查询数量 */
	int findTotal(Map<String, String> map);
	
	/* 分页查询 */
	List<Role> findRolePageList(Map<String, Object> map);
	
	/* 新增角色关系 */
	int addRoleRelation(Role role);
	
	/* 查询最后增加的角色关系 */
	public List<Role> findEndAddRoleRelation();
	
	
	/* 查询最后增加的角色关系 */
	public List<Role> findRoleRelationById(int id);
	
	
	/* 查询最后增加的角色关系 */
	int updateRoleRelation(Role role);
	
	
	/* 查询最后增加的角色关系 */
	int deleteRoleRelation(int id);

	
}
