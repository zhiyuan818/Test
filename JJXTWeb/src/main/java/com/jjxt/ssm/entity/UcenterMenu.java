package com.jjxt.ssm.entity;

/**
 * 
 * @author houyaohua
 *
 */
public class UcenterMenu {

	private Integer id; // 菜单id
	private String title; // 菜单名称
	private Integer parentId; // 父类id
	private String parentName;// 父类名称
	private String menuLink; // 访问路径
	private String iconClass; // 主菜单图标
	private Integer spId; // 是否是可用状态 0：不可用 1：可用
	private String buttons; // 页面按钮

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMenuLink() {
		return menuLink;
	}

	public void setMenuLink(String menuLink) {
		this.menuLink = menuLink;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getButtons() {
		return buttons;
	}

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	@Override
	public String toString() {
		return "UcenterMenu [id=" + id + ", title=" + title + ", parentId=" + parentId + ", parentName=" + parentName
				+ ", menuLink=" + menuLink + ", iconClass=" + iconClass + ", spId=" + spId + ", buttons=" + buttons
				+ "]";
	}

	public UcenterMenu(Integer id, String title, Integer parentId, String parentName, String menuLink, String iconClass,
			Integer spId, String buttons) {
		super();
		this.id = id;
		this.title = title;
		this.parentId = parentId;
		this.parentName = parentName;
		this.menuLink = menuLink;
		this.iconClass = iconClass;
		this.spId = spId;
		this.buttons = buttons;
	}

	public UcenterMenu() {
		super();
	}

}
