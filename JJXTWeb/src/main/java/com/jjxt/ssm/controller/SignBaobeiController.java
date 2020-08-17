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

import com.jjxt.ssm.utils.*;
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
import com.jjxt.ssm.entity.SignBaobei;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.SignBaobeiService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/signBaobei")
public class SignBaobeiController {

	private static Logger logger = Logger.getLogger(SignBaobeiController.class);

	private static final String PATH = "signBaobei/";

	@Autowired
	private SignBaobeiService signbaobeiService;
	@Autowired
	private ChannelService channelService;
	
	@RequestMapping("/goSignBaobeiPage.action")
	public String goSignBaobeiPage(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][BAOBEI] ", e);
		}
		request.setAttribute("channels", channels);
		return PATH + "signBaobeiList";
	}

	
	@RequestMapping(value = "/findAllList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<SignBaobei> findAllList(HttpServletRequest request, HttpServletResponse response,
			Integer pageSize, Integer pageIndex, String sign, Integer maxExt, Integer minExt, String baobeiFlag,
			String channelId, String startTime, String endTime) {

		List<SignBaobei> lists = new ArrayList<SignBaobei>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer total = 0;

		Long sTime = System.currentTimeMillis();
		try {
			map.put("sign", sign);
			map.put("maxExt", maxExt);
			map.put("minExt", minExt);
			map.put("baobeiFlag", baobeiFlag);
			map.put("channelId", channelId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);

			total = signbaobeiService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][BAOBEI] 查询签名报备总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("sign", sign);
			map1.put("maxExt", maxExt);
			map1.put("minExt", minExt);
			map1.put("baobeiFlag", baobeiFlag);
			map1.put("channelId", channelId);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			lists = signbaobeiService.findAllList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][BAOBEI] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",sign=" + sign
					+ ",maxExt=" + maxExt + ",minExt=" + minExt + ",baobeiFlag=" + baobeiFlag + ",channelId="
					+ channelId + "startTime" + startTime + "endTime" + endTime + ",time=" + (eTime - sTime));
			return new PageResult<SignBaobei>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][BAOBEI] 查询签名报备分页出错.", e);
		}

		return null;
	}

	
	@DataOperate(bussiness = Bussiness.signbaobei, operation = Operate.DELETE)
	@RequestMapping("/delSignBaobei.action")
	@ResponseBody
	public ResponseResult delSignBaobei(Integer id) {

		int del = 0;
		SignBaobei oldData = null;
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = signbaobeiService.findListById(id);

		} catch (Exception e) {
			logger.error("[ERROR][BAOBEI] 查询旧数据出错.", e);
		}
		try {
			del = signbaobeiService.delSignBaobei(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][BAOBEI] oldData=" + oldData + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][BAOBEI] 删除pre_sign_baobei表数据出错.", e1);
		}

		return result;
	}

	
	@DataOperate(bussiness = Bussiness.signbaobei, operation = Operate.UPDATE)
	@RequestMapping("/changeBaobeiFlag.action")
	@ResponseBody
	public ResponseResult changeBaobeiFlag(Integer id, String flag) {
		int update = 0;
		ResponseResult result = new ResponseResult();

		try {
			if (flag.equals("yes")) {
				update = signbaobeiService.updateFlagToNo(id);

			} else {
				update = signbaobeiService.updateFlagToYes(id);

			}
			result.setResult(update);
			result.setOldData(flag);

		} catch (Exception e2) {
			logger.error("[ERROR][BAOBEI] 修改报备标记出错.", e2);
		}

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
			String update, String ext, String baobeiFlag, String channelId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
		List<List<Object>> listob = ExcelUtils.getBankListByExcel(is, targetFile.getPath());
		if (is != null) {
			is.close();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", listob);
		map.put("channelId", channelId);
		map.put("ext", ext);
		map.put("baobeiFlag", baobeiFlag);
		map.put("update", update);
		map.put("file", targetFile.getPath());
		return map;
	}

	@SuppressWarnings("unchecked")
	@DataOperate(bussiness=Bussiness.signbaobei,operation=Operate.IMPORT)
	@RequestMapping("/importData.action")
	@ResponseBody
	public ResponseResult importData(String sp, @RequestParam(value = "check[]") Integer[] check, String file,
			String channelId, String update, String ext, String baobeiFlag) {
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
			num = signbaobeiService.addBatchSignBaoBei(listob, map, check, update, ext, baobeiFlag, channelId);
		} catch (Exception e) {
			logger.error("[ERROR][SIGNBAOBEI]", e);
		}
		result.setResult(num);
		return result;
	}

	@RequestMapping(value="/exportData.action",method = RequestMethod.GET)
	@ResponseBody
	public void exportData(String param, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][SING_BAOBEI] param="+param);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssms");
		String dateStr = sdf.format(new Date());
		JSONArray ary = JSONArray.fromObject(param);
		@SuppressWarnings("unchecked")
		List<String> patams=ary;
		XSSFWorkbook workbook = null;
		try {
			List<SignBaobei> creditInfoList = signbaobeiService.findSignBaobeiAll();
			List<ExcelBean> ems = new ArrayList<>();
			Map<Integer, List<ExcelBean>> linkMap = new LinkedHashMap<>();
			for (String s : patams) {
				if ("sign".equals(s)) {
					ems.add(new ExcelBean("签名", "sign", 0));
				}
				if ("channelName".equals(s)) {
					ems.add(new ExcelBean("通道名称", "channelName", 0));
				}
				if ("companyName".equals(s)) {
					ems.add(new ExcelBean("公司名称", "companyName", 0));
				}
				if ("extSrc".equals(s)) {
					ems.add(new ExcelBean("扩展号", "extSrc", 0));
				}
				if ("baobeiFlag".equals(s)) {
					ems.add(new ExcelBean("报备状态", "baobeiFlag", 0));
				}
				if ("activeTime".equals(s)) {
					ems.add(new ExcelBean("时间", "activeTime", 0));
				}

			}
			linkMap.put(0, ems);
			workbook = ExcelUtils.createExcelFile(SignBaobei.class, creditInfoList, linkMap, "签名报备信息");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| IntrospectionException | ParseException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			logger.error("[ERROR][SING_BAOBEI] 导出，查询出错",e);
		}
		 OutputStream output;  
	        try {  
	        	response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(("签名报备_" +dateStr+".xlsx"),"UTF-8"));  
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
	            e.printStackTrace();  
	        }  
	}
}
