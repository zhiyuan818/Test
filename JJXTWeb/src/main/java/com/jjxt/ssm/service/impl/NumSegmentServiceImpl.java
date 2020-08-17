package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.NumSegment;
import com.jjxt.ssm.mapper.NumSegmentMapper;
import com.jjxt.ssm.service.NumSegmentService;

@Service("NumSegmentService")
@Transactional
public class NumSegmentServiceImpl implements NumSegmentService {
	
	@Autowired
	private NumSegmentMapper numSegmentMapper;

	@Override
	public int findNumSegmentTotal(Map<String, Object> paramMap) throws Exception {
		return numSegmentMapper.findNumSegmentTotal(paramMap);
	}

	@Override
	public List<NumSegment> findNumSegmentList(Map<String, Object> paramMap) throws Exception {
		return numSegmentMapper.findNumSegmentList(paramMap);
	}

	@Override
	public int findNumSegmentByCondition(Map<String, Object> paramMap) throws Exception {
		return numSegmentMapper.findNumSegmentByCondition(paramMap);
	}

	@Override
	public int addNumSegment(NumSegment numSegment) throws Exception {
		return numSegmentMapper.addNumSegment(numSegment);
	}

	@Override
	public NumSegment findNumSegmentById(Integer id) throws Exception {
		return numSegmentMapper.findNumSegmentById(id);
	}

	@Override
	public int updateNumSegment(NumSegment numSegment) throws Exception {
		return numSegmentMapper.updateNumSegment(numSegment);
	}

	@Override
	public int deleteNumSegById(Integer id) throws Exception {
		return numSegmentMapper.deleteNumSegById(id);
	}

	@Override
	public List<NumSegment> findNumSegByIds(Integer[] ids) throws Exception {
		return numSegmentMapper.findNumSegByIds(ids);
	}

	@Override
	public int delNumSegBatch(Integer[] ids) throws Exception {
		return numSegmentMapper.delNumSegBatch(ids);
	}

}
