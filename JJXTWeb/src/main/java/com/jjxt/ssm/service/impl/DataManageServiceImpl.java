package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.util.StringUtils;
import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.entity.BlackLevel1;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.PhoneStatus;
import com.jjxt.ssm.entity.WhiteLevel;
import com.jjxt.ssm.mapper.DataManageMapper;
import com.jjxt.ssm.redis.RedisUtil;
import com.jjxt.ssm.service.DataManageService;
import com.jjxt.ssm.utils.StringUtil;
@Service("DataManageService")
@Transactional
public class DataManageServiceImpl implements DataManageService{
	
	@Autowired
	private DataManageMapper dataManageMapper;

	@Override
	public List<BlackLevel2> findPhoneByBlack2(List<String> phone,String appId) throws Exception {
		List<BlackLevel2> blackList2 = new ArrayList<>();
		if(!StringUtil.isEmpty(phone) && phone.size()>0 && !StringUtils.isEmpty(appId)) {
			for(String str:phone) {
				if(RedisUtil.hexist("logic:black2", appId+"_"+str, Constants.Common.Redis.Naming.BLACK)) {
					BlackLevel2 blLevel2 = new BlackLevel2();
					blLevel2.setAppId(Integer.parseInt(appId));
					blLevel2.setPhoneNumber(str);
					blackList2.add(blLevel2);
				}
			}
		}
		return blackList2;
	}

	@Override
	public int addWhite(WhiteLevel white) throws Exception {
		int result = 0;
		if(white.getPhoneNumber().trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:white",white.getPhoneNumber().trim(),"".getBytes(),Constants.Common.Redis.Naming.WHITE);
		return result;
	}

	@Override
	public int addBlackLevel3(String phone) throws Exception {
		int result = 0;
		if(phone.trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:black3",phone.trim(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
		return result;
	}
	
	@Override
	public int addBlackLevel5(String phone) throws Exception {
		int result = 0;
		if(phone.trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:black5",phone.trim(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
		return result;
	}

	@Override
	public int addBlackLevel1(BlackLevel1 black1) throws Exception {
		int result = 0;
		if(black1.getPhoneNumber().trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:black1",black1.getPhoneNumber().trim(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
		return result;
	}

	@Override
	public int addBlackLevel2(BlackLevel2 black2) throws Exception {
		int result = 0;
		if(black2.getPhoneNumber().trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:black2",black2.getAppId()+"_"+black2.getPhoneNumber().trim(),"".getBytes(),Constants.Common.Redis.Naming.BLACK);
		return result;
	}
	
	@Override
	public int addShunt(String phone) throws Exception {
		int result = 0;
		if(phone.trim().length() != 11) return 0;
		result = (int)RedisUtil.hset("logic:shuntphone",phone.trim(),"".getBytes(),Constants.Common.Redis.Naming.SHUNT_PHONE);
		return result;
	}
	


	@Override
	public int deletePhone(Map<String, String> map) throws Exception {
		String table = map.get("tableName");
		String phoneNumber = map.get("phoneNumber");
		if("pre_shunt_phone".equals(table)) {
			RedisUtil.hdel("logic:shuntphone", phoneNumber, Constants.Common.Redis.Naming.SHUNT_PHONE);
		}
		return dataManageMapper.deletePhone(map);
	}
	@Override
	public List<PhoneStatus> findPhone(List<String> phoneList) throws Exception {
		List<PhoneStatus> findPhone = new ArrayList<PhoneStatus>();
		PhoneStatus phoneStatus = null;
		for(String phone : phoneList) {
			if(RedisUtil.hexist("logic:black1",phone,Constants.Common.Redis.Naming.BLACK)) {
				 phoneStatus = new PhoneStatus();
				phoneStatus.setStatus("black1");
				phoneStatus.setPhoneNumber(phone);
				findPhone.add(phoneStatus);
			}
			if(RedisUtil.hexist("logic:white",phone,Constants.Common.Redis.Naming.WHITE)) {
				phoneStatus = new PhoneStatus();
				phoneStatus.setStatus("white");
				phoneStatus.setPhoneNumber(phone);
				findPhone.add(phoneStatus);
			}
			if(RedisUtil.hexist("logic:black3",phone,Constants.Common.Redis.Naming.BLACK)) {
				 phoneStatus = new PhoneStatus();
				phoneStatus.setStatus("black3");
				phoneStatus.setPhoneNumber(phone);
				findPhone.add(phoneStatus);
			}
			if(RedisUtil.hexist("logic:black5",phone,Constants.Common.Redis.Naming.BLACK)) {
				 phoneStatus = new PhoneStatus();
				phoneStatus.setStatus("black5");
				phoneStatus.setPhoneNumber(phone);
				findPhone.add(phoneStatus);
			}
			if(RedisUtil.hexist("logic:shuntphone",phone,Constants.Common.Redis.Naming.SHUNT_PHONE)) {
				 phoneStatus = new PhoneStatus();
				phoneStatus.setStatus("shunt");
				phoneStatus.setPhoneNumber(phone);
				findPhone.add(phoneStatus);
			}
		}
		return findPhone;
	}

	@Override
	public int addBlackLevel1Batch(List<PhoneStatus> phoneStatusList) {
		return dataManageMapper.addBlackLevel1Batch(phoneStatusList);
	}

	@Override
	public int addWhiteBatch(List<PhoneStatus> list) throws Exception {
		return dataManageMapper.addWhiteBatch(list);
	}
	
}
