package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Alarm;
import com.jjxt.ssm.mapper.AlarmMapper;
import com.jjxt.ssm.service.AlarmService;

@Service("AlarmService")
@Transactional
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	private AlarmMapper alarmMapper;

	@Override
	public List<Alarm> findAllAlarmUser() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllAlarmUser();
	}
	@Override
	public int addToAlarmUser(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.addToAlarmUser(map);
	}
	@Override
	public Alarm findAlarmUserById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAlarmUserById(id);
	}
	@Override
	public int deleteAlarmUserById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.deleteAlarmUserById(id);
	}
	@Override
	public int updateAlarmUser(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.updateAlarmUser(map);
	}
	@Override
	public int updateStrategySend(List<Alarm> list) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.updateStrategySend(list);
	}
	@Override
	public int addStrategySend(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.addStrategySend(map);
	}
	@Override
	public List<Alarm> findAllTypes() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllTypes();
	}
	@Override
	public List<Alarm> findAllAlarmModel() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllAlarmModel();
	}
	@Override
	public int addToAlarmModel(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.addToAlarmModel(map);
	}
	@Override
	public Alarm findAlarmModelById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAlarmModelById(id);
	}
	@Override
	public int findAlarmConfigIsExistModel(Integer id) throws Exception {
		return alarmMapper.findAlarmConfigIsExistModel(id);
	}
	@Override
	public int deleteAlarmModelById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.deleteAlarmModelById(id);
	}
	@Override
	public int delConfigByModelId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delConfigByModelId(id);
	}
	@Override
	public int delRelationByModel(Map<String,Integer> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delRelationByModel(map);
	}
	@Override
	public List<Alarm> findAlarmConfigByModelId(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAlarmConfigByModelId(id);
	}
	@Override
	public int updateAlarmModel(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.updateAlarmModel(map);
	}
	@Override
	public List<Alarm> findAllAlarmType() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllAlarmType();
	}
	@Override
	public int addToAlarmType(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.addToAlarmType(map);
	}
	@Override
	public Alarm findAlarmTypeById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAlarmTypeById(id);
	}
	@Override
	public int findAlarmConfigIsExistType(Integer id) throws Exception {
		return alarmMapper.findAlarmConfigIsExistType(id);
	}
	@Override
	public int deleteAlarmTypeById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.deleteAlarmTypeById(id);
	}
	@Override
	public int updateAlarmType(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.updateAlarmType(map);
	}
	@Override
	public List<Alarm> findAllStrategyConfig(Map<String,Integer> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllStrategyConfig(map);
	}
	@Override
	public int addStrategyConfig(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.addStrategyConfig(map);
	}
	@Override
	public int findIsExistStrategyConfig(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findIsExistStrategyConfig(map);
	}
	@Override
	public int findIsExistStrategySend(Map<String, Object> map) throws Exception {
		return alarmMapper.findIsExistStrategySend(map);
	}
	@Override
	public int updateConfigById(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.updateConfigById(map);
	}
	@Override
	public int delConfigById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delConfigById(id);
	}
	@Override
	public Alarm findConfigById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findConfigById(id);
	}
	@Override
	public List<Alarm> findAllModels() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllModels();
	}
	@Override
	public int delSendById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delSendById(id);
	}
	@Override
	public int validatorUserName(String name) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.validatorUserName(name);
	}
	@Override
	public int validatorModelName(String name) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.validatorModelName(name);
	}
	@Override
	public Alarm findTypeDetail(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findTypeDetail(id);
	}
	@Override
	public List<Alarm> findAllUsers() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findAllUsers();
	}
	@Override
	public List<Alarm> findUserRelation(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findUserRelation(map);
	}
	@Override
	public int findUserRelationTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findUserRelationTotal(map);
	}
	@Override
	public List<Alarm> findTypeRelation(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findTypeRelation(map);
	}
	@Override
	public int findTypeRelationTotal(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findTypeRelationTotal(map);
	}
	@Override
	public int findUserIdByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findUserIdByName(name);
	}
	@Override
	public List<Alarm> findStrategyConfigIdList() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findStrategyConfigIdList();
	}
	@Override
	public int insertStrategySend(List<Alarm> list) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.insertStrategySend(list);
	}
	@Override
	public List<Integer> findUserIdList() throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findUserIdList();
	}
	@Override
	public int findTypeIdByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.findTypeIdByName(name);
	}
	@Override
	public int delRelationByuserId(Integer userId) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delRelationByuserId(userId);
	}
	@Override
	public int findConfigByTypeLevel(Map<String,Integer> map) throws Exception {
		return alarmMapper.findConfigByTypeLevel(map);
	}
	@Override
	public int delRelationBytypeId(Integer typeId) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.delRelationBytypeId(typeId);
	}
	@Override
	public List<Integer> validatorRelation(List<Alarm> list) throws Exception {
		// TODO Auto-generated method stub
		return alarmMapper.validatorRelation(list);
	}


}
