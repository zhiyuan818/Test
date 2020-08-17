package com.jjxt.ssm.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jjxt.ssm.utils.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BlackLevel2;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.MO;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.MOService;

@Controller
@RequestMapping("/mo")
public class MOController {

	private static Logger logger = Logger.getLogger(MOController.class);

	private static final String PATH = "mo/";
	@Autowired
	private MOService moService;
	@Autowired
	private ApplicationService applicationMapper;
	@Autowired
	private ChannelService channelService;
	/**
	 * 页面跳转
	 */
	@RequestMapping("/goMOPageList.action")
	public String goMOPageList(HttpServletRequest request) {
		List<Channel> channels = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		try {
			channels = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MO] ",e);
		}
		List<Application> apps = new ArrayList<>();
		try {
			apps = applicationMapper.findAppName();
		} catch (Exception e) {
			logger.error("[ERROR][MO] ",e);
		}
		request.setAttribute("channels", channels);
		request.setAttribute("apps", apps);
		return PATH + "moList";
	}

	/**
	 * 查询所有通道
	 */
	
	@RequestMapping("/findChannelName.action")
	@ResponseBody
	public List<Channel> findChannelName() {
		List<Channel> list = new ArrayList<>();
		Map<String, Object> map=new HashMap<>();
		map.put("channelStatus", "deleted");
		try {
			list = channelService.findChannel(map);
		} catch (Exception e) {
			logger.error("[ERROR][MO] ",e);
		}
		return list;
	}

	/**
	 * 查询全部上行数据
	 */
	
	@RequestMapping(value = "/findMOPageList.action", produces = "application/json; charset=utf-8")
	@ResponseBody
	public PageResult<MO> findMOPageList(Integer pageSize, Integer pageIndex, String logDate, String appId,
			String srcNumber, String content, String channelId, String startTime, String endTime, String destNumber,
			String city, HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[BINS][MO] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",logDate=" + logDate + ",appId="
				+ appId + ",srcNumber=" + srcNumber + ",content=" + content + ",channelId=" + channelId + "startTime"
				+ startTime + "endTime" + endTime + "destNumber" + destNumber + "city" + city);
		Long sTime = System.currentTimeMillis();
		if (StringUtil.isEmpty(logDate)) {
			logDate = DateUtil.convertDate1(new Date());
		} else {
			try {
				logDate = DateUtil.convertDate1(DateUtil.converDateFromStr4(logDate));
			} catch (ParseException e) {
				logger.error("[ERROR][MO] 时间转换异常.", e);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		try {
			map.put("logDate", logDate);
			map.put("appId", appId);
			map.put("srcNumber", srcNumber);
			map.put("content", content);
			map.put("channelId", channelId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("destNumber", destNumber);
			map.put("city", city);

			total = moService.findMOTotal(map);
		} catch (Exception e) {
			logger.error("[ERROR][MO] 查询上行日志总数出错.", e);
		}
		Page page = new Page(pageSize, total, pageIndex);
		Map<String, Object> map1 = PageUtil.getDefaultPageMap(page);
		try {
			map1.put("logDate", logDate);
			map1.put("appId", appId);
			map1.put("srcNumber", srcNumber);
			map1.put("content", content);
			map1.put("channelId", channelId);
			map1.put("startTime", startTime);
			map1.put("endTime", endTime);
			map1.put("destNumber", destNumber);
			map1.put("city", city);
			List<MO> findMoLists = moService.findMOPageList(map1);
			Long eTime = System.currentTimeMillis();
			logger.debug("[BINS][MO] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",logDate=" + logDate
					+ ",appId=" + appId + ",srcNumber=" + srcNumber + ",content=" + content + ",channelId=" + channelId
					+ "startTime" + startTime + "endTime" + endTime + "destNumber" + destNumber + "city" + city
					+ ",time=" + (eTime - sTime));
			return new PageResult<MO>(total, findMoLists);
		} catch (Exception e) {
			logger.error("[ERROR][MO] 查询上行日志分页出错.", e);
		}

		return new PageResult<MO>(0, new ArrayList<MO>());
	}

	/**
	 * 上行加黑处理
	 * 
	 * @param id
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel2, operation = Operate.INSERT)
	@RequestMapping("/findMOById.action")
	@ResponseBody
	public ResponseResult findMOById(Integer id, String logDate) {

		BlackLevel2 blackLevel2 = null;
		int selectResult = 0;
		int addResult = 0;
		int updateResult = 0;
		ResponseResult result = new ResponseResult();
		List<BlackLevel2> newList = new ArrayList<BlackLevel2>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("logDate", logDate);
		try {
			MO mo = moService.findMOById(map);
			blackLevel2 = new BlackLevel2();
			blackLevel2.setAppId(mo.getAppId());
			blackLevel2.setChannelId(mo.getChannelId());
			blackLevel2.setPhoneNumber(mo.getSrcNumber());
			blackLevel2.setAddTime(new Date());
			selectResult = moService.selectBlackLevel2(blackLevel2);
			if (selectResult > 0) {
				// 修改上行加黑标记
				updateResult = moService.updateFlag(map);
				if (updateResult == 1) {
					logger.debug("[BINS][MO] 修改加黑标记成功！ id=" + id);
				}
			} else {
				// 添加到二级黑
				Long sTime = System.currentTimeMillis();
				addResult = moService.addToBlack(blackLevel2);
				newList.add(blackLevel2);
				if (addResult == 1) {
					logger.debug("[BINS][MO] 添加二级黑成功！ channelId=" + blackLevel2.getChannelId() + ",app_id="
							+ blackLevel2.getAppId() + ",phone_number=" + blackLevel2.getPhoneNumber());
				}

				result.setResult(addResult);
				result.setNewData(newList);
				result.setOldData(null);
				Long eTime = System.currentTimeMillis();
				logger.debug("[BINS][MO] newData=" + newList + ",time=" + (eTime - sTime) + "ms,result="
						+ result.getResult());

				// 修改上行加黑标记
				updateResult = moService.updateFlag(map);
				if (updateResult == 1) {
					logger.debug("[BINS][MO] 修改加黑标记成功！id=" + id);
				}
			}
		} catch (Exception e) {
			logger.error("[ERROR][MO] 上行加黑处理出错.", e);
		}

		return result;
	}

	/**
	 * 批量加黑
	 * 
	 * @param ids
	 * @return
	 */
	
	@DataOperate(bussiness = Bussiness.blacklevel2, operation = Operate.INSERTBATCH)
	@RequestMapping("/addToBlackLevel2Batch.action")
	@ResponseBody
	public ResponseResult addToBlackLevel2Batch(
			@RequestParam(value = "ids[]", required = false, defaultValue = "") Integer[] ids, String logDate) {

		BlackLevel2 black2 = null;
		int selectResult = 0;
		int addResult = 0;
		int updateResult = 0;
		int num = 0;
		MO mo = null;
		ResponseResult result = new ResponseResult();
		List<BlackLevel2> newList = new ArrayList<BlackLevel2>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logDate", logDate);
		try {
			for (Integer id : ids) {
				map.put("id", id);
				mo = moService.findMOById(map);
				black2 = new BlackLevel2();
				black2.setAppId(mo.getAppId());
				black2.setChannelId(mo.getChannelId());
				black2.setPhoneNumber(mo.getSrcNumber());
				black2.setAddTime(new Date());
				selectResult = moService.selectBlackLevel2(black2);

				if (selectResult > 0) {
					// 修改上行加黑标记
					updateResult = moService.updateFlag(map);
					if (updateResult == 1) {
						logger.debug("[BINS][MO] 修改标记成功！id=" + id);
					}
				} else {
					// 添加到二级黑
					Long sTime = System.currentTimeMillis();
					addResult = moService.addToBlack(black2);
					newList.add(black2);
					if (addResult == 1) {
						logger.debug("[BINS][MO] 添加二级黑成功！channelId=" + black2.getChannelId() + ",app_id="
								+ black2.getAppId() + ",phone_number=" + black2.getPhoneNumber());

					}
					result.setNewData(newList);
					result.setOldData(null);
					Long eTime = System.currentTimeMillis();
					logger.debug("[BINS][MO] newData=" + newList + ",time=" + (eTime - sTime) + "ms,result="
							+ result.getResult());
					// 修改上行加黑标记
					updateResult = moService.updateFlag(map);
					if (updateResult == 1) {
						logger.debug("[BINS][MO] 修改标记成功！id=" + id);
					}
				}

				num++;
			}

		} catch (Exception e) {
			logger.error("[ERROR][MO] 上行批量加黑处理出错.", e);
		}

		if (ids.length == num) {
			result.setResult(1);
			return result;
		} else {
			result.setResult(0);
			return result;
		}
	}

}
