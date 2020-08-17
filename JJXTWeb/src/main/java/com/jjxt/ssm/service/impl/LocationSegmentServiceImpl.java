package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.LocationSegment;
import com.jjxt.ssm.mapper.LocationSegmentMapper;
import com.jjxt.ssm.service.LocationSegmentService;

@Service("LocationSegmentService")
@Transactional
public class LocationSegmentServiceImpl implements LocationSegmentService {
	
	@Autowired
	private LocationSegmentMapper locationSegmentMapper;

	@Override
	public int findLocationSegmentTotal(Map<String, Object> paramMap) throws Exception {
		return locationSegmentMapper.findLocationSegmentTotal(paramMap);
	}

	@Override
	public List<LocationSegment> findLocationSegmentList(Map<String, Object> paramMap) throws Exception {
		return locationSegmentMapper.findLocationSegmentList(paramMap);
	}

	@Override
	public int findLocationSegmentByCondition(Map<String, Object> paramMap) throws Exception {
		return locationSegmentMapper.findLocationSegmentByCondition(paramMap);
	}

	@Override
	public int addLocationSegment(LocationSegment locationSegment) throws Exception {
		return locationSegmentMapper.addLocationSegment(locationSegment);
	}

	@Override
	public LocationSegment findLocationSegmentById(Integer id) throws Exception {
		return locationSegmentMapper.findLocationSegmentById(id);
	}

	@Override
	public int updateLocationSegment(LocationSegment locationSegment) throws Exception {
		return locationSegmentMapper.updateLocationSegment(locationSegment);
	}

	@Override
	public int deleteLocationSegById(Integer id) throws Exception {
		return locationSegmentMapper.deleteLocationSegById(id);
	}

	@Override
	public List<LocationSegment> findLocationSegByIds(Integer[] ids) throws Exception {
		return locationSegmentMapper.findLocationSegByIds(ids);
	}

	@Override
	public int delLocationSegBatch(Integer[] ids) throws Exception {
		return locationSegmentMapper.delLocationSegBatch(ids);
	}

	
}
