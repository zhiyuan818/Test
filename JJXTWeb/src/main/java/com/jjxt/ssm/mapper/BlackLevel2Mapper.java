package com.jjxt.ssm.mapper;

import java.util.List;

import com.jjxt.ssm.entity.BlackLevel2;

public interface BlackLevel2Mapper {

	List<BlackLevel2> findByPhoneNumber(String phone);

}
