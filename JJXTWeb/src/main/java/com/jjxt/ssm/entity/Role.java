package com.jjxt.ssm.entity;

public class Role {
	private int id;
	private int roleId;
	private String roleName;
	private int managerId;
	private String managerName;
	private String chineseName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getManagerId() {
		return managerId;
	}
	
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	
	public String getManagerName() {
		return managerName;
	}
	
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleId=" + roleId + ", roleName=" + roleName + ", managerId=" + managerId
				+ ", managerName=" + managerName + ", chineseName=" + chineseName + "]";
	}
	
	
	
	
	

}
