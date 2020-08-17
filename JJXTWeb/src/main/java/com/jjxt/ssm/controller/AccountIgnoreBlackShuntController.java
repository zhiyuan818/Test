package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.jjxt.ssm.entity.AccountIgnoreBlackShunt;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.AccountIgnoreBlackShuntService;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.ResultData;

@Controller
@RequestMapping("/accountIgnoreBlackShunt")
public class AccountIgnoreBlackShuntController {
	
	 	private static Logger logger = Logger.getLogger(AccountIgnoreBlackShuntController.class);

	    private static final String PATH = "accountIgnoreBlackShunt/";
	    
	    @Autowired
	    AccountIgnoreBlackShuntService accountIgnoreBlackShuntService;
	    @Autowired
	    ApplicationService applicationService;
	    
	    @RequestMapping("/goAccountIgnoreBlackShuntList.action")
	    public String goAccountIgnoreBlackShuntList(HttpServletRequest request, HttpServletResponse response) {
	    	Map<String, Object> map=new HashMap<>();
			UcenterManager ucenterManager = (UcenterManager) request.getSession()
	                .getAttribute(Constant.SERVER_USER_SESSION);
			if(ucenterManager != null) {
				map.put("chineseName", ucenterManager.getChineseName());
				map.put("title", ucenterManager.getTitle());
				map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
				map.put("isAllChannel", ucenterManager.getIsAllChannel());
			}
	    	List<Application> apps = new ArrayList<Application>();
	    	List<AccountIgnoreBlackShunt> levels = new ArrayList<AccountIgnoreBlackShunt>();
	    	try {
	            apps = applicationService.findAppNameBySort(map);
	            levels = accountIgnoreBlackShuntService.findShuntLevels();
	        } catch (Exception e) {
	            logger.error("[ERROR][APPLICATION] ex=" + e);
	        }
	    	request.setAttribute("apps", apps);
	    	request.setAttribute("levels", levels);
	    	return PATH + "accountIgnoreBlackShuntList";
	    }
	    
	    @RequestMapping("findAccountIgnoreBlackShunt.action")
	    @ResponseBody
	public PageResult<AccountIgnoreBlackShunt> findAccountIgnoreBlackShunt(HttpServletRequest request, Integer pageSize,
			Integer pageIndex, String appId, String content, String level) {
	        logger.debug("[BINS][AccountIgnoreBlackShunt] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appId=" + appId+",content="+content);
	        Map<String, String> paramMap = new HashMap<>();
	        int total = 0;
	        List<AccountIgnoreBlackShunt> list = new ArrayList<>();
	        paramMap.put("appId", appId);
	        paramMap.put("content", content);
	        paramMap.put("level", level);
	        UcenterManager ucenterManager = (UcenterManager) request.getSession()
	                .getAttribute(Constant.SERVER_USER_SESSION);
			if(ucenterManager != null) {
				paramMap.put("chineseName", ucenterManager.getChineseName());
				paramMap.put("title", ucenterManager.getTitle());
				paramMap.put("isAllCustomer", ucenterManager.getIsAllCustomer());
				paramMap.put("isAllChannel", ucenterManager.getIsAllChannel());
			}
	        try {
	            total = accountIgnoreBlackShuntService.findTotal(paramMap);
	        } catch (Exception e) {
	            logger.error("[ERROR][ACCOUNT] ", e);
	        }
	        if (total == 0) {
	            return new PageResult<>(total, list);
	        }
	        Page page = new Page(pageSize, total, pageIndex);
	        Map<String, Object> map = PageUtil.getDefaultPageMap(page);
	        map.put("appId", appId);
	        map.put("content", content);
	        map.put("level", level);
	        if(ucenterManager != null) {
				map.put("chineseName", ucenterManager.getChineseName());
				map.put("title", ucenterManager.getTitle());
				map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
				map.put("isAllChannel", ucenterManager.getIsAllChannel());
			}
	        try {
	            list = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntPageList(map);
	        } catch (Exception e) {
	            logger.error("[ERROR][ACCOUNT] ", e);
	        }
	        return new PageResult<>(total, list);
	    }
	    
	    @DataOperate(bussiness = Bussiness.accountIgnoreBlackShunt, operation = Operate.INSERT)
	    @RequestMapping("/addAccountIgnoreBlackShunt.action")
	    @ResponseBody
	    public ResponseResult addAccountIgnoreBlackShunt(String appId,String content,String level) {
	    	AccountIgnoreBlackShunt account = new AccountIgnoreBlackShunt();
	    	account.setAppId(Integer.parseInt(appId));
	    	account.setContent(content);
	    	account.setLevel(level);
	        logger.debug("[BINS][AccountIgnoreBlackShunt] accountIgnoreBlackShunt=" + account);
	        int addRes = 0;
	        AccountIgnoreBlackShunt acc = null;
	        try {
	            addRes = accountIgnoreBlackShuntService.addAccountIgnoreBlackShunt(account);
	        } catch (Exception e) {
	            logger.error("[ERROR][BINS][ADDACCOUNT] exce=", e);
	        }
	        if (addRes == 0) {
	            return new ResponseResult(addRes, account, null);
	        }
	        try {
	            acc = accountIgnoreBlackShuntService.findEndAdd();
	        } catch (Exception e1) {
	            logger.error("[ERROR][ACCOUNT] ", e1);
	        }
	        if (acc == null) {
	            return new ResponseResult(addRes, acc, null);
	        }
	        return new ResponseResult(addRes, acc, null);
	    }
	    
