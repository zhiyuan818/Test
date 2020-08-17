package com.jjxt.ssm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.UcenterManagerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static Logger logger = Logger.getLogger(CustomerController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);

	private static final String PATH = "customer/";

	@Autowired
	private BaseCompanyService companyService;
	@Autowired
	private UcenterManagerService ucenterManagerService;

	@RequestMapping("/goCustomerList.action")
	public String goCustomerList(HttpServletRequest request) {
		List<UcenterManager> sales = new ArrayList<>();
		List<UcenterManager> saleAfter = new ArrayList<>();
		try {
			sales = ucenterManagerService.findSalesManager();
			List<BaseCompany> findAllSales = companyService.findAllSales();
			for (BaseCompany baseCompany : findAllSales) {
				boolean flag = true;
				String sale = baseCompany.getSales();
				for (UcenterManager salesManager : sales) {
					if(sale.equalsIgnoreCase(salesManager.getChineseName())) {
						flag = false;
						break;
					}
				}
				if(flag) {
					UcenterManager salesManager = new UcenterManager();
					salesManager.setChineseName(sale);
					sales.add(salesManager);
				}
				
			}
		} catch (Exception e) {
			logger.error("[ERROR][CUSTOMER] ", e);
		}
		try {
			saleAfter = ucenterManagerService.findSaleAfterManager();
			List<BaseCompany> findAllSaleAfter = companyService.findAllSaleAfter();
			for (BaseCompany baseCompany : findAllSaleAfter) {
				boolean flag = true;
				String saleA = baseCompany.getSaleAfter();
				for (UcenterManager salesManager : saleAfter) {
					if(saleA.equalsIgnoreCase(salesManager.getChineseName())) {
						flag = false;
						break;
					}
				}
				if(flag) {
					UcenterManager salesManager = new UcenterManager();
					salesManager.setChineseName(saleA);
					saleAfter.add(salesManager);
				}
				
			}
		} catch (Exception e) {
			logger.error("[ERROR][CUSTOMER] ", e);
		}
		request.setAttribute("sales", sales);
		request.setAttribute("saleAfter", saleAfter);
		return PATH + "customerList";
	}

	
	@RequestMapping("findCustomer.action")
	@ResponseBody
	public PageResult<BaseCompany> findCustomer(Integer pageSize, Integer pageIndex, String companyKey, String sales, String saleAfter) {
		logger.debug("[BINS][CUSTOMER] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",companyKey=" + companyKey + ",sales=" + sales + ",saleAfter=" + saleAfter);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("companyKey", companyKey);
		paramMap.put("sales", sales);
		paramMap.put("saleAfter", saleAfter);
		int total = 0;
		try {
			total = companyService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("companyKey", companyKey);
		map.put("sales", sales);
		map.put("saleAfter", saleAfter);
		List<BaseCompany> list = new ArrayList<>();
		try {
			list = companyService.findCustomerPageList(map);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		return new PageResult<>(total, list);
	}

	
	@DataOperate(bussiness = Bussiness.customer, operation = Operate.INSERT)
	@RequestMapping("/addCustomer.action")
	@ResponseBody
	public ResponseResult addCustomer(BaseCompany company) {
		logger.debug("[BINS][CUSTOMER] company=" + company);
		int addRes = 0;
		try {
			company.setCreateTime(sdf.parse(sdf.format(new Date())));
			company.setUpdateTime(sdf.parse(sdf.format(new Date())));
			addRes = companyService.addBaseCompany(company);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][ADDACCOUNT] exce=", e);
		}

		return new ResponseResult(addRes, company, null);
	}

	
	@DataOperate(bussiness = Bussiness.customer, operation = Operate.UPDATE)
	@RequestMapping(value = "updateCustomer.action", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult updateCustomer(HttpServletRequest request, HttpServletResponse response,
			BaseCompany company) {
		logger.debug("[BINS][CUSTOMER] company=" + company);
		BaseCompany oldCustomer = null;
		try {
			oldCustomer = companyService.findCustomerById(company.getId());
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		int updateRes = 0;
		try {
			updateRes = companyService.updateCustomer(company);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}

		return new ResponseResult(updateRes, company, oldCustomer);
	}

	
	@RequestMapping("/findCustomerById.action")
	@ResponseBody
	public BaseCompany findCustomerById(Integer id) {
		logger.debug("[BINS][CUSTOMER] id=" + id);
		BaseCompany base = null;
		try {
			base = companyService.findCustomerById(id);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		return base;
	}
	
	@RequestMapping("/findLinkAcc.action")
	@ResponseBody
	public List<Map<String,Object>> findLinkAcc(Integer id) {
		logger.debug("[BINS][CUSTOMER] id=" + id);
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = companyService.findLinkAcc(id);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		return result;
	}
	
	@RequestMapping("/findAllCustomer.action")
	@ResponseBody
	public Map<String, Object> findAllCustomer() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<BaseCompany> list = new ArrayList<>();
		try {
			list = companyService.findAllCompany();
		} catch (Exception e) {
			logger.error("exce=" + e);
		}

		map.put("data", list);
		return map;
	}
	
	@RequestMapping("/validatorCustomer.action")
	@ResponseBody
	public ResultData validatorCustomer(String companyName,String companyKey,String oldCompanyName,String oldCompanyKey) {
		ResultData result = new ResultData();
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("companyName", companyName);
		paramMap.put("companyKey", companyKey);
		int total = 0;
		try {
			total = companyService.findTotalByName(paramMap);
		} catch (Exception e) {
			logger.error("[BINS][CUSTOMER] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}
}
