package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BlackSign;
import com.jjxt.ssm.mapper.BlackSignMapper;
import com.jjxt.ssm.service.BlackSignService;

@Service("blackSignService")
@Transactional
public class BlackSignServiceImpl implements BlackSignService{
	@Autowired
	private BlackSignMapper blackSignMapper;
	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		return blackSignMapper.findTotal(map);
	}

	@Override
	public List<BlackSign> findBlackSignList(Map<String, Object> map) throws Exception {
		return blackSignMapper.findBlackSignList(map);
	}

	@Override
	public int addBlackSign(BlackSign blackSign) throws Exception {
		return blackSignMapper.addBlackSign(blackSign);
	}

	@Override
	public List<BlackSign> findAddEnd() throws Exception {
		return blackSignMapper.findAddEnd();
	}

	@Override
	public List<BlackSign> findBlackSignById(Integer id) throws Exception {
		return blackSignMapper.findBlackSignById(id);
	}

	@Override
	public int deleteBlackSign(Integer id) throws Exception {
		return blackSignMapper.deleteBlackSign(id);
	}

	@Override
	public int updateBlackSign(BlackSign blackSign) throws Exception {
		return blackSignMapper.updateBlackSign(blackSign);
	}

}
