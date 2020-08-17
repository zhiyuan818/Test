package com.jjxt.ssm.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjxt.ssm.entity.Template;

/**
 * @author shangcp
 *	模版匹配算法
 */
public class MsgTemplateUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MsgTemplateUtils.class);
	
	/**
	 * 将list的数据按ruleIndex从大到小排序
	 * @param list
	 * @return
	 */
	public static List<Template> sort(List<Template> list){
		List<Template> result = new ArrayList<>();
		try {
			for(Template msg : list){
				if(result.size() == 0){
					result.add(msg);
				}else{
					int index = 0;
					for(int i=0;i<result.size();i++){
						Template temp = result.get(i);
						if(msg.getRuleIndex() >= temp.getRuleIndex()){
							index = i;
							break;
						}else{
							index = i;
						}
					}
					result.add(index, msg);
				}
			}
		} catch (Exception e) {
			LOGGER.error("[TEM] exce={}",e);
		}
		return result;
	}
	
	/**
	 * 判断content是否符合formula的公式
	 * @param content
	 * @param formula
	 * @return
	 */
	public static boolean match(String content,String formula){
		boolean result = false;
		List<String> kuoList = new ArrayList<>();
		List<String> fuList = new ArrayList<>();
		
		
		try {
			spitFormula(kuoList, fuList, formula);
			
			List<Boolean> resList = new ArrayList<>();
			for(String str : kuoList){
				if(str.indexOf('^') >=0 ){
					if(content.startsWith(str.substring(1))){
						resList.add(true);
					}else{
						resList.add(false);
					}
					continue;
				}else if(str.indexOf('&') >=0 ){
					String[] strs = str.split("&");
					boolean res = true;
					for(String s : strs){
						if(content.indexOf(s)<0){
							res = false;
							break;
						}
					}
					resList.add(res);
					continue;
				}else if(str.indexOf('|') >=0 ){
					String[] strs = str.split("\\|");
					boolean res = false;
					for(String s : strs){
						if(content.indexOf(s)>=0){
							res = true;
							break;
						}
					}
					resList.add(res);
					continue;
				}else{
					if(content.indexOf(str) >= 0){
						resList.add(true);
					}else{
						resList.add(false);
					}
				}
			}
			
			for(int i=0;i<fuList.size();i++){
				String str = fuList.get(i);
				if(str.equals("&")){
					Boolean b1 = resList.get(i);
					Boolean b2 = resList.get(i+1);
					resList.remove(b1);
					resList.remove(b2);
					if(b1 && b2){
						resList.add(0,true);
					}else{
						resList.add(0,false);
					}
					
				}else if(str.equals("|")){
					Boolean b1 = resList.get(i);
					Boolean b2 = resList.get(i+1);
					resList.remove(b1);
					resList.remove(b2);
					if(b1 || b2){
						resList.add(0,true);
					}else{
						resList.add(0,false);
					}
				}
				fuList.remove(str);
				i--;
			}
			
			result = resList.get(0);
		} catch (Exception e) {
			LOGGER.error("[TEM] exce={}",e);
		}
		
		return result;
	}
	
	
	private static void spitFormula(List<String> kuoList,List<String> fuList,String formula){
		try {
			formula = formula.trim();
			int leftIndex = formula.indexOf("(");
			int rightIndex = formula.indexOf(")");
			if(leftIndex<0 && rightIndex<0){
				return;
			}
			String fu = formula.substring(0,leftIndex);
			String ss = formula.substring(leftIndex+1,rightIndex);
			formula = formula.substring(rightIndex+1);
			kuoList.add(ss);
			if(!StringUtil.isEmpty(fu)){
				fuList.add(fu);
			}
			spitFormula(kuoList, fuList, formula);
		} catch (Exception e) {
			LOGGER.error("[TEM] exce={}",e);
		}
	}
	
	

	public static void main(String[] args) {
		String content = "法轮我是一名国民党";
		String formula = "(^法轮|中国政府|共产党)&(国民党)";//类似这种算法,此只是demo
		long start = System.currentTimeMillis();
		boolean result = match(content, formula);
		System.out.println(result+" cost"+(System.currentTimeMillis()-start));
	}
}
