package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.SignMatchExt;
import com.jjxt.ssm.mapper.SignMatchExtMapper;
import com.jjxt.ssm.service.SignMatchExtService;

@Service("signMatchExtService")
@Transactional
public class SignMatchExtServiceImpl implements SignMatchExtService{
	
	@Autowired
	private SignMatchExtMapper signMatchExtMapper;
	@Override
	public List<SignMatchExt> findPageList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return signMatchExtMapper.findPageList(map);
	}

	@Override
	public Integer findTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return signMatchExtMapper.findTotal(map);
	}

	@Override
	public Integer findMaxExtByAppIdAndExtLength(SignMatchExt signMExt) throws Exception {
		return signMatchExtMapper.findMaxExtByAppIdAndExtLength(signMExt);
	}

	@Override
	public Integer addSignMatchExt(SignMatchExt signMExt) throws Exception {
		return signMatchExtMapper.addSignMatchExt(signMExt);
	}

	@Override
	public int deleteSignMatchExt(String id) throws Exception {
		
		return signMatchExtMapper.deleteSignMatchExt(id);
	}

	@Override
	public SignMatchExt findListById(String id) throws Exception {
		// TODO Auto-generated method stub
		return signMatchExtMapper.findListId(id);
	}

	@Override
	public List<SignMatchExt> findAllList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return signMatchExtMapper.findAllList(map);
	}

}
