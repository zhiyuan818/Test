package com.jjxt.ssm.mapper;

import java.util.List;

import com.jjxt.ssm.entity.GlobalSetting;

public interface GlobalSettingMapper {

	/* 查询全部 */
	public List<GlobalSetting> findAll();

	/* 添加数据 */
	public int addToGlobalSetting(GlobalSetting global);

	/* 通过id查询数据 */
	public GlobalSetting findGlobalSettingById(Integer id);

	/* 通过id删除数据 */
	public int deleteGlobalById(Integer id);

	/* 判断key是否存在 */
	public int findGlobalByKey(String key);

	/* 修改配置 */
	public int updateGlobal(GlobalSetting global);

	public String findGlobalValueByKey(String key);

}
