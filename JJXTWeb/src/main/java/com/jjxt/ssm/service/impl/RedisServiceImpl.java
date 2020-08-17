package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjxt.ssm.common.Constants;
import com.jjxt.ssm.mapper.GlobalSettingMapper;
import com.jjxt.ssm.service.RedisService;
import com.jjxt.ssm.utils.StringUtil;

@Service("redisService")
public class RedisServiceImpl implements RedisService{
	
	@Autowired
	private GlobalSettingMapper globalSettingMapper;
	@Override
	public List<String> getRedisNameList(String redisName) {
		
		List<String> redisNameList = new ArrayList<String>();
		String all_acc_redis_flag = globalSettingMapper.findGlobalValueByKey(Constants.Common.Naming.CVN_ALL_ACC_REDIS_FLAG);
		String all_chan_redis_flag = globalSettingMapper.findGlobalValueByKey(Constants.Common.Naming.CVN_ALL_CHAN_REDIS_FLAG);
		String redis_account = globalSettingMapper.findGlobalValueByKey(Constants.Common.Naming.CVN_REDIS_ACCOUNT);
		String redis_channel = globalSettingMapper.findGlobalValueByKey(Constants.Common.Naming.CVN_REDIS_CHANNEL);
		
		if(!StringUtil.isEmpty(redis_account) && Arrays.asList(redis_account.split(",")).contains(redisName)) {
			if(!StringUtil.isEmpty(all_acc_redis_flag)) {
				String[] split = all_acc_redis_flag.split(",");
				for(String s:split) {
					redisNameList.add("acc"+s+":"+redisName);
				}
			}else {
				redisNameList.add(redisName);
			}
		}else if(!StringUtil.isEmpty(redis_channel) && Arrays.asList(redis_channel.split(",")).contains(redisName)) {
			if(!StringUtil.isEmpty(all_chan_redis_flag)) {
				String[] split = all_chan_redis_flag.split(",");
				for(String s:split) {
					redisNameList.add("chan"+s+":"+redisName);
				}
			}else {
				redisNameList.add(redisName);
			}
		}else {
			redisNameList.add(redisName);
		}	
		
		return redisNameList;
	}

}
