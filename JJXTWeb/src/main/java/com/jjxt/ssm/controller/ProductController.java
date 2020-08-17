package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.LgProvincesSend;
import com.jjxt.ssm.entity.Product;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ProductService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.ResultData;
import com.jjxt.ssm.utils.StringUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static Logger logger = Logger.getLogger(ProductController.class);

	private static final String PATH = "product/";
	@Autowired
	private ProductService productService;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private ApplicationService applicationService;
	/**
	 * 跳转产品列表页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/goProductPageList.action")
	public String goProductPageList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<Channel>();
		List<Channel> cmccChannels =  new ArrayList<Channel>();
		List<Channel> unicomChannels =  new ArrayList<Channel>();
		List<Channel> telecomChannels =  new ArrayList<Channel>();
		List<Channel> intlChannels= new ArrayList<Channel>();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL]", e);
		}
		for(Channel cha:channels){
			if("yes".equals(cha.getToCmcc())){
				cmccChannels.add(cha);
			}
			if("yes".equals(cha.getToUnicom())){
				unicomChannels.add(cha);
			}
			if("yes".equals(cha.getToTelecom())){
				telecomChannels.add(cha);
			}
			if("yes".equals(cha.getToIntl())) {
				intlChannels.add(cha);
			}
		}
		Channel channel=new Channel();
		channel.setChannelId(1);
		channel.setName("响应无通道");
		cmccChannels.add(channel);
		unicomChannels.add(channel);
		telecomChannels.add(channel);
		intlChannels.add(channel);
		Collections.sort(cmccChannels, new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				// TODO Auto-generated method stub
				return o1.getChannelId()-o2.getChannelId();
			}
		});
		Collections.sort(unicomChannels, new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				// TODO Auto-generated method stub
				return o1.getChannelId()-o2.getChannelId();
			}
		});
		Collections.sort(telecomChannels, new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				// TODO Auto-generated method stub
				return o1.getChannelId()-o2.getChannelId();
			}
		});
		Collections.sort(intlChannels, new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				// TODO Auto-generated method stub
				return o1.getChannelId()-o2.getChannelId();
			}
		});
		List<Product> products=new ArrayList<>();
		try {
			products = productService.findAllProduct();
		} catch (Exception e) {
			logger.error("[ERR][PRODUCT] EX="+e);
		}
		request.setAttribute("channels", channels);
		request.setAttribute("cmccChannels", cmccChannels);
		request.setAttribute("unicomChannels", unicomChannels);
		request.setAttribute("telecomChannels", telecomChannels);
		request.setAttribute("intlChannels", intlChannels);
		request.setAttribute("products", products);
		return PATH + "productPageList";
	}

	/**
	 * 产品分页
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	@RequestMapping("/findProductPageList.action")
	@ResponseBody
	public PageResult<Product> findProductPageList(Integer pageIndex, Integer pageSize,String productId,String productStatus,String channelId) {
		logger.debug("[BINS][PRODUCT] pageIndex=" + pageIndex + ",pageSize=" + pageSize+",productId="+productId+",productStatus="+productStatus+",channelId="+channelId);
		long stime=System.currentTimeMillis();
		int total = 0;
		Map<String, String> map1=new HashMap<String,String>();
		map1.put("productId", productId);
		map1.put("productStatus", productStatus);
		map1.put("channelId", channelId);
		try {
			total = productService.findTotal(map1);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] 查询总数异常", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("productId", productId);
		map.put("productStatus", productStatus);
		map.put("channelId", channelId);
		List<Product> productPageList = null;
		try {
			productPageList = productService.findProductPageList(map);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] 查询列表异常", e);
		}
		logger.debug("[BINS][PRODUCT] TIME="+(System.currentTimeMillis()-stime));
		return new PageResult<>(total, productPageList);
	}

	/**
	 * 产品新增提交
	 * 
	 * @param productType
	 * @param productClass
	 * @param productName
	 * @param description
	 * @param cmccChannelId
	 * @param unicomChannelId
	 * @param telecomChannelId
	 * @param isSign
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.product, operation = Operate.INSERT)
	@RequestMapping("/addProduct.action")
	@ResponseBody
	public ResponseResult addProduct(String productType, String productClass, String productName, String description,
			Integer cmccChannelId, Integer unicomChannelId, Integer telecomChannelId,Integer intlChannelId, String isSign) {
		logger.debug("[BINS][PRODUCT] productType=" + productType + ",productClass=" + productClass + ",productName="
				+ productName + ",description=" + description + ",cmccChannelId=" + cmccChannelId + ",unicomChannelId="
				+ unicomChannelId + ",telecomChannelId=" + telecomChannelId +",intlChannelId="+intlChannelId + ",isSign=" + isSign);
		Product pro = new Product();
		pro.setCmccChannelId(cmccChannelId == null ? 0 : cmccChannelId);
		pro.setUnicomChannelId(unicomChannelId == null ? 0 : unicomChannelId);
		pro.setTelecomChannelId(telecomChannelId == null ? 0 : telecomChannelId);
		pro.setIntlChannelId(intlChannelId == null ? 0 : intlChannelId);
		pro.setProductClass(productClass);
		pro.setProductName(productName);
		pro.setProductStatus("normal");
		pro.setProductType(productType);
		pro.setCreateTime(new Date());
		pro.setUpdateTime(new Date());
		pro.setDescription(description);
		pro.setIsSign(isSign);
		pro.setIsCheck("no");
		pro.setUnknownChannelId(0);
		pro.setAllowProvince("");
		pro.setTacticsId(0);
		int i = 0;
		try {
			i = productService.addProduct(pro);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] ADD EXCEPTION", e);
		}
		List<Product> products = null;
		try {
			products = productService.findEndAddProduct();
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] 查询最后添加异常", e);
		}
		return new ResponseResult(i, products, null);
	}

	/**
	 * 根据ID查询产品
	 * 
	 * @param id
	 * @return
	 */
	
	@RequestMapping("/findProductById.action")
	@ResponseBody
	public Map<String, Object> findProductById(Integer id) {
		logger.debug("[BINS][PRODUCT] id=" + id);
		long sTime=System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		Product products = null;
		try {
			products = productService.findProductById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] id查询异常", e);
		}
		List<LgProvincesSend> provincesSends = null;
		try {
			provincesSends = productService.findLgProvincesSendByPorductId(id);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] id查询分省异常", e);
		}
		logger.debug("[BINS][PRODUCT] TIME="+(System.currentTimeMillis()-sTime));
		if (null != products) {
			map.put("product", products);
			map.put("provincesSends", provincesSends);
			return map;
		}
		return null;
	}

	/**
	 * 产品更新提交
	 * 
	 * @param id
	 * @param productName
	 * @param description
	 * @param cmccChannelId
	 * @param unicomChannelId
	 * @param telecomChannelId
	 * @param productStatus
	 * @param isSign
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.product, operation = Operate.UPDATE)
	@RequestMapping("/updateProduct.action")
	@ResponseBody
	public ResponseResult updateProduct(Integer id, String productName, String description, Integer cmccChannelId,
			Integer unicomChannelId, Integer telecomChannelId,Integer intlChannelId, String productStatus, String isSign) {
		logger.debug("[BINS][PRODUCT] id=" + id + ",productName=" + productName + ",description=" + description
				+ ",cmccChannelId=" + cmccChannelId + ",unicomChannelId=" + unicomChannelId + ",telecomChannelId="
				+ telecomChannelId+",intlChannelId="+intlChannelId + ",productStatus=" + productStatus + ",isSign=" + isSign);
		Product pro = new Product();
		Map<String, Object> oldMap=new HashMap<>();
		Map<String, Object> newMap=new HashMap<>();
		pro.setId(id);
		pro.setCmccChannelId(cmccChannelId == null ? 0 : cmccChannelId);
		pro.setUnicomChannelId(unicomChannelId == null ? 0 : unicomChannelId);
		pro.setTelecomChannelId(telecomChannelId == null ? 0 : telecomChannelId);
		pro.setIntlChannelId(intlChannelId == null ? 0 : intlChannelId);
		pro.setProductName(productName);
		pro.setProductStatus(productStatus);
		pro.setUpdateTime(new Date());
		pro.setDescription(description);
		pro.setIsSign(isSign);
		Product oldData = null;
		try {
			oldData = productService.findProductById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] 查询最后添加异常", e);
		}
		if(oldData == null){
			return new ResponseResult(0, newMap, oldMap);
		}
		oldMap.put("oldDate", oldData);
		int i = 0;
		try {
			i = productService.updateProduct(pro);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] ADD EXCEPTION", e);
		}
		Product newData = null;
		try {
			newData = productService.findProductById(id);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] 查询最后添加异常", e);
		}
		newMap.put("newData", newData);
		return new ResponseResult(i, newMap, oldMap);
	}

	/**
	 * 查询所有的产品
	 * 
	 * @return
	 */
	
	@RequestMapping("/findAllProduct.action")
	@ResponseBody
	public Map<String, Object> findAllProduct() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Product> list = new ArrayList<>();
		try {
			list = productService.findAllProduct();
		} catch (Exception e) {
			logger.error("exce=" + e);
		}

		map.put("data", list);
		return map;
	}

	/**
	 * 判断产品名称是否存在
	 * 
	 * @param productName
	 * @return
	 */
	
	@RequestMapping("/validatorProduct.action")
	@ResponseBody
	public ResultData validatorProduct(String productName,String oldProductName) {
		ResultData result = new ResultData();
		if(productName.equals(oldProductName)){
			result.setValid(true);
			return result;
		}
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("validName", productName);
		int total = 0;
		try {
			total = productService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT] ", e);
		}
		if (total > 0) {
			result.setValid(false);
		} else {
			result.setValid(true);
		}

		return result;
	}

	
	@SuppressWarnings("unchecked")
	@DataOperate(bussiness = Bussiness.product, operation = Operate.UPDATEBATCH)
	@RequestMapping("/provinceConfig.action")
	@ResponseBody
	public ResponseResult provinceConfig(String json) {
		logger.debug("[BINS][PRODUCT] json=" + json );
		if (StringUtil.isEmpty(json)) {
			return null;
		}
		JSONArray jsonArray = JSONArray.fromObject(json);
		List<LgProvincesSend> list = (List<LgProvincesSend>) JSONArray.toCollection(jsonArray, LgProvincesSend.class);
		Integer productId=null;
		String carriers="";
		if(list.size()>0) {
			productId=list.get(0).getProductId();
			carriers=list.get(0).getCarriers();
		}
		Map<String, String> map=new HashMap<String,String>();
		map.put("productId", String.valueOf(productId));
		map.put("carriers", carriers);
		List<LgProvincesSend> oldData = null;
		try {
			oldData = productService.findLgProvinceSendByAppIdAndPrivoder(map);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT]", e);
		}
		int result = 0;
		try {
			result = productService.updateProvinceConfig(map,list);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT]", e);
		}
		List<LgProvincesSend> newData = null;
		try {
			newData = productService.findLgProvinceSendByAppIdAndPrivoder(map);
		} catch (Exception e) {
			logger.error("[ERROR][PRODUCT]", e);
		}
		return new ResponseResult(result, newData, oldData);
	}

	
	@RequestMapping("/validatorproductIsUse.action")
	@ResponseBody
	public ResultData validatorproductIsUse(Integer id){
		Map<String, String> map=new HashMap<String, String>();
		ResultData resultData=new ResultData();
		map.put("productId", id.toString());
		int i=0;
		try {
			i=applicationService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][application]" ,e);
		}
		if(i>0){
			resultData.setValid(false);
			return resultData;
		}else{
			resultData.setValid(true);
			return resultData;
		}
	}

	
	@RequestMapping("/findAccountByProductId.action")
	@ResponseBody
	public ResultData findAccountByProductId(Integer id){
		List<Application> apps=null;
		ResultData data=new ResultData();
		try {
			apps = productService.findAccountByProductId(id);
		} catch (Exception e) {
			logger.error("[ERROR][APPLICATION]",e);
		}
		if(apps != null && apps.size()>0){
			data.setValid(false);
			return data;
		}
		data.setValid(true);
		return data;
		
	}

	//	查询产品关联帐户
	
	@RequestMapping("/findLinkAcc.action")
	@ResponseBody
	public List<Map<String,Object>> findLinkAcc(Integer id) {
		logger.debug("[BINS][CUSTOMER] id=" + id);
		List<Map<String, Object>> result = new ArrayList<>();
		try {
			result = productService.findLinkAcc(id);
		} catch (Exception e) {
			logger.error("[ERR][CUSTOMER] ", e);
		}
		return result;
	}
	
//	//	查询产品关联帐户
//	@RequestMapping("/findLinkAcc.action")
//	@ResponseBody
//	public PageResult<Map<String,Object>> findLinkAcc(Integer pageIndex, Integer pageSize,Integer id) {
//		logger.debug("[BINS][CUSTOMER] id=" + id);
//		int total = 0;
//		try {
//			total = productService.findAppNumber(id);
//		} catch (Exception e) {
//			logger.error("[ERROR][PRODUCT] 查询总数异常", e);
//		}
//		Page page = new Page(pageSize, total, pageIndex);
//		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
//		map.put("id", id);
//		
//		List<Map<String, Object>> result = new ArrayList<>();
//		try {
//			result = productService.findLinkAcc(map);
//		} catch (Exception e) {
//			logger.error("[BINS][CUSTOMER] ", e);
//		}
//		return new PageResult<>(total, result);
//	}
	
}
