package com.jjxt.ssm.entity;

/**
 * 管理员菜单的中间表
 */
import java.io.Serializable;

public class ManagerMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3479876973142869608L;

	private Integer id;// 主键id

	private Integer ucenterManagerId;// 管理员id

	private Integer ucenterMenuId;// 菜单id

	private String menuButtons;// 用户拥有的菜单按钮

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUcenterManagerId() {
		return ucenterManagerId;
	}

	public void setUcenterManagerId(Integer ucenterManagerId) {
		this.ucenterManagerId = ucenterManagerId;
	}

	public Integer getUcenterMenuId() {
		return ucenterMenuId;
	}

	public void setUcenterMenuId(Integer ucenterMenuId) {
		this.ucenterMenuId = ucenterMenuId;
	}

	public String getMenuButtons() {
		return menuButtons;
	}

	public void setMenuButtons(String menuButtons) {
		this.menuButtons = menuButtons;
	}

	@Override
	public String toString() {
		return "ManagerMenu [id=" + id + ", ucenterManagerId=" + ucenterManagerId + ", ucenterMenuId=" + ucenterMenuId
				+ ", menuButtons=" + menuButtons + "]";
	}

}
