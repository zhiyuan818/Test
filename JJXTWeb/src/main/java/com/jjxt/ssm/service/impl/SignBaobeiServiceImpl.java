package com.jjxt.ssm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjxt.ssm.entity.SignBaobei;
import com.jjxt.ssm.mapper.SignBaobeiMapper;
import com.jjxt.ssm.service.SignBaobeiService;
import com.jjxt.ssm.utils.StringUtil;

@Service("signBaobeiService")
@Transactional
public class SignBaobeiServiceImpl implements SignBaobeiService {

	@Autowired
	private SignBaobeiMapper signBaobeiMapper;

	@Override
	public List<SignBaobei> findAllList(Map<String, Object> map) throws Exception {
		return signBaobeiMapper.findAllList(map);
	}

	@Override
	public Integer findTotal(Map<String, Object> map) throws Exception {
		return signBaobeiMapper.findTotal(map);
	}

	@Override
	public SignBaobei findListById(Integer id) throws Exception {
		return signBaobeiMapper.findListById(id);
	}

	@Override
	public int delSignBaobei(Integer id) throws Exception {
		return signBaobeiMapper.delSignBaobei(id);
	}

	@Override
	public int updateFlagToNo(Integer id) throws Exception {
		return signBaobeiMapper.updateFlagToNo(id);
	}

	@Override
	public int updateFlagToYes(Integer id) throws Exception {
		return signBaobeiMapper.updateFlagToYes(id);
	}

	@Override
	public int addBatchSignBaoBei(List<List<Object>> listob, Map<String, String> sp, Integer[] check, String update,
			String ext, String baobeiFlag, String channelId) throws Exception {
		if (listob == null) {
			return 0;
		}
		if (sp == null) {
			return 0;
		}
		List<List<Object>> list = null;
		List<SignBaobei> existList = null;
		List<SignBaobei> signBaobeis = new ArrayList<SignBaobei>();
		SignBaobei signBaobei = null;
		StringBuffer extBuf = new StringBuffer();
		StringBuffer signBuf = new StringBuffer();
		StringBuffer comBuf = new StringBuffer();
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
						signBaobei = new SignBaobei();
						extBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						signBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("sign"))));
						if (!StringUtil.isEmpty(list.get(j).get(Integer.parseInt(sp.get("companyName"))))) {
							comBuf.append("," + list.get(j).get(Integer.parseInt(sp.get("companyName"))));
						}
						signBaobei.setChannelId(Integer.parseInt(channelId));
						signBaobei.setSign((String) list.get(j).get(Integer.parseInt(sp.get("sign"))));
						signBaobei.setBaobeiFlag(baobeiFlag);
						signBaobei.setExtSrc((String) list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						signBaobei.setCompanyName((String) list.get(j).get(Integer.parseInt(sp.get("companyName"))));
						signBaobeis.add(signBaobei);
					}
				}
				comBuf.append("," + " ");
				map.put("extSrc", extBuf.substring(1));
				map.put("sign", signBuf.substring(1));
				map.put("companyName", comBuf.substring(1));
				map.put("channelId", channelId);
				existList = signBaobeiMapper.findExistList(map);
				for (SignBaobei baobei : existList) {
					ids.append("," + baobei.getId());
				}
				if (ids.length() > 0) {
					signBaobeiMapper.deleteBatchSignBaobei(ids.substring(1));
				}
				num += signBaobeiMapper.addBatchSignBaobei(signBaobeis);
			}
		} else {
			// 清空签名报备表
			int deleteAll = signBaobeiMapper.deleteAll();
			for (int i = 0; i < listob.size(); i += 1000) {
				if (listob.size() - i <= 1000) {
					list = listob.subList(i, listob.size());
				} else {
					list = listob.subList(i, i + 1000);
				}
				for (int j = 0; j < list.size(); j++) {
					if (!Arrays.asList(check).contains(j)) {
						signBaobei = new SignBaobei();
						signBaobei.setExtSrc((String) list.get(j).get(Integer.parseInt(sp.get("extSrc"))));
						signBaobei.setCompanyName((String) list.get(j).get(Integer.parseInt(sp.get("companyName"))));
						signBaobei.setSign((String) list.get(j).get(Integer.parseInt(sp.get("sign"))));
						signBaobei.setChannelId(Integer.parseInt(channelId));
						signBaobei.setBaobeiFlag(baobeiFlag);
						signBaobeis.add(signBaobei);
					}
				}
				num += signBaobeiMapper.addBatchSignBaobei(signBaobeis);
			}
		}
		return num;
	}

	@Override
	public List<SignBaobei> findSignBaobeiAll() throws Exception {
		return signBaobeiMapper.findSignBaobeiAll();
	}

}
