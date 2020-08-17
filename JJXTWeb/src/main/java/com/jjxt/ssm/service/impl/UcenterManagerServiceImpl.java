package com.jjxt.ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.mapper.UcenterManagerMapper;
import com.jjxt.ssm.service.UcenterManagerService;

@Service("ucenterManagerService")
@Transactional
public class UcenterManagerServiceImpl implements UcenterManagerService {

	@Autowired
	@Qualifier("ucenterManagerMapper")
	private UcenterManagerMapper ucenterManagerMapper;

	public UcenterManagerMapper ucenterManagerMapper() throws Exception {
		return ucenterManagerMapper;
	}

	public void setUcenterManagerMapper(UcenterManagerMapper ucenterManagerMapper) throws Exception {
		this.ucenterManagerMapper = ucenterManagerMapper;
	}

	// 查询所有管理员
	public List<UcenterManager> findAllManager() throws Exception {
		return ucenterManagerMapper.findAllManager();
	}

	// 管理员登录
	public UcenterManager findUcenterManagerByNameAndPwd(Map<String, String> map) throws Exception {
		return ucenterManagerMapper.findUcenterManagerByNameAndPwd(map);
	}

	// 管理员删除
	public int deleteUcenterManager(int id) throws Exception {
		ucenterManagerMapper.deleteAuthorizedByUcenterManagerId(id);
		return ucenterManagerMapper.deleteUcenterManager(id);
	}

	// 添加管理员
	public int addUcenterManager(UcenterManager ucenterManager) throws Exception {
		return ucenterManagerMapper.addUcenterManager(ucenterManager);
	}

	// 管理员修改
	public int updateUcenterManager(UcenterManager ucenterManager) throws Exception {
		return ucenterManagerMapper.updateUcenterManager(ucenterManager);
	}

	// 修改辅助：通过id查找管理员
	public List<UcenterManager> findUcenterManagerById(int id) throws Exception {
		return ucenterManagerMapper.findUcenterManagerById(id);
	}

	// 使用ajax验证用户名是否存在
	public UcenterManager findUcenterManagerByName(String managerName) throws Exception {
		return ucenterManagerMapper.findUcenterManagerByName(managerName);
	}

	@Override
	public int findManagerTotal(String searchManagerName, String searchTitle, String searchIsAllCustomer, String searchIsAllChannel) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchManagerName", searchManagerName);
		map.put("searchTitle", searchTitle);
		map.put("searchIsAllCustomer", searchIsAllCustomer);
		map.put("searchIsAllChannel", searchIsAllChannel);
		return ucenterManagerMapper.findManagerTotal(map);
	}

	@Override
	public List<UcenterManager> findUcenterManagerList(Map<String, Object> map) throws Exception {
		return ucenterManagerMapper.findUcenterManagerList(map);
	}

	@Override
	public List<UcenterManager> findEndAddManager() throws Exception {

		return ucenterManagerMapper.findEndAddManager();
	}

	@Override
	public int deleteUcenterManagerBatch(Integer[] ids) throws Exception {
		ucenterManagerMapper.deleteAuthorizedByUcenterManagerIds(ids);
		return ucenterManagerMapper.deleteUcenterManagerBatch(ids);
	}

	@Override
	public List<UcenterManager> findUcenterManagerByIds(Integer[] ids) throws Exception {
		return ucenterManagerMapper.findUcenterManagerByIds(ids);
	}

	@Override
	public List<ManagerMenu> findManagerMenu(Integer id) throws Exception {
		return ucenterManagerMapper.findManagerMenu(id);
	}

	@Override
	public int updateAuthorized(List<ManagerMenu> managerMenu) throws Exception {
		ucenterManagerMapper.deleteAuthorizedByUcenterManagerId(managerMenu.get(0).getUcenterManagerId());
		int authorized = ucenterManagerMapper.authorized(managerMenu);
		return authorized;
	}

	@Override
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id) throws Exception {

		return ucenterManagerMapper.findMenuLinkByUcenterManagerId(id);
	}

	@Override
	public List<UcenterManager> findSalesManager() throws Exception {
		return ucenterManagerMapper.findSalesManager();
	}

	@Override
	public List<UcenterManager> findUcenterManager(Map<String, String> map) throws Exception {
		return ucenterManagerMapper.findUcenterManager(map);
	}
	
	@Override
	public List<GlobalSetting> findGlobalSetting() throws Exception {
		return ucenterManagerMapper.findGlobalSetting();
	}

	@Override
	public List<UcenterManager> findHeadManager() throws Exception {
		return ucenterManagerMapper.findHeadManager();
	}
	@Override
	public List<UcenterManager> findSaleAfterManager() throws Exception {
		return ucenterManagerMapper.findSaleAfterManager();
	}
}
