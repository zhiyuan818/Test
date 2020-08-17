package com.jjxt.ssm.controller;

import java.beans.IntrospectionException;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
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
import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.SignMatchExt;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.SignMatchExtService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.ExcelBean;
import com.jjxt.ssm.utils.ExcelUtils;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/signMatchExt")
public class SignMatchExtController {
	
	private static Logger logger = Logger.getLogger(SignBaobeiController.class);

	private static final String PATH = "signMatchExt/";
	
	@Autowired
	private SignMatchExtService signMatchExtService;
	@Autowired
	private ApplicationService applicationService;
	
	@RequestMapping("/goSignMatchExtList.action")
	public String goSignMatchExtList(HttpServletRequest request) {
		List<Map<String, Object>> findAccount = new ArrayList<>();
		try {
			findAccount = applicationService.findAccount();
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_M_EXT] ", e);
		}
		request.setAttribute("apps", findAccount);
		return PATH+"signMatchExtList";
	}

	
	@RequestMapping(value = "/findAllList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<SignMatchExt> findAllList(HttpServletRequest request, HttpServletResponse response,
			Integer pageSize, Integer pageIndex, String sign, Integer ext, Integer extLength, String appId,
			String startTime, String endTime) {

		List<SignMatchExt> lists = new ArrayList<SignMatchExt>();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer total = 0;

		Long sTime = System.currentTimeMillis();
		try {
			map.put("sign", sign);
			map.put("ext", ext);
			map.put("extLength", extLength);
			map.put("appId", appId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);

			total = signMatchExtService.findTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][BAOBEI] 查询签名报备总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("sign", sign);
			map1.put("ext", ext);
			map1.put("extLength", extLength);
			map1.put("appId", appId);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			lists = signMatchExtService.findPageList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][SIGN_M_EXT] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",sign=" + sign
					+ ",ext=" + ext + ",extLength=" + extLength + ",appId=" + appId + ",startTime=" + startTime + " ,endTime=" + endTime + ",time=" + (eTime - sTime));
			return new PageResult<SignMatchExt>(total, lists);
		} catch (Exception e) {
			logger.error("[ERROR][SIGN_M_EXT] 查询签名报备分页出错.", e);
		}

		return new PageResult<SignMatchExt>(0, new ArrayList<>());
	}
	
	@DataOperate(bussiness = Bussiness.signMatchExt, operation = Operate.INSERT)
	@RequestMapping("/addSignMatchExt.action")
	@ResponseBody
	public ResponseResult addSignMatchExt(String sign,int extLength,Integer appId) {
		 	logger.debug("[BINS][SING_M_EXT] sign ="+sign+",extLength="+extLength+",appId="+appId);
		 	SignMatchExt signMExt = new SignMatchExt();
		 	signMExt.setSign(sign);
		 	signMExt.setExtLength(extLength);
		 	signMExt.setAppId(appId);
		 	signMExt.setIsSync("1");
		 	signMExt.setAddTime(new Date());
		 	Integer maxExt =0;
		 	try {
				maxExt = signMatchExtService.findMaxExtByAppIdAndExtLength(signMExt);
			} catch (Exception e) {
				logger.debug("[ERR][SING_M_EXT] EX="+e);
				return new ResponseResult(-1, null, signMExt);
			}
		 	if(!StringUtil.isEmpty(maxExt)) {
		 		maxExt++;
		 	}else {
		 		switch (extLength) {
					case 2:
						maxExt = 10;
						break;
					case 3:
						maxExt = 200;
						break;
					case 4:
						maxExt = 3000;
						break;
					case 5:
						maxExt = 40000;
						break;
					case 6:
						maxExt = 500000;
						break;
				}
		 	}
		 	
		 	if(extLength != String.valueOf(maxExt).length()) {
		 		logger.debug("[ERR][SIGN_M_EXT] extLength = "+extLength +", maxExt="+maxExt);
		 		return new ResponseResult(-2, null, signMExt);
		 	}
		 	boolean flag = false;
		 	switch (extLength) {
				case 2:
					if(maxExt == 20) {
						flag = true;
					}
					break;
				case 3:
					if(maxExt == 300) {
						flag = true;
					}
					break;
				case 4:
					if(maxExt == 4000) {
						flag = true;
					}
					break;
				case 5:
					if(maxExt == 50000) {
						flag = true;
					}
					break;
				case 6:
					if(maxExt == 600000) {
						flag = true;
					}
					break;
			}
		 	
		 	if(flag) {
		 		return new ResponseResult(-3, signMExt, null);
		 	}
		 	signMExt.setExt(String.valueOf(maxExt));
		 	Integer result = 0;
		 	try {
		 		result=signMatchExtService.addSignMatchExt(signMExt);
			} catch (Exception e) {
				logger.error("[ERR][SIGN_M_EXT] EX="+e);
				return new ResponseResult(-4, signMExt, null);
			}
		 	return new ResponseResult(result, signMExt, null);
	    }
	
	@DataOperate(bussiness = Bussiness.signMatchExt, operation = Operate.DELETE)
	@RequestMapping("/delSign.action")
	@ResponseBody
	public ResponseResult delSign(String id) {
		int del = 0;
		SignMatchExt oldData = null;
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			oldData = signMatchExtService.findListById(id);

		} catch (Exception e) {
			logger.error("[ERROR][SIGN_M_EXT] 查询旧数据出错.", e);
		}
		try {
			del = signMatchExtService.deleteSignMatchExt(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData(oldData);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][SIGN_M_EXT] oldData=" + oldData + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e1) {
			logger.error("[ERROR][SIGN_M_EXT] delete sign_m_ext err.", e1);
		}

		return result;
	}
	
	@DataOperate(bussiness = Bussiness.signMatchExt, operation = Operate.IMPORT)
	@RequestMapping(value = "/parseFile.action")
	@ResponseBody
	public ResponseResult parseFile(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			int extLength, int appId, HttpServletRequest request,
			HttpServletResponse response) {

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
		FileInputStream inputStream=null;
		BufferedReader bufferedReader =null;
		int i=0;
		List<SignMatchExt> list = new ArrayList<SignMatchExt>();
		try {
			inputStream = new FileInputStream(targetFile);
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;
			while((str = bufferedReader.readLine()) != null){
				SignMatchExt signMExt = new SignMatchExt();
			 	signMExt.setSign(str);
			 	signMExt.setExtLength(extLength);
			 	signMExt.setAppId(appId);
			 	signMExt.setIsSync("1");
			 	signMExt.setAddTime(new Date());
			 	Integer maxExt =0;
				maxExt = signMatchExtService.findMaxExtByAppIdAndExtLength(signMExt);
			 	if(!StringUtil.isEmpty(maxExt)) {
			 		maxExt++;
			 	}else {
			 		switch (extLength) {
						case 2:
							maxExt = 10;
							break;
						case 3:
							maxExt = 200;
							break;
						case 4:
							maxExt = 3000;
							break;
						case 5:
							maxExt = 40000;
							break;
						case 6:
							maxExt = 500000;
							break;
					}
			 	}
			 	
			 	if(extLength != String.valueOf(maxExt).length()) {
			 		logger.debug("[ERR][SIGN_M_EXT] extLength = "+extLength +", maxExt="+maxExt);
			 		return new ResponseResult(i, list, null);
			 	}
			 	boolean flag = false;
			 	switch (extLength) {
					case 2:
						if(maxExt == 20) {
							flag = true;
						}
						break;
					case 3:
						if(maxExt == 300) {
							flag = true;
						}
						break;
					case 4:
						if(maxExt == 4000) {
							flag = true;
						}
						break;
					case 5:
						if(maxExt == 50000) {
							flag = true;
						}
						break;
					case 6:
						if(maxExt == 600000) {
							flag = true;
						}
						break;
				}
			 	
			 	if(flag) {
			 		return new ResponseResult(i, list, null);
			 	}
			 	signMExt.setExt(String.valueOf(maxExt));
			 	Integer result = 0;
		 		result=signMatchExtService.addSignMatchExt(signMExt);
		 		list.add(signMExt);
		 		i += result;
			}
		} catch (FileNotFoundException e) {
			logger.error("[ERR][SIGN_M_EXT] EX="+e);
		} catch (IOException e) {
			logger.error("[ERR][SIGN_M_EXT] EX="+e);
		} catch (Exception e) {
			logger.error("[ERR][SIGN_M_EXT] EX="+e);
		}finally {
			try {
				if(inputStream!=null) {
					inputStream.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				if(bufferedReader!=null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new ResponseResult(i, list, null);
	}
	
	@RequestMapping(value="/exportData.action",method = RequestMethod.GET)
	@ResponseBody
	public void exportData(int appId,String extLength, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("[BINS][SING_M_EXT] appId="+appId +",extLength="+extLength);
		Application acc = null;
		try {
			acc = applicationService.findApplicationById(appId);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("appId", appId);
		map.put("extLength", extLength);
		XSSFWorkbook workbook = null;
		try {
			List<SignMatchExt> findAllList = signMatchExtService.findAllList(map);
			List<ExcelBean> ems = new ArrayList<>();
			ems.add(new ExcelBean("签名", "sign", 0));
			ems.add(new ExcelBean("扩展", "ext", 0));
			Map<Integer, List<ExcelBean>> linkMap = new LinkedHashMap<>();
			linkMap.put(0, ems);
			workbook = ExcelUtils.createExcelFile(SignMatchExt.class, findAllList, linkMap, "签名分配扩展");
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException
				| IntrospectionException | ParseException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			logger.error("[ERROR][SING_M_EXT] 导出，查询出错",e);
		}
		 OutputStream output;  
	        try {  
	        	response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(("签名分配扩展_"+acc==null?"null":acc.getAppName()+".xlsx"),"UTF-8"));  
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
