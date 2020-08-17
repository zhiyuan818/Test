package com.jjxt.ssm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BlackLevel1;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.PhoneStatus;
import com.jjxt.ssm.entity.WhiteLevel;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.DataManageService;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/dataManage")
public class DataManageController {
	private static Logger logger = Logger.getLogger(DataManageController.class);

	private static final String PATH = "dataManage/";

	@Autowired
	private DataManageService dataManageService;
	@Autowired
	private ApplicationService applicationService;

	/**
	 * 跳转黑白名单页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goDataManage.action")
	public String goblackWhite(HttpServletRequest request) {
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
			e.printStackTrace();
		}
		request.setAttribute("apps", apps);
		return PATH + "dataManage";
	}

	/**
	 * 
	 * 根据手机号码查询黑白名单状态
	 * 
	 * 白名单、投诉黑名单（一级黑），可通过数据库查询和修改
	 * 普通黑（原三级黑）和金融黑（原五级黑）合并为三级黑，仅能通过redis查询和修改
	 * 新增12321投诉号码入关键黑（五级黑），仅能通过redis进行查询修改，删除需保留记录（入其他队列）
	 * 
	 * @param phoneNumbers
	 * @return
	 */

	
	@RequestMapping("/searchPhone.action")
	@ResponseBody
	public List<PhoneStatus> searchPhone(String phoneNumbers,String appId) {
		logger.debug("[BINS][DATA_MANAGE] search phoneNumbers=" + phoneNumbers+",appId="+appId);
		String[] phoneNumberArr = phoneNumbers.replace(" ", "").split(",");

		// 去重
		Arrays.sort(phoneNumberArr);
		Set<String> phoneNumberSet = new HashSet<String>();
		phoneNumberSet.addAll(Arrays.asList(phoneNumberArr));
		List<String> phone = new ArrayList<>(phoneNumberSet);

		List<PhoneStatus> list = new ArrayList<PhoneStatus>();
		// 得到一级黑名单,三级黑名单,五级黑名单,白名单,实号库
		try {
			list = dataManageService.findPhone(phone);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
			e.printStackTrace();
		}

		// 得到二级黑名单
		List<BlackLevel2> black2 = new ArrayList<>();
		try {
			black2 = dataManageService.findPhoneByBlack2(phone,appId);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
			e.printStackTrace();
		}
		for (BlackLevel2 blackLevel2 : black2) {
			Application app = null;
			try {
				app = applicationService.findApplicationById(blackLevel2.getAppId());
			} catch (Exception e) {
				logger.error("[ERROR][DATA_MANAGE]e=", e);
				e.printStackTrace();
			}
			PhoneStatus phoneS = new PhoneStatus();
			if (!StringUtil.isEmpty(app)) {
				phoneS.setAppName(app.getAppName());
			}
			phoneS.setAppId(String.valueOf(blackLevel2.getAppId()));
			phoneS.setPhoneNumber(blackLevel2.getPhoneNumber());
			phoneS.setStatus("black2");
			list.add(phoneS);
		}

		// 得到无状态的数据
		List<String> phoneEXIST = new ArrayList<String>();
		for (PhoneStatus phoneStatus : list) {
			phoneEXIST.add(phoneStatus.getPhoneNumber());
		}
		for (String string : phone) {
			if (!phoneEXIST.contains(string)) {
				PhoneStatus phoneStatus = new PhoneStatus();
				phoneStatus.setPhoneNumber(string);
				phoneStatus.setStatus("empty");
				list.add(phoneStatus);
			}
		}

		int i = 1;
		for (PhoneStatus phoneStatus : list) {
			phoneStatus.setId(i);
			i++;
		}

		return list;
	}

