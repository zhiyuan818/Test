package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.ExtSign;
import com.jjxt.ssm.entity.SignExtTemplate;
import com.jjxt.ssm.mapper.ExtSignMapper;
import com.jjxt.ssm.service.ExtSignService;

@Service("extSignService")
@Transactional
public class ExtSignServiceImpl implements ExtSignService{

	@Autowired
	private ExtSignMapper extsignMapper;
	
	@Override
	public List<ExtSign> findAllList(Map<String, Object> map) throws Exception {
		return extsignMapper.findAllList(map);
	}

	@Override
	public Integer findTotal(Map<String, String> map) throws Exception {
		return extsignMapper.findTotal(map);
	}

	@Override
	public int addToExtSign(Map<String, Object> map) throws Exception {
		return extsignMapper.addToExtSign(map);
	}

	@Override
	public int delExtSign(Map<String, String> map) throws Exception {
		return extsignMapper.delExtSign(map);
	}

	@Override
	public ExtSign findExtSignById(Integer id) throws Exception {
		return extsignMapper.findExtSignById(id);
	}

	@Override
	public int updateExtSign(Map<String, Object> map) throws Exception {
		return extsignMapper.updateExtSign(map);
	}

	@Override
	public int findAppId(String str) throws Exception {
		return extsignMapper.findAppId(str);
	}

	@Override
	public int existData(Map<String, Object> map) throws Exception {
		return extsignMapper.existData(map);
	}

	@Override
	public int addBatchExtSign(List<List<Object>> listob, Map<String, String> sp, Integer[] check, String update) throws Exception {
		if (listob == null) {
			return 0;
		}
		if (sp == null) {
			return 0;
		}
		List<List<Object>> list = null;
		List<ExtSign> existList = null;
		List<ExtSign> signBaobeis = new ArrayList<ExtSign>();
		ExtSign extSign = null;
		StringBuffer extBuf = new StringBuffer();
		StringBuffer signBuf = new StringBuffer();
		StringBuffer appBuf = new StringBuffer();
		StringBuffer ids = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		int num = 0;
		if ("yes".equals(update)) {
			for (int i = 0; i < listob.size(); i = i + 1000) {
				if (listob.size() - i <= 1000) {
					list = listob.subList(i, listob.size());
				} else {
					list = listob.subList(i, i + 1000);
				}
				for (int j = 0; j < list.size(); j++) {
					if (!Arrays.asList(check).contains(j)) {
						extSign = new ExtSign();
						extBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						signBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("sign"))));
						appBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("appId"))));
						extSign.setSign((String) list.get(j).get(Integer.parseInt(sp.get("sign"))));
						extSign.setExtSrc((String) list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						extSign.setAppId(Integer.parseInt(list.get(j).get(Integer.parseInt(sp.get("appId"))).toString()));
						signBaobeis.add(extSign);
					}
				}
				map.put("extSrc", extBuf.substring(1));
				map.put("sign", signBuf.substring(1));
				map.put("appId", appBuf.substring(1));
				existList = extsignMapper.findExistList(map);
				for (ExtSign baobei : existList) {
					ids.append("," + baobei.getId());
				}
				if (ids.length() > 0) {
					extsignMapper.deleteBatchExtSign(ids.substring(1));
				}
				num += extsignMapper.addBatchExtSign(signBaobeis);
			}
		} else {
			// 清空签名报备表
			int delNum=extsignMapper.deleteAll();
			for (int i = 0; i < listob.size(); i += 1000) {
				if (listob.size() - i <= 1000) {
					list = listob.subList(i, listob.size());
				} else {
					list = listob.subList(i, i + 1000);
				}
				for (int j = 0; j < list.size(); j++) {
					if (!Arrays.asList(check).contains(j)) {
						extSign = new ExtSign();
						extSign.setExtSrc((String) list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						extSign.setSign((String) list.get(j).get(Integer.parseInt(sp.get("sign"))));
						extSign.setAppId(Integer.parseInt(list.get(j).get(Integer.parseInt(sp.get("appId"))).toString()));
						signBaobeis.add(extSign);
					}
				}
				num += extsignMapper.addBatchExtSign(signBaobeis);
			}
		}
		return num;
	}

	@Override
	public List<SignExtTemplate> findSignExtBySigns(List<String> list) throws Exception {
		return extsignMapper.findSignExtBySigns(list);
	}

	@Override
	public Integer findTemplateTotal(Map<String, String> map) throws Exception {
		return extsignMapper.findTemplateTotal(map);
	}

	@Override
	public List<SignExtTemplate> findTemplateList(Map<String, Object> map) throws Exception {
		return extsignMapper.findTemplateList(map);
	}

	@Override
	public SignExtTemplate findTemplateById(Integer id) throws Exception {
		return extsignMapper.findTemplateById(id);
	}

	@Override
	public int addToSignExtTemplate(Map<String, Object> map) throws Exception {
		return extsignMapper.addToSignExtTemplate(map);
	}

	@Override
	public int isExistTemplate(Map<String, Object> map) throws Exception {
		return extsignMapper.isExistTemplate(map);
	}

	@Override
	public int updateSignExtTemplate(Map<String, Object> map) throws Exception {
		return extsignMapper.updateSignExtTemplate(map);
	}

	@Override
	public int delSignExtTemplate(Integer id) throws Exception {
		return extsignMapper.delSignExtTemplate(id);
	}

	@Override
	public int delTemplateBatch(Integer[] ids) throws Exception {
		return extsignMapper.delTemplateBatch(ids);
	}


}
