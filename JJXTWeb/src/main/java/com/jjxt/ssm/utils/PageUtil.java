package com.jjxt.ssm.utils;

import java.util.HashMap;
import java.util.Map;

public class PageUtil {
	public PageUtil() {

	}

	/**
	 * 封装开始和结束数值
	 * 
	 * @param startName
	 *            以startName为键名，保存开始值到map中
	 * @param endName
	 *            以endName为键名，保存开始值到map中
	 * @param page
	 * @return
	 */
	public static Map<String, Object> getPageMap(String startName, String endName, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page.getCurrPageNo() - 1) * page.getPageSize();
		Integer end = page.getCurrPageNo() * page.getPageSize();
		map.put(startName, start);
		map.put(endName, end);
		return map;
	}

	/**
	 * 封装开始和结束数值,默认map中的开始键为start，结束键为 end
	 * 
	 * @param page
	 * @return
	 */
	public static Map<String, Object> getDefaultPageMap(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer start = (page.getCurrPageNo() - 1) * page.getPageSize();
		Integer end = page.getPageSize();
		map.put("start", start);
		map.put("end", end);
		return map;
	}
}
