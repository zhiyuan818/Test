package com.jjxt.ssm.aspect;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jjxt.ssm.entity.DataOperationLog;
import com.jjxt.ssm.entity.UcenterManager;
import com.jjxt.ssm.service.DataOperationLogService;
import com.jjxt.ssm.utils.Constant;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.StringUtil;

/**
 * 日志管理
 * 
 * @author Administrator
 *
 */
@Aspect
@Component
@Order(2)
public class LogAspect {

	private static Logger logger = Logger.getLogger(LogAspect.class);
	@Autowired
	private DataOperationLogService dataOperationLogService;

	@Pointcut("@annotation(com.jjxt.ssm.utils.DataOperate)")
	public void controllerAspecta() {
	}

	// 日志表 正常返回通知 附带参数
	/*
	 * @AfterReturning(value =
	 * "controllerAspecta() && @annotation(annotation) &&args(object,..) ",
	 * argNames = "annotation,object") public void
	 * interceptorApplogicA(DataOperate annotation, Object object){
	 * HttpServletRequest request = ((ServletRequestAttributes)
	 * RequestContextHolder.getRequestAttributes()).getRequest(); UcenterManager
	 * ucenterManager =
	 * (UcenterManager)request.getSession().getAttribute(Constant.
	 * SERVER_USER_SESSION); SimpleDateFormat sdf=new
	 * SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
	 * 
	 * if(ucenterManager!=null){ DataOperationLog dataOperationLog=new
	 * DataOperationLog();
	 * dataOperationLog.setBussiness(annotation.bussiness());
	 * dataOperationLog.setOperation(annotation.operation());
	 * dataOperationLog.setUcenterManagerId(ucenterManager.getId());
	 * dataOperationLog.setUcenterManagerName(ucenterManager.getManager_name());
	 * try { dataOperationLog.setDateTime(sdf.parse(sdf.format(new Date()))); }
	 * catch (ParseException e1) { logger.error("日志管理时间转换出错"); } Map<String,
	 * String[]> parameter = request.getParameterMap();
	 * dataOperationLog.setMapToParams(parameter); try {
	 * dataOperationLogService.addDataOperationLog(dataOperationLog); } catch
	 * (Exception e) { logger.error("日志表添加失败"+dataOperationLog); } } }
	 */
	// 环绕通知，日志表记录操作前后数据的变化
	@Around(value = "controllerAspecta() && @annotation(dataOperate)")
	public ResponseResult interceptor(ProceedingJoinPoint joinPoint, DataOperate dataOperate) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		UcenterManager ucenterManager = (UcenterManager) request.getSession()
				.getAttribute(Constant.SERVER_USER_SESSION);
		SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		ResponseResult result = null;
		try {
			result = (ResponseResult) joinPoint.proceed();
		} catch (Throwable e2) {
			logger.error("[ERROR]环绕通知执行错误", e2);
		}
		Object newData=null;
		Object oldData=null;
		if(result!=null){
			newData = result.getNewData();
			oldData = result.getOldData();
		}
		if (ucenterManager != null) {
			DataOperationLog dataOperationLog = new DataOperationLog();
			dataOperationLog.setBussiness(dataOperate.bussiness());
			dataOperationLog.setOperation(dataOperate.operation());
			dataOperationLog.setUcenterManagerId(ucenterManager.getId());
			dataOperationLog.setUcenterManagerName(ucenterManager.getManagerName());
			try {
				dataOperationLog.setDateTime(sdf.parse(sdf.format(new Date())));
			} catch (ParseException e1) {
				logger.error("[ERROR]日志管理时间转换出错", e1);
			}
			try {
				dataOperationLog.setNewData(splicing(newData, result.getResult()));
				dataOperationLog.setOldData(splicing(oldData, result.getResult()));
			} catch (IllegalAccessException | IllegalArgumentException e1) {
				logger.error("[ERROR]拼接失败", e1);
			}

			try {
				dataOperationLogService.addDataOperationLog(dataOperationLog);
			} catch (Exception e) {
				logger.error("[ERROR]日志表添加失败" + dataOperationLog, e);
			}
		}
		return result;
	}

	// 将对象的键值进行拼接
	@SuppressWarnings("unchecked")
	public static String splicing(Object obj, Integer result) throws IllegalAccessException, IllegalArgumentException {

		if (obj == null)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append("result:" + result + ";");
		if (obj instanceof List) {
			List<Object> taskList = new ArrayList<Object>();
			if (obj instanceof ArrayList<?>) {
				taskList = (ArrayList<Object>) obj;
			}
			for (Object o : taskList) {
				Field[] fields = o.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					if (i == 0) {
						sb.append(StringUtil.urldecodeChineseString(fields[i].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[i].get(o)==null?null:fields[i].get(o).toString()));
					} else {
						sb.append("&").append(StringUtil.urldecodeChineseString(fields[i].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[i].get(o)==null?null:fields[i].get(o).toString()));
					}
				}
				sb.append(";");
			}
		} else if (obj instanceof Map) {
			Map<String, Object> map = (Map<String, Object>) obj;
			List<String> keys = new ArrayList<String>(map.keySet());
			Collections.sort(keys);
			try {
				for (int i = 0; i < keys.size(); i++) {
					String key = keys.get(i);
					Object value = map.get(key);
					if(StringUtil.isEmpty(value)) {
						continue;
					}
					if (value instanceof String) {
						value = URLEncoder.encode((String) value, "UTF-8");
						if (i == 0) {
							sb.append(StringUtil.urldecodeChineseString(key)).append("=").append(StringUtil.urldecodeChineseString(value==null?null:value.toString()));
						} else {
							sb.append("&").append(StringUtil.urldecodeChineseString(key)).append("=").append(StringUtil.urldecodeChineseString(value==null?null:value.toString()));
						}
					}else if (value instanceof List){
						List<Object> taskList = new ArrayList<Object>();
						if (value instanceof ArrayList<?>) {
							taskList = (ArrayList<Object>) value;
						}
						for (Object o : taskList) {
							Field[] fields = o.getClass().getDeclaredFields();
							for (int j = 0; j < fields.length; j++) {
								fields[j].setAccessible(true);
								if (j == 0) {
									sb.append(StringUtil.urldecodeChineseString(fields[j].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[j].get(o)==null?null:fields[j].get(o).toString()));
								} else {
									sb.append("&").append(StringUtil.urldecodeChineseString(fields[j].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[j].get(o)==null?null:fields[j].get(o).toString()));
								}
							}
							sb.append(";");
						}
					}else{
						Field[] fields = value.getClass().getDeclaredFields();
						for (int j = 0; j < fields.length; j++) {
							fields[j].setAccessible(true);
							if (j == 0) {
								sb.append(StringUtil.urldecodeChineseString(fields[j].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[j].get(value)==null?null:fields[j].get(value).toString()));
							} else {
								sb.append("&").append(StringUtil.urldecodeChineseString(fields[j].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[j].get(value)==null?null:fields[j].get(value).toString()));
							}
						}
						sb.append(";");	
					}
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("[ERROR]转换编码格式出错", e);
			}
		}else if(obj instanceof String) {
			sb.append(String.valueOf(obj)+";");
		} else {
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				if (i == 0) {
					sb.append(StringUtil.urldecodeChineseString(fields[i].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[i].get(obj)==null?null:fields[i].get(obj).toString()));
				} else {
					sb.append("&").append(StringUtil.urldecodeChineseString(fields[i].getName())).append("=").append(StringUtil.urldecodeChineseString(fields[i].get(obj)==null?null:fields[i].get(obj).toString()));
				}
			}
			sb.append(";");
		}
		return sb.toString();
	}
}
