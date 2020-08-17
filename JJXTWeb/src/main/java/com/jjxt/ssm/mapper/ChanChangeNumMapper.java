package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;
import com.jjxt.ssm.entity.ChanChangeNum;

public interface ChanChangeNumMapper {

	int findTotal(Map<String, String> map);
	
	List<ChanChangeNum> findchanChangeNumPageList(Map<String, Object> map1);
	
	int addChanChangeNum(ChanChangeNum chanChangeNum);
	
	List<ChanChangeNum> findAddEnd();
	
	List<ChanChangeNum> findChanChangeNumById(Integer id);
	
	int updateChanChangeNum(ChanChangeNum chanChangeNum);
	
	public int deleteChanChangeNum(Integer id);
	
	List<ChanChangeNum> findChanChangeNumByIds(Integer[] ids);
	
	int deleteChanChangeNumBatch(Integer[] ids);
}
