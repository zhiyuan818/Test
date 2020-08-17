package com.jjxt.ssm.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jjxt.ssm.utils.*;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jjxt.ssm.common.HttpService;
import com.jjxt.ssm.entity.BaseChannelSupplier;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.Model;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.entity.Variant;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ChannelSupplierService;
import com.jjxt.ssm.service.LgModelSendService;
import com.jjxt.ssm.service.ProductService;
import com.jjxt.ssm.service.UcenterManagerService;


@Controller
@RequestMapping("/channel")
public class ChannelController {

	private static Logger logger = Logger.getLogger(ChannelController.class);

	private static final String PATH = "channel/";
	@Autowired
	private ChannelService channelService;
	@Value("${account}")
	private String account;
	@Value("${password}")
	private String password;
	@Value("${channelTest1}")
	private String channelTest1;
	@Value("${channelTest2}")
	private String channelTest2;
	@Value("${platformFlag}")
	private String platformFlag;
	@Autowired
	private HttpService httpService;
	@Autowired
	private ProductService productService;
	@Autowired
	private LgModelSendService lgModelSendService;
	@Autowired
	private ChannelSupplierService channelSupplierService;
	@Autowired
	private UcenterManagerService ucenterManagerService;
	/**
	 * 跳转通道列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goChannelPageList.action")
	public String goChannelPageList(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String,Object>();
		List<Channel> findChannel = null;
		List<Variant> variants=null;
		List<Model> models=null;
		List<Model> logics=null;
		List<Channel> findNormalChannel = new ArrayList<>();
		List<BaseChannelSupplier> baseChannelSuppliers = null;
		List<UcenterManager> heads = new ArrayList<>();
		String normalChannel = "";
		try {
			findChannel = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		try {
			findNormalChannel = channelService.findNormalChannel();
			normalChannel = JSON.toJSONString(findNormalChannel);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		try {
			variants=channelService.findVariant();
		} catch (Exception e) {
			logger.error("[ERROR][VARIANT]",e);
		}
		
		try {
			map.clear();
			map.put("type", "model");
			models = channelService.findModel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MODEL]",e);
		}
		try {
			map.clear();
			map.put("type", "logic");
			logics = channelService.findModel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MODEL]",e);
		}
		try {
			baseChannelSuppliers = channelSupplierService.findAllChannelSupplier();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		try {
			heads = ucenterManagerService.findHeadManager();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_SUPPLIER] ", e);
		}
		request.setAttribute("models", models);
		request.setAttribute("logics", logics);
		request.setAttribute("channels", findChannel);
		request.setAttribute("variants", variants);
		request.setAttribute("normalChannel", normalChannel);
		request.setAttribute("normalChannelList", findNormalChannel);
		request.setAttribute("suppliers", baseChannelSuppliers);
		request.setAttribute("heads", heads);
		return PATH + "channelPageList";
	}

	/**
	 * 通道分页
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	@RequestMapping("/findChannelPageList.action")
	@ResponseBody
	public PageResult<Channel> findChannelPageList(Integer pageIndex, Integer pageSize,String supplierId,String head,String channelId,String channelStatus,
			String account,String svcAddr,String serviceCode,String enterpriseCode,String variant,String platformFlag,String allPlatformUsed) {
		logger.debug("[BINS][CHANNEL] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",supplierId="+supplierId+",channelId="+channelId+",channelStatus="+channelStatus);
		int total = 0;
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("supplierId", supplierId);
		map1.put("head", head);
		map1.put("channelId", channelId);
		map1.put("channelStatus", channelStatus);
		map1.put("account", account);
		map1.put("svcAddr", svcAddr);
		map1.put("serviceCode", serviceCode);
		map1.put("enterpriseCode", enterpriseCode);
		map1.put("variant", variant);
		map1.put("platformFlag", platformFlag);
		map1.put("allPlatformUsed", allPlatformUsed);
		try {
			total = channelService.findTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 查询通道总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("supplierId", supplierId);
		map.put("head", head);
		map.put("channelId", channelId);
		map.put("channelStatus", channelStatus);
		map.put("account", account);
		map.put("svcAddr", svcAddr);
		map.put("serviceCode", serviceCode);
		map.put("enterpriseCode", enterpriseCode);
		map.put("variant", variant);
		map.put("platformFlag", platformFlag);
		map.put("allPlatformUsed", allPlatformUsed);
		List<Channel> channels = new ArrayList<Channel>();
		try {
			channels = channelService.findChannelPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通道列表异常", e);
		}
		return new PageResult<>(total, channels);
	}

	/**
	 * 通道添加提交
	 * 
	 * @param channel
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.channel, operation = Operate.INSERT)
	@RequestMapping("/addChannel.action")
	@ResponseBody
	public ResponseResult addChannel(Channel channel) {
		logger.debug("[BINS][CHANNEL] channel=" + channel);
		channel.setCallOut("");
		channel.setSvcAddr("");
		channel.setAccount("");
		channel.setPassword("");
		channel.setLinkMax(0);
		channel.setLinkSpeed(0);
		channel.setServiceCode("");
		channel.setEnterpriseCode("");
		channel.setExtras("");
		channel.setShortNum("");
		channel.setVariant("");
		channel.setModel("");
		channel.setLogicModel("");
		channel.setAlarmCode("");
		channel.setSubmitAlarmCode("");
		channel.setChannelStatus("paused");
		channel.setToCmcc(channel.getToCmcc()==null?"no":"yes");
		channel.setToUnicom(channel.getToUnicom()==null?"no":"yes");
		channel.setToTelecom(channel.getToTelecom()==null?"no":"yes");
		channel.setPlatformFlag(platformFlag);
		channel.setAllPlatformUsed("no");
		Integer channelId = null;
		try {
			channelId = channelService.findMaxChannelId();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 查询最后添加通道 异常", e);
		}
		channel.setChannelId(channelId==null?0:channelId + 1);
		int i = 0;
		try {
			i = channelService.addChannel(channel);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] add channel exception", e);
		}
		List<Channel> newData = null;
		try {
			newData = channelService.findEndAddChannel();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 查询最后添加通道 异常", e);
		}
		return new ResponseResult(i, newData, null);
	}

	/**
	 * 通道名称验证
	 * 
	 * @param name
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("/validatorChannel.action")
	public ResultData validatorChannel(String name,String oldName) {
		ResultData result = new ResultData();
		if(name.equals(oldName)){
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("validateName", name);
		int total = 0;
		try {
			total = channelService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}

	/**
	 * 通道修改值回显
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findChannelById.action")
	@ResponseBody
	public Channel findChannelById(Integer id) {
		List<Channel> channels = null;
		try {
			channels = channelService.findChannelById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		return channels == null ? null : channels.get(0);
	}

	/**
	 * 通道修改提交
	 */
	
