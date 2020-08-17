package com.jjxt.ssm.service;

import java.util.List;

import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.utils.DataSource;

public interface BlackLevel2Service {
	@DataSource("master")
	public List<BlackLevel2> findByPhoneNumber(String phone) throws Exception;

}
