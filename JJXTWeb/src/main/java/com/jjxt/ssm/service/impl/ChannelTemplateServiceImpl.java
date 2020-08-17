package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ChannelTemplate;
import com.jjxt.ssm.mapper.ChannelTemplateMapper;
import com.jjxt.ssm.service.ChannelTemplateService;
import com.jjxt.ssm.utils.StringUtil;

@Service("channelTemplateService")
@Transactional
public class ChannelTemplateServiceImpl implements ChannelTemplateService {

	@Autowired
	private ChannelTemplateMapper channelTemplateMapper;

	@Override
	public List<ChannelTemplate> findChannelAppIds() throws Exception {
		return channelTemplateMapper.findChannelAppIds();
	}
	
	@Override
	public List<ChannelTemplate> findAllList(Map<String, Object> map) throws Exception {
		return channelTemplateMapper.findAllList(map);
	}

	@Override
	public Integer findTotal(Map<String, Object> map) throws Exception {
		return channelTemplateMapper.findTotal(map);
	}

	@Override
	public int addChannelTemplate(ChannelTemplate channelTemplate) throws Exception {
		return channelTemplateMapper.addChannelTemplate(channelTemplate);
	}

	@Override
	public List<ChannelTemplate> findAddEnd() throws Exception {
		return channelTemplateMapper.findAddEnd();
	}

	@Override
	public List<ChannelTemplate> findChannelTemplateById(Integer id) throws Exception {
		return channelTemplateMapper.findChannelTemplateById(id);
	}

	@Override
	public int updateChannelTemplate(ChannelTemplate channelTemplate) throws Exception {
		return channelTemplateMapper.updateChannelTemplate(channelTemplate);
	}

	@Override
	public int deleteChannelTemplate(Integer id) throws Exception {
		return channelTemplateMapper.deleteChannelTemplate(id);
	}

	@Override
	public List<ChannelTemplate> findChannelTemplateByIds(Integer[] ids) throws Exception {
		return channelTemplateMapper.findChannelTemplateByIds(ids);
	}

	@Override
	public int deleteChannelTemplateBatch(Integer[] ids) throws Exception {
		return channelTemplateMapper.deleteChannelTemplateBatch(ids);
	}

	@Override
	public int addChannelTemplateBatch(List<List<Object>> listob, Map<String, String> sp, Integer[] check,
			String channelId) throws Exception {
		if (listob == null) {
			return 0;
		}
		if (sp == null) {
			return 0;
		}
		List<List<Object>> list = null;
		List<ChannelTemplate> channelTemplateList = new ArrayList<ChannelTemplate>();
		ChannelTemplate channelTemplate = null;
		int num = 0;
		for (int i = 0; i < listob.size(); i = i + 1000) {
			if (listob.size() - i <= 1000) {
				list = listob.subList(i, listob.size());
			} else {
				list = listob.subList(i, i + 1000);
			}
			List<String> fuList = null;
			for (int j = 0; j < list.size(); j++) {
				if (!Arrays.asList(check).contains(j)) {
					channelTemplate = new ChannelTemplate();
					
					String channelContent=(String) list.get(j).get(Integer.parseInt(sp.get("channelTemplate")));
					
					fuList = new ArrayList<>();
					channelContent=channelContent.trim();
					spitFormula(fuList, channelContent);
					
					StringBuffer sb = new StringBuffer();
					for (String string : fuList) {
						sb.append(string).append("*");
					}
					String content =sb.toString();
					if(!channelContent.endsWith("}")){
						content=content.substring(0,content.length()-1);
					}
					channelTemplate.setChannelId(Integer.parseInt(channelId));
					channelTemplate.setChannelAppId((String) list.get(j).get(Integer.parseInt(sp.get("channelAppId"))));
					channelTemplate.setChannelTemplateId((String) list.get(j).get(Integer.parseInt(sp.get("channelTemplateId"))));
					channelTemplate.setChannelTemplate(channelContent);
					channelTemplate.setTemplate(content);
					channelTemplate.setExtras((String) list.get(j).get(Integer.parseInt(sp.get("extras"))));
					
					channelTemplateList.add(channelTemplate);
				}
			}
			num += channelTemplateMapper.addChannelTemplateBatch(channelTemplateList);
		}
		return num;
	}
	
	private static void spitFormula(List<String> fuList,String formula) throws Exception {
		int leftIndex = formula.indexOf("$");
		int rightIndex = formula.indexOf("}");
		if(leftIndex<0 && rightIndex<0){
			if(!StringUtil.isEmpty(formula)) {
				fuList.add(formula);
			}
			return;
		}
		String fu = formula.substring(0,leftIndex);
		formula = formula.substring(rightIndex+1);
		fuList.add(fu);
		spitFormula(fuList, formula);
	}

	@Override
	public List<ChannelTemplate> findChannelTemplateAll() throws Exception {
		return channelTemplateMapper.findChannelTemplateAll();
	}

}