	    @RequestMapping("/findAccountIgnoreBlackShuntById.action")
	    @ResponseBody
	    public AccountIgnoreBlackShunt findAccountIgnoreBlackShuntById(Integer id) {
	        logger.debug("[BINS][AccountIgnoreBlackShunt] id=" + id);
	        AccountIgnoreBlackShunt a = null;
	        try {
	            a = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntById(id);
	        } catch (Exception e) {
	            logger.error("[ERROR][ACCOUNT] ", e);
	        }
	        return a;
	    }
	    
	    @DataOperate(bussiness = Bussiness.accountIgnoreBlackShunt, operation = Operate.UPDATE)
	    @RequestMapping(value = "updateAccountIgnoreBlackShunt.action", method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseResult updateAccountIgnoreBlackShunt(HttpServletRequest request, HttpServletResponse response,
	    		String id,String appId,String content,String level) {
	    	AccountIgnoreBlackShunt account = new AccountIgnoreBlackShunt();
	    	account.setId(Integer.parseInt(id));
	    	account.setAppId(Integer.parseInt(appId));
	    	account.setContent(content);
	    	account.setLevel(level);
	    	logger.debug("[BINS][AccountIgnoreBlackShunt] account=" + account);
	        AccountIgnoreBlackShunt oldAccount = null;
	        try {
	            oldAccount = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntById(account.getId());
	        } catch (Exception e1) {
	            logger.error("[ERROR][ACCOUNT] ", e1);
	        }
	        if (oldAccount == null) {
	            return new ResponseResult(0, null, account);
	        }
	        int updateRes = 0;
	        try {
	            updateRes = accountIgnoreBlackShuntService.updateAccountIgnoreBlackShunt(account);
	        } catch (Exception e1) {
	            logger.error("[ERROR][ACCOUNT] ", e1);
	        }
	        if (updateRes == 0) {
	            return new ResponseResult(updateRes, null, account);
	        }
	        AccountIgnoreBlackShunt newAccount = null;
	        try {
	            newAccount = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntById(account.getId());
	        } catch (Exception e1) {
	            logger.error("[ERROR][ACCOUNT] ", e1);
	        }
	        if (newAccount == null) {
	            return new ResponseResult(updateRes, null, account);
	        }
	        return new ResponseResult(updateRes, newAccount, oldAccount);
	    }
	    
	    @DataOperate(bussiness = Bussiness.accountIgnoreBlackShunt, operation = Operate.DELETE)
		@RequestMapping("/delAccountIgnoreBlackShuntById.action")
		@ResponseBody
		public ResponseResult delAccountIgnoreBlackShuntById(Integer id) {
	    	logger.debug("[BINS][AccountIgnoreBlackShunt] id=" + id);
	        AccountIgnoreBlackShunt oldAccount = null;
	        try {
	            oldAccount = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntById(id);
	        } catch (Exception e1) {
	            logger.error("[ERROR][AccountIgnoreBlackShunt] ", e1);
	        }
	        if (oldAccount == null) {
	            return new ResponseResult(0, null, id);
	        }
	        int del = 0;
	        try {
	        	del = accountIgnoreBlackShuntService.delAccountIgnoreBlackShuntById(id);
			} catch (Exception e) {
				logger.error("[ERROR][AccountIgnoreBlackShunt] ", e);
			}
			
			return new ResponseResult(del, null, oldAccount);
		}
	    
	    @DataOperate(bussiness = Bussiness.accountIgnoreBlackShunt, operation = Operate.DELETEBATCH)
	    @RequestMapping("/delAccountIgnoreBlackShuntByIds.action")
	    @ResponseBody
	    public ResponseResult delAccountIgnoreBlackShuntByIdBatch(
				@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
	    	logger.debug("[BINS][AccountIgnoreBlackShunt] id=" + Arrays.toString(ids));
	    	List<AccountIgnoreBlackShunt> oldAccounts = null;
	    	try {
	    		oldAccounts = accountIgnoreBlackShuntService.findAccountIgnoreBlackShuntByIds(ids);
	    	} catch (Exception e1) {
	    		logger.error("[ERROR][AccountIgnoreBlackShunt] ", e1);
	    	}
	    	if (oldAccounts == null) {
	    		return new ResponseResult(0, null, ids);
	    	}
	    	int del = 0;
	    	try {
	    		del = accountIgnoreBlackShuntService.delAccountIgnoreBlackShuntByIdBatch(ids);
	    	} catch (Exception e) {
	    		logger.error("[ERROR][AccountIgnoreBlackShunt] ", e);
	    	}
	    	
	    	return new ResponseResult(del, null, oldAccounts);
	    }
	    
	    @RequestMapping("checkAppIdContent.action")
	    @ResponseBody
	    public ResultData checkAppIdContent(String content,String appId,String level) throws Exception {
			logger.debug("[BINS][AccountIgnoreBlackShunt] content=" + content+",appId="+appId+",level="+level);
			ResultData resultData = new ResultData();
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("content", content);
			paramMap.put("appId", appId);
			paramMap.put("level", level);
			int findTotal = accountIgnoreBlackShuntService.findTotal(paramMap);
			// System.out.println("用户登录用户名为："+ucenterMan);
			if (findTotal <1) {
				resultData.setValid(true);
			} else {
				resultData.setValid(false);
			}
			logger.debug("[BINS][AccountIgnoreBlackShunt] content=" + content + ", result=" + resultData.getValid());
			return resultData; // 注：ajax方式不能返回页面，只能返回数据
		}
}