	/**
	 * 根据手机号码和状态添加
	 * 
	 * @param phoneNumbers
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.dataManage, operation = Operate.INSERT)
	@RequestMapping("/addPhone.action")
	@ResponseBody
	public ResponseResult addPhone(String addPhone, String addLevel) {
		logger.debug("[BINS][DATA_MANAGE] addPhone=" + addPhone + ",addLevel=" + addLevel);

		int result = 0;
		if (addLevel.equalsIgnoreCase("black1")) {
			// 投诉黑名单
			result = addBlackLevel1(addPhone);
		} else if (addLevel.equalsIgnoreCase("black3")) {
			// 普通黑名单
			result = addBlackLevel3(addPhone);
		} else if (addLevel.equalsIgnoreCase("black5")) {
			// 金融黑名单
			result = addBlackLevel5(addPhone);
		} else if (addLevel.equalsIgnoreCase("white")) {
			// 白名单
			result = addWhite(addPhone);
		} else if (addLevel.equalsIgnoreCase("shunt")) {
			// 实号库
			result = addShunt(addPhone);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber", addPhone);
		map.put("status", addLevel);
		return new ResponseResult(result, map, null);
	}

	/**
	 * 添加白名单
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.whitelevel, operation = Operate.INSERT)
	@ResponseBody
	public int addWhite(String phone) {
		logger.debug("[BINS][DATA_MANAGE][ADD_WHITE]phone=" + phone);

		WhiteLevel white = new WhiteLevel();
		white.setAppId(0);
		white.setContent("后台添加");
		white.setPhoneNumber(phone);
		white.setSpId(1);

		int i = 0;
		try {
			i = dataManageService.addWhite(white);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
		}
		return i;
	}

	/**
	 * 添加一级黑名单
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel1, operation = Operate.INSERT)
	@ResponseBody
	public int addBlackLevel1(String phone) {
		logger.debug("[BINS][DATA_MANAGE][ADD_BLACKLEVEL1]phone=" + phone);

		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		BlackLevel1 black1 = new BlackLevel1();
		try {

			black1.setPhoneNumber(phone);
			black1.setAddTime(sdf.parse(sdf.format(new Date())));
		} catch (Exception e) {
			logger.error("[ERROR][BLACKWHITE][ADD_BLACKLEVEL1]", e);
		}
		int result = 0;
		try {
			result = dataManageService.addBlackLevel1(black1);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
		}
		return result;
	}

	/**
	 * 添加二级黑名单
	 * 
	 * @param phone
	 * @param appId
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel2, operation = Operate.INSERT)
	@RequestMapping("/addBlack2Phone.action")
	@ResponseBody
	public ResponseResult addBlack2Phone(String addBlack2Phone, String addBlack2AppId) {
		logger.debug("[BINS][DATA_MANAGE][ADD_BLACKLEVEL2]phone=" + addBlack2Phone + ",appID=" + addBlack2AppId);

		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		BlackLevel2 black2 = new BlackLevel2();
		try {
			black2.setChannelId(0);
			black2.setPhoneNumber(addBlack2Phone);
			black2.setAddTime(sdf.parse(sdf.format(new Date())));

			if (StringUtil.isEmpty(addBlack2AppId)) {
				return new ResponseResult(-2, black2, null);
			}
			black2.setAppId(Integer.parseInt(addBlack2AppId));
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE][ADD_BLACKLEVEL2]", e);
		}
		int addRes = 0;
		try {
			addRes = dataManageService.addBlackLevel2(black2);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE]e=", e);
		}
		return new ResponseResult(addRes, black2, null);
	}

	/**
	 * 添加三级黑名单
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel3, operation = Operate.INSERT)
	@ResponseBody
	public int addBlackLevel3(String phone) {
		logger.debug("[BINS][DATA_MANAGE][ADD_BLACKLEVEL3]phone=" + phone);
		try {
			return dataManageService.addBlackLevel3(phone);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE][ADD_BLACKLEVEL3]", e);
		}
		return 0;
	}
	
	/**
	 * 添加五级黑名单 
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel5, operation = Operate.INSERT)
	@ResponseBody
	public int addBlackLevel5(String phone) {
		logger.debug("[BINS][DATA_MANAGE][ADD_BLACKLEVEL5]phone=" + phone);
		try {
			return dataManageService.addBlackLevel5(phone);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE][ADD_BLACKLEVEL5]", e);
		}
		return 0;
	}

	/**
	 * 添加实号库 merge不能插入
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.shuntPhone, operation = Operate.INSERT)
	@ResponseBody
	public int addShunt(String phone) {
		logger.debug("[BINS][DATA_MANAGE][ADD_SHUNT]phone=" + phone);
		try {
			return dataManageService.addShunt(phone);
		} catch (Exception e) {
			logger.error("[ERROR][DATA_MANAGE][ADD_SHUNT]", e);
		}
		return 0;
	}

	/**
	 * 删除手机号
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.dataManage, operation = Operate.DELETE)
	@RequestMapping("/deletePhone.action")
	@ResponseBody
	public ResponseResult deletePhone(String phoneNumber, String appId, String status) {
		logger.debug("[BINS][DATA_MANAGE][DELETE]phoneNumber=" + phoneNumber + ",appId=" + appId + ",status=" + status);

		if (StringUtil.isEmpty(phoneNumber)) {
			return new ResponseResult(-2, phoneNumber, null);
		}
		if (StringUtil.isEmpty(status)) {
			return new ResponseResult(-3, phoneNumber, null);
		}
		if (status.equalsIgnoreCase("empty")) {
			return new ResponseResult(-4, phoneNumber, null);
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber", phoneNumber);
		
		int addRes = 0;
		if("black3".equals(status)) {
			map.put("redis", "black3");
			addRes = (int)RedisUtil.hdel("logic:black3",phoneNumber,Constants.Common.Redis.Naming.BLACK);
			return new ResponseResult(addRes, map, null);
		}else if("black5".equals(status)) {
			map.put("redis", "black5");
			addRes = (int)RedisUtil.hdel("logic:black5",phoneNumber,Constants.Common.Redis.Naming.BLACK);
			RedisUtil.hset("logic:black5:bak",phoneNumber,"".getBytes(),Constants.Common.Redis.Naming.BLACK);
			return new ResponseResult(addRes, map, null);
		}else if("shunt".equals(status)) {
			map.put("redis", "shunt");
			addRes = (int)RedisUtil.hdel("logic:shuntphone",phoneNumber,Constants.Common.Redis.Naming.SHUNT_PHONE);
			return new ResponseResult(addRes, map, null);
		}else if("white".equals(status)) {
			map.put("redis", "white");
			addRes = (int)RedisUtil.hdel("logic:white",phoneNumber,Constants.Common.Redis.Naming.WHITE);
			return new ResponseResult(addRes, map, null);
		}else if("black1".equals(status)) {
			map.put("redis", "black1");
			addRes = (int)RedisUtil.hdel("logic:black1",phoneNumber,Constants.Common.Redis.Naming.BLACK);
			return new ResponseResult(addRes, map, null);
		}else if("black2".equals(status)) {
			map.put("redis", "black2");
			String key = appId + "_" + phoneNumber;
			addRes = (int)RedisUtil.hdel("logic:black2",key,Constants.Common.Redis.Naming.BLACK);
			return new ResponseResult(addRes, map, null);
		}
		return new ResponseResult(addRes, map, null);
	}



	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.dataManage, operation = Operate.DELETEBATCH)
	@RequestMapping("/delBatch.action")
	@ResponseBody
	public ResponseResult delBatch(String phoneStatusArr) {
		logger.debug("[BINS][DATA_MANAGE][DELETEBATCH]phoneStatusArr=" + phoneStatusArr);

		JSONArray json = JSONArray.fromObject(phoneStatusArr);
		int addRes = 0;
		if (json.size() > 0) {
			@SuppressWarnings({ "unchecked", "static-access" })
			List<PhoneStatus> list = (List<PhoneStatus>) json.toCollection(json, PhoneStatus.class);
			for (PhoneStatus phoneStatus : list) {
				if (phoneStatus.getStatus().equalsIgnoreCase("empty")) {
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("phoneNumber", phoneStatus.getPhoneNumber());

				if("black3".equals(phoneStatus.getStatus())) {
					addRes += (int)RedisUtil.hdel("logic:black3",phoneStatus.getPhoneNumber(),Constants.Common.Redis.Naming.BLACK);
				}else if("black5".equals(phoneStatus.getStatus())) {
					addRes += (int)RedisUtil.hdel("logic:black5",phoneStatus.getPhoneNumber(),Constants.Common.Redis.Naming.BLACK);
					RedisUtil.hset("logic:black5:bak",phoneStatus.getPhoneNumber(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
				}else if("shunt".equals(phoneStatus.getStatus())) {
					addRes += (int)RedisUtil.hdel("logic:shuntphone",phoneStatus.getPhoneNumber(),Constants.Common.Redis.Naming.SHUNT_PHONE);
				}else if("white".equals(phoneStatus.getStatus())) {
					addRes += (int)RedisUtil.hdel("logic:white",phoneStatus.getPhoneNumber(),Constants.Common.Redis.Naming.WHITE);
				}else if("black1".equals(phoneStatus.getStatus())) {
					addRes += (int)RedisUtil.hdel("logic:black1",phoneStatus.getPhoneNumber(),Constants.Common.Redis.Naming.BLACK);
				}else if("black2".equals(phoneStatus.getStatus())) {
					String key = phoneStatus.getAppId()+"_"+phoneStatus.getPhoneNumber();
					addRes += (int)RedisUtil.hdel("logic:black2",key,Constants.Common.Redis.Naming.BLACK);
				}
			}
		}
		return new ResponseResult(addRes, phoneStatusArr, null);
	}

	/**
	 * 批量添加一级黑名单
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel1, operation = Operate.INSERTBATCH)
	@RequestMapping("/addBlackLevel1Batch.action")
	@ResponseBody
	public ResponseResult addBlackLevel1Batch(String phoneStatusArr) {
		logger.debug("[BINS][DATA_MANAGE][ADD_BLACKLEVEL1BATCH]phoneStatusArr=" + phoneStatusArr);

		JSONArray json = JSONArray.fromObject(phoneStatusArr);
		int result = 0;
		if (json.size() > 0) {
			@SuppressWarnings({ "unchecked", "static-access" })
			List<PhoneStatus> list = (List<PhoneStatus>) json.toCollection(json, PhoneStatus.class);
			for(PhoneStatus phoneStatus:list) {
				result += (int)RedisUtil.hset("logic:black1",phoneStatus.getPhoneNumber(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
			}
		}
		return new ResponseResult(result, phoneStatusArr, null);
	}

	/**
	 * 批量添加白名单
	 * 
	 * @param phone
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.whitelevel, operation = Operate.INSERTBATCH)
	@RequestMapping("/addWhiteBatch.action")
	@ResponseBody
	public ResponseResult addWhiteBatch(String phoneStatusArr) {
		logger.debug("[BINS][DATA_MANAGE][ADD_WHITE]phoneStatusArr=" + phoneStatusArr);

		JSONArray json = JSONArray.fromObject(phoneStatusArr);
		int result = 0;
		if (json.size() > 0) {
			@SuppressWarnings({ "unchecked", "static-access" })
			List<PhoneStatus> list = (List<PhoneStatus>) json.toCollection(json, PhoneStatus.class);
			for(PhoneStatus phoneStatus:list) {
				result += (int)RedisUtil.hset("logic:white",phoneStatus.getPhoneNumber(),"".getBytes(),Constants.Common.Redis.Naming.WHITE);
			}
		}
		return new ResponseResult(result, phoneStatusArr, null);
	}

}
