package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.jjxt.ssm.entity.Role;
import com.jjxt.ssm.service.RoleService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;




@Controller
@RequestMapping("/role")
public class RoleRelationController {

	private static Logger logger = Logger.getLogger(RoleRelationController.class);
	private static final String PATH = "role/";
	@Autowired
	private RoleService roleServer;
	
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/goRoleRelation.action")
	public String goRoleRelation(HttpServletRequest request){
		List<Role> managerMap = new ArrayList<>();
		List<Role> roleMap = new ArrayList<>();
		try {
			
	    	managerMap = roleServer.findAllManager();
			roleMap = roleServer.findAllRole();
		} catch (Exception e) {
			logger.error("[ROLE] find managerMap and roleMap err.E={}", e);
		}
		request.setAttribute("managerMap", managerMap);
		request.setAttribute("roleMap", roleMap);
		return PATH + "roleRelationList";
	}
	
	/**
	 * 用户列表
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/findRolePageList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<Role> findRolePageList(Integer pageIndex, Integer pageSize, String roleId, String managerId){
		logger.debug("[BINS][Role] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",roleId="+roleId+",managerId="+managerId);
		int total = 0;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("roleId", roleId);
		map1.put("managerId", managerId);
		try {
			total = roleServer.findTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][ROLE] 查询角色总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("roleId", roleId);
		map.put("managerId", managerId);
		List<Role> role = new ArrayList<Role>();		
		try {
			role = roleServer.findRolePageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][ROLE] 角色列表异常", e);
		}
		return new PageResult<>(total, role);
	}
	
	
	
	
	/**
	 * 角色关系添加提交
	 * 
	 * @param channel
	 * @return
	 */
	@DataOperate(bussiness = Bussiness.role, operation = Operate.INSERT)
	@RequestMapping("/addRoleRelation.action")
	@ResponseBody
	public synchronized ResponseResult addRoleRelation(Role role) {
		logger.debug("[BINS][ROLE] role=" + role.toString());
		int i = 0;
		try {
			i = roleServer.addRoleRelation(role);
		} catch (Exception e) {
			logger.error("[ERROR][ROLE] add roleRelation exception", e);
		}
		List<Role> newData = null;
		try {
			newData = roleServer.findEndAddRoleRelation();
		} catch (Exception e) {
			logger.error("[ERROR][ROLE] 查询最后添加角色关系 异常", e);
		}
		return new ResponseResult(i, newData, null);
	}
	
	
	
	/**
	 * 通过id查找角色关系
	 * 
	 * @param map
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("findRoleRelationById.action")
	@ResponseBody
	public Role findRoleRelationById(@RequestParam(value = "") Integer id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("[BINS][ROLE]  id=" + id);
		try {
			return roleServer.findRoleRelationById(id).get(0);
		} catch (Exception e) {
			logger.error("[BINS][Role] find roleRelation by id is error ", e);
		}
		return null;
	}
	
	/**
	 * 更新管理员
	 * 
	 * @param manager_id
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	@DataOperate(bussiness = Bussiness.role, operation = Operate.UPDATE)
	@RequestMapping(value = "updateRoleRelation.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateRoleRelation(Role role) throws Exception {
		logger.info("[BINS][ROLE]  " + role);
		Long startTime = System.currentTimeMillis();
		List<Role> oldData = null;
		try {
			oldData = roleServer.findRoleRelationById(role.getId());
			logger.debug(oldData.get(0).toString());
		} catch (Exception e1) {
			logger.error("[BINS][ROLE] get old role relation is error", e1);
		}
		int i = 0;
		try {
			i = roleServer.updateRoleRelation(role);
			logger.debug(i);
		} catch (Exception e) {
			logger.error("[BINS][ROLE] update role relation is error", e);
		}
		List<Role> newData = null;
		try {
			newData = roleServer.findRoleRelationById(role.getId());
			logger.debug(newData.get(0).toString());
		} catch (Exception e) {
			logger.error("[BINS][ROLE] get new role relation is error", e);
		}
		ResponseResult result = new ResponseResult(i, newData, oldData);
		Long endTime = System.currentTimeMillis();
		logger.info("[BINS][Role] " + role.toString() + ",time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}
	
	
	/**
	 * 角色关系删除
	 * 
	 * @param id
	 * @return
	 */
	@DataOperate(bussiness = Bussiness.role, operation = Operate.DELETE)
	@RequestMapping("deleteRoleRelation.action")
	@ResponseBody
	public ResponseResult deleteRoleRelation(Integer id) {
		logger.debug("[BINS][ROLE] delete role relation  id=" + id);
		Long startTime = System.currentTimeMillis();
		List<Role> oldData = null;
		try {
			oldData = roleServer.findRoleRelationById(id);
		} catch (Exception e1) {
			logger.error("[BINS][ROLE] put old role relation is error", e1);
		}
		int i = 0;
		try {
			i = roleServer.deleteRoleRelation(id);
		} catch (Exception e) {
			logger.error("[BINS][ROLE] delete role relation is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.info("[BINS][ROLE] delete role relation  id=" + id + " time=" + (endTime - startTime) + ", result="
				+ result.getResult());
		return result;
	}

}
