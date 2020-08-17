package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BlackSummaryConfig;
import com.jjxt.ssm.mapper.BlackSummaryConfigMapper;
import com.jjxt.ssm.service.BlackSummaryConfigService;

@Service("blackSummaryConfigService")
@Transactional
public class BlackSummaryConfigServiceImpl implements BlackSummaryConfigService {
	@Autowired
	private BlackSummaryConfigMapper blackSummaryConfigMapper;

	@Override
	public int findTotal(Map<String, Object> param) throws Exception {
		return blackSummaryConfigMapper.findTotal(param);
	}
	
	@Override
	public List<BlackSummaryConfig> findBlackSummaryConfigPageList(Map<String, Object> map) throws Exception {
		return blackSummaryConfigMapper.findBlackSummaryConfigPageList(map);
	}
	
	@Override
	public int addBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception {
		return blackSummaryConfigMapper.addBlackSummaryConfig(blackSummaryConfig);
	}
	
	@Override
	public BlackSummaryConfig findAddEnd() throws Exception {
		return blackSummaryConfigMapper.findAddEnd();
	}
	
	@Override
	public List<BlackSummaryConfig> findBlackSummaryConfigById(Integer id) throws Exception {
		return blackSummaryConfigMapper.findBlackSummaryConfigById(id);
	}
	
	@Override
	public int updateBlackSummaryConfig(BlackSummaryConfig blackSummaryConfig) throws Exception {
		return blackSummaryConfigMapper.updateBlackSummaryConfig(blackSummaryConfig);
	}
	
	@Override
	public int deleteBlackSummaryConfig(Integer id) throws Exception {
		return blackSummaryConfigMapper.deleteBlackSummaryConfig(id);
	}
	
	@Override
	public List<BlackSummaryConfig> findBlackSummaryConfigByIds(Integer[] ids) throws Exception {
		return blackSummaryConfigMapper.findBlackSummaryConfigByIds(ids);
	}
	
	@Override
	public int deleteBlackSummaryConfigBatch(Integer[] ids) throws Exception {
		return blackSummaryConfigMapper.deleteBlackSummaryConfigBatch(ids);
	}

	@Override
	public List<BlackSummaryConfig> validatorAppIdAndTypeAndLevel(Map<String, String> map) throws Exception {
		return blackSummaryConfigMapper.validatorAppIdAndTypeAndLevel(map);
	}

	@Override
	public int startSummaryConfig(Integer id) throws Exception {
		return blackSummaryConfigMapper.startSummaryConfig(id);
	}

	@Override
	public int pauseSummaryConfig(Integer id) throws Exception {
		return blackSummaryConfigMapper.pauseSummaryConfig(id);
	}

	@Override
	public int startSummaryConfigBatch(Integer[] ids) throws Exception {
		return blackSummaryConfigMapper.startSummaryConfigBatch(ids);
	}

	@Override
	public int pauseSummaryConfigBatch(Integer[] ids) throws Exception {
		return blackSummaryConfigMapper.pauseSummaryConfigBatch(ids);
	}

	@Override
	public List<BlackSummaryConfig> findAlllevel() throws Exception {
		return blackSummaryConfigMapper.findAlllevel();
	}

}
