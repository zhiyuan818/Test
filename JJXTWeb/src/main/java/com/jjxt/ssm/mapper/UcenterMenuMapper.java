package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterMenu;

@Repository("ucenterMenuMapper")
public interface UcenterMenuMapper {

	/* 根据用户id获取子菜单 menu_list */
	public List<UcenterMenu> findUcenterMenuListByUcenterManagerId(Integer id);

	/* 查询所有的菜单列表 */
	public List<UcenterMenu> findUcenterMenuList();

	/* 添加菜单 */
	public int addUcenterMenu(UcenterMenu ucenterMenu);

	/* 删除菜单 */
	public int deleteUcenterMenu(Integer id);

	/* 修改菜单 */
	public int updateUcenterMenu(UcenterMenu ucenterMenu);

	/* 菜单总数 */
	public int findMenuTotal(@Param(value = "searchTitle") String searchTitle);

	/* 菜单分页 */
	public List<UcenterMenu> findUcenterMenuList(Map<String, Object> map);

	/* 查询所有根菜单 */
	public List<UcenterMenu> findParentMenu();

	/* 批量删除菜单 */
	public int deleteUcenterMenuBatch(Integer[] ids);

	/* 查询最后添加的菜单 */
	public List<UcenterMenu> findEndAddMenu();

	/* 根据菜单id进行查询 */
	public List<UcenterMenu> findUcenterMenuById(Integer id);

	/* 根据多个菜单id进行查询 */
	public List<UcenterMenu> findUcenterMenuByIds(Integer[] ids);

	/* 查询当前用户的菜单路径 */
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id);

	/* 查询所有菜单 */
	public List<UcenterMenu> findAllMenu();

	/* 查询用户菜单的按钮 */
	public String findManagerMenuButtons(Map<String, Integer> result);

}
