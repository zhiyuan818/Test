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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.DataOperationLog;
import com.jjxt.ssm.service.DataOperationLogService;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;

@Controller
@RequestMapping("/system")
public class BizLogController {
	private static Logger logger = Logger.getLogger(BizLogController.class);
	private static final String PATH = "system/";
	@Autowired
	private DataOperationLogService service;

	/**
	 * 跳转菜单分页页面
	 * 
	 * @return
	 */
	@RequestMapping("/findLogList.action")
	public String goMTPageList() {
		return PATH + "logList";
	}

	/**
	 * 查询所有的菜单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	@RequestMapping(value = "/findLogList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<DataOperationLog> findMTPageList(Integer pageSize, Integer pageIndex, String sort,
			String sortOrder, String bussiness, String operation, String newData, String oldData, String userName,
			String startTime, String endTime, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[BINS][LOG] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",sort=" + sort + ",sortOrder="
				+ sortOrder + ",bussiness=" + bussiness + ",operation=" + operation + ",newData=" + newData
				+ ",oldData=" + oldData + ",userName=" + userName + ",startTime=" + startTime + ",endTime=" + endTime);

		Map<String, Object> map = new HashMap<String, Object>();
		List<DataOperationLog> ucenterMenuLists=new ArrayList<DataOperationLog>();
		map.put("bussiness", bussiness);
		map.put("operation", operation);
		map.put("newData", newData);
		map.put("oldData", oldData);
		map.put("userName", userName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		int total = 0;
		try {
			total = service.findLogForCount(map);
		} catch (Exception e1) {
			logger.error("[ERROR:PUT_MT_TOTAL] put mt_total is error", e1);
		}
		if(total==0){
			return new PageResult<DataOperationLog>(total, ucenterMenuLists);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("bussiness", bussiness);
			map1.put("operation", operation);
			map1.put("newData", newData);
			map1.put("oldData", oldData);
			map1.put("userName", userName);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);

			ucenterMenuLists = service.findLogForPage(map1);
			return new PageResult<DataOperationLog>(total, ucenterMenuLists);
		} catch (Exception e) {
			logger.error("[ERROR:PUT_MT_PAGElIST] put mt pageList is error", e);
		}
		return new PageResult<DataOperationLog>(total, ucenterMenuLists);
	}
}
