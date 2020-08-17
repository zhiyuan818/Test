package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.utils.DataSource;

public interface UcenterManagerService {
	// 管理员登录
	@DataSource("master")
	public UcenterManager findUcenterManagerByNameAndPwd(Map<String, String> map) throws Exception;

	// 查询所有管理员
	@DataSource("master")
	public List<UcenterManager> findAllManager() throws Exception;

	// 查找所有的销售人员
	@DataSource("master")
	public List<UcenterManager> findSalesManager() throws Exception;

	// 删除管理员
	@DataSource("master")
	public int deleteUcenterManager(int id) throws Exception;

	// 新增管理员
	@DataSource("master")
	public int addUcenterManager(UcenterManager ucenterManager) throws Exception;

	// 修改管理员
	@DataSource("master")
	public int updateUcenterManager(UcenterManager ucenterManager) throws Exception;

	// 修改辅助：通过id查找管理员
	@DataSource("master")
	public List<UcenterManager> findUcenterManagerById(int id) throws Exception;

	// 使用ajax验证用户名是否存在
	@DataSource("master")
	public UcenterManager findUcenterManagerByName(String managerName) throws Exception;

	/* 查询用户总条数 */
	@DataSource("master")
	public int findManagerTotal(String managerName, String searchTitle, String searchIsAllCustomer, String searchIsAllChannel) throws Exception;

	/* 根据条件查询用户的列表 */
	@DataSource("master")
	public List<UcenterManager> findUcenterManagerList(Map<String, Object> map) throws Exception;

	/* 查询最后添加的用户 */
	@DataSource("master")
	public List<UcenterManager> findEndAddManager() throws Exception;

	/* 批量删除管理员 */
	@DataSource("master")
	public int deleteUcenterManagerBatch(Integer[] ids) throws Exception;

	/* 根据多个id查询管理员列表 */
	@DataSource("master")
	public List<UcenterManager> findUcenterManagerByIds(Integer[] ids) throws Exception;

	/* 查询用户菜单 */
	@DataSource("master")
	public List<ManagerMenu> findManagerMenu(Integer id) throws Exception;

	/* 授权 */
	@DataSource("master")
	public int updateAuthorized(List<ManagerMenu> list) throws Exception;

	/* 查询当前用户的所有按钮 */
	@DataSource("master")
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id) throws Exception;
	
	@DataSource("master")
	public List<UcenterManager> findUcenterManager(Map<String, String> map) throws Exception;
	
	@DataSource("master")
	public List<GlobalSetting> findGlobalSetting() throws Exception;
	
	// 查找所有的商务人员
	@DataSource("master")
	public List<UcenterManager> findHeadManager() throws Exception;

	// 查找所有的客服人员
	@DataSource("master")
	public List<UcenterManager> findSaleAfterManager() throws Exception;
}
