package com.jjxt.ssm.service;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.entity.Product;
import com.jjxt.ssm.utils.DataSource;

public interface ProductService {
	/* 查看产品的总数 */
	@DataSource("master")
	Integer findProductTotal() throws Exception;

	/* 分页查询list */
	@DataSource("master")
	List<Product> findProductPageList(Map<String, Object> map) throws Exception;

	/* 查询产品的账户使用数 */
	@DataSource("master")
	Integer findAppNumber(Integer id) throws Exception;

	/* 添加查询 */
	@DataSource("master")
	int addProduct(Product pro) throws Exception;

	/* 查询最后添加产品 */
	@DataSource("master")
	List<Product> findEndAddProduct() throws Exception;

	/* 根据ID查询 */
	@DataSource("master")
	Product findProductById(Integer id) throws Exception;

	/* 修改产品 */
	@DataSource("master")
	int updateProduct(Product pro) throws Exception;

	/* 查询所有产品 */
	@DataSource("master")
	List<Product> findAllProduct() throws Exception;

	/* 查询总数 */
	@DataSource("master")
	int findTotal(Map<String, String> paramMap) throws Exception;

	/* 根据产品ID查询使用该产品的客户 */
	@DataSource("master")
	List<Application> findAccountByProductId(Integer id) throws Exception;

	/* 修改分省发送配置 */
	@DataSource("master")
	int updateProvinceConfig(Map<String, String> map, List<LgProvincesSend> list) throws Exception;

	/* 根据appId,privoder查询分省发送 */
	@DataSource("master")
	List<LgProvincesSend> findLgProvinceSendByAppIdAndPrivoder(Map<String, String> map) throws Exception;

	/* 查询通道的分省列表 */
	@DataSource("master")
	List<LgProvincesSend> findLgProvincesSendByPorductId(Integer id) throws Exception;

	/* 用户新增产品时，添加分省发送 */
	@DataSource("master")
	int addProvinceConfigs(List<LgProvincesSend> sends) throws Exception;

	/* 查询产品关联帐户*/
	@DataSource("master")
	List<Map<String, Object>> findLinkAcc(Integer id) throws Exception;
//	List<Map<String, Object>> findLinkAcc(Map<String, Object> map) throws Exception;
	
	@DataSource("master")
	Product findProductByIds(Integer id) throws Exception;
	
	/* 根据通道ID查询产品*/
	@DataSource("master")
	List<Map<String, Object>> findLinkProduct(Integer id) throws Exception;

}
