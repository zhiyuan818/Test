package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.KhdOutbox;
import com.jjxt.ssm.mapper.KhdOutboxMapper;
import com.jjxt.ssm.service.KhdOutboxService;

@Service("/khdOutboxService")
@Transactional
public class KhdOutboxServiceImpl implements KhdOutboxService{
	
	@Autowired
	private KhdOutboxMapper khdOutboxMapper;
	@Override
	public int findTotal(Map<String, Object> map) {
		return khdOutboxMapper.findTotal(map);
	}

	@Override
	public List<KhdOutbox> findKhdOutboxList(Map<String, Object> param) {
		return khdOutboxMapper.findKhdOutboxList(param);
	}

}
