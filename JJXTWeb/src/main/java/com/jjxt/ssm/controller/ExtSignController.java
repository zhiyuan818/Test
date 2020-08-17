package com.jjxt.ssm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.ExtSign;
import com.jjxt.ssm.entity.SignExtTemplate;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ExtSignService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/extSign")
public class ExtSignController {

	private static Logger logger = Logger.getLogger(ExtSignController.class);

	private static final String PATH = "extSign/";
	@Autowired
	private ExtSignService extsignService;
	@Autowired
	private ApplicationService appService;
	@Autowired
	private ChannelService chanService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goExtSignPage.action")
	public String goExtSignPage(HttpServletRequest request) {
		List<Application> names = new ArrayList<>();
		List<Channel> channels = new ArrayList<>();
		List<Channel> cmccChannels =  new ArrayList<Channel>();
		List<Channel> unicomChannels =  new ArrayList<Channel>();
		List<Channel> telecomChannels =  new ArrayList<Channel>();
		Map<String, Object> map=new HashMap<String,Object>();
		try {
			names = appService.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] find appid and name", e);
		}
		try {
			channels = chanService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] find channelid and name", e);
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
		}
		request.setAttribute("apps", names);
		request.setAttribute("channels", channels);
		request.setAttribute("cmccChannels", cmccChannels);
		request.setAttribute("unicomChannels", unicomChannels);
		request.setAttribute("telecomChannels", telecomChannels);

		return PATH + "extSignList";
	}

	
	@RequestMapping(value = "/findAllList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<ExtSign> findAllList(HttpServletRequest request, HttpServletResponse response, Integer pageSize,
			Integer pageIndex, String appId, String sign) {

		List<ExtSign> lists = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		Integer total = 0;
		Long sTime = System.currentTimeMillis();
		try {
			map.put("appId", appId);
			map.put("sign", sign);
			
			total = extsignService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 查询扩展加签名总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("appId", appId);
			map1.put("sign", sign);
			lists = extsignService.findAllList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][EXT_SIGN] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appId=" + appId
					+ ",sign=" + sign + ",time=" + (eTime - sTime));
			return new PageResult<ExtSign>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 查询扩展加签名分页出错.", e);
		}

		return null;
	}

	
	@DataOperate(bussiness = Bussiness.extsign, operation = Operate.INSERT)
	@RequestMapping(value = "/addToExtSign.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToExtSign(HttpServletRequest request, HttpServletResponse response, String appId,
			String sign, String extSrc) {

		Long sTime = System.currentTimeMillis();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		int add = 0;

		try {
			map.put("appId", appId);
			map.put("sign", sign);
			map.put("extSrc", extSrc);
			add = extsignService.addToExtSign(map);
			newList.add(map);
		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 添加到pre_ext_sign表出错.", e);
		}
		ResponseResult result = new ResponseResult();
		result.setResult(add);
		result.setNewData(newList);
		result.setOldData(null);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][EXT_SIGN] addData=" + newList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

		return result;
	}

	
	@DataOperate(bussiness = Bussiness.extsign, operation = Operate.DELETE)
	@RequestMapping("/delExtSign.action")
	@ResponseBody
	public ResponseResult delExtSign(Integer id) {
		Map<String, String> map=new HashMap<>();
		map.put("id", id.toString());
		int del = 0;
		ExtSign oldData = null;
		List<ExtSign> oldList = new ArrayList<ExtSign>();
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = extsignService.findExtSignById(id);
			oldList.add(oldData);

		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 查询旧数据出错.", e);
		}
		try {
			del = extsignService.delExtSign(map);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldList);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][EXT_SIGN] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][EXT_SIGN] 删除pre_ext_sign表数据出错.", e1);
		}

		return result;
	}

	
	@RequestMapping("/findExtSignById.action")
	@ResponseBody
	public ExtSign findExtSignById(Integer id) {

		ExtSign esign = null;
		try {
			esign = extsignService.findExtSignById(id);
		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 查询pre_ext_sign表数据出错.", e);
		}

		return esign;
	}

	
	@DataOperate(bussiness = Bussiness.extsign, operation = Operate.UPDATE)
	@RequestMapping("/updateExtSign.action")
	@ResponseBody
	public ResponseResult updateExtSign(HttpServletRequest request, HttpServletResponse response, ExtSign esign) {

		int res = 0;
		ResponseResult result = new ResponseResult();
		Integer id = esign.getId();
		ExtSign oldData = new ExtSign();
		List<Map<String, Object>> newData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int data = 0;
		try {
			map.put("appId", esign.getAppId());
			map.put("sign", esign.getSign());
			map.put("extSrc", esign.getExtSrc());
			data = extsignService.existData(map);

		} catch (Exception e) {
			logger.error("[ERROR][EXT_SIGN] 查询修改pre_ext_sign前期准备数据出错.", e);
		}
		if (data == 1) {
			res = 2;
			result.setResult(res);
		} else {
			Long sTime = System.currentTimeMillis();
			try {
				oldData = extsignService.findExtSignById(id);
			} catch (Exception e) {
				logger.error("[ERROR][EXT_SIGN] 查询旧数据出错.", e);
			}
			try {
				map.put("id", id);
				res = extsignService.updateExtSign(map);
				newData.add(map);
				result.setResult(res);
				result.setNewData(newData);
				result.setOldData(oldData);
				Long eTime = System.currentTimeMillis();
				logger.debug("[BINS][EXT_SIGN] newData=" + newData + ",time=" + (eTime - sTime) + "ms,result="
						+ result.getResult());
			} catch (Exception e1) {
				logger.error("[ERROR][EXT_SIGN] 修改pre_ext_sign表数据出错.", e1);
			}
		}

		return result;
	}

	
	@DataOperate(bussiness = Bussiness.extsign, operation = Operate.DELETEBATCH)
	@RequestMapping("/delExtSignBatch.action")
	@ResponseBody
	public ResponseResult delExtSignBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {

		int del = 0;
		ResponseResult result = null;
		ExtSign esign = new ExtSign();
		List<ExtSign> list = new ArrayList<ExtSign>();
		Long sTime = System.currentTimeMillis();
		Map<String, String> map=new HashMap<>();
		for (Integer id : ids) {
			map.put("id", id.toString());
			try {
				esign = extsignService.findExtSignById(id);
				list.add(esign);
			} catch (Exception e) {
				logger.error("[ERROR][EXT_SIGN] 查询旧数据出错.", e);
			}

			try {
				del = extsignService.delExtSign(map);
			} catch (Exception e) {
				logger.error("[ERROR][EXT_SIGN] 批量删除数据出错.", e);
			}

			if (del != 1) {
				result = new ResponseResult();
				result.setResult(del);
				result.setNewData(null);
				result.setOldData(esign);
				logger.debug("[BINS][EXT_SIGN] 删除数据失败,id=" + id);

				return result;
			}

		}
		result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(list);
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][EXT_SIGN] oldData=" + esign + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

		return result;
	}

	
	@RequestMapping(value = "/parseFile.action")
	@ResponseBody
	public Map<String, Object> parseFile(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			String update, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = uploadFile.getOriginalFilename();
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			uploadFile.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		InputStream is = new FileInputStream(targetFile);
		List<List<Object>> listob=null;
		try{
			listob = ExcelUtils.getBankListByExcel(is, targetFile.getPath());
		}catch(Exception e){
			logger.error("[ERROR][IMPORT-FILE]",e);
		}
		if (is != null) {
			is.close();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", listob);
		map.put("update", update);
		map.put("file", targetFile.getPath());
		return map;
	}

	
	@SuppressWarnings("unchecked")
	@DataOperate(bussiness=Bussiness.extsign,operation=Operate.IMPORT)
	@RequestMapping("/importData.action")
	@ResponseBody
	public ResponseResult importData(String sp, @RequestParam(value = "check[]") Integer[] check, String file, String update) {
		InputStream is = null;
		JSONObject jb = JSONObject.fromObject(sp);
		ResponseResult result=new ResponseResult();
		Map<String, String> param=new HashMap<>();
		param=jb;
		param.put("update", update);
		result.setNewData(param);
		Map<String, String> map = jb;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			logger.error("[BINS][ERROR] new InputStream error", e1);
		}
		List<List<Object>> listob = null;
		try {
			listob = ExcelUtils.getBankListByExcel(is, file);
		} catch (Exception e) {
			logger.error("[BINS][ERROR] 获取list异常", e);
		}
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			logger.error("[BINS][ERROR] InputStream close error", e);
		}
		int num=0;
		try {
			num = extsignService.addBatchExtSign(listob, map, check, update);
		} catch (Exception e) {
			logger.error("[ERROR][EXTSIGN]", e);
		}
		result.setResult(num);
		return result;
	}
	
	/**
	 * 签名扩展跳转
	 */
	
	@RequestMapping(value="/findTemplateList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<SignExtTemplate> findTemplateList(HttpServletRequest request, HttpServletResponse response, Integer pageSize,
			Integer pageIndex, String sign, String ext, String channelId){
		List<SignExtTemplate> lists = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		Integer total = 0;
		Long sTime = System.currentTimeMillis();
		try {
			map.put("channelId", channelId);
			map.put("sign", sign);
			map.put("ext", ext);
			
			total = extsignService.findTemplateTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询签名扩展跳转总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("channelId", channelId);
			map1.put("sign", sign);
			map1.put("ext", ext);
			lists = extsignService.findTemplateList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][SIGN_EXT] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",channelId=" + channelId
					+ ",sign=" + sign + "ext=" + ext + ",time=" + (eTime - sTime));
			return new PageResult<SignExtTemplate>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询签名扩展跳转分页出错.", e);
		}
		
		return null;
	}
	
	@RequestMapping("/findTemplateById.action")
	@ResponseBody
	public SignExtTemplate findTemplateById(Integer id) {

		SignExtTemplate temp = null;
		try {
			temp = extsignService.findTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询签名扩展跳转数据出错.", e);
		}

		return temp;
	}
	
	@DataOperate(bussiness = Bussiness.signext, operation = Operate.INSERT)
	@RequestMapping(value="/addToSignExtTemplate.action", method=RequestMethod.POST, produces="application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addToSignExtTemplate(HttpServletRequest request, HttpServletResponse response, String sign, String ext,
			String cmccChannelId, String unicomChannelId, String telecomChannelId) {

		Long sTime = System.currentTimeMillis();
		ResponseResult result = new ResponseResult();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		int res = 0;
		int data = 0;
		try {
			map.put("cmccChannelId", cmccChannelId);
			map.put("unicomChannelId", unicomChannelId);
			map.put("telecomChannelId", telecomChannelId);
			map.put("sign", sign);
			map.put("ext", ext);
			data = extsignService.isExistTemplate(map);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询是否存在签名扩展跳转数据出错.", e);
		}
		if (data > 0) {
			res = 2;
			result.setResult(res);
		}else{
			try {
				res = extsignService.addToSignExtTemplate(map);
				newList.add(map);
			} catch (Exception e) {
				logger.error("[ERROR][SIGN_EXT] 添加签名扩展跳转出错.", e);
			}
			result.setResult(res);
			result.setNewData(newList);
			result.setOldData(null);
		}
		
		Long eTime = System.currentTimeMillis();
		logger.debug(
				"[BINS][SIGN_EXT] addData=" + newList + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());

		return result;
	}

	@DataOperate(bussiness = Bussiness.signext, operation = Operate.UPDATE)
	@RequestMapping("/updateSignExtTemplate.action")
	@ResponseBody
	public ResponseResult updateSignExtTemplate(HttpServletRequest request, HttpServletResponse response, SignExtTemplate temp) {

		int res = 0;
		ResponseResult result = new ResponseResult();
		int id = temp.getId();
		SignExtTemplate oldData = new SignExtTemplate();
		List<Map<String, Object>> newData = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int data = 0;
		try {
			map.put("cmccChannelId", temp.getCmccChannelId());
			map.put("unicomChannelId", temp.getUnicomChannelId());
			map.put("telecomChannelId", temp.getTelecomChannelId());
			map.put("sign", temp.getSign());
			map.put("ext", temp.getExt());
			data = extsignService.isExistTemplate(map);

		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询是否存在签名扩展跳转数据出错.", e);
		}
		if (data > 0) {
			res = 2;
			result.setResult(res);
		} else {
			Long sTime = System.currentTimeMillis();
			try {
				oldData = extsignService.findTemplateById(id);
			} catch (Exception e) {
				logger.error("[ERROR][SIGN_EXT] 查询旧数据出错.", e);
			}
			try {
				map.put("id", id);
				res = extsignService.updateSignExtTemplate(map);
				newData.add(map);
				result.setResult(res);
				result.setNewData(newData);
				result.setOldData(oldData);
				Long eTime = System.currentTimeMillis();
				logger.debug("[BINS][SIGN_EXT] newData=" + newData + ",time=" + (eTime - sTime) + "ms,result="
						+ result.getResult());
			} catch (Exception e1) {
				logger.error("[ERROR][SIGN_EXT] 修改签名扩展跳转数据出错.", e1);
			}
		}

		return result;
	}
	
	@DataOperate(bussiness = Bussiness.signext, operation = Operate.DELETE)
	@RequestMapping("/delSignExtTemplate.action")
	@ResponseBody
	public ResponseResult delSignExtTemplate(Integer id) {
		int del = 0;
		SignExtTemplate oldData = null;
		List<SignExtTemplate> oldList = new ArrayList<SignExtTemplate>();
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = extsignService.findTemplateById(id);
			oldList.add(oldData);

		} catch (Exception e) {
			logger.error("[ERROR][SIGN_EXT] 查询签名扩展跳转旧数据出错.", e);
		}
		try {
			del = extsignService.delSignExtTemplate(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldList);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][SIGN_EXT] oldData=" + oldList + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][SIGN_EXT] 删除签名扩展跳转数据出错.", e1);
		}

		return result;
	}
	
	@DataOperate(bussiness = Bussiness.signext, operation = Operate.DELETEBATCH)
	@RequestMapping("/delTemplateBatch.action")
	@ResponseBody
	public ResponseResult delTemplateBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {

		int del = 0;
		ResponseResult result = null;
		Long sTime = System.currentTimeMillis();

			try {
				del = extsignService.delTemplateBatch(ids);
			} catch (Exception e) {
				logger.error("[ERROR][SIGN_EXT] 批量删除签名扩展跳转数据出错.", e);
			}

			if (del > 0) {
				result = new ResponseResult();
				result.setResult(del);
				result.setNewData(null);
				result.setOldData(ids);
				Long eTime = System.currentTimeMillis();
				logger.debug(
						"[BINS][EXT_SIGN] oldData=" + ids + ",time=" + (eTime - sTime) + "ms,result=" + result.getResult());
				return result;
			}

			result = new ResponseResult();
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(ids);
			logger.debug("[BINS][SIGN_EXT] 批量删除签名扩展跳转数据失败,id=" + ids);

		return result;
	}
	
}
