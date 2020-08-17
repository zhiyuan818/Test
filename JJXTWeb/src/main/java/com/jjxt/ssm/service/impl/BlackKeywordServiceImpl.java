package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BlackKeyword;
import com.jjxt.ssm.mapper.BlackKeywordMapper;
import com.jjxt.ssm.service.BlackKeywordService;

@Service("blackKeywordService")
@Transactional
public class BlackKeywordServiceImpl implements BlackKeywordService {

	@Autowired
	private BlackKeywordMapper blackKeywordMapper;

	@Override
	public List<BlackKeyword> findAllList() throws Exception {
		return blackKeywordMapper.findAllList();
	}

	@Override
	public int addToBlackKeyword(String str) throws Exception {
		return blackKeywordMapper.addToBlackKeyword(str);
	}

	@Override
	public List<BlackKeyword> findPageList(Map<String, Object> map) throws Exception {
		return blackKeywordMapper.findPageList(map);
	}

	@Override
	public int findTotal(Map<String, Object> map) throws Exception {
		return blackKeywordMapper.findTotal(map);
	}

	@Override
	public int delBlackKeyword(Integer id) throws Exception {
		return blackKeywordMapper.delBlackKeyword(id);
	}

	@Override
	public BlackKeyword findBlackKeywordById(Integer id) throws Exception {
		return blackKeywordMapper.findBlackKeywordById(id);
	}

	@Override
	public int updateBlackKeyword(BlackKeyword blackKeyword) throws Exception {
		return blackKeywordMapper.updateBlackKeyword(blackKeyword);
	}

	@Override
	public int findByKeyWord(String str) throws Exception {
		// TODO Auto-generated method stub
		return blackKeywordMapper.findByKeyWord(str);
	}

}
