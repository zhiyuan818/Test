package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.GlobalSetting;
import com.jjxt.ssm.entity.ManagerMenu;
import com.jjxt.ssm.entity.UcenterManager;

@Repository("ucenterManagerMapper")
public interface UcenterManagerMapper {
	// 管理员登录
	public UcenterManager findUcenterManagerByNameAndPwd(Map<String, String> map) throws Exception;

	// 查找所有管理
	public List<UcenterManager> findAllManager() throws Exception;

	// 查找所有的销售人员
	public List<UcenterManager> findSalesManager() throws Exception;

	// 删除管理员
	public int deleteUcenterManager(int id) throws Exception;

	// 新增管理员
	public int addUcenterManager(UcenterManager ucenterManager) throws Exception;

	// 修改管理员
	public int updateUcenterManager(UcenterManager ucenterManager) throws Exception;

	// 修改辅助：通过id查找管理员
	public List<UcenterManager> findUcenterManagerById(int id) throws Exception;

	// 使用ajax验证登录的用户名是否存在
	public UcenterManager findUcenterManagerByName(String managerName) throws Exception;

	/* 查询管理员分页 */
	public List<UcenterManager> findUcenterManagerList(Map<String, Object> map) throws Exception;

	/* 查询管理员总条数 */
	public int findManagerTotal(Map<String, String> map) throws Exception;

	/* 查询最后一次添加的用户 */
	public List<UcenterManager> findEndAddManager() throws Exception;

	/* 多个管理员进行删除 */
	public int deleteUcenterManagerBatch(Integer[] ids) throws Exception;

	/* 查询多个管理员的列表 */
	public List<UcenterManager> findUcenterManagerByIds(Integer[] ids) throws Exception;

	/* 查询用户菜单 */
	public List<ManagerMenu> findManagerMenu(Integer id) throws Exception;

	/* 用户授权 */
	public int authorized(List<ManagerMenu> managerMenu) throws Exception;

	/* 删除权限 */
	public int deleteAuthorizedByUcenterManagerId(Integer ucenterManagerId) throws Exception;

	/* 查询当前用户的所有按钮 */
	public List<ManagerMenu> findMenuLinkByUcenterManagerId(Integer id);

	/* 批量删除权限 */
	public void deleteAuthorizedByUcenterManagerIds(Integer[] ids);

	public List<UcenterManager> findUcenterManager(Map<String, String> map);
	
	public List<GlobalSetting> findGlobalSetting();
	
	// 查找所有的销售人员
	public List<UcenterManager> findHeadManager() throws Exception;
	
	// 查找所有的客服人员
	public List<UcenterManager> findSaleAfterManager() throws Exception;

}
