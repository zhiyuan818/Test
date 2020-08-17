package com.jjxt.ssm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.UcenterMenu;
import com.jjxt.ssm.service.UcenterMenuService;

@Controller
@RequestMapping("/ucenterMenu")
public class UcenterMenuController {

	private static Logger logger = Logger.getLogger(UcenterMenuController.class);

	private static final String PATH = "ucenterMenu/";
	@Autowired
	private UcenterMenuService ucenterMenuService;

	/**
	 * 跳转菜单分页页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUcenterMenuPageList.action")
	public String goUcenterMenuPageList() {
		return PATH + "ucenterMenuPageList";
	}

	/**
	 * 根据用户id查询菜单列表
	 * 
	 * @param Integer
	 * @return
	 */
	
	@RequestMapping("/findUcenterMenuByUcenterManagerId.action")
	@ResponseBody
	public Map<String, List<Tree<UcenterMenu>>> findUcenterMenuByUcenterManagerId(@RequestParam Integer id) {
		logger.debug("[BINS][MENU] id=" + id);
		try {
			return ucenterMenuService.findUcenterMenuByUcenterManagerId(id);
		} catch (Exception e) {
			logger.error("[BINS][MENU] put munu is error", e);
		}
		return null;
	}

	/**
	 * 查询所有的菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "/findUcenterMenu.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<UcenterMenu> findUcenterMenu(Integer pageSize, Integer pageIndex, String sort, String sortOrder,
			String searchTitle, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][MENU] pagesize=" + pageSize + ", pageIndex=" + pageIndex + ", sort=" + sort
				+ " ,sortOrder=" + sortOrder + " ,searchTitle=" + searchTitle);
		int total = 0;
		try {
			total = ucenterMenuService.findMenuTotal(searchTitle);
		} catch (Exception e1) {
			logger.error("[BINS][MENU] put menu tota", e1);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		try {
			map.put("sort", sort);
			map.put("sortOrder", sortOrder);
			map.put("searchTitle", searchTitle);
			List<UcenterMenu> ucenterMenuLists = ucenterMenuService.findUcenterMenuList(map);

			return new PageResult<UcenterMenu>(total, ucenterMenuLists);
		} catch (Exception e) {
			logger.error("[BINS][MENU] put menu pageList is error", e);
		}
		return null;
	}

	/**
	 * 添加菜单
	 * 
	 * @param request
	 * @param response
	 * @param Ucenter
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.caidan, operation = Operate.INSERT)
	@RequestMapping(value = "addUcenterMenu.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public synchronized ResponseResult addUcenterMenu(HttpServletRequest request, HttpServletResponse response,
			UcenterMenu ucenterMenu) {
		logger.debug("[BINS][MENU] " + ucenterMenu);
		Long startTime = System.currentTimeMillis();
		int i = 0;
		try {
			i = ucenterMenuService.addUcenterMenu(ucenterMenu);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] add menu is error", e);
		}
		// 查询最后添加的数据
		List<UcenterMenu> menu = null;
		try {
			menu = ucenterMenuService.findEndAddMenu();
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] put last add menu is error", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(i);
		result.setNewData(menu);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][MENU]  " + ucenterMenu + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 更新菜单
	 * 
	 * @param request
	 * @param response
	 * @param ucenterMenu
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.caidan, operation = Operate.UPDATE)
	@ResponseBody
	@RequestMapping(value = "updateUcenterMenu.action", method = RequestMethod.POST)
	public ResponseResult updateUcenterMenu(HttpServletRequest request, HttpServletResponse response,
			UcenterMenu ucenterMenu) {
		logger.debug("[BINS][MENU] " + ucenterMenu);
		Long startTime = System.currentTimeMillis();
		List<UcenterMenu> oldData = null;
		try {
			oldData = ucenterMenuService.findUcenterMenuById(ucenterMenu.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MENU] put old menu is error", e1);
		}
		int i = 0;
		try {
			i = ucenterMenuService.updateUcenterMenu(ucenterMenu);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] update menu is error", e);
		}
		List<UcenterMenu> newData = null;
		try {
			newData = ucenterMenuService.findUcenterMenuById(ucenterMenu.getId());
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] put new menu is error", e);
		}
		ResponseResult result = new ResponseResult(i, newData, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][MENU]  " + ucenterMenu + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}


	/**
	 * 批量删除操作
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.caidan, operation = Operate.DELETEBATCH)
	@RequestMapping("deleteUcenterMenuBatch.action")
	@ResponseBody
	public ResponseResult deleteUcenterMenuBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][MENU] ids[]=" + ids);
		Long startTime = System.currentTimeMillis();
		List<UcenterMenu> oldData = null;
		try {
			oldData = ucenterMenuService.findUcenterMenuByIds(ids);
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MENU] put old menu is error", e1);
		}
		int i = 0;
		try {
			i = ucenterMenuService.deleteUcenterMenuBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] delete batch menu is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][MENU] ids[]=" + ids + " ,time=" + (endTime - startTime) + "ms ,result=" + result.getResult());
		return result;
	}

	/* 根据id查找菜单 */
	
	@RequestMapping("findUcenterMenuById.action")
	@ResponseBody
	public Map<String, Object> findUcenterMenuById(Integer id) {
		logger.debug("[BINS][MENU]  id=" + id);
		Map<String, Object> map=new HashMap<>();
		UcenterMenu ucenterMenu=null;
		try {
			ucenterMenu = ucenterMenuService.findUcenterMenuById(id).get(0);
		} catch (Exception e) {
			logger.error("[ERROR][MENU] ",e);
		}
		List<UcenterMenu> parentMenu=null;
		try {
			parentMenu = ucenterMenuService.findParentMenu();
		} catch (Exception e) {
			logger.error("[ERROR][MENU] ",e);
		}
		map.put("menu", ucenterMenu);
		map.put("parentMenu", parentMenu);
		return map;
	}

	
	@RequestMapping("findParentMenu.action")
	@ResponseBody
	public List<UcenterMenu> findParentMenu() {
		List<UcenterMenu> parentMenu=null;
		try {
			parentMenu = ucenterMenuService.findParentMenu();
		} catch (Exception e) {
			logger.error("[ERROR][MENU] ",e);
		}
		return parentMenu;
	}

	/* 删除菜单 */
	
	@DataOperate(bussiness = Bussiness.caidan, operation = Operate.DELETE)
	@RequestMapping("/deleteUcenterMenu.action")
	@ResponseBody
	public ResponseResult deleteUcenterMenu(Integer id) {
		logger.debug("[BINS][MENU] id=" + id);
		Long startTime = System.currentTimeMillis();
		List<UcenterMenu> oldData = null;
		try {
			oldData = ucenterMenuService.findUcenterMenuById(id);
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MENU]put old menu is error", e1);
		}
		int i = 0;
		try {
			i = ucenterMenuService.deleteUcenterMenu(id);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] delete menu is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][MENU]  id=" + id + " ,time=" + (endTime - startTime) + "ms, result=" + result.getResult());
		return result;
	}
}
