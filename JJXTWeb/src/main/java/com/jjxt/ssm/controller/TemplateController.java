package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.SignExtTemplate;
import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ExtSignService;
import com.jjxt.ssm.service.TemplateService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.MsgTemplateUtils;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.ResultData;
import com.jjxt.ssm.utils.StringUtil;

@RequestMapping("/template")
@Controller
public class TemplateController {

	private static Logger logger = Logger.getLogger(RepController.class);

	private static final String PATH = "template/";
	
	@Autowired
	private TemplateService templateService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ExtSignService signExtService;
	
	@RequestMapping("/goTemplateList.action")
	public String goTemplateList(HttpServletRequest request) {
		List<Application> apps=new ArrayList<Application>();
		try {
			apps=applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERR][APPS] EX="+e);
		}
		List<Channel> channels = new ArrayList<Channel>();
		List<Channel> cmccChannels =  new ArrayList<Channel>();
		List<Channel> unicomChannels =  new ArrayList<Channel>();
		List<Channel> telecomChannels =  new ArrayList<Channel>();
		Map<String, Object> map=new HashMap<String,Object>();
		
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL]", e);
		}
		for(Channel cha:channels){
			if("yes".equals(cha.getToCmcc())){
				cmccChannels.add(cha);
			}
			if("yes".equals(cha.getToUnicom())){
				unicomChannels.add(cha);
			}
			if("yes".equals(cha.getToTelecom())){
				telecomChannels.add(cha);
			}
		}
		request.setAttribute("channels", channels);
		request.setAttribute("cmccChannels", cmccChannels);
		request.setAttribute("unicomChannels", unicomChannels);
		request.setAttribute("telecomChannels", telecomChannels);
		request.setAttribute("apps", apps);
		return PATH+"templateList";
	}

	
	@RequestMapping("/findTemplateList.action")
	@ResponseBody
	public PageResult<Template> findTemplateList(Integer pageIndex,Integer pageSize,String appId,String rule,String templateInfo,String strategy,String status,String id,String result){
		logger.debug("[BINS][TEMPLATE] pageIndex="+pageIndex+",pageSize="+pageSize+",appId="+appId+",rule="+rule+",templateInfo="+templateInfo+",strategy="+strategy+",id="+id+",result="+result);
		Map<String, String> map=new HashMap<String,String>();
		map.put("appId", appId);
		map.put("rule", rule);
		map.put("templateInfo", templateInfo);
		map.put("strategy", strategy);
		map.put("status", status);
		map.put("id", id);
		map.put("result", result);
		int total=0;
		try {
			total = templateService.findTemplateTotal(map);
		} catch (Exception e) {
			logger.error("[ERR][TEMPLATE] EX="+e);
		}
		if(total==0) {
			return new PageResult<>(0, new ArrayList<Template>());
		}
		Page page=new Page(pageSize, total, pageIndex);
		Map<String, Object> param = PageUtil.getDefaultPageMap(page);
		param.put("appId", appId);
		param.put("rule", rule);
		param.put("templateInfo", templateInfo);
		param.put("strategy", strategy);
		param.put("status", status);
		param.put("id", id);
		param.put("result", result);
		List<Template> tempaltes=new ArrayList<Template>();
		try {
			tempaltes=templateService.findTemplatePageList(param);
		} catch (Exception e) {
			logger.error("[ERR][TEMPlATE] EX="+e);
		}
		return new PageResult<>(total, tempaltes);
	}

	
	@RequestMapping("/validatorAppIdAndRule.action")
	@ResponseBody
	public ResultData validatorAppIdAndRule(String appId,String rule) {
		logger.debug("[BINS][VALI_TEMP] appId="+appId+",rule="+rule);
		List<Template> temps=new ArrayList<>();
		Map<String, String> map=new HashMap<String ,String>();
		map.put("appId", appId);
		map.put("rule", rule);
		try {
			temps=templateService.validatorAppIdAndRule(map);
		} catch (Exception e) {
			logger.error("[ERR][TEMPLATE] EX="+e);
		}
		ResultData result=new ResultData();
		if(temps.size()>0) {
			result.setValid(false);
		}else {
			result.setValid(true);
		}
		return result;
	}

	
	@RequestMapping("/addTemplate.action")
	@ResponseBody
	@DataOperate(bussiness=Bussiness.template,operation=Operate.INSERT)
	public ResponseResult addTemplate(String appId,String templateInfo,String strategy,String status,String rule,String keyWord,String result) {
		rule = rule.replaceAll("（", "(");
		rule = rule.replaceAll("）", ")");
		Template template=new Template();
		template.setAppId(Integer.parseInt(appId));
		template.setTemplateInfo(templateInfo);
		template.setRule(rule);
		template.setKeyWord(keyWord);
		template.setResult(result);
		template.setStrategy(strategy);
		template.setStatus(status);
		logger.debug("[BINS][ADD_TEMP] "+template);
		
		Integer index=null;
		if("pass".equals(template.getStrategy())) {
			index=300;
		}else if("reject".equals(template.getStrategy())){
			index=200;
		}else {
			index=100;
		}
		Random random = new Random();
		index+=random.nextInt(99);
		template.setRuleIndex(index);
		int id=0;
		try {
			id=templateService.addTemplate(template);
		} catch (Exception e) {
			logger.error("[ERR][ADD_TEMP] EX="+e);
		}
		logger.debug("[BINS] id="+id);
		template.setId(id);
		if(id>0) {
			return new ResponseResult(1,template,null);
		}
		return new ResponseResult(0,template,null);
	}

	
	@RequestMapping("/findTemplateById.action")
	@ResponseBody
	public Template findTemplateById(String id) {
		logger.debug("[BINS][TEMP] id="+id);
		Template temp=null;
		try {
			temp=templateService.findTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERR][TEMP] EX="+e);
		}
		return temp;
	}

	
	@RequestMapping("/updateTemplate.action")
	@ResponseBody
	@DataOperate(bussiness=Bussiness.template,operation=Operate.UPDATE)
	public ResponseResult updateTemplate(String id,String appId,String templateInfo,String strategy,String rule,String keyWord,String result,String ruleIndex,String status) {
		Template template=new Template();
		template.setId(Integer.parseInt(id));
		template.setAppId(Integer.parseInt(appId));
		template.setTemplateInfo(templateInfo);
		template.setRule(rule);
		template.setKeyWord(keyWord);
		template.setResult(result);
		template.setStrategy(strategy);
		template.setStatus(status);
		if(!StringUtil.isEmpty(ruleIndex)) {
			template.setRuleIndex(Integer.parseInt(ruleIndex));
		}
		logger.debug("[BINS][UPDATE_TEMP] "+template);
		Template oldData=null;
		try {
			oldData=templateService.findTemplateById(String.valueOf(template.getId()));
		} catch (Exception e) {
			logger.error("[ERR][TEMP] id="+template.getId()+" EX="+e);
		}
		int i=0;
		try {
			i=templateService.updateTemplate(template);
		} catch (Exception e) {
			logger.error("[ERR][UPDATE_TEMP] "+template+",EX="+e);
		}
		template.setId(template.getId());
		if(i>0) {
			return new ResponseResult(1, template, oldData);
		}else {
			return new ResponseResult(0, null, oldData);
		}
	}

	
	@RequestMapping("/deleteTemplate.action")
	@ResponseBody
	@DataOperate(bussiness=Bussiness.template, operation=Operate.DELETE)
	public ResponseResult deleteTemplate(String id) {
		logger.debug("[BINS][DEL_TEMP] id="+id);
		int i=0;
		Template oldData=null;
		try {
			oldData=templateService.findTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERR][TEMP] id="+id+" EX="+e);
		}
		try {
			i=templateService.deleteTemplate(id);
		} catch (Exception e) {
			logger.error("[ERR][TEMP] id="+id+" EX="+e);
		}
		if(i>0) {
			return new ResponseResult(i, null, oldData);
		} else {
			return new ResponseResult(i, null, oldData);
		}
	}

	
	@RequestMapping("/checkContent.action")
	@ResponseBody
	public List<Boolean> checkContent(String id,String content){
		logger.debug("[BINS][TEMP] id="+id+",content="+content);
		Template template=null;
		try {
			template=templateService.findTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERR][TEMP] EX="+e);
		}
		String rule=template.getRule();
		String[] contents = content.split("\r\n");
		List<Boolean> result=new ArrayList<Boolean>();
		for(String cont:contents) {
			boolean match = MsgTemplateUtils.match(cont, rule);
			result.add(match);
		}
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.template,operation=Operate.DELETEBATCH)
	@RequestMapping("/delTemplateBatch.action")
	@ResponseBody
	public ResponseResult delTemplateBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		List<Template> oldList = new ArrayList<>();
		int del = 0;
		Long sTime = System.currentTimeMillis();
		try {
			oldList = templateService.findTemplateByIds(ids);
		} catch (Exception e) {
			logger.debug("[ERROR][TEMP] Error getting old data", e);
		}
		try {
			del = templateService.delTemplateBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] Delete batch error", e);
		}
		ResponseResult result = new ResponseResult(del, null, oldList);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	
	@DataOperate(bussiness=Bussiness.template,operation=Operate.PAUSEBATCH)
	@RequestMapping("/pauseTemplateBatch.action")
	@ResponseBody
	public ResponseResult pauseTemplateBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			pau = templateService.pauseTemplateBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] paused batch error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, ids);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.template,operation=Operate.STARTBATCH)
	@RequestMapping("/startTemplateBatch.action")
	@ResponseBody
	public ResponseResult startTemplateBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			str = templateService.startTemplateBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] start batch error", e);
		}
		ResponseResult result = new ResponseResult(str, null, ids);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.template,operation=Operate.START)
	@RequestMapping("/startTemplate.action")
	@ResponseBody
	public ResponseResult startTemplate(Integer id){
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			str = templateService.startTemplate(id);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] start data error", e);
		}
		ResponseResult result = new ResponseResult(str, null, id);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.template,operation=Operate.PAUSE)
	@RequestMapping("/pauseTemplate.action")
	@ResponseBody
	public ResponseResult pauseTemplate(Integer id){
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			pau = templateService.pauseTemplate(id);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] pause data error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, id);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@RequestMapping("/findSignExtDetails.action" )
	@ResponseBody
	public List<SignExtTemplate> findSignExtDetails(String rule){
		logger.debug("[BINS][TEMP] rule="+rule);
		if(StringUtil.isEmpty(rule)){
			return null;
		}
		List<String> signList = new ArrayList<String>();
		String sign = "";
		int sIndex = 0;
		int eIndex = 0;
		String param = rule;
		sIndex = param.indexOf("【");
		eIndex = param.indexOf("】");
		while (sIndex >= 0 && eIndex >= 0 && eIndex > sIndex) {
			sign = param.substring(sIndex, eIndex+1);
			signList.add(sign);
			param = param.substring(eIndex+1);
			sIndex = param.indexOf("【");
			eIndex = param.indexOf("】");
		}
		
		param = rule;
		sIndex = param.indexOf("[");
		eIndex = param.indexOf("]");
		while (sIndex >= 0 && eIndex >= 0 && eIndex > sIndex) {
			sign = param.substring(sIndex, eIndex+1);
			signList.add(sign);
			param = param.substring(eIndex+1);
			sIndex = param.indexOf("[");
			eIndex = param.indexOf("]");
		}
		
		List<SignExtTemplate> resList = new ArrayList<>();
		if(!StringUtil.isEmpty(signList) && signList.size()>0){
			try {
				resList = signExtService.findSignExtBySigns(signList);
			} catch (Exception e) {
				logger.error("[ERROR][TEMP] find signext error", e);
			}
		}
		return resList;
	}
	
}
