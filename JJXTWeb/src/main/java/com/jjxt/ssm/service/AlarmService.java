package com.jjxt.ssm.service;


import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Alarm;
import com.jjxt.ssm.utils.DataSource;

public interface AlarmService {

	/**
	 * alarm_user
	 */
	/* 查询全部 */
	@DataSource("master")
	public List<Alarm> findAllAlarmUser() throws Exception;
	/* 添加数据 */
	@DataSource("master")
	public int addToAlarmUser(Map<String, Object> map) throws Exception;
	/* 通过id查询数据 */
	@DataSource("master")
	public Alarm findAlarmUserById(Integer id) throws Exception;
	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteAlarmUserById(Integer id) throws Exception;
	/* 修改配置 */
	@DataSource("master")
	public int updateAlarmUser(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int updateStrategySend(List<Alarm> list) throws Exception;
	@DataSource("master")
	public int addStrategySend(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Alarm> findAllTypes() throws Exception;
	@DataSource("master")
	public List<Alarm> findAllUsers() throws Exception;
	@DataSource("master")
	public List<Alarm> findAllModels() throws Exception;
	@DataSource("master")
	public int delSendById(Integer id) throws Exception;
	@DataSource("master")
	public int validatorUserName(String name) throws Exception;
	@DataSource("master")
	public int validatorModelName(String name) throws Exception;
	@DataSource("master")
	public int findUserIdByName(String name) throws Exception;
	@DataSource("master")
	public int findTypeIdByName(String name) throws Exception;
	@DataSource("master")
	public List<Alarm> findStrategyConfigIdList() throws Exception;
	@DataSource("master")
	public List<Integer> findUserIdList() throws Exception;
	@DataSource("master")
	public int insertStrategySend(List<Alarm> list) throws Exception;
	@DataSource("master")
	public int delRelationByuserId(Integer userId) throws Exception;
	@DataSource("master")
	public int delRelationBytypeId(Integer typeId) throws Exception;
	@DataSource("master")
	public int findConfigByTypeLevel(Map<String,Integer> map) throws Exception;
	
	
	/**
	 * alarm_model
	 */
	/* 查询全部 */
	@DataSource("master")
	public List<Alarm> findAllAlarmModel() throws Exception;
	/* 添加数据 */
	@DataSource("master")
	public int addToAlarmModel(Map<String, Object> map) throws Exception;
	/* 通过id查询数据 */
	@DataSource("master")
	public Alarm findAlarmModelById(Integer id) throws Exception;
	@DataSource("master")
	public int findAlarmConfigIsExistModel(Integer id) throws Exception;
	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteAlarmModelById(Integer id) throws Exception;
	/* 通过id删除数据 */
	@DataSource("master")
	public int delRelationByModel(Map<String ,Integer> map) throws Exception;
	/* 通过id删除数据 */
	@DataSource("master")
	public int delConfigByModelId(Integer id ) throws Exception;
	/* 通过model_id获取数据 */
	@DataSource("master")
	public List<Alarm> findAlarmConfigByModelId(Integer id) throws Exception;
	/* 修改 */
	@DataSource("master")
	public int updateAlarmModel(Map<String, Object> map) throws Exception;
	
	/**
	 * alarm_type
	 */
	/* 查询全部 */
	@DataSource("master")
	public List<Alarm> findAllAlarmType() throws Exception;
	/* 添加数据 */
	@DataSource("master")
	public int addToAlarmType(Map<String, Object> map) throws Exception;
	/* 通过id查询数据 */
	@DataSource("master")
	public Alarm findAlarmTypeById(Integer id) throws Exception;
	@DataSource("master")
	public int findAlarmConfigIsExistType(Integer id) throws Exception;
	/* 通过id删除数据 */
	@DataSource("master")
	public int deleteAlarmTypeById(Integer id) throws Exception;
	/* 修改 */
	@DataSource("master")
	public int updateAlarmType(Map<String, Object> map) throws Exception;
	
	/**
	 * alarm_strategy_config
	 * @return
	 */
	/* 查询全部 */
	@DataSource("master")
	public List<Alarm> findAllStrategyConfig(Map<String,Integer> map) throws Exception;
	/* 添加策略配置 */
	@DataSource("master")
	public int addStrategyConfig(Map<String, Object> map) throws Exception;
	/* 添加策略配置前判断是否存在 */
	@DataSource("master")
	public int findIsExistStrategyConfig(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int findIsExistStrategySend(Map<String, Object> map) throws Exception;
	/* 修改策略配置 */
	@DataSource("master")
	public int updateConfigById(Map<String, Object> map) throws Exception;
	/* 删除策略配置 */
	@DataSource("master")
	public int delConfigById(Integer id) throws Exception;
	/* 根据id查询数据 */
	@DataSource("master")
	public Alarm findConfigById(Integer id) throws Exception;
	@DataSource("master")
	public Alarm findTypeDetail(Integer id) throws Exception;
	
	/**
	 * alarm_relation
	 */
	/*查询用户数据*/
	@DataSource("master")
	public List<Alarm> findUserRelation(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int findUserRelationTotal(Map<String, Object> map) throws Exception;
	/*查询类型数据*/
	@DataSource("master")
	public List<Alarm> findTypeRelation(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public int findTypeRelationTotal(Map<String, Object> map) throws Exception;
	@DataSource("master")
	public List<Integer> validatorRelation(List<Alarm> list) throws Exception;
}
