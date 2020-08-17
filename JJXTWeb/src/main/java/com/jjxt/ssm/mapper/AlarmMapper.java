package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Alarm;

public interface AlarmMapper {

	/**
	 * alarm_user
	 */
	/* 查询全部 */
	public List<Alarm> findAllAlarmUser();
	/* 添加数据 */
	public int addToAlarmUser(Map<String, Object> map);
	/* 根据id查询数据 */
	public Alarm findAlarmUserById(Integer id);
	/* 根据id删除数据 */
	public int deleteAlarmUserById(Integer id);
	/* 修改用户信息 */
	public int updateAlarmUser(Map<String, Object> map);

	/* 修改报警方式 */
	public int updateStrategySend(List<Alarm> list);
	/* 添加报警方式 */
	public int addStrategySend(Map<String, Object> map);
	
	public List<Alarm> findAllTypes();
	
	public List<Alarm> findAllUsers();
	
	public List<Alarm> findAllModels();
	/* 删除报警发送信息 */
	public int delSendById(Integer id);
	
	public int validatorUserName(String name);
	
	public int validatorModelName(String name);
	
	public int findUserIdByName(String name); 
	
	public int findTypeIdByName(String name); 
	
	public List<Alarm> findStrategyConfigIdList();
	
	public List<Integer> findUserIdList();
	
	public int insertStrategySend(List<Alarm> list);
	
	public int delRelationByuserId(Integer userId);
	
	public int findConfigByTypeLevel(Map<String,Integer> map);
	
	public int delRelationBytypeId(Integer typeId);
	
	/**
	 * alarm_model
	 */
	/* 查询全部 */
	public List<Alarm> findAllAlarmModel();
	/* 添加数据 */
	public int addToAlarmModel(Map<String, Object> map);
	/* 通过id查询数据 */
	public Alarm findAlarmModelById(Integer id);
	
	public int findAlarmConfigIsExistModel(Integer id);
	/* 通过id查询数据 */
	public List<Alarm> findAlarmConfigByModelId(Integer id);
	/* 通过id删除数据 */
	public int deleteAlarmModelById(Integer id);
	/* 通过id删除数据 */
	public int delConfigByModelId(Integer id);
	/* 通过model删除数据 */
	public int delRelationByModel(Map<String ,Integer> map);
	/* 修改 */
	public int updateAlarmModel(Map<String, Object> map);
	
	/**
	 * alarm_type
	 */
	/* 查询全部 */
	public List<Alarm> findAllAlarmType();
	/* 添加数据 */
	public int addToAlarmType(Map<String, Object> map);
	/* 通过id查询数据 */
	public Alarm findAlarmTypeById(Integer id);
	
	public int findAlarmConfigIsExistType(Integer id);
	/* 通过id删除数据 */
	public int deleteAlarmTypeById(Integer id);
	/* 修改 */
	public int updateAlarmType(Map<String, Object> map);

	/**
	 * alarm_strategy_config
	 */
	/* 查询全部 */
	public List<Alarm> findAllStrategyConfig(Map<String,Integer> map);
	/* 添加策略配置 */
	public int addStrategyConfig(Map<String, Object> map);
	/* 判断策略是否重复 */
	public int findIsExistStrategyConfig(Map<String, Object> map);
	/* 判断配置是否重复 */
	public int findIsExistStrategySend(Map<String, Object> map);
	/* 修改策略配置 */
	public int updateConfigById(Map<String, Object> map);
	/* 删除策略配置 */
	public int delConfigById(Integer id);
	/* 根据id查询数据 */
	public Alarm findConfigById(Integer id);
	
	public Alarm findTypeDetail(Integer id);
	
	/**
	 * alarm_relation
	 */
	/*查询用户数据*/
	public List<Alarm> findUserRelation(Map<String, Object> map);
	
	public int findUserRelationTotal(Map<String, Object> map);
	/*查询类型数据*/
	public List<Alarm> findTypeRelation(Map<String, Object> map);
	
	public int findTypeRelationTotal(Map<String, Object> map);
	
	public List<Integer> validatorRelation(List<Alarm> list);
	
}
