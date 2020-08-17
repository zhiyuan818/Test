package com.jjxt.ssm.controller;

import com.jjxt.ssm.entity.*;
import com.jjxt.ssm.service.*;
import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/account")
public class AccountController {
    private static Logger logger = Logger.getLogger(AccountController.class);

    private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);

    private static final String PATH = "account/";

    @Autowired
    ApplicationService applicationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ExtSignService extSignService;
    @Autowired
    private BaseCompanyService companyService;
    @Autowired
    private ChannelService channelService;

    @RequestMapping("/goAccountList.action")
    public String goAccountList(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = new ArrayList<>();
        List<BaseCompany> complains = new ArrayList<>();
        List<Application> parents = new ArrayList<>();
        List<Application> apps = new ArrayList<>();
        List<Map<String, Object>> channels = new ArrayList<>();
        List<BaseCompany> sales = new ArrayList<>();
		List<BaseCompany> saleAfter = new ArrayList<>();
        try {
            products = productService.findAllProduct();
        } catch (Exception e) {
            logger.error("[ERROR][PRODUCT]  ex=" + e);
        }
        try {
            complains = companyService.findAllCompany();
        } catch (Exception e) {
            logger.error("[ERROR][BASECOMPANY]  ex=" + e);
        }
        try {
            parents = applicationService.findAllParent();
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION] ex=" + e);
        }
        try {
            apps = applicationService.findAppName();
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION] ex=" + e);
        }
        try {
            channels = channelService.findChannel();
        } catch (Exception e) {
            logger.error("[ERROR][CHANNEL] ex=" + e);
        }
        try {
			sales = companyService.findAllSales();
		} catch (Exception e) {
			logger.error("[ERROR][CUSTOMER] ", e);
		}
		try {
			saleAfter = companyService.findAllSaleAfter();
		} catch (Exception e) {
			logger.error("[ERROR][CUSTOMER] ", e);
		}
        request.setAttribute("complains", complains);
        request.setAttribute("products", products);
        request.setAttribute("parents", parents);
        request.setAttribute("apps", apps);
        request.setAttribute("channels", channels);
        request.setAttribute("sales", sales);
		request.setAttribute("saleAfter", saleAfter);
        return PATH + "accountList";
    }

    @RequestMapping("findAccount.action")
    @ResponseBody
    public PageResult<Application> findAccount(Integer pageSize, Integer pageIndex, String appId,
                                               String appStatus, String product, String companyId, String channelId,String appExtSrc,String sales,String saleAfter) {
        logger.debug("[BINS][ACCOUNT] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appId=" + appId + "appStatus=" + appStatus + ",product=" + product + ",companyId=" + companyId + ",channelId=" + channelId + ",appExtSrc=" + appExtSrc + ",sales=" + sales + ",saleAfter=" + saleAfter);
        Map<String, String> paramMap = new HashMap<>();
        int total = 0;
        List<Application> list = new ArrayList<>();
        paramMap.put("appId", appId);
        paramMap.put("appStatus", appStatus);
        paramMap.put("productId", product);
        paramMap.put("companyId", companyId);
        paramMap.put("channelId", channelId);
        paramMap.put("appExtSrc", appExtSrc);
        paramMap.put("sales", sales);
        paramMap.put("saleAfter", saleAfter);
        try {
            total = applicationService.findTotal(paramMap);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        if (total == 0) {
            return new PageResult<>(total, list);
        }
        Page page = new Page(pageSize, total, pageIndex);
        Map<String, Object> map = PageUtil.getDefaultPageMap(page);
        map.put("appId", appId);
        map.put("appStatus", appStatus);
        map.put("productId", product);
        map.put("companyId", companyId);
        map.put("channelId", channelId);
        map.put("appExtSrc", appExtSrc);
        map.put("sales", sales);
        map.put("saleAfter", saleAfter);
        try {
            list = applicationService.findAccoutPageList(map);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        return new PageResult<>(total, list);
    }

    
    @DataOperate(bussiness = Bussiness.account, operation = Operate.INSERT)
    @RequestMapping("/addAccount.action")
    @ResponseBody
    public ResponseResult addAccount(Application account) {
        logger.debug("[BINS][ACCOUNT] account=" + account);
        int addRes = 0;
        Application acc = null;
        try {
			int i = applicationService.findAppByName(account.getAppName());
			if(i > 0) {
				return new ResponseResult(2, account, null);
			}
		} catch (Exception e2) {
			logger.error("[ERROR][ACCOUNT] ", e2);
		}
        try {
            account.setCreateTime(sdf.parse(sdf.format(new Date())));
            account.setAppPassword(StringUtil.getStringRandom(8));

            account.setAppExtSrcCmcc(account.getAppExtSrc());
            account.setAppExtSrcTelecom(account.getAppExtSrc());
            account.setAppExtSrcUnicom(account.getAppExtSrc());

            addRes = applicationService.addAccount(account);
        } catch (Exception e) {
            logger.error("[ERROR][BINS][ADDACCOUNT] exce=", e);
        }
        if (addRes == 0) {
            return new ResponseResult(addRes, account, null);
        }
        try {
            acc = applicationService.findEndAdd();
        } catch (Exception e1) {
            logger.error("[ERROR][ACCOUNT] ", e1);
        }
        if (acc == null) {
            return new ResponseResult(addRes, acc, null);
        }
        return new ResponseResult(addRes, acc, null);
    }

    
    @RequestMapping("/validatorAccount.action")
    @ResponseBody
    public ResultData validatorAccount(String appName) {
        ResultData result = new ResultData();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("validator", appName);
        int total = 0;
        try {
            total = applicationService.findTotal(paramMap);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        if (total > 0) {
            result.setValid(false);
        } else {
            result.setValid(true);
        }

        return result;
    }

    
    @RequestMapping("/validatorAccountExt.action")
    @ResponseBody
    public ResultData validatorAccountExt(String appExtSrc, String oldAppExtSrc) {
        logger.debug("[BINS][APPLICATION] appExtSrc=" + appExtSrc + ",oldAppExtSrc=" + oldAppExtSrc);
        ResultData result = new ResultData();
        Map<String, String> paramMap = new HashMap<>();
        if (appExtSrc.equals(oldAppExtSrc)) {
            result.setValid(true);
            return result;
        }
        paramMap.put("validatorAppExt", appExtSrc);
        int total = 0;
        try {
            total = applicationService.findTotal(paramMap);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        if (total > 0) {
            result.setValid(false);
        } else {
            result.setValid(true);
        }

        return result;
    }

    
    @RequestMapping("/validatorChargeNum.action")
    @ResponseBody
    public ResultData validatorChargeNum(String changeType, String changeNum, String appId) {
        logger.debug("[BINS][APPLICATION] changeType=" + changeType + ",changeNum=" + changeNum + ",appId=" + appId);
        ResultData result = new ResultData();
        if ("核减".equals(changeType) || "扣罚".equals(changeType) || "退款".equals(changeType)) {
            Application application = null;
            try {
                application = applicationService.findApplicationById(Integer.parseInt(appId));
            } catch (Exception e) {
                logger.error("[ERROR][APPLICATION]", e);
            }
            if (application == null) {
                result.setValid(true);
                return result;
            }
            if (application.getLimitCount() >= Integer.parseInt(changeNum)) {
                result.setValid(true);
                return result;
            } else {
                result.setValid(false);
                return result;
            }
        } else {
            result.setValid(true);
            return result;
        }
    }

    
    @DataOperate(bussiness = Bussiness.account, operation = Operate.UPDATE)
    @RequestMapping(value = "updateAccount.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult updateAccount(HttpServletRequest request, HttpServletResponse response, Application account,String appExtras) {
        logger.debug("[BINS][ACCOUNT] appExtras=" + appExtras + " account=" + account);
        if (account == null) {
            return new ResponseResult(0, null, account);
        }
        account.setPrice(account.getPrice() == null ? 0 : account.getPrice());
        account.setPriceCmcc(account.getPriceCmcc() == null ? 0 : account.getPriceCmcc());
        account.setPriceTelecom(account.getPriceTelecom() == null ? 0 : account.getPriceTelecom());
        account.setPriceUnicom(account.getPriceUnicom() == null ? 0 : account.getPriceUnicom());
        account.setBlackLevel(account.getBlackLevel() == null ? "" : account.getBlackLevel());
        account.setAppExtras(appExtras);
        Application oldAccount = null;
        try {
            oldAccount = applicationService.findApplicationById(account.getId());
        } catch (Exception e1) {
            logger.error("[ERROR][ACCOUNT] ", e1);
        }
        if (oldAccount == null) {
            return new ResponseResult(0, null, account);
        }
        int updateRes = 0;
        try {
            account.setAppExtSrcCmcc(account.getAppExtSrc());
            account.setAppExtSrcTelecom(account.getAppExtSrc());
            account.setAppExtSrcUnicom(account.getAppExtSrc());

            updateRes = applicationService.updateAccount(account);
        } catch (Exception e1) {
            logger.error("[ERROR][ACCOUNT] ", e1);
        }
        if (updateRes == 0) {
            return new ResponseResult(updateRes, null, account);
        }
        Application newAccount = null;
        try {
            newAccount = applicationService.findApplicationById(account.getId());
        } catch (Exception e1) {
            logger.error("[ERROR][ACCOUNT] ", e1);
        }
        if (newAccount == null) {
            return new ResponseResult(updateRes, null, account);
        }
        return new ResponseResult(updateRes, newAccount, oldAccount);
    }


    
    @RequestMapping("/findProductById.action")
    @ResponseBody
    public Product findProductByIds(Integer id) {
        Product p = null;
        try {
            p = productService.findProductByIds(id);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] find product error.", e);
        }
        return p;
    }

    @RequestMapping("/findAccountById.action")
    @ResponseBody
    public Application findAccountById(Integer id) {
        logger.debug("[BINS][ACCOUNT] id=" + id);
        Application a = null;
        try {
            a = applicationService.findApplicationById(id);
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        return a;
    }

    
    @RequestMapping("/findAllParent.action")
    @ResponseBody
    public Map<String, Object> findAllParent() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Application> list = new ArrayList<>();
        try {
            list = applicationService.findAllParent();
        } catch (Exception e) {
            logger.error("[ERROR][ACCOUNT] ", e);
        }
        map.put("data", list);
        return map;
    }


    
    @DataOperate(bussiness = Bussiness.account, operation = Operate.UPDATE)
    @RequestMapping(value = "chargeAccount.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult chargeAccount(HttpServletRequest request, HttpServletResponse response, DataFinance dataFinance) {
        logger.debug("[BINS][CHARGEACCOUNT] dataFinance=" + dataFinance);
        Application oldAccount = null;
        Application newAccount = null;
        int result = 0;
        if (dataFinance == null) {
            return new ResponseResult(result, newAccount, oldAccount);
        }
        try {
            int chargeNum = Math.abs(dataFinance.getChangeNum());
            String changeType = dataFinance.getChangeType();
            if (changeType.equals("核减") || changeType.equals("扣罚") || changeType.equals("退款")) {
                chargeNum = 0 - chargeNum;
            }
            dataFinance.setChangeNum(chargeNum);
            UcenterManager ucenterManager = (UcenterManager) request.getSession()
                    .getAttribute(Constant.SERVER_USER_SESSION);
            dataFinance.setAddName(ucenterManager.getManagerName());
            dataFinance.setInitTime(sdf.parse(sdf.format(new Date())));
            dataFinance.setAddTime(sdf.parse(sdf.format(new Date())));
            dataFinance.setDueFrom(dataFinance.getPrice() * chargeNum / 100);
            oldAccount = applicationService.findApplicationById(dataFinance.getAppId());
            result = applicationService.chargeAccount(dataFinance);
            newAccount = applicationService.findApplicationById(dataFinance.getAppId());
        } catch (Exception e) {
            logger.error("[ERROR][CHARGEACCOUNT] exce=", e);
        }
        return new ResponseResult(result, newAccount, oldAccount);
    }

    
    @RequestMapping("/findAccountSub.action")
    @ResponseBody
    public List<Application> findAccountSub(Integer id) {
        List<Application> apps = null;
        try {
            apps = applicationService.findAccountSub(id);
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION]", e);
        }
        return apps;
    }

    
    @RequestMapping("/validatorAccountIsUse.action")
    @ResponseBody
    public Integer validatorAccountIsUse(String id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("appId", id);
        Integer findTotal = null;
        try {
            findTotal = extSignService.findTotal(map);
        } catch (Exception e) {
            logger.error("[ERROR][EXT_SIGN]", e);
        }
        if (findTotal != null && findTotal > 0) {
            return 2;
        }
        return 0;
    }

    
    @RequestMapping("/changeCompany.action")
    @ResponseBody
    public Application changeCompany(Integer appId) {
        logger.debug("[BINS][APPLICATION] appId=" + appId);
        if (appId == null) {
            return null;
        }
        Application application = null;
        try {
            application = applicationService.findApplicationById(appId);
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION] ", e);
        }
        return application;
    }

    
    @RequestMapping("/findSubList.action")
    @ResponseBody
    public List<Application> findSubList(Integer id) {
        List<Application> apps = new ArrayList<>();
        try {
            apps = applicationService.findAccountSub(id);
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION] ", e);
        }
        return apps;
    }
}
