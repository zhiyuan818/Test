package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jjxt.ssm.entity.ChanChangeNum;
import com.jjxt.ssm.mapper.ChanChangeNumMapper;
import com.jjxt.ssm.service.ChanChangeNumService;

@Service("/chanChangeNumService")
@Transactional
public class ChanChangeNumServiceImpl implements ChanChangeNumService{
	
	@Autowired
	private ChanChangeNumMapper chanChangeNumMapper;

	@Override
	public int findTotal(Map<String, String> map) throws Exception {
		return chanChangeNumMapper.findTotal(map);
	}

	@Override
	public List<ChanChangeNum> findchanChangeNumPageList(Map<String, Object> map1) throws Exception {
		return chanChangeNumMapper.findchanChangeNumPageList(map1);
	}

	@Override
	public int addChanChangeNum(ChanChangeNum chanChangeNum) throws Exception {
		return chanChangeNumMapper.addChanChangeNum(chanChangeNum);
	}

	@Override
	public List<ChanChangeNum> findAddEnd() throws Exception {
		return chanChangeNumMapper.findAddEnd();
	}

	@Override
	public List<ChanChangeNum> findChanChangeNumById(Integer id) throws Exception {
		return chanChangeNumMapper.findChanChangeNumById(id);
	}

	@Override
	public int updateChanChangeNum(ChanChangeNum chanChangeNum) throws Exception {
		return chanChangeNumMapper.updateChanChangeNum(chanChangeNum);
	}

	@Override
	public int deleteChanChangeNum(Integer id) throws Exception {
		return chanChangeNumMapper.deleteChanChangeNum(id);
	}

	@Override
	public List<ChanChangeNum> findChanChangeNumByIds(Integer[] ids) throws Exception {
		return chanChangeNumMapper.findChanChangeNumByIds(ids);
	}

	@Override
	public int deleteChanChangeNumBatch(Integer[] ids) throws Exception {
		return chanChangeNumMapper.deleteChanChangeNumBatch(ids);
	}


}
