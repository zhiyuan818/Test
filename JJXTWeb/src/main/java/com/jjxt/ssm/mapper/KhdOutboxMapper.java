package com.jjxt.ssm.mapper;

import java.util.List;
import java.util.Map;

import com.jjxt.ssm.entity.KhdOutbox;

public interface KhdOutboxMapper {

	int findTotal(Map<String, Object> map);

	List<KhdOutbox> findKhdOutboxList(Map<String, Object> param);

}
