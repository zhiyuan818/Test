package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgModelSend;
import com.jjxt.ssm.entity.Statistics;
import com.jjxt.ssm.entity.Template;
import com.jjxt.ssm.utils.DataSource;

public interface GrossProfitService {
	@DataSource("slave")
	public List<Statistics> findAccountProfitList(Map<String, Object> map) throws Exception;
	@DataSource("slave")
	public List<Statistics> findChannelProfitList(Map<String, Object> map) throws Exception;
	@DataSource("slave")
	public List<Application> findApplication(Map<String, Object> map) throws Exception;
	@DataSource("slave")
	public List<LgModelSend> findLgModelSendList(Map<String, Object> map) throws Exception;
	@DataSource("slave")
	public List<Template> findTemplatePageListByIds(Integer[] ids) throws Exception;
	@DataSource("slave")
	public List<Template> findAllTemplate() throws Exception;
	
}
