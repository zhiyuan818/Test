package com.jjxt.ssm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.entity.Product;
import com.jjxt.ssm.mapper.ProductMapper;
import com.jjxt.ssm.service.ProductService;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public Integer findProductTotal() throws Exception {
		return productMapper.findProductTotal();
	}

	@Override
	public List<Product> findProductPageList(Map<String, Object> map) throws Exception {
		return productMapper.findProductPageList(map);
	}

	@Override
	public Integer findAppNumber(Integer id) throws Exception {
		return productMapper.findAppNumber(id);
	}

	@Override
	public int addProduct(Product pro) throws Exception {
		return productMapper.addProduct(pro);
	}

	@Override
	public List<Product> findEndAddProduct() throws Exception {
		return productMapper.findEndAddProduct();
	}

	@Override
	public Product findProductById(Integer id) throws Exception {
		return productMapper.findProductById(id);
	}

	@Override
	public int updateProduct(Product pro) throws Exception {
		return productMapper.updateProduct(pro);
	}

	@Override
	public List<Product> findAllProduct() throws Exception {
		return productMapper.findAllProduct();
	}

	@Override
	public int findTotal(Map<String, String> map) throws Exception {
		return productMapper.findTotal(map);
	}

	@Override
	public List<Application> findAccountByProductId(Integer id) throws Exception {
		return productMapper.findAccountByProductId(id);
	}

	@Override
	public int updateProvinceConfig(Map<String, String> map,List<LgProvincesSend> sends) throws Exception {
		
		Map<String, String> map1 = new HashMap<String,String>();
		
		for(LgProvincesSend send : sends) {
			map1.put("productId", String.valueOf(send.getProductId()));
			map1.put("carriers", send.getCarriers());
			map1.put("province", send.getProvince());
			LgProvincesSend entry = productMapper.findLgProvinceSendByMap(map1);
			if(entry != null) {
				send.setStatus(entry.getStatus());
			}else{
				send.setStatus("normal");
			}
		}
		
		int deleteProvinceConfig = productMapper.deleteProvinceConfig(map);
		return productMapper.addProvinceConfigs(sends);
	}

	@Override
	public List<LgProvincesSend> findLgProvinceSendByAppIdAndPrivoder(Map<String, String> map) throws Exception {
		
		return productMapper.findLgProvinceSendByAppIdAndPrivoder(map);
	}

	@Override
	public List<LgProvincesSend> findLgProvincesSendByPorductId(Integer id) throws Exception {
		return productMapper.findLgProvincesSendByPorductId(id);
	}

	@Override
	public int addProvinceConfigs(List<LgProvincesSend> sends) throws Exception {
		return productMapper.addProvinceConfigs(sends);
	}

	
	@Override
	public List<Map<String, Object>> findLinkAcc(Integer id) throws Exception {
		return productMapper.findLinkAcc(id);
	}

	@Override
	public Product findProductByIds(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return productMapper.findProductByIds(id);
	}
	
	@Override
	public List<Map<String, Object>> findLinkProduct(Integer id) throws Exception {
		return productMapper.findLinkProduct(id);
	}
}
