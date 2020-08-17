package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterMenu;
import com.jjxt.ssm.mapper.UcenterMenuMapper;
import com.jjxt.ssm.service.UcenterMenuService;
import com.jjxt.ssm.utils.BuildTree;
import com.jjxt.ssm.utils.Tree;

@Service("ucenterMenuService")
@Transactional
public class UcenterMenuServiceImpl implements UcenterMenuService {
	@Autowired
	private UcenterMenuMapper ucenterMenuMapper;

	@Override
	public Map<String, List<Tree<UcenterMenu>>> findUcenterMenuByUcenterManagerId(Integer id) throws Exception {
		if (id == null) {
			return null;
		}
		List<Tree<UcenterMenu>> trees = new ArrayList<Tree<UcenterMenu>>();
		Map<String, List<Tree<UcenterMenu>>> map = new HashMap<String, List<Tree<UcenterMenu>>>();
		/* 获取用户的子菜单字符串 */
		List<UcenterMenu> ucenterMenu = ucenterMenuMapper.findUcenterMenuListByUcenterManagerId(id);
		Tree<UcenterMenu> tree = null;
		for (UcenterMenu test : ucenterMenu) {
			tree = new Tree<UcenterMenu>();
			tree.setId(test.getId().toString());
			tree.setParentId(test.getParentId().toString());
			tree.setText(test.getTitle());
			tree.setMenuLink(test.getMenuLink());
			tree.setIconClass(test.getIconClass());
			tree.setButtons(test.getButtons());
			tree.setSpId(test.getSpId());
			trees.add(tree);
		}
		List<Tree<UcenterMenu>> build = BuildTree.build(trees);
		map.put("ucenterMenu", build);
		return map;
	}

	@Override
	public int addUcenterMenu(UcenterMenu ucenterMenu) throws Exception {

		return ucenterMenuMapper.addUcenterMenu(ucenterMenu);
	}

	@Override
	public int deleteUcenterMenu(Integer id) throws Exception {
		return ucenterMenuMapper.deleteUcenterMenu(id);
	}

	@Override
	public int updateUcenterMenu(UcenterMenu ucenterMenu) throws Exception {
		return ucenterMenuMapper.updateUcenterMenu(ucenterMenu);
	}

	@Override
	public int findMenuTotal(String search_title) throws Exception {

		return ucenterMenuMapper.findMenuTotal(search_title);
	}

	@Override
	public List<UcenterMenu> findUcenterMenuList(Map<String, Object> map) throws Exception {

		return ucenterMenuMapper.findUcenterMenuList(map);
	}

	@Override
	public List<UcenterMenu> findParentMenu() throws Exception {

		return ucenterMenuMapper.findParentMenu();
	}

	@Override
	public int deleteUcenterMenuBatch(Integer[] ids) throws Exception {

		return ucenterMenuMapper.deleteUcenterMenuBatch(ids);
	}

	@Override
	public List<UcenterMenu> findEndAddMenu() throws Exception {

		return ucenterMenuMapper.findEndAddMenu();
	}

	@Override
	public List<UcenterMenu> findUcenterMenuById(Integer id) throws Exception {
		return ucenterMenuMapper.findUcenterMenuById(id);
	}

	@Override
	public List<UcenterMenu> findUcenterMenuByIds(Integer[] ids) throws Exception {
		return ucenterMenuMapper.findUcenterMenuByIds(ids);
	}

	@Override
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id) throws Exception {
		return ucenterMenuMapper.findMenuLinkByUcenterManagerId(id);
	}

	@Override
	public List<UcenterMenu> findAllMenu() throws Exception {
		return ucenterMenuMapper.findAllMenu();
	}

	@Override
	public List<UcenterMenu> findUcenterMenuListByUcenterManagerId(Integer id) throws Exception {
		return ucenterMenuMapper.findUcenterMenuListByUcenterManagerId(id);
	}

	@Override
	public String findManagerMenuButtons(Map<String, Integer> result) throws Exception {
		return ucenterMenuMapper.findManagerMenuButtons(result);
	}

}
