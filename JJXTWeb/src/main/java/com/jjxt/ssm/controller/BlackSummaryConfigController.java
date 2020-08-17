package com.jjxt.ssm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BlackSummaryConfig;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BlackSummaryConfigService;

@Controller
@RequestMapping("/black")
public class BlackSummaryConfigController {

	private static Logger logger = Logger.getLogger(BlackSummaryConfigController.class);
	private static final String PATH = "black/";
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private BlackSummaryConfigService blackSummaryConfigService;
	
	private static Pattern MOBILE_NUM_PATTERN = Pattern.compile("^1[0-9]{10}$");

	/**
	 * 跳转页面
	 * 
	 * @return
	 */
	@RequestMapping("/goBlackSummaryConfigPageList.action")
	public String goBlackSummaryConfigPageList(HttpServletRequest request, HttpServletResponse response) {
		List<Application> apps = new ArrayList<>();
		List<BlackSummaryConfig> levels = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
			levels = blackSummaryConfigService.findAlllevel();
		} catch (Exception e) {
			logger.error("[ERROR][APP] ", e);
		}
		request.setAttribute("apps", apps);
		request.setAttribute("levels", levels);
		return PATH + "blackSummaryConfigPageList";
	}

	/**
	 * 分页查询
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @param content
	 * @return
	 */
	
	@RequestMapping("/findBlackSummaryConfigPageList.action")
	@ResponseBody
	public PageResult<BlackSummaryConfig> findBlackSummaryConfigPageList(Integer pageSize, Integer pageIndex, String appId,
			String type, String level, String result, String sendFlag, String status, String phone) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appId=" + appId
				+ ",type=" + type + ",level=" + level + ",result=" + result + ",sendFlag=" + sendFlag + ",status=" + status + ",phone=" + phone);
		int total = 0;
		
		Set<String> levelSet=new HashSet<>();
		if(!StringUtil.isEmpty(phone)) {
			levelSet = RedisUtil.hexistsPipeline(phone, Constants.Common.Redis.Naming.BLACK);
			if(levelSet.size() == 0) {
				levelSet.add("");
			}
		}
		if(!StringUtil.isEmpty(level)) {
			if(levelSet.size() > 0) {
				if(levelSet.contains(level)) {
					levelSet=new HashSet<>();
					levelSet.add(level);
				}else {
					levelSet=new HashSet<>();
					levelSet.add("");
				}
			}else {
				levelSet.add(level);
			}
		}
		
		Map<String, Object> param = new HashMap<>();
		param.put("appId", appId);
		param.put("type", type);
		param.put("level", levelSet);
		param.put("result", result);
		param.put("sendFlag", sendFlag);
		param.put("status", status);
		try {
			total = blackSummaryConfigService.findTotal(param);
		} catch (Exception e) {
			logger.error("[ERROR][BLACKSUMMARYCONFIG]", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appId", appId);
		map.put("type", type);
		map.put("level", levelSet);
		map.put("result", result);
		map.put("sendFlag", sendFlag);
		map.put("status", status);
		map.put("phone", phone);
		List<BlackSummaryConfig> bsc = null;
		try {
			bsc = blackSummaryConfigService.findBlackSummaryConfigPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]", e);
		}
		return new PageResult<>(total, bsc);
	}
	
	@RequestMapping("/validatorAppIdAndTypeAndLevel.action")
	@ResponseBody
	public ResultData validatorAppIdAndTypeAndLevel(String appId,String type,String level) {
		logger.debug("[BINS][VALI_BSC] appId="+appId+",type="+type+",level="+level);
		List<BlackSummaryConfig> bscs=new ArrayList<>();
		Map<String, String> map=new HashMap<String ,String>();
		map.put("appId", appId);
		map.put("type", type);
		map.put("level", level);
		try {
			bscs=blackSummaryConfigService.validatorAppIdAndTypeAndLevel(map);
		} catch (Exception e) {
			logger.error("[ERR][TEMPLATE] EX="+e);
		}
		ResultData result=new ResultData();
		if(bscs.size()>0) {
			result.setValid(false);
		}else {
			result.setValid(true);
		}
		return result;
	}
	
	/**
	 * 新增
	 * 
	 * @param lgModelAudit
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackSummaryConfig, operation = Operate.INSERT)
	@RequestMapping("addBlackSummaryConfig.action")
	@ResponseBody
	public ResponseResult addBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] " + blackSummaryConfig);
		Long startTime = System.currentTimeMillis();
		
		blackSummaryConfig.setStatus("normal");
		Integer index=null;
		if("pass".equals(blackSummaryConfig.getType())) {
			index=300;
		}else if("reject".equals(blackSummaryConfig.getType())){
			index=200;
		}
		Random random = new Random();
		index+=random.nextInt(99);
		blackSummaryConfig.setPriority(index);
		
		int i = 0;
		try {
			i = blackSummaryConfigService.addBlackSummaryConfig(blackSummaryConfig);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]", e);
		}
		// 查询最后添加的数据
		BlackSummaryConfig bsc = null;
		try {
			bsc = blackSummaryConfigService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]put last add menu is error", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(i);
		result.setNewData(bsc);
		result.setOldData(null);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][BLACKSUMMARYCONFIG]  " + blackSummaryConfig + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}
	
	/**
	 * 根据id查询对象
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("findBlackSummaryConfigById.action")
	@ResponseBody
	public BlackSummaryConfig findBlackSummaryConfigById(Integer id) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] id=" + id);
		BlackSummaryConfig blackSummaryConfig = null;
		try {
			blackSummaryConfig = blackSummaryConfigService.findBlackSummaryConfigById(id).get(0);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]", e);
		}
		return blackSummaryConfig;
	}
	
	/**
	 * 更新号码配置
	 * 
	 * @param request
	 * @param response
	 * @param lgModelAudit
	 * @return
	 * @throws Exception
	 */
	
	@DataOperate(bussiness = Bussiness.blackSummaryConfig, operation = Operate.UPDATE)
	@ResponseBody
	@RequestMapping(value = "updateBlackSummaryConfig.action", method = RequestMethod.POST)
	public ResponseResult updateBlackSummaryConfig(HttpServletRequest request, HttpServletResponse response,
			BlackSummaryConfig blackSummaryConfig) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] " + blackSummaryConfig);
		Long startTime = System.currentTimeMillis();
		List<BlackSummaryConfig> oldData = null;
		try {
			oldData = blackSummaryConfigService.findBlackSummaryConfigById(blackSummaryConfig.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]", e1);
		}
		
		int i = 0;
		try {
			i = blackSummaryConfigService.updateBlackSummaryConfig(blackSummaryConfig);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][MENU] update menu is error", e);
		}
		List<BlackSummaryConfig> newData = null;
		try {
			newData = blackSummaryConfigService.findBlackSummaryConfigById(blackSummaryConfig.getId());
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG]", e1);
		}
		ResponseResult result = new ResponseResult(i, newData, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][BLACKSUMMARYCONFIG] " + blackSummaryConfig + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}
	
	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackSummaryConfig, operation = Operate.DELETE)
	@RequestMapping("/deleteBlackSummaryConfig.action")
	@ResponseBody
	public ResponseResult deleteBlackSummaryConfig(Integer id) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] id=" + id);
		List<BlackSummaryConfig> blackSummaryConfigs = null;
		try {
			blackSummaryConfigs = blackSummaryConfigService.findBlackSummaryConfigById(id);
		} catch (Exception e) {
			logger.error("[error][BINS][BLACKSUMMARYCONFIG]", e);
		}
		int i = 0;
		try {
			i = blackSummaryConfigService.deleteBlackSummaryConfig(id);
		} catch (Exception e) {
			logger.error("[error][BINS][BLACKSUMMARYCONFIG] delete error", e);
		}
		return new ResponseResult(i, null, blackSummaryConfigs);
	}
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blackSummaryConfig, operation = Operate.DELETEBATCH)
	@ResponseBody
	@RequestMapping("/deleteBlackSummaryConfigBatch.action")
	public ResponseResult deleteBlackSummaryConfigBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {
		logger.debug("[BINS][BLACKSUMMARYCONFIG] ids[]=" + ids);
		Long startTime = System.currentTimeMillis();
		List<BlackSummaryConfig> oldData = null;
		try {
			oldData = blackSummaryConfigService.findBlackSummaryConfigByIds(ids);
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][MENU] put old menu is error", e1);
		}
		int i = 0;
		try {
			i = blackSummaryConfigService.deleteBlackSummaryConfigBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][BLACKSUMMARYCONFIG] delete batch menu is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][BLACKSUMMARYCONFIG] ids[]=" + ids + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.blackSummaryConfig,operation=Operate.START)
	@RequestMapping("/startSummaryConfig.action")
	@ResponseBody
	public ResponseResult startSummaryConfig(Integer id){
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			str = blackSummaryConfigService.startSummaryConfig(id);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] start data error", e);
		}
		ResponseResult result = new ResponseResult(str, null, id);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.blackSummaryConfig,operation=Operate.PAUSE)
	@RequestMapping("/pauseSummaryConfig.action")
	@ResponseBody
	public ResponseResult pauseSummaryConfig(Integer id){
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			pau = blackSummaryConfigService.pauseSummaryConfig(id);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] pause data error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, id);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] id=" + id + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.blackSummaryConfig,operation=Operate.STARTBATCH)
	@RequestMapping("/startSummaryConfigBatch.action")
	@ResponseBody
	public ResponseResult startSummaryConfigBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		int str = 0;
		Long sTime = System.currentTimeMillis();
		try {
			str = blackSummaryConfigService.startSummaryConfigBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] start batch error", e);
		}
		ResponseResult result = new ResponseResult(str, null, ids);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	@DataOperate(bussiness=Bussiness.blackSummaryConfig,operation=Operate.PAUSEBATCH)
	@RequestMapping("/pauseSummaryConfigBatch.action")
	@ResponseBody
	public ResponseResult pauseSummaryConfigBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids){
		int pau = 0;
		Long sTime = System.currentTimeMillis();
		try {
			pau = blackSummaryConfigService.pauseSummaryConfigBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][TEMP] paused batch error", e);
		}
		ResponseResult result = new ResponseResult(pau, null, ids);
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][TEMP] ids[]=" + ids + ",time=" + (eTime-sTime) + "ms, result=" + result.getResult());
		return result;
	}
	
	
	@RequestMapping(value = "/importFile.action")
	@ResponseBody
	public Map<String, Object> importFile(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			HttpServletRequest request, String level, String operation){
		String upFileName = uploadFile.getOriginalFilename();
		Map<String, Object> map = new HashMap<>();
		File desFile = null;
		String path = request.getSession().getServletContext().getRealPath("upload");
		
		if(!uploadFile.isEmpty() && upFileName.endsWith(".txt")){
			List<String> phoneList = new ArrayList<>();
			InputStreamReader read=null;
			BufferedReader bufferedReader =null;
			try {
				desFile = new File(path, upFileName);
				if (!desFile.exists()) {
					desFile.mkdirs();
					uploadFile.transferTo(desFile);
				}
				read = new InputStreamReader(new FileInputStream(desFile));
				bufferedReader = new BufferedReader(read);
				String phone = null;
				while((phone = bufferedReader.readLine()) != null){
					//校验手机号
					if(MOBILE_NUM_PATTERN.matcher(phone.trim()).matches()){
						phoneList.add(phone.trim());
					}else{
						logger.error("[MSIN] phone=" + phone.trim());
					}
				}
			} catch (Exception e) {
				logger.error("[MSIN] 文件解析失败.E={}", e);
			}finally {
				if(read!=null) {
					try {
						read.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(bufferedReader!=null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			try {
				long result = 0;
				if("addBatch".equalsIgnoreCase(operation)) {
					result = RedisUtil.hsetPipeLine("logic:summary:level："+level, phoneList, Constants.Common.Redis.Naming.BLACK);
				}else if("delBatch".equalsIgnoreCase(operation)){
					result = RedisUtil.hdelPipeline("logic:summary:level："+level, phoneList, Constants.Common.Redis.Naming.BLACK);
				}
				map.put("result", result);
			} catch (Exception e) {
				logger.error("[MSIN] addMsinBatch err.E={}", e);
			}
			
		}else{
			logger.error("[MSIN] 文件（格式）错误.fileName=" + upFileName);
			map.put("result", "-9999");
			map.put("fileName", upFileName);
			return map;
		}
		desFile.delete();
		
		return map;
	}
	
	@RequestMapping("/findLevel.action")
	@ResponseBody
	public Map<String, Object> findLevel(String level) {
		logger.debug("[BINS][FIND_LEVEL] level="+level);
		
		Map<String, Object> map = new HashMap<String, Object>();
		long hlen = RedisUtil.hlen("logic:summary:level："+level, Constants.Common.Redis.Naming.BLACK);
		map.put("name", "logic:summary:level："+level);
		map.put("num", hlen);
		return map;
	}
	
	@RequestMapping("/handleLevel.action")
	@ResponseBody
	public Map<String, Object> handleLevel(String name, String phone, String model) {
		logger.debug("[BINS][HAND_LEVEL] name="+name+",phone="+phone+",model="+model);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if("add".equalsIgnoreCase(model)) {
			long hset = RedisUtil.hset(name, phone, "".getBytes(), Constants.Common.Redis.Naming.BLACK);
			map.put("addResult", hset);
		}else if("search".equalsIgnoreCase(model)) {
			boolean hexist = RedisUtil.hexist(name, phone, Constants.Common.Redis.Naming.BLACK);
			map.put("searchResult", hexist);
		}else if("delete".equalsIgnoreCase(model)) {
			long hdel = RedisUtil.hdel(name, phone, Constants.Common.Redis.Naming.BLACK);
			map.put("deleteResult", hdel);
		}
		return map;
	}
	
	@RequestMapping("/deletePhone.action")
	@ResponseBody
	public long deletePhone(String level, String phone) {
		return RedisUtil.hdel("logic:summary:level:"+level, phone, Constants.Common.Redis.Naming.BLACK);
	}
	
}
