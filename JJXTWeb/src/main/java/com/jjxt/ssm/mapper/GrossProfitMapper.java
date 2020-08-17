package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.Template;

public interface GrossProfitMapper {
	
	public List<Statistics> findAccountProfitList(Map<String, Object> map);
	public List<Statistics> findChannelProfitList(Map<String, Object> map);
	public List<Application> findApplication(Map<String, Object> map);
	public List<LgModelSend> findLgModelSendList(Map<String, Object> map);
	public List<Template> findTemplatePageListByIds(Integer[] ids);
	public List<Template> findAllTemplate();
	
}