	@DataOperate(bussiness = Bussiness.channel, operation = Operate.UPDATE)
	@RequestMapping("/updateChannelBasis.action")
	@ResponseBody
	public ResponseResult updateChannelBasis(Channel channel) {
		channel.setToCmcc(channel.getToCmcc()==null?"no":channel.getToCmcc());
		channel.setToUnicom(channel.getToUnicom()==null?"no":channel.getToUnicom());
		channel.setToTelecom(channel.getToTelecom()==null?"no":channel.getToTelecom());
		logger.debug("[BINS][CHANNEL] channel=" + channel);
		logger.debug("[BINS][CHANNEL] channel.submitAlarmCode=" + channel.getSubmitAlarmCode());
		List<Channel> oldData = null;
		try {
			oldData = channelService.findChannelById(channel.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通过ID查询对象异常", e);
		}
		int i = 0;
		try {
			i = channelService.updateChannel(channel);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] update channel exception", e);
		}
		List<Channel> newData = null;
		try {
			newData = channelService.findChannelById(channel.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通过ID查询对象异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}
	
	@DataOperate(bussiness = Bussiness.channel, operation = Operate.UPDATE)
	@RequestMapping("/updateChannelLevel.action")
	@ResponseBody
	public ResponseResult updateChannelLevel(Channel channel,String extras) {
		logger.debug("[BINS][CHANNEL] channel=" + channel+",extras="+extras);
		channel.setAccount(channel.getAccount().trim());
		channel.setPassword(channel.getPassword().trim());
		channel.setExtras(extras);
		List<Channel> oldData = null;
		try {
			oldData = channelService.findChannelById(channel.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通过ID查询对象异常", e);
		}
		int i = 0;
		try {
			i = channelService.updateChannel(channel);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] update channel exception", e);
		}
		List<Channel> newData = null;
		try {
			newData = channelService.findChannelById(channel.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] 通过ID查询对象异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}

	/**
	 * 跳转通道测试页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goChannelTest.action")
	public String goChannelTest(HttpServletRequest request) {
		List<Channel> findChannel = null;
		try {
			findChannel = channelService.findNormalChannel();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ", e);
		}
		request.setAttribute("channels", findChannel);
		return PATH + "channelTest";
	}

	/**
	 * 通道测试提交
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/channelTestSubmit.action")
	@ResponseBody
	public String channelTestSubmit(HttpServletRequest request,String channelId, String extNumber, String destNumber, String content) {
		logger.debug("[BINS][CHANNEL_TEST] channelId=" + channelId + ",extNumber=" + extNumber + ",destNumber="
				+ destNumber + ",content=" + content);
		String[] phoneNumberArr = destNumber.replace(" ", "").split(",");
	    List<String> list = Arrays.asList(phoneNumberArr);
	    Set<String> set = new HashSet<String>(list);
	    phoneNumberArr=(String [])set.toArray(new String[0]);
		StringBuffer sf=new StringBuffer();
		for(String phone:phoneNumberArr){
			sf.append(",").append(phone);
		}
		if(sf.length()==0){
			logger.debug("[BINS][CHANNEL_TEST] dest 为空");
			return "-8888";
		}
		logger.debug("[BINS][CHANNEL_TEST] dest size "+phoneNumberArr.length);
		String phones = sf.toString().substring(1);
		Map<String, Object> map=new HashMap<String,Object>();
		HttpSession session = request.getSession();
		UcenterManager manager = (UcenterManager) session.getAttribute(Constant.SERVER_USER_SESSION);
		String ts = DateUtil.getTimeStamp();
		String token=StringUtil.sha1(account+password+ts);
		map.put("account", account);
		map.put("token", token);
		map.put("ts", ts);
		map.put("dest", phones);
		map.put("content", content);
		map.put("ref", manager.getChineseName());
		map.put("forcedext", extNumber);
		map.put("forcedchannelid", channelId);
		String result=null;
		try {
			result=httpService.doBooleanPost(httpService,channelTest1, channelTest2, map);
		} catch (URISyntaxException | IOException e) {
			logger.error("[ERROR][CHANNELSERVICE]",e);
		}
		logger.debug("[BINS][CHANNEL_TEST] result="+result);
		if(result==null){
			logger.debug("[BINS][CHANNEL_TEST] connection time out ");
			return "-9999";
		}
		Map<String, Object> param = new HashMap<String, Object>();
		Gson gson = new Gson();
		if(result!=null){
			param = gson.fromJson(result, param.getClass());
		}
		String status = param.get("status").toString();
		if("0.0".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] success");
			return result;
		}else if("-2001".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Insufficient account balance.");
			return result;
		}else if("-2002".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Account is disabled.");
			return result;
		}else if("-2003".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Account is not in service time.");
			return result;
		}else if("-2010".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Account query action is too frequent.");
			return result;
		}else if("-2101".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Product not configured.");
			return result;
		}else if("-2102".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Product is paused.");
			return result;
		}else if("-2103".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Not in product service time.");
			return result;
		}else if("-2104".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Product is not a kind of SMS or MMS.");
			return result;
		}else if("-2201".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Channel not found.");
			return result;
		}else if("-2202".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Channel not in service");
			return result;
		}else if("-2301".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Missing destination number.");
			return result;
		}else if("-2302".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Invalid destination numbers.");
			return result;
		}else if("-2303".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] http包超长");
			return result;
		}else if("-2401".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Invalid extension number.");
			return result;
		}else if("-2402".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] 错误时间格式");
			return result;
		}else if("-2501".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Missing content.");
			return result;
		}else if("-2502".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Content exceeds limit.");
			return result;
		}else if("-2503".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Missing signature in content.");
			return result;
		}else if("-2504".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Rejected due to the keywords.");
			return result;
		}else if("-2505".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] Number of destination doesn't match the number of content for apersonalized message.");
			return result;
		}else if("-2999".equals(status)){
			logger.debug("[BINS][CHANNEL_TEST] 未知");
			return result;
		}else{
			return result;
		}
	}
	
	@ResponseBody
	@RequestMapping("/findModel.action")
	public List<Model> findModel(String type){
		Map<String, Object> map=new HashMap<String,Object>();
		map.put("type", type);
		List<Model> models=null;
		try {
			models = channelService.findModel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MODEL]",e);
		}
		return models;
	}
	
	
	@RequestMapping("/findLinkProduct.action")
	@ResponseBody
	public List<Map<String,Object>> findLinkProduct(Integer id) {
		logger.debug("[BINS][CHANNEL] id=" + id);
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = productService.findLinkProduct(id);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL] ", e);
		}
		return result;
	}
	@RequestMapping("/channelIsUse.action")
	@ResponseBody
	public Integer  findchannelIsUse(Integer id){
		logger.debug("[BINS][CHANNEL] channelId="+id);
		Map<String, String> map=new HashMap<String,String>();
		map.put("channelId", id.toString());
		Integer pros=null;
		try {
			pros=productService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ",e);
		}
		Integer models=null;
		try {
			models = lgModelSendService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][LGMODELSEND]",e);
		}
		if(pros!=null && pros>0 && models != null && models > 0){
			return 3;
		}
		if(pros!=null && pros>0){
			return 2;
		}
		if(models != null && models > 0){
			return 1;
		}
		return 0;
	}
	
	@RequestMapping("/findChannelAllConfigByChannelId.action")
	@ResponseBody
	public List<Channel>  findChannelAllConfigByChannelId(Integer id){
		logger.debug("[BINS][CHANNEL] findChannelAllConfigByChannelId channelId="+id);
		List<Channel> list = new ArrayList<>();
		try {
			list=channelService.findChannelAllConfigByChannelId(id);
			for(int i=1; i<=list.size(); i++){
				list.get(i-1).setId(i);
			}
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL] ",e);
		}
		return list;
	}
	
	@DataOperate(bussiness = Bussiness.channel, operation = Operate.UPDATEBATCH)
	@RequestMapping("/updateChannelCongifg.action")
	@ResponseBody
	public ResponseResult updateChannelCongifg(String array){
		logger.debug("[BINS][CHANNEL] updateChannelCongifgBy"+ array);
		 JSONArray jsonArray = JSONArray.fromObject(array);
		 String type = null;
		 String provider = null;
		 Integer newChannelId = 0;
		 Integer consumerId = 0;
		 Integer channelId = 0;
		 int resultSum = 0;
		 int result = 0;
//		 Channel channel = null;
		 Map<String,Object> map  = new HashMap<>();
		 Map<String,Object> newmap  = new HashMap<>();
		 List<Channel> oldList = new ArrayList<>();
		 List<Map<String, Object>> newList = new ArrayList<>();
		 Channel oldData = new Channel();
		 Channel newData = new Channel();
		for(int i = 0; i < jsonArray.size(); i++) {
			 type = jsonArray.getJSONObject(i).get("type") == null ? null : String.valueOf(jsonArray.getJSONObject(i).get("type"));
			 provider = jsonArray.getJSONObject(i).get("provider") == null ? null : String.valueOf(jsonArray.getJSONObject(i).get("provider"));
			 newChannelId = jsonArray.getJSONObject(i).get("newChannelId") == null ? -1 : Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("newChannelId")));
			 consumerId = jsonArray.getJSONObject(i).get("consumerId") == null ? -1 : Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("consumerId")));
			 channelId = jsonArray.getJSONObject(i).get("channelId") == null ? -1 : Integer.valueOf(String.valueOf(jsonArray.getJSONObject(i).get("channelId")));
			if(type == null || provider == null || newChannelId == -1 || consumerId == -1 || channelId == -1) {
				continue;
			}
			map.put("type", type);
			map.put("provider", provider);
			map.put("consumerId", consumerId);
			map.put("channelId", channelId);
			map.put("newChannelId", newChannelId);
			
			newmap.put("type", type);
			newmap.put("provider", provider);
			newmap.put("consumerId", consumerId);
			newmap.put("channelId", newChannelId);
			
			//确认运营商
			try {
				result=channelService.findConfirmProvider(map);
				if(result == 0) {
					continue;
				}else {
					result = 0;
				}
			} catch (Exception e) {
				logger.error("[ERROR][CHANNEL] findConfirmProvider old error =  ",e);
			}
			
			if("主用".equals(type)) {
				try {
					oldData = channelService.findMainData(map);
					if(oldData != null) {
						oldList.add(oldData);
						//通过consumerId(产品ID)和provider修改该产品所对应的唯一通道
						result = channelService.updateProductCongifgByChannelId(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateProductCongifgByChannelId error =  ",e);
				}
			}else if("按账户重发".equals(type)){
				//处理三网通道
				try {
					oldData = channelService.findAccResendData(map);
					if(oldData != null) {
						oldList.add(oldData);
						//通过consumerId(账户ID)和原重发通道 修改重发中该配置对应的所有通道
						result = channelService.updateAppResendCongifgByChannelId(map);
						if(result > 1) {
							result = 1;
						}
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateAppResendCongifgByChannelId error =  ",e);
				}
			}else if("按通道重发".equals(type)){
				//处理三网通道
				try {
					oldData = channelService.findChanResendData(map);
					if(oldData != null) {
						oldList.add(oldData);
						//通过consumerId(通道ID)和原重发通道修改重发中该配置对应的所有通道
						result = channelService.updateChannelResendCongifgByChannelId(map);
						if(result > 1) {
							result = 1;
						}
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateChannelResendCongifgByChannelId error =  ",e);
				}
			}else if(type.startsWith("分省-")){
				try {
					map.put("province", type.substring(3));
					newmap.put("province", type.substring(3));
					oldData = channelService.findProvincesSendData(map);
					if(oldData != null) {
						oldList.add(oldData);
						//通过consumerId(产品ID)、provider和province修改分省发送中对应的唯一通道
					result = channelService.updateProvincesSendByChannelId(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateProvincesSendByChannelId error =  ",e);
				}
			}else if(type.startsWith("定向模板")){
				try {
					oldData = channelService.findModelSendData(map);
					if(oldData != null){
						oldList.add(oldData);
						result = channelService.updateModelSendByChannelId(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateProductCongifgByChannelId error =  ",e);
				}
			}else if(type.startsWith("策略模板")){
				//获取跳转规则
				String strategy = "";
				try {
					oldData = channelService.findTemplateData(map);
					if(oldData != null){
						oldList.add(oldData);
						strategy = channelService.findStrategyFromTemplate(map);
						if("cmcc".equals(provider)){
							strategy = strategy.replace("cmcc:"+channelId, "cmcc:"+newChannelId);
						}else if("unicom".equals(provider)){
							strategy = strategy.replace("unicom:"+channelId, "unicom:"+newChannelId);
						}else if("telecom".equals(provider)){
							strategy = strategy.replace("telecom:"+channelId, "telecom:"+newChannelId);
						}
						map.put("result", strategy);
						result = channelService.updateMsgTemplateByChannelId(map);
					}
				} catch (Exception e) {
					logger.error("[ERROR][CHANNEL] updateMsgTemplateByChannelId error =  ",e);
				}
			}else {
				continue;
			}
			oldData = null;
			newList.add(map);
			resultSum = result + resultSum;
			result = 0;
			map.clear();
			newmap.clear();
			logger.debug("[BINS][CHANNEL][updateChannelCongifg] type="+type+" provider=" + provider+" consumerId=" + consumerId+" channelId=" + channelId+" newChannelId=" + newChannelId+" result=" + result+" resultSum=" + resultSum+" i=" + i);
		}
		if(resultSum == 0) {
			//全部失败
			return new ResponseResult(3, newList, oldList);
		}
		if (resultSum ==  jsonArray.size()) {
			//全部成功
			return new ResponseResult(1, newList, oldList);
		}else {
			//存在部分失败
			return new ResponseResult(2, newList, oldList);
		}
		
	}
}
