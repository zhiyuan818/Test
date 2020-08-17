package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.DataOperationLog;
import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.entity.UcenterMenu;
import com.jjxt.ssm.service.DataOperationLogService;
import com.jjxt.ssm.service.UcenterManagerService;
import com.jjxt.ssm.service.UcenterMenuService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "ucenterManager")
public class UcenterManagerController {

	private static Logger logger = Logger.getLogger(UcenterManagerController.class);

	public static final String PATH = "ucenterManager/";
	@Autowired
	private UcenterManagerService ucenterManagerService;
	@Autowired
	private UcenterMenuService ucenterMenuService;
	@Autowired
	private DataOperationLogService dataOperationLogService;

	/**
	 * 跳转用户分页页面
	 * 
	 * @return
	 */
	@RequestMapping("/goUcenterManagerPageList.action")
	public String goUcenterManagerPageList(HttpServletRequest request, Integer model) {
		return PATH + "ucenterManagerPageList";
	}

	/**
	 * 查询所有的管理员列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "/findUcenterManager.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<UcenterManager> findUcenterManager(Integer pageSize, Integer pageIndex, String sort,
			String sortOrder, String searchTitle, String searchManagerName, String searchIsAllCustomer, String searchIsAllChannel, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug(
				"[BINS][MANAGER] pagesize=" + pageSize + ", pageIndex=" + pageIndex + ", sort=" + sort + ", sortOrder="
						+ sortOrder + ", searchTitle=" + searchTitle + ", searchManagerName=" + searchManagerName + ", searchIsAllCustomer=" + searchIsAllCustomer + ", searchIsAllChannel=" + searchIsAllChannel);
		Long startTime = System.currentTimeMillis();
		int total = 0;
		try {
			total = ucenterManagerService.findManagerTotal(searchManagerName, searchTitle,searchIsAllCustomer,searchIsAllChannel);
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MANAGER]", e1);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("sort", sort);
		map.put("sortOrder", sortOrder);
		map.put("searchManagerName", searchManagerName);
		map.put("searchTitle", searchTitle);
		map.put("searchIsAllCustomer", searchIsAllCustomer);
		map.put("searchIsAllChannel", searchIsAllChannel);
		try {
			List<UcenterManager> ucenterMenuLists = ucenterManagerService.findUcenterManagerList(map);
			Long endTime = System.currentTimeMillis();
			logger.debug("[BINS][MANAGER] pagesize=" + pageSize + ",pageIndex=" + pageIndex + ",sort=" + sort
					+ ", sortOrder=" + sortOrder + ", searchTitle=" + searchTitle + ", searchManagerName="
					+ searchManagerName + ", searchIsAllCustomer=" + searchIsAllCustomer + ", searchIsAllChannel=" + searchIsAllChannel + ", time=" + (endTime - startTime) + "ms");
			return new PageResult<UcenterManager>(total, ucenterMenuLists);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MANAGER]error ", e);
		}
		return null;
	}

	/**
	 * 管理员登录
	 * 
	 * @param manager_name
	 * @param manager_password
	 * @param request
	 * @return
	 */
	@RequestMapping("adminLogin.action")
	public String adminLoginValidate(String managerName, String managerPassword, HttpServletRequest request) {
		logger.debug("[BINS][LOGIN] managerName=" + managerName);
		long startTime = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("managerName", managerName);
			map.put("managerPassword", managerPassword);
			UcenterManager ucenter = ucenterManagerService.findUcenterManagerByNameAndPwd(map);
			if (ucenter != null) {
				request.getSession().setMaxInactiveInterval(60*60);//session过期时间60分钟
				request.getSession().setAttribute(Constant.SERVER_USER_SESSION, ucenter);
				List<GlobalSetting> globalSettingList = ucenterManagerService.findGlobalSetting();
				String platformFlag = "3";
				String platformName = "集客短信管理平台";
				String platformNameCocol = "#0066CC";
				if(globalSettingList != null && globalSettingList.size() > 0) {
				for(GlobalSetting globalSetting : globalSettingList) {
					if(globalSetting == null) {
						continue;
					}
					String key = globalSetting.getKey();
					if("platformFlag".equals(key)) {
						platformFlag = globalSetting.getVal();
					}else if("platformName".equals(key)){
						platformName = globalSetting.getVal();
					}else if("platformNameCocol".equals(key)){
						platformNameCocol = globalSetting.getVal();
					}
				}
				}
				request.getSession().setAttribute(Constant.PLATFORM_FLAG_SESSION, platformFlag);
				request.getSession().setAttribute(Constant.PLATFORM_NAME_SESSION, platformName);
				request.getSession().setAttribute(Constant.PLATFORM_NAME_COCOL_SESSION, platformNameCocol);
				List<ManagerMenu> managerMenu = ucenterManagerService.findMenuLinkByUcenterManagerId(ucenter.getId());
				request.getSession().setAttribute(Constant.SERVER_MENU_SESSION, MenuButtonsMap(managerMenu));
				DataOperationLog dataOperationLog = new DataOperationLog();
				dataOperationLog.setBussiness(Bussiness.yonghu);
				dataOperationLog.setOperation(Operate.LOGIN);
				dataOperationLog.setUcenterManagerId(ucenter.getId());
				dataOperationLog.setUcenterManagerName(ucenter.getManagerName());
				try {
					dataOperationLog.setDateTime(sdf.parse(sdf.format(new Date())));
				} catch (ParseException e1) {
					logger.error("[BINS] date ParseException", e1);
				}
				String ip=getIpAddress(request);
				dataOperationLog.setNewData("访问IP:"+ip+", 浏览器信息:"+request.getHeader("user-agent"));
				try {
					dataOperationLogService.addDataOperationLog(dataOperationLog);
				} catch (Exception e) {
					logger.error("[BINS][MAIN][DATAOPERZTIONLOG]" + dataOperationLog, e);
				}

				logger.info("[BINS][LOGIN] LOGIN SUCCESS managerName=" + managerName + ", time="
						+ (System.currentTimeMillis() - startTime) + "ms");
				return "redirect:/index.jsp";
			} else {
				logger.info("[BINS][LOGIN] LOGIN ERROR managerName=" + managerName);
				request.getSession().setAttribute("errorinfo", "用户名或密码错误,请重新输入!!!");
				return "redirect:/login.jsp";
			}
		} catch (Exception e) {
			logger.error("[BINS][LOGIN] LOGIN IS ERROR ", e);
		}
		return null;
	}
	
	/**
	 * 使用ajax验证用户名是否正确
	 * 
	 * @param ucenterManager
	 * @param manager_name
	 * @return
	 * @throws Exception
	 *             Springmvc中使用ajax可直接加@ResponseBody注解，可将数据自动转化为json格式
	 */
	@RequestMapping("checkLoginName.action")
	public @ResponseBody ResultData checkLoginName(UcenterManager ucenterManager, String managerName) throws Exception {
		logger.debug("[DEBUG][BINS][MANAGER] managerName=" + managerName);
		ResultData resultData = new ResultData();
		UcenterManager ucenterMan = ucenterManagerService.findUcenterManagerByName(managerName);
		// System.out.println("用户登录用户名为："+ucenterMan);
		if (ucenterMan == null) {
			resultData.setValid(true);
		} else {
			resultData.setValid(false);
		}
		logger.debug("[BINS][LOGIN] manager_name=" + managerName + ", result=" + resultData.getValid());
		return resultData; // 注：ajax方式不能返回页面，只能返回数据
	}

	/**
	 * 注销登录方法
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("outLogin.action")
	public String outLogin(HttpSession session) {
		if(session != null){
			if(session.getAttribute(Constant.SERVER_USER_SESSION)!=null){
				logger.info("[BINS][OUTLOGIN] MANAGER OUTLOGIN manager="
						+ ((UcenterManager) session.getAttribute(Constant.SERVER_USER_SESSION)).getManagerName());
			}
			session.invalidate();
		}
		return "redirect:/login.jsp"; // 重定向到login.jsp页面
	}

	/**
	 * 添加管理员
	 * 
	 * @param manager_name
	 * @param manager_password
	 * @param chinese_name
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.yonghu, operation = Operate.INSERT)
	@RequestMapping(value = "addUcenterManager.action", method = RequestMethod.POST)
	@ResponseBody
	public synchronized ResponseResult addUcenterManager(UcenterManager ucenterManager) {
		logger.debug("[BINS][MANAGER] " + ucenterManager);
		Long startTime = System.currentTimeMillis();
		int i = 0;
		try {
			i = ucenterManagerService.addUcenterManager(ucenterManager);
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] ADD IS ERROR", e);
		}
		List<UcenterManager> manager = null;
		try {
			manager = ucenterManagerService.findEndAddManager();
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] PUT LAST MANAGER", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(i);
		result.setNewData(manager);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.info("[BINS][MANAGER] " + ucenterManager + " ,time=" + (endTime - startTime) + "ms ,result="
				+ result.getResult());
		return result;
	}

	/**
	 * 批量删除操作
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.yonghu, operation = Operate.DELETEBATCH)
	@RequestMapping("deleteUcenterManagerBatch.action")
	@ResponseBody
	public ResponseResult deleteUcenterManagerBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][MANAGER]  ids[]=" + ids);
		Long startTime = System.currentTimeMillis();
		List<UcenterManager> oldData = null;
		try {
			oldData = ucenterManagerService.findUcenterManagerByIds(ids);
		} catch (Exception e1) {
			logger.error("[BINS][MANAGER] put old manager ", e1);
		}
		int i = 0;
		try {
			i = ucenterManagerService.deleteUcenterManagerBatch(ids);
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] delete is error ", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.info(
				"[BINS][MANAGER] ids[]=" + ids + ", time=" + (endTime - startTime) + ", result=" + result.getResult());
		return result;
	}

	/**
	 * 更新管理员
	 * 
	 * @param manager_name
	 * @param manager_password
	 * @param chinese_name
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.yonghu, operation = Operate.UPDATE)
	@RequestMapping(value = "updateUcenterManager.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateUcenterManager(UcenterManager ucenterManager) throws Exception {
		logger.info("[BINS][MANAGER]  " + ucenterManager);
		Long startTime = System.currentTimeMillis();
		List<UcenterManager> oldData = null;
		try {
			oldData = ucenterManagerService.findUcenterManagerById(ucenterManager.getId());
		} catch (Exception e1) {
			logger.error("[BINS][MANAGER] put old manager is error", e1);
		}
		int i = 0;
		try {
			i = ucenterManagerService.updateUcenterManager(ucenterManager);
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] update manager is error", e);
		}
		List<UcenterManager> newData = null;
		try {
			newData = ucenterManagerService.findUcenterManagerById(ucenterManager.getId());
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] put new manager is error", e);
		}
		ResponseResult result = new ResponseResult(i, newData, oldData);
		Long endTime = System.currentTimeMillis();
		logger.info("[BINS][MANAGER] " + ucenterManager + ",time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 通过id查找管理员，更新管理员辅助方法
	 * 
	 * @param map
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("findUcenterManagerById.action")
	@ResponseBody
	public UcenterManager findUcenterManagerById(@RequestParam(value = "") Integer id, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("[BINS][MANAGER]  id=" + id);
		try {
			return ucenterManagerService.findUcenterManagerById(id).get(0);
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] put manager is error ", e);
		}
		return null;
	}

	/**
	 * 用户授权
	 * 
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping("findUcenterAndMenu.action")
	@ResponseBody
	public Map<String, Object> findUcenterAndMenu(HttpServletRequest request, Integer id) throws Exception {
		logger.debug("[BINS][MANAGER] put manager and menu  id=" + id);
		Map<String, Object> map = new HashMap<String, Object>();
		/* 根据当前用户查询菜单 */
		List<ManagerMenu> managerMenu = ucenterManagerService.findManagerMenu(id);
		/* 查询所有菜单授权 */
		List<UcenterMenu> ucenterMenu = ucenterMenuService.findAllMenu();
		map.put("ucenterMenu", ucenterMenu);
		map.put("managerMenu", managerMenu);
		return map;
	}

	/**
	 * 用户提交授权
	 * 
	 * @param managerMenuArg
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.yonghu, operation = Operate.UPDATE)
	@RequestMapping("authorized.action")
	@ResponseBody
	public ResponseResult authorized(String managerMenuArg) {
		logger.debug("[BINS][MANAGER] manager authorized  managerMenuArg=" + managerMenuArg);
		Long startTime = System.currentTimeMillis();
		JSONArray jsonArray = JSONArray.fromObject(managerMenuArg);
		int i = 0;
		if (jsonArray != null) {
			@SuppressWarnings({ "unchecked", "static-access" })
			List<ManagerMenu> list = (List<ManagerMenu>) jsonArray.toCollection(jsonArray, ManagerMenu.class);
			List<ManagerMenu> oldData = null;
			try {
				oldData = ucenterManagerService.findManagerMenu(list.get(0).getUcenterManagerId());
			} catch (Exception e) {
				logger.error("[BINS][MANAGER] put managerMenu is error", e);
			}
			try {
				i = ucenterManagerService.updateAuthorized(list);
			} catch (Exception e) {
				logger.error("[BINS][MANAGER] manager authorized is error", e);
			}
			List<ManagerMenu> newData = null;
			try {
				newData = ucenterManagerService.findManagerMenu(list.get(0).getUcenterManagerId());
			} catch (Exception e) {
				logger.error("[BINS][MANAGER] put new managerMenu is error", e);
			}
			ResponseResult result = new ResponseResult(i, newData, oldData);
			Long endTime = System.currentTimeMillis();
			logger.info("[BINS][MANAGER] manager authorized  managerMenuArg=" + managerMenuArg + "time="
					+ (endTime - startTime) + "ms result=" + result.getResult());
			return result;
		}
		return null;
	}

	/**
	 * 用户删除
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.yonghu, operation = Operate.DELETE)
	@RequestMapping("deleteUcenterManager.action")
	@ResponseBody
	public ResponseResult deleteUcenterManager(Integer id) {
		logger.debug("[BINS][MANAGER] delete manager  id=" + id);
		Long startTime = System.currentTimeMillis();
		List<UcenterManager> oldData = null;
		try {
			oldData = ucenterManagerService.findUcenterManagerById(id);
		} catch (Exception e1) {
			logger.error("[BINS][MANAGER] put old manager is error", e1);
		}
		int i = 0;
		try {
			i = ucenterManagerService.deleteUcenterManager(id);
		} catch (Exception e) {
			logger.error("[BINS][MANAGER] delete manager is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.info("[BINS][MANAGER] delete manager  id=" + id + " time=" + (endTime - startTime) + ", result="
				+ result.getResult());
		return result;
	}

	public static Map<String, String> MenuButtonsMap(List<ManagerMenu> managerMenu) {
		Map<String, String> map = new HashMap<String, String>();
		for (ManagerMenu mm : managerMenu) {
			map.put(mm.getUcenterMenuId().toString(), mm.getMenuButtons());
		}
		return map;
	}

	
	@RequestMapping("/findSales.action")
	@ResponseBody
	public Map<String, Object> findSalesManager() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<UcenterManager> list = new ArrayList<>();
		try {
			list = ucenterManagerService.findSalesManager();
		} catch (Exception e) {
			logger.error("exce=" + e);
		}

		map.put("data", list);
		return map;
	}
	
	public static String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	}
	

}
