package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.MT;
import com.jjxt.ssm.mapper.MTMapper;
import com.jjxt.ssm.service.MTService;

@Service("mtService")
@Transactional
public class MTServiceImpl implements MTService {
	@Autowired
	private MTMapper mtMapper;

	@Override
	public List<MT> findMt() throws Exception {
		return mtMapper.findMT();
	}

	@Override
	public int findMTTotal(Map<String, Object> map) throws Exception {
		return mtMapper.findMTTotal(map);
	}

	@Override
	public List<MT> findMTPageList(Map<String, Object> map) throws Exception {
		return mtMapper.findMTPageList(map);
	}

	@Override
	public MT findMTByLinkId(Map<String, String> map) throws Exception {
		return mtMapper.findMTByLinkId(map);
	}

	@Override
	public Integer findPutReportSum(Map<String, Object> map) throws Exception {
		return mtMapper.findPutReportSum(map);
	}

	@Override
	public int findNewMtTotal(Map<String, Object> map) throws Exception {
		return mtMapper.findNewMtTotal(map);
	}
	
	@Override
	public List<MT> findNewMTPageList(Map<String, Object> map) throws Exception {
		List<MT> list=new ArrayList<MT>();
		Object logDate = map.get("logDate");
			List<MT> findNewMTPageList = mtMapper.findNewMTPageList(map);
			if(findNewMTPageList!=null &&findNewMTPageList.size()>0) {
				Set<String> uniqueSet=new HashSet<String>();
				for(MT mt:findNewMTPageList) {
					uniqueSet.add(mt.getUniqueId());
				}
				if(uniqueSet.size()>0) {
					Map<String, Object> uniqueMap=new HashMap<>();
					uniqueMap.put("logDate", logDate);
					uniqueMap.put("uniqueSet", uniqueSet);
					List<MT> contentList=mtMapper.findMtContentByUniqueSet(uniqueMap);
					if(contentList!=null && contentList.size()>0) {
						for(MT mt:findNewMTPageList) {
							for(MT contentMap:contentList) {
								if(mt.getUniqueId().equals(contentMap.getUniqueId())) {
									mt.setContent(contentMap.getContent());
								}
							}
						}
					}
					list.addAll(findNewMTPageList);
				}
			}else {
				return new ArrayList<MT>();
			}
	
		return list;
	}

	@Override
	public MT findNewMTByLinkId(Map<String, String> map) throws Exception {
		return mtMapper.findNewMtByLinkId(map);
	}

	@Override
	public Integer findNewPutReportSum(Map<String, Object> map) throws Exception {
		return mtMapper.findNewPutReportSum(map);
	}


}
