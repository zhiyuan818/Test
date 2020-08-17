package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.BaseCompany;

/**
 * @Repository用于将数据访问层 (DAO 层 ) 的类标识为 Spring Bean; 同时它还能将所标注的类中抛出的数据访问异常封装为
 *                     Spring 的数据访问异常类型。
 *                     Spring本身提供了一个丰富的并且是与具体的数据访问技术无关的数据访问异常结构，
 *                     用于封装不同的持久层框架抛出的异常，使得异常独立于底层的框架。
 * @author taoliu
 */
@Repository(value = "baseCompanyMapper")
public interface BaseCompanyMapper {
	// 查询所有公司信息
	public List<BaseCompany> findAllCompany() throws Exception;

	// 新增公司信息
	public Integer addBaseCompany(BaseCompany baseCompany) throws Exception;

	int findTotalByCondition(Map<String, String> paramMap);
	
	int findTotalByName(Map<String, String> paramMap);
	
	public List<BaseCompany> findCustomerPageList(Map<String, Object> paramMap);

	public BaseCompany findCustomerById(Integer id);

	public int updateCustomer(BaseCompany company);
	
	public List<Map<String, Object>> findLinkAcc(Integer id);

	public BaseCompany findCompanyDetails(String appId);

	public BaseCompany findCustomerByAppId(int id);

	public List<Map<String, String>> findCompanyMapByAppId(@Param("set")Set<String> addIdList);
	
	public List<BaseCompany> findAllCompanyKey();
	
	public List<BaseCompany> findAllSales();
	
	public List<BaseCompany> findAllSaleAfter();
	
	public List<BaseCompany> findCompanyKeyBySort(Map<String,Object> map);
	
	public List<BaseCompany> findSalesBySort(Map<String,Object> map);
	
	public List<BaseCompany> findSaleAfterBySort(Map<String,Object> map);
}
