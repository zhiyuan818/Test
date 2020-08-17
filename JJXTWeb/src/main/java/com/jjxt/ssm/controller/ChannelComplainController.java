package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.ChannelComplain;
import com.jjxt.ssm.service.ChannelComplainService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.utils.DateUtil;
import com.jjxt.ssm.utils.PageResult;

@Controller
@RequestMapping("/complain")
public class ChannelComplainController {

	private static Logger logger = Logger.getLogger(ChannelComplainController.class);

	private SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.PATTERN_yyyy_MM_dd);

	private static final String PATH = "complain/";
	@Autowired
	private ChannelComplainService chaComplain;
	@Autowired
	private ChannelService channelService;

	@RequestMapping("/goChannelComplainPage.action")
	public String goChannelComplain(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MT] ", e);
		}
		request.setAttribute("channels", channels);
		return PATH + "channelComplainPageList";
	}

	
	@RequestMapping("/findAllLists.action")
	@ResponseBody
	public PageResult<ChannelComplain> findAllLists() {

		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<ChannelComplain> lists = new ArrayList<ChannelComplain>();

		Date date = new Date();
//		String year = DateUtil.convertDate(date);
		Date start = DateUtil.getFirstdayOfMonth(date);
		String bTime = sdf.format(start);
		Date end = DateUtil.getLastdayOfMonth(date);
		String eTime = sdf.format(end);
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
//		map.put("year", year);
		try {
			lists = chaComplain.findAllLists(map);

		} catch (Exception e) {
			logger.error("[ERROR][C_COMPLAIN] 查询通道投比出错.", e);
		}

		total = lists.size();

		return new PageResult<ChannelComplain>(total, lists);
	}

	
	@RequestMapping(value = "/findListByChannelId.action",produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<ChannelComplain> findListByChannelId(Integer channelId, String logDate) {

		int total = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<ChannelComplain> lists = new ArrayList<ChannelComplain>();

		Date date = null;
		try {
			date = DateUtil.converDateFromStr4(logDate);
		} catch (ParseException e1) {

			e1.printStackTrace();
		}
//		String year = DateUtil.convertDate(date);
		Date start = DateUtil.getLast26dayOfMonth(date);	//获取上月26日
		String bTime = sdf.format(start);
		Date end = DateUtil.getCurrent25dayOfMonth(date);	//获取本月25日
		String eTime = sdf.format(end);
		map.put("beginTime", bTime);
		map.put("endTime", eTime);
		map.put("channelId", channelId);
//		map.put("year", year);

		if (channelId == null) {
			try {
				lists = chaComplain.findAllLists(map);

			} catch (Exception e) {
				logger.error("[ERROR][C_COMPLAIN] 按日期查询通道投比出错.", e);
			}

			total = lists.size();

			return new PageResult<ChannelComplain>(total, lists);

		} else {

			try {
				lists = chaComplain.findListByChannelId(map);

			} catch (Exception e) {
				logger.error("[ERROR][C_COMPLAIN] 按通道ID查询通道投比出错.", e);
			}

			total = lists.size();

			return new PageResult<ChannelComplain>(total, lists);

		}

	}

}
