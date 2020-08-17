package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.entity.Product;
@Repository("productMapper")
public interface ProductMapper {

	List<Product> findProductPageList(Map<String, Object> map);

	Integer findProductTotal();

	Integer findAppNumber(Integer id);

	int addProduct(Product pro);

	List<Product> findEndAddProduct();

	Product findProductById(Integer id);

	int updateProduct(Product pro);
	
	List<Product> findAllProduct();

	int findTotal(Map<String, String> map);

	List<Application> findAccountByProductId(Integer id);

	int deleteProvinceConfig(Map<String, String> map);

	int addProvinceConfig(@Param("param")Map<String, String> map, @Param("privoder")String privoder, @Param("productId")String productId);

	List<LgProvincesSend> findLgProvinceSendByAppIdAndPrivoder(Map<String, String> map);

	List<LgProvincesSend> findLgProvincesSendByPorductId(Integer id);

	int addProvinceConfigs(@Param("param") List<LgProvincesSend> sends);
	
	List<Map<String, Object>> findLinkAcc(Integer id);
	
	Product findProductByIds(Integer id);
	
	List<Map<String, Object>> findLinkProduct(Integer id);

	LgProvincesSend findLgProvinceSendByMap(Map<String, String> map1);

}
