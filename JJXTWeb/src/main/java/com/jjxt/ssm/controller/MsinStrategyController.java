package com.jjxt.ssm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jjxt.ssm.entity.MsinStrategy;
import com.jjxt.ssm.service.MsinStrategyService;

@Controller
@RequestMapping("/msin")
public class MsinStrategyController {

	private Logger logger = Logger.getLogger(MsinStrategyController.class);
	private static final String PATH = "msin/";
	@Autowired
	private MsinStrategyService msinService;
	
	private static Pattern MOBILE_NUM_PATTERN = Pattern.compile("^1[0-9]{10}$");
	
	/**
	 * 跳转页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/goMsinStrategy.action")
	public String goMsinStrategy(HttpServletRequest request){
		List<MsinStrategy> apps = new ArrayList<>();
		List<MsinStrategy> levels = new ArrayList<>();
		try {
			apps = msinService.findAllapp();
			levels = msinService.findAlllevel();
			request.setAttribute("apps", apps);
			request.setAttribute("levels", levels);
		} catch (Exception e) {
			logger.error("[MSIN] select apps err.E={}", e);
		}
		return PATH + "msinStrategy";
	}
	
	/**
	 * 查询展示
	 * @param pageIndex
	 * @param pageSize
	 * @param appId
	 * @param level
	 * @param phoneNumber
	 * @return
	 */
	
	@RequestMapping(value = "/findAllData.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<MsinStrategy> findAllData(Integer pageIndex, Integer pageSize, Integer appId, Integer level, String phoneNumber){
		int total = 0;
		List<MsinStrategy> lists = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		try {
			paramMap.put("appId", appId);
			paramMap.put("level", level);
			paramMap.put("phoneNumber", phoneNumber);
			total = msinService.findTotal(paramMap);
		} catch (Exception e) {
			logger.error("[MSIN] find msin_strategy total err.E={}", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map = PageUtil.getDefaultPageMap(page);
		map.put("appId", appId);
		map.put("level", level);
		map.put("phoneNumber", phoneNumber);
		try {
			lists = msinService.findMsinList(map);
		} catch (Exception e) {
			logger.error("[MSIN] select msinList error.E={}", e);
		}
		
		return new PageResult<>(total, lists);
	}

	
	@DataOperate(bussiness = Bussiness.msinStrategy, operation = Operate.DELETE)
	@RequestMapping("/delMsinStrategyById.action")
	@ResponseBody
	public ResponseResult delMsinStrategyById(Integer id){
		int del = 0;
		ResponseResult result = new ResponseResult();
		Long sTime = System.currentTimeMillis();
		try {
			del = msinService.delMsinStrategyById(id);
			result.setResult(del);
			result.setNewData(null);
			result.setOldData("id="+id);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][MSIN] oldId=" + id + ",time=" + (eTime - sTime) + "ms,result="
					+ result.getResult());
		} catch (Exception e) {
			logger.error("[MSIN] del oldData err.E={}", e);
		}
		return result;
	}

	
	@DataOperate(bussiness = Bussiness.msinStrategy, operation = Operate.INSERT)
	@RequestMapping("addMsinStrategy.action")
	@ResponseBody
	public ResponseResult addMsinStrategy(MsinStrategy msin){
		Long startTime = System.currentTimeMillis();
		int res = 0;
		int isExist = 0;
		try {
			isExist = msinService.isExistConfig(msin);
		} catch (Exception e) {
			logger.error("[MSIN] find isExistConfig err.E={}", e);
		}
		try {
			if(isExist > 0){
				msinService.addMsinStrategy(msin);
				res = 1;
			}
		} catch (Exception e) {
			logger.error("[MSIN] addMsinStrategy err.E={}", e);
			
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][MSIN] newData=" + msin + ", time=" + (endTime - startTime) + "ms, result=" + res);
		return new ResponseResult(res, msin, null);
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.msinStrategy, operation = Operate.DELETEBATCH)
	@RequestMapping("/delMsinBatch.action")
	@ResponseBody
	public ResponseResult delMsinBatch(@RequestParam(value = "ids[]", required = false, defaultValue = "")Integer[] ids){
		Long sTime = System.currentTimeMillis();
		List<MsinStrategy> oldData = null;
		int del = 0;
		try {
			oldData = msinService.findMsinByIds(ids);
		} catch (Exception e) {
			logger.error("[MSIN] find msin by ids err.E={}", e);
		}
		try {
			del = msinService.delMsinBatch(ids);
		} catch (Exception e) {
			logger.error("[MSIN] delete batch msinStrategy err.E={}", e);
		}
		Long eTime = System.currentTimeMillis();
		logger.debug("[BINS][MSIN] ids[]="+ ids +",time="+ (eTime-sTime) +"ms, result=" + del);
		return new ResponseResult(del, null, oldData);
	}

	@RequestMapping(value = "/importFile.action")
	@ResponseBody
	public Map<String, Object> importFile(@RequestParam(value = "uploadFile", required = false) MultipartFile uploadFile,
			HttpServletRequest request, String appId, String level){
		String upFileName = uploadFile.getOriginalFilename();
		Map<String, Object> map = new HashMap<>();
		File desFile = null;
		String path = request.getSession().getServletContext().getRealPath("upload");
		int isExist = 0;
		MsinStrategy bean = new MsinStrategy();
		bean.setAppId(Integer.valueOf(appId));
		bean.setLevel(Integer.valueOf(level));
		try {
			isExist = msinService.isExistConfig(bean);
		} catch (Exception e1) {
			logger.error("[MSIN] select isExistConfig err.E={}", e1);
		}
		if(isExist == 0){
			map.put("result", "-9998");
			map.put("fileName", upFileName);
			return map;
		}
		
		if(!uploadFile.isEmpty() && upFileName.endsWith(".txt")){
			try {
				desFile = new File(path, upFileName);
				if (!desFile.exists()) {
					desFile.mkdirs();
					uploadFile.transferTo(desFile);
				}
				InputStreamReader read = new InputStreamReader(new FileInputStream(desFile));
				BufferedReader bufferedReader = new BufferedReader(read);
				List<MsinStrategy> phoneList = new ArrayList<>();
				String phone = null;
				MsinStrategy ms = null;
				while((phone = bufferedReader.readLine()) != null){
					//校验手机号
					if(MOBILE_NUM_PATTERN.matcher(phone.trim()).matches()){
						ms = new MsinStrategy();
						ms.setAppId(Integer.valueOf(appId));
						ms.setLevel(Integer.valueOf(level));
						ms.setPhoneNumber(phone.trim());
						
						phoneList.add(ms);
					}else{
						logger.error("[MSIN] phone=" + phone.trim());
					}
				}
				try {
					msinService.addMsinBatch(phoneList);
					map.put("result", "0");
				} catch (Exception e) {
					logger.error("[MSIN] addMsinBatch err.E={}", e);
				}
				
				read.close();
			} catch (Exception e) {
				logger.error("[MSIN] 文件解析失败.E={}", e);
			}
			
		}else{
			logger.error("[MSIN] 文件（格式）错误.fileName=" + upFileName);
			map.put("result", "-9999");
			map.put("fileName", upFileName);
			return map;
		}
		desFile.delete();
		
		return map;
	}
	
	
	
	
}
