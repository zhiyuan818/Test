package com.jjxt.ssm.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.mapper.BaseCompanyMapper;
import com.jjxt.ssm.service.BaseCompanyService;

@Transactional
@Service("baseCompanyService")
public class BaseCompanyServiceImpl implements BaseCompanyService {
	/**
	 * @Autowired注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 @Qualifier注解，qualifier的意思是合格者，通过这个标示，配合上面的@Autowired注解，指定了哪个实现类才是我们所需要的。
	 */
	@Autowired
	@Qualifier("baseCompanyMapper")
	private BaseCompanyMapper baseCompanyMapper;

	public BaseCompanyMapper baseCompanyMapper() {
		return baseCompanyMapper;
	}

	public void setBaseCompanyMapper(BaseCompanyMapper baseCompanyMapper) throws Exception {
		this.baseCompanyMapper = baseCompanyMapper;
	}

	// 查询公司信息
	public List<BaseCompany> findAllCompany() throws Exception {
		return baseCompanyMapper.findAllCompany();
	}

	// 新增公司信息
	public Integer addBaseCompany(BaseCompany baseCompany) throws Exception {
		return baseCompanyMapper.addBaseCompany(baseCompany);
	}

	@Override
	public int findTotal(Map<String, String> paramMap) throws Exception {
		return baseCompanyMapper.findTotalByCondition(paramMap);
	}

	@Override
	public List<BaseCompany> findCustomerPageList(Map<String, Object> paramMap) throws Exception {
		return baseCompanyMapper.findCustomerPageList(paramMap);
	}

	@Override
	public BaseCompany findCustomerById(Integer id) throws Exception {
		return baseCompanyMapper.findCustomerById(id);
	}

	@Override
	public int updateCustomer(BaseCompany company) throws Exception {
		return baseCompanyMapper.updateCustomer(company);
	}

	@Override
	public int findTotalByName(Map<String, String> paramMap) throws Exception {
		return baseCompanyMapper.findTotalByName(paramMap);
	}

	@Override
	public List<Map<String, Object>> findLinkAcc(Integer id) throws Exception {
		return baseCompanyMapper.findLinkAcc(id);
	}

	@Override
	public BaseCompany findCompanyDetails(String appId) throws Exception {
		return baseCompanyMapper.findCompanyDetails(appId);
	}

	@Override
	public BaseCompany findCustomerByAppId(int parseInt) throws Exception {
		return baseCompanyMapper.findCustomerByAppId(parseInt);
	}

	@Override
	public List<Map<String, String>> findCompanyMapByAppId(Set<String> addIdList) throws Exception {
		return baseCompanyMapper.findCompanyMapByAppId(addIdList);
	}
	
	@Override
	public List<BaseCompany> findAllCompanyKey() throws Exception {
		return baseCompanyMapper.findAllCompanyKey();
	}

	@Override
	public List<BaseCompany> findAllSales() throws Exception {
		return baseCompanyMapper.findAllSales();
	}
	
	@Override
	public List<BaseCompany> findAllSaleAfter() throws Exception {
		return baseCompanyMapper.findAllSaleAfter();
	}

	@Override
	public List<BaseCompany> findCompanyKeyBySort(Map<String, Object> map) throws Exception {
		return baseCompanyMapper.findCompanyKeyBySort(map);
	}

	@Override
	public List<BaseCompany> findSalesBySort(Map<String, Object> map) throws Exception {
		return baseCompanyMapper.findSalesBySort(map);
	}

	@Override
	public List<BaseCompany> findSaleAfterBySort(Map<String, Object> map) throws Exception {
		return baseCompanyMapper.findSaleAfterBySort(map);
	}
}
