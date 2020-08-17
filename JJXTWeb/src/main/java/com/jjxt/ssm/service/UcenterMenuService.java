package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterMenu;
import com.jjxt.ssm.utils.DataSource;
import com.jjxt.ssm.utils.Tree;

public interface UcenterMenuService {

	/* 根居用戶id获取用户的菜单 */
	@DataSource("master")
	public Map<String, List<Tree<UcenterMenu>>> findUcenterMenuByUcenterManagerId(Integer id) throws Exception;

	/* 查询分页菜单的列表 */
	@DataSource("master")
	public List<UcenterMenu> findUcenterMenuList(Map<String, Object> map) throws Exception;

	/* 添加菜单 */
	@DataSource("master")
	public int addUcenterMenu(UcenterMenu ucenterMenu) throws Exception;

	/* 删除菜单 */
	@DataSource("master")
	public int deleteUcenterMenu(Integer id) throws Exception;

	/* 修改菜单 */
	@DataSource("master")
	public int updateUcenterMenu(UcenterMenu ucenterMenu) throws Exception;

	/* 查询菜单总条数 */
	@DataSource("master")
	public int findMenuTotal(String search_title) throws Exception;

	/* 查询所有根菜单 */
	@DataSource("master")
	public List<UcenterMenu> findParentMenu() throws Exception;

	/* 批量删除菜单 */
	@DataSource("master")
	public int deleteUcenterMenuBatch(Integer[] ids) throws Exception;

	/* 查询最后添加的菜单 */
	@DataSource("master")
	public List<UcenterMenu> findEndAddMenu() throws Exception;

	/* 根据菜单id进行查询 */
	@DataSource("master")
	public List<UcenterMenu> findUcenterMenuById(Integer id) throws Exception;

	/* 根据多个id查询菜单列表 */
	@DataSource("master")
	public List<UcenterMenu> findUcenterMenuByIds(Integer[] ids) throws Exception;

	/* 查询当前用户的菜单按钮 */
	@DataSource("master")
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id) throws Exception;

	/* 查询所有菜单 */
	@DataSource("master")
	public List<UcenterMenu> findAllMenu() throws Exception;

	/* 根居用戶id获取用户的菜单 */
	@DataSource("master")
	public List<UcenterMenu> findUcenterMenuListByUcenterManagerId(Integer id) throws Exception;

	/* 查询用户当前菜单的按钮 */
	@DataSource("master")
	public String findManagerMenuButtons(Map<String, Integer> result) throws Exception;

}
