package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.GrossProfitService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/statistics")
public class GrossProfitController {

	private static Logger logger = Logger.getLogger(GrossProfitController.class);

	private static final String PATH = "statistics/";

	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BaseCompanyService companyService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private GrossProfitService grossProfitService;

	@RequestMapping("/goGrossProfit.action")
	public String goGrossProfit(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map=new HashMap<>();
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
                .getAttribute(Constant.SERVER_USER_SESSION);
		if(ucenterManager != null) {
			map.put("chineseName", ucenterManager.getChineseName());
			map.put("title", ucenterManager.getTitle());
			map.put("isAllCustomer", ucenterManager.getIsAllCustomer());
			map.put("isAllChannel", ucenterManager.getIsAllChannel());
		}
		List<BaseCompany> companys = new ArrayList<>();
		try {
			companys = companyService.findCompanyKeyBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][GROSS_PROFIT] ", e);
		}
		
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][GROSS_PROFIT] ", e);
		}
		
		List<Channel> channels = new ArrayList<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannelNameBySort(map);
		} catch (Exception e) {
			logger.error("[ERROR][GROSS_PROFIT] ", e);
		}
		
		request.setAttribute("companys", companys);
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "grossProfit";
	}

	
	@ResponseBody
	@RequestMapping("/findAccountProfitList.action")
	public List<Statistics> findAccountProfitList(String companyKey,String appId,String channelId,String provider,String type, HttpServletRequest request, HttpServletResponse response){
		logger.debug("[BINS][APP_GROSS_PROFIT]companyKey="+companyKey+",appId="+appId+",channelId="+channelId+",provider="+provider+",type="+type);
		
		if(StringUtil.isEmpty(companyKey) && StringUtil.isEmpty(appId)){
			return null;
		}
		
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("companyKey", companyKey);
		map.put("appId", appId);
		map.put("channelId", channelId);
		map.put("provider", provider);
		map.put("type", type);
		List<Statistics> statisticsList=new ArrayList<>();
		try {
			statisticsList = grossProfitService.findAccountProfitList(map);
		} catch (Exception e) {
			logger.error("[ERROR][APP_GROSS_PROFIT] put mt pageList is error", e);
		}
		
		List<Application> appList = new ArrayList<>();
        try {
        	appList = grossProfitService.findApplication(map);
        } catch (Exception e) {
            logger.error("[ERROR][APP_GROSS_PROFIT] ", e);
        }
        
        List<LgModelSend> lgModelSendList = new ArrayList<>();
		try {
			lgModelSendList = grossProfitService.findLgModelSendList(map);
		} catch (Exception e) {
			logger.error("[ERRPR][APP_GROSS_PROFIT]  ",e);
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<br>定向模板<br>");
		for (LgModelSend lgModelSend : lgModelSendList) {
			sb.append(lgModelSend.getContent());
			if(lgModelSend.getCmccChannelId() > 0 ) {
				sb.append(",移动发到=").append(lgModelSend.getCmccChannelId());
			}
			if(lgModelSend.getUnicomChannelId() > 0 ) {
				sb.append(",联通发到=").append(lgModelSend.getUnicomChannelId());
			}
			if(lgModelSend.getTelecomChannelId() > 0 ) {
				sb.append(",电信发到=").append(lgModelSend.getTelecomChannelId());
			}
			sb.append("<br>");
		}
        
		Map<String, String> map1=new HashMap<String,String>();
		StringBuffer content = new StringBuffer();
        try {
        	for (Application app : appList) {
        		content.append("账号=").append(app.getId()).append(":").append(app.getAppName())
        		.append((!StringUtil.isEmpty(app.getIsTemplate()) && app.getIsTemplate().equalsIgnoreCase("yes"))?",过策略模板":"")
        		.append((!StringUtil.isEmpty(app.getSkipMustWords()) && app.getSkipMustWords().equalsIgnoreCase("yes"))?",跳过敏感词":"")
        		.append((!StringUtil.isEmpty(app.getIsModel()) && app.getIsModel().equalsIgnoreCase("yes"))?",过模板":"")
        		.append((!StringUtil.isEmpty(app.getCheckWordsType()) && app.getCheckWordsType().equalsIgnoreCase("yes"))?",过必审词":"")
        		.append((!StringUtil.isEmpty(app.getIsShuntPhone()) && app.getIsShuntPhone().equalsIgnoreCase("yes"))?",过实号库":"")
        		.append((!StringUtil.isEmpty(app.getIsDayLimit()) && app.getIsDayLimit().equalsIgnoreCase("yes"))?",日限条数="+app.getDayLimitCount():"")
        		.append((!StringUtil.isEmpty(app.getIsMinLimit()) && app.getIsMinLimit().equalsIgnoreCase("yes"))?",分钟限(秒数)="+app.getMinLimitCount():"")
        		.append((!StringUtil.isEmpty(app.getIsDayContentLimit()) && app.getIsDayContentLimit().equalsIgnoreCase("yes"))?",内容限数="+app.getDayLimitContentCount():"")
        		.append((!StringUtil.isEmpty(app.getIsContAudit()) && app.getIsContAudit().equalsIgnoreCase("yes"))?",内容审核条数="+app.getContAuditCount():"")
        		.append((!StringUtil.isEmpty(app.getIsMonthLimit()) && app.getIsMonthLimit().equalsIgnoreCase("yes"))?",月限条数="+app.getMonthLimitCount():"")
        		.append((!StringUtil.isEmpty(app.getIsDayLimitCheck()) && app.getIsDayLimitCheck().equalsIgnoreCase("yes"))?",日限进审核":"")
        		.append((!StringUtil.isEmpty(app.getIsDefaultSignSubmit()) && app.getIsDefaultSignSubmit().equalsIgnoreCase("yes"))?",非默认签名拦截":"")
        		.append("<br>");
        		
        		Integer[] ids = new Integer[] {0,app.getId()};
        		List<Template> templateList = grossProfitService.findTemplatePageListByIds(ids);
        		
        		content.append("<br>策略模板<br>");
        		for (Template template : templateList) {
        			if(template.getAppId() == 0) {
        				content.append("账号=全局");
        			}else {
        				content.append("账号=").append(template.getAppId()).append(":").append(template.getAppName());
        			}
        			content.append(",规则=").append(template.getRule()).append(",说明=").append(template.getTemplateInfo())
        			.append(",策略=").append(template.getStrategy()).append(",回执状态=").append(template.getResult())
        			.append(",标记=").append(template.getKeyWord()).append(",优先级=").append(template.getRuleIndex())
        			.append("<br>");
        		}
        		content.append(sb);
        		
        		map1.put(app.getId()+":"+app.getAppName(), content.toString());
        		content.setLength(0);
			}
        } catch (Exception e) {
            logger.error("[ERROR][APP_GROSS_PROFIT] ", e);
        }
        
        boolean flag = true;
        for (Entry<String, String> entry : map1.entrySet()) {
        	flag = true;
        	String key = entry.getKey();
        	int appid= Integer.parseInt(key.split(":")[0]);
        	String value = entry.getValue();
        	
        	
        	for (Statistics statistics : statisticsList) {
        		if(appid == statistics.getAppId()){
	            	flag= false;
	            	statistics.setContent(value);
	            	continue;
	            }
			}
        	
	        if(flag) {
	        	Statistics stat = new Statistics();
	        	stat.setAppId(appid);
	        	stat.setAppName(key.split(":")[1]);
	        	stat.setContent(value);
	        	statisticsList.add(stat);
	        }
        }
        
		return statisticsList;
	}
	
	
	@ResponseBody
	@RequestMapping("/findChannelProfitList.action")
	public List<Statistics> findChannelProfitList(String channelId,String appId,String provider,String type, HttpServletRequest request, HttpServletResponse response){
		logger.debug("[BINS][CHANNEL_GROSS_PROFIT] appId="+appId+",channelId="+channelId+",provider="+provider+",type="+type);
		
		if(StringUtil.isEmpty(channelId)){
			return null;
		}
		
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("appId", appId);
		map.put("channelId", channelId);
		map.put("provider", provider);
		map.put("type", type);
		List<Statistics> statisticsList=new ArrayList<>();
		try {
			statisticsList = grossProfitService.findChannelProfitList(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_GROSS_PROFIT] put mt pageList is error", e);
		}
		
		Channel channel = null;
		try {
			channel = channelService.findChannelByChannelId(Integer.parseInt(channelId));
        } catch (Exception e) {
            logger.error("[ERROR][CHANNEL_GROSS_PROFIT] ", e);
        }
		StringBuffer content = new StringBuffer();
		content.append("通道=").append(channel.getChannelId()).append(":").append(channel.getName())
		.append((!StringUtil.isEmpty(channel.getIsMonthLimit()) && channel.getIsMonthLimit().equalsIgnoreCase("yes"))?",月限条数="+channel.getMonthLimitCount():"")
		.append("<br>");
        
        List<LgModelSend> lgModelSendList = new ArrayList<>();
		try {
			lgModelSendList = grossProfitService.findLgModelSendList(map);
		} catch (Exception e) {
			logger.error("[ERRPR][CHANNEL_GROSS_PROFIT]  ",e);
		}
		
		content.append("<br>定向模板<br>");
		for (LgModelSend lgModelSend : lgModelSendList) {
			content.append(lgModelSend.getContent());
			if(lgModelSend.getCmccChannelId() > 0 ) {
				content.append(",移动发到=").append(lgModelSend.getCmccChannelId());
			}
			if(lgModelSend.getUnicomChannelId() > 0 ) {
				content.append(",联通发到=").append(lgModelSend.getUnicomChannelId());
			}
			if(lgModelSend.getTelecomChannelId() > 0 ) {
				content.append(",电信发到=").append(lgModelSend.getTelecomChannelId());
			}
			content.append("<br>");
		}
		
		List<Template> templateList= new ArrayList<>();
		try {
			templateList = grossProfitService.findAllTemplate();
		} catch (Exception e) {
			logger.error("[ERRPR][CHANNEL_GROSS_PROFIT]  ",e);
		}
        
		content.append("<br>策略模板<br>");
		for (Template template : templateList) {
			if(template.getAppId() == 0) {
				content.append("账号=全局");
			}else {
				content.append("账号=").append(template.getAppId()).append(":").append(template.getAppName());
			}
			content.append(",规则=").append(template.getRule()).append(",说明=").append(template.getTemplateInfo())
			.append(",策略=").append(template.getStrategy()).append(",回执状态=").append(template.getResult())
			.append(",标记=").append(template.getKeyWord()).append(",优先级=").append(template.getRuleIndex())
			.append("<br>");
		}
		
		for (Statistics statistics : statisticsList) {
			statistics.setContent(content.toString());
		}
		return statisticsList;
	}
	
	
}
