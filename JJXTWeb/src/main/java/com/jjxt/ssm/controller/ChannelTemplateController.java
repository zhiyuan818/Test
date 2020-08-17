package com.jjxt.ssm.controller;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.protobuf.TextFormat.ParseException;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.ChannelTemplate;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ChannelTemplateService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.ExcelBean;
import com.jjxt.ssm.utils.ExcelUtils;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/template")
public class ChannelTemplateController {

	private static Logger logger = Logger.getLogger(ChannelTemplateController.class);

	private static final String PATH = "template/";
	@Autowired
	private ChannelTemplateService channelTemplateService;
	@Autowired
	private ChannelService channelService;

	/**
	 * 页面跳转
	 */
	@RequestMapping("/goChannelTemplateList.action")
	public String goChannelTemplateList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
//		List<ChannelTemplate> channelAppIds = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannel(map);
//			channelAppIds = channelTemplateService.findChannelAppIds();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] ", e);
		}

		request.setAttribute("channels", channels);
//		request.setAttribute("channelAppIds", channelAppIds);
		return PATH + "channelTemplateList";
	}

	@RequestMapping(value = "/findAllList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<ChannelTemplate> findAllList(HttpServletRequest request, HttpServletResponse response,
			Integer pageSize, Integer pageIndex, String channelId, String channelAppId, String channelTemplateId,
			String channelTemplate, String extras) {

		List<ChannelTemplate> lists = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		Integer total = 0;
		Long sTime = System.currentTimeMillis();
		try {
			map.put("channelId", channelId);
			map.put("channelAppId", channelAppId);
			map.put("channelTemplateId", channelTemplateId);
			map.put("channelTemplate", channelTemplate);
			map.put("extras", extras);
			total = channelTemplateService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] 查询通道模板总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("channelId", channelId);
			map1.put("channelAppId", channelAppId);
			map1.put("channelTemplateId", channelTemplateId);
			map1.put("channelTemplate", channelTemplate);
			map1.put("extras", extras);
			lists = channelTemplateService.findAllList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][CHANNEL_TEMPLATE] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",channelId="
					+ channelId + ",channelAppId=" + channelAppId + ",channelTemplateId=" + channelTemplateId
					+ ",channelTemplate=" + channelTemplate + ",extras=" + extras + ",time=" + (eTime - sTime));
			return new PageResult<ChannelTemplate>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] 查询通道模板分页出错.", e);
		}

		return null;
	}

	/**
	 * 新增通道模板
	 * 
	 * @param channelTemplate
	 * @return
	 */
	@DataOperate(bussiness = Bussiness.channelTemplate, operation = Operate.INSERT)
	@RequestMapping("/addChannelTemplate.action")
	@ResponseBody
	public ResponseResult addChannelTemplate(ChannelTemplate channelTemplate) {
		logger.debug("[BINS][CHANNEL_TEMPLATE] channelTemplate=" + channelTemplate);
		int result = 0;
		try {
			result = channelTemplateService.addChannelTemplate(channelTemplate);
		} catch (Exception e) {
			logger.error("[ERRPR][CHANNEL_TEMPLATE]  ", e);
		}
		List<ChannelTemplate> newData = null;
		try {
			newData = channelTemplateService.findAddEnd();
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] ", e);
		}
		return new ResponseResult(result, newData, null);
	}

	/**
	 * 根据ID查询
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping("/findChannelTemplateById.action")
	@ResponseBody
	public ChannelTemplate findChannelTemplateById(Integer id) {
		logger.debug("[BINS][CHANNEL_TEMPLATE] id=" + id);
		List<ChannelTemplate> channelTemplate = null;
		try {
			channelTemplate = channelTemplateService.findChannelTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] id查询异常", e);
		}
		if (!(null == channelTemplate || channelTemplate.isEmpty())) {
			return channelTemplate.get(0);
		}
		return null;
	}

	/**
	 * 更新通道模板
	 * 
	 * @param channelTemplate
	 * @return
	 */

	@DataOperate(bussiness = Bussiness.channelTemplate, operation = Operate.UPDATE)
	@RequestMapping("/updateChannelTemplate.action")
	@ResponseBody
	public ResponseResult updateChannelTemplate(ChannelTemplate channelTemplate) {
		logger.error("[BINS][CHANNEL_TEMPLATE] channelTemplate=" + channelTemplate);
		if (channelTemplate == null) {
			return new ResponseResult(0, null, null);
		}
		List<ChannelTemplate> oldData = null;
		try {
			oldData = channelTemplateService.findChannelTemplateById(channelTemplate.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] ID查询异常", e);
		}
		int i = 0;
		try {
			i = channelTemplateService.updateChannelTemplate(channelTemplate);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] UPDATE EXCEPTION", e);
		}
		List<ChannelTemplate> newData = null;
		try {
			newData = channelTemplateService.findChannelTemplateById(channelTemplate.getId());
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] ID查询异常", e);
		}
		return new ResponseResult(i, newData, oldData);
	}

	/**
	 * 删除通道模板
	 * 
	 * @param id
	 * @return
	 */
	@DataOperate(bussiness = Bussiness.channelTemplate, operation = Operate.DELETE)
	@RequestMapping("/deleteChannelTemplate.action")
	@ResponseBody
	public ResponseResult deleteChannelTemplate(Integer id) {
		logger.debug("[BINS][CHANNEL_TEMPLATE] id=" + id);
		List<ChannelTemplate> channelTemplate = null;
		try {
			channelTemplate = channelTemplateService.findChannelTemplateById(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE]", e);
		}
		int i = 0;
		try {
			i = channelTemplateService.deleteChannelTemplate(id);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] delete error", e);
		}
		return new ResponseResult(i, null, channelTemplate);
	}

	@DataOperate(bussiness = Bussiness.channelTemplate, operation = Operate.DELETEBATCH)
	@RequestMapping("/delChannelTemplateBatch.action")
	@ResponseBody
	public ResponseResult delChannelTemplateBatch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids) {

		logger.debug("[BINS][CHANNEL_TEMPLATE] ids[]=" + ids);
		Long startTime = System.currentTimeMillis();
		List<ChannelTemplate> oldData = null;
		try {
			oldData = channelTemplateService.findChannelTemplateByIds(ids);
		} catch (Exception e1) {
			logger.error("[ERROR][BINS][CHANNEL_TEMPLATE] put old menu is error", e1);
		}
		int i = 0;
		try {
			i = channelTemplateService.deleteChannelTemplateBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][BINS][CHANNEL_TEMPLATE] delete batch menu is error", e);
		}
		ResponseResult result = new ResponseResult(i, null, oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][CHANNEL_TEMPLATE] ids[]=" + ids + ", time=" + (endTime - startTime) + "ms, result="
				+ result.getResult());
		return result;
	}

	/**
	 * 解析文件
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/parseFile.action")
	@ResponseBody
	public Map<String, Object> parseFile(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			String channelId, String isExtras, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<List<Object>> list = new ArrayList<>();

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
		List<List<Object>> listob = ExcelUtils.getBankListByExcelTitle(is, targetFile.getPath());
		if (is != null) {
			is.close();
		}
		
		Map<Integer, String> extrasMap= new HashMap<>();
		for (int i = 0; i < listob.size(); i++) {
			List<Object> li = new ArrayList<Object>();
			StringBuffer sb = new StringBuffer();
			List<Object> objectList = listob.get(i);
			for (int j = 0; j < objectList.size(); j++) {
				if (i == 0) {
					if("yes".equals(isExtras)) {
						if (String.valueOf(objectList.get(j)).contains("extras")) {
							extrasMap.put(j, String.valueOf(objectList.get(j)).split("=")[1]);
						}
					}
				} else {
					if(extrasMap.containsKey(j)) {
						sb.append(extrasMap.get(j)).append("=").append(String.valueOf(objectList.get(j))).append("&");
					}else {
						li.add(objectList.get(j));
					}
				}
			}
			if(sb.length() > 0) {
				li.add(sb.substring(0, sb.length()-1));
			}
			if(li.size()>0) {
				list.add(li);
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("channelId", channelId);
		map.put("isExtras", isExtras);
//		map.put("channelAppId", channelAppId);
		map.put("file", targetFile.getPath());
		return map;
	}

	@SuppressWarnings("unchecked")
	@DataOperate(bussiness = Bussiness.channelTemplate, operation = Operate.IMPORT)
	@RequestMapping("/importData.action")
	@ResponseBody
	public ResponseResult importData(String sp, @RequestParam(value = "check[]") Integer[] check, String file,
			String channelId, String isExtras) {
		InputStream is = null;
		JSONObject jb = JSONObject.fromObject(sp);
		ResponseResult result = new ResponseResult();
		Map<String, String> map = jb;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			logger.error("[BINS][CHANNEL_TEMPLATE][ERROR] new InputStream error", e1);
		}
		List<List<Object>> listob = null;
		try {
			listob = ExcelUtils.getBankListByExcelTitle(is, file);
		} catch (Exception e) {
			logger.error("[BINS][CHANNEL_TEMPLATE][ERROR] 获取list异常", e);
		}
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
			logger.error("[BINS][CHANNEL_TEMPLATE][ERROR] InputStream close error", e);
		}
		
		List<List<Object>> list = new ArrayList<>();
		Map<Integer, String> extrasMap= new HashMap<>();
		for (int i = 0; i < listob.size(); i++) {
			List<Object> li = new ArrayList<Object>();
			StringBuffer sb = new StringBuffer();
			List<Object> objectList = listob.get(i);
			for (int j = 0; j < objectList.size(); j++) {
				if (i == 0) {
					if("yes".equals(isExtras)) {
						if (String.valueOf(objectList.get(j)).contains("extras")) {
							extrasMap.put(j, String.valueOf(objectList.get(j)).split("=")[1]);
						}
					}
				} else {
					if(extrasMap.containsKey(j)) {
						sb.append(extrasMap.get(j)).append("=").append(String.valueOf(objectList.get(j))).append("&");
					}else {
						li.add(objectList.get(j));
					}
				}
			}
			if(sb.length() > 0) {
				li.add(sb.substring(0, sb.length()-1));
			}
			if(li.size()>0) {
				list.add(li);
			}
		}
		
		int num = 0;
		try {
			num = channelTemplateService.addChannelTemplateBatch(list, map, check, channelId);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE]", e);
		}
		result.setResult(num);
		return result;
	}
	
	@RequestMapping(value="/exportData.action",method = RequestMethod.GET)
	@ResponseBody
	public void exportData(String param, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][CHANNEL_TEMPLATE] param="+param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");
		String dateStr = sdf.format(new Date());
		JSONArray ary = JSONArray.fromObject(param);
		@SuppressWarnings("unchecked")
		List<String> patams=ary;
		XSSFWorkbook workbook = null;
		try {
			List<ChannelTemplate> creditInfoList = channelTemplateService.findChannelTemplateAll();
			List<ExcelBean> ems = new ArrayList<>();
			Map<Integer, List<ExcelBean>> linkMap = new LinkedHashMap<>();
			for (String s : patams) {
				if ("channelId".equals(s)) {
					ems.add(new ExcelBean("通道ID", "channelId", 0));
				}
				if ("channelName".equals(s)) {
					ems.add(new ExcelBean("通道名称", "channelName", 0));
				}
				if ("channelAppId".equals(s)) {
					ems.add(new ExcelBean("通道应用ID", "channelAppId", 0));
				}
				if ("channelTemplateId".equals(s)) {
					ems.add(new ExcelBean("通道模板ID", "channelTemplateId", 0));
				}
				if ("channelTemplate".equals(s)) {
					ems.add(new ExcelBean("通道模板内容", "channelTemplate", 0));
				}
				if ("template".equals(s)) {
					ems.add(new ExcelBean("模板内容", "template", 0));
				}
				if ("extras".equals(s)) {
					ems.add(new ExcelBean("参数", "extras", 0));
				}

			}
			linkMap.put(0, ems);
			workbook = ExcelUtils.createExcelFile(ChannelTemplate.class, creditInfoList, linkMap, "通道模板信息");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| IntrospectionException | ParseException e1) {
			e1.printStackTrace();
			logger.error("[ERROR][CHANNEL_TEMPLATE] 导出，查询出错",e1);
		} catch (Exception e) {
			logger.error("[ERROR][CHANNEL_TEMPLATE] 导出，查询出错",e);
		}
		 OutputStream output;  
	        try {  
	        	response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(("通道模板_" +dateStr+".xlsx"),"UTF-8"));  
		        response.setContentType("application/vnd.ms-excel;charset=UTF-8");  
		        response.setHeader("Pragma", "no-cache");  
		        response.setHeader("Cache-Control", "no-cache");  
		        response.setDateHeader("Expires", 0);  
	            output = response.getOutputStream();  
	          
	            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);  
	            bufferedOutPut.flush();  
	            workbook.write(bufferedOutPut);  
	            bufferedOutPut.close();  
	              
	        } catch (IOException e) {  
	        	logger.error("[ERROR][CHANNEL_TEMPLATE] 导出，查询出错",e);
	            e.printStackTrace();  
	        }  
	}

}
