package com.jjxt.ssm.service;

import java.util.List;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.utils.DataSource;

public interface GlobalSettingService {

	/* 查询全部 */
	@DataSource("master")
	public List<GlobalSetting> findAll() throws Exception;

	/* 添加数据 */
	@DataSource("master")
	public int addToGlobalSetting(GlobalSetting global) throws Exception;

	/* 通过id查询数据 */
	@DataSource("master")
	public GlobalSetting findGlobalSettingById(Integer id) throws Exception;

	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteGlobalById(Integer id) throws Exception;

	/* 判断key是否存在 */
	@DataSource("master")
	public int findGlobalByKey(String key) throws Exception;

	/* 修改配置 */
	@DataSource("master")
	public int updateGlobal(GlobalSetting global) throws Exception;
	
	@DataSource("master")
	public String findGlobalValueByKey(String key) throws Exception;

}
