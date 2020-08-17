package com.jjxt.ssm.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjxt.ssm.entity.Application;
import com.jjxt.ssm.entity.BaseCompany;
import com.jjxt.ssm.entity.Channel;
import com.jjxt.ssm.entity.ReSendConfig;
import com.jjxt.ssm.service.ApplicationService;
import com.jjxt.ssm.service.BaseCompanyService;
import com.jjxt.ssm.service.ChannelService;
import com.jjxt.ssm.service.ReSendConfigService;
import com.jjxt.ssm.utils.Bussiness;
import com.jjxt.ssm.utils.DataOperate;
import com.jjxt.ssm.utils.Operate;
import com.jjxt.ssm.utils.Page;
import com.jjxt.ssm.utils.PageResult;
import com.jjxt.ssm.utils.PageUtil;
import com.jjxt.ssm.utils.ResponseResult;
import com.jjxt.ssm.utils.StringUtil;

@Controller
@RequestMapping("/reSend")
public class ReSendController {
    private static Logger logger = Logger.getLogger(ReSendController.class);


    private static final String PATH = "reSend/";
    
    @Autowired
    private BaseCompanyService companyService;
    @Autowired
    ApplicationService applicationService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ReSendConfigService reSendConfigService;

    @RequestMapping("/goReSendConfigList.action")
    public String goReSendConfigList(HttpServletRequest request, HttpServletResponse response) {
        List<BaseCompany> complains = new ArrayList<>();
        List<Application> apps = new ArrayList<>();
        List<Channel> chanNormal = new ArrayList<>();
        List<Map<String, Object>> channels = new ArrayList<>();
        try {
            complains = companyService.findAllCompany();
        } catch (Exception e) {
            logger.error("[ERROR][BASECOMPANY]  ex=" + e);
        }
        try {
            apps = applicationService.findAppName();
        } catch (Exception e) {
            logger.error("[ERROR][APPLICATION] ex=" + e);
        }
        try {
            channels = channelService.findChannel();
        } catch (Exception e) {
            logger.error("[ERROR][CHANNEL] ex=" + e);
        }
        try {
            chanNormal = channelService.findNormalChannel();
        } catch (Exception e) {
            logger.error("[ERROR][CHANNEL] ex=" + e);
        }
        request.setAttribute("complains", complains);
        request.setAttribute("apps", apps);
        request.setAttribute("channels", channels);
        request.setAttribute("chanNormal", chanNormal);
        return PATH + "reSendConfigList";
    }

	@RequestMapping("findReSendConfig.action")
    @ResponseBody
    public PageResult<ReSendConfig> findReSendConfig(Integer pageSize, Integer pageIndex, String appId, String channelId, String idType,
                                               String status, String companyId, String msgType, String toChannelId) {
        logger.debug("[BINS][RESENDCONFIG] pageSize=" + pageSize + ",pageIndex=" + pageIndex + ",appId=" + appId + "channelId=" + channelId + "idType=" + idType + "status=" + status + ",companyId=" + companyId + ",msgType=" + msgType + ",toChannelId=" + toChannelId);
        Map<String, String> paramMap = new HashMap<>();
        int total = 0;
        List<ReSendConfig> list = new ArrayList<>();
        if(!StringUtil.isEmpty(idType) && idType.equals("channel")) {
        
        paramMap.put("channelId", channelId);
        paramMap.put("toChannelId", toChannelId);
        paramMap.put("status", status);
        paramMap.put("msgType", msgType);        
        try {
            total = reSendConfigService.findTotalByChannel(paramMap);
        } catch (Exception e) {
            logger.error("[ERROR][RESENDCONFIG] ", e);
        }
        if (total == 0) {
            return new PageResult<ReSendConfig>(total, list);
        }
        Page page = new Page(pageSize, total, pageIndex);
        Map<String, Object> map = PageUtil.getDefaultPageMap(page);
        
        map.put("channelId", channelId);
        map.put("toChannelId", toChannelId);
        map.put("status", status);
        map.put("msgType", msgType);
        
        try {
            list = reSendConfigService.findReSendConfigByChannelPageList(map);
        } catch (Exception e) {
            logger.error("[ERROR][RESENDCONFIG] ", e);
        }
    }else {

    	paramMap.put("companyId", companyId);
    	paramMap.put("appId", appId);
        paramMap.put("toChannelId", toChannelId);
        paramMap.put("status", status);
        paramMap.put("msgType", msgType); 
      
      try {
          total = reSendConfigService.findTotalByApp(paramMap);
      } catch (Exception e) {
          logger.error("[ERROR][RESENDCONFIG] ", e);
      }
      if (total == 0) {
          return new PageResult<>(total, list);
      }
      Page page = new Page(pageSize, total, pageIndex);
      Map<String, Object> map = PageUtil.getDefaultPageMap(page);
      
      map.put("companyId", companyId);
      map.put("appId", appId);
      map.put("toChannelId", toChannelId);
      map.put("status", status);
      map.put("msgType", msgType); 
      
      try {
          list = reSendConfigService.findReSendConfigByAppPageList(map);
      } catch (Exception e) {
          logger.error("[ERROR][RESENDCONFIG] ", e);
      }
    }
        return new PageResult<>(total, list);
    }

    @RequestMapping("/findReSendConfigById.action")
	@ResponseBody
	public ReSendConfig findReSendConfigById(Integer id,String idType) {
    	
    	ReSendConfig reSendConfig = null;
    	if(!StringUtil.isEmpty(idType) && "channel".equals(idType)) {
    		try {
    			reSendConfig = reSendConfigService.findReSendConfigByChannelId(id);
    		} catch (Exception e) {
    			logger.error("[ERROR][RESENDCONFIG] select reSendConfig error.E={}", e);
    		}
    	}else {
    		try {
    			reSendConfig = reSendConfigService.findReSendConfigByAppId(id);
    		} catch (Exception e) {
    			logger.error("[ERROR][RESENDCONFIG] select reSendConfig error.E={}", e);
    		}
    	}
		return reSendConfig;
	}

	/**
	 * 修改
	 */
	
	@DataOperate(bussiness = Bussiness.ReSendConfig, operation = Operate.UPDATE)
	@RequestMapping(value = "/updateReSendConfig.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult updateReSendConfig(HttpServletRequest request, HttpServletResponse response,
			Integer id, String idType,String appId,String channelId, String status, String msgType, String toChannelId,String ignoreChanOrAcc) {
		logger.debug("[BINS][RESENDCONFIG] id=" + id + ",idType=" + idType + ",appId=" + appId + ",channelId=" + channelId + ",status=" + status + ",msgType=" + msgType + ",toChannelId=" + toChannelId + ",ignoreChanOrAcc="+ignoreChanOrAcc);
		int update = 0;
		status = status.trim();
		ReSendConfig oldData = null;
		Map<String, Object> map = new HashMap<String, Object>();
		Long startTime = System.currentTimeMillis();
		if(!StringUtil.isEmpty(idType) && "channel".equals(idType)) {
			
			map.put("id", id);
			map.put("channelId", channelId);
			map.put("status", status);
			map.put("msgType", msgType);
			map.put("toChannelId", toChannelId);
			map.put("ignoreChanOrAcc", ignoreChanOrAcc);
			try {
				oldData = reSendConfigService.findReSendConfigByChannelId(id);
			} catch (Exception e1) {
				logger.error("[RESEND] findReSendConfigByChannelId error.E={}", e1);
			}
			try {
				update = reSendConfigService.updateReSendConfigByChannelId(map);
			} catch (Exception e) {
				logger.error("[RESEND] updateReSendConfigByChannelId is error.E={}", e);
			}
			
		}else {
			map.put("id", id);
			map.put("appId", appId);
			map.put("status", status);
			map.put("msgType", msgType);
			map.put("toChannelId", toChannelId);
			map.put("ignoreChanOrAcc", ignoreChanOrAcc);
			try {
				oldData = reSendConfigService.findReSendConfigByAppId(id);
			} catch (Exception e1) {
				logger.error("[RESEND] findReSendConfigByAppId error.E={}", e1);
			}
			try {
				update = reSendConfigService.updateReSendConfigByAppId(map);
			} catch (Exception e) {
				logger.error("[RESEND] updateReSendConfigByAppId is error.E={}", e);
			}
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][RESEND] newData=" + map + ",time=" + (endTime - startTime) + "ms,result="
					+ update);
		return new ResponseResult(update, map, oldData);
	}
	
	
	
	@DataOperate(bussiness = Bussiness.ReSendConfig, operation = Operate.INSERT)
	@RequestMapping(value = "/addReSendConfig.action", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseResult addReSendConfig(HttpServletRequest request, HttpServletResponse response,
			String addIdType, String appId, String channelId, String status, String toChannelId,String msgType,String ignoreChanOrAcc) {

		logger.debug("[BINS][RESENDCONFIG] appId=" + appId + "idType="+addIdType + "channelId="+channelId + "status="+status + "toChannelId="+toChannelId + "msgType="+msgType);
		Map<String, Object> map = new HashMap<String, Object>();
		status = status.trim();
		if(!StringUtil.isEmpty(addIdType) && "channel".equals(addIdType)) {
			map.put("appChanId", channelId);
		}else {
			map.put("appChanId", appId);
		}
		map.put("idType", addIdType);
		map.put("status", status);
		map.put("toChannelId", toChannelId);
		map.put("msgType", msgType);
		map.put("ignoreChanOrAcc", ignoreChanOrAcc);
		int i=0;
		try {
			i = reSendConfigService.findReSendConfigIsRepeat(map);
			if(i > 0) {
				return new ResponseResult(2, null, null);
			}
		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] findReSendConfigIsRepeat exception", e);
		}
		try {
			i = reSendConfigService.addReSendConfig(map);
		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] addReSendConfig exception", e);
		}
		ReSendConfig newData = null;
		try {
			newData = reSendConfigService.findEndAddReSendConfig(addIdType);
		} catch (Exception e) {
			logger.error("[RESENDCONFIG][] 查询最后添加重发配置 异常", e);
		
		}

		return new ResponseResult(i, newData, null);
	}
	
	
	@DataOperate(bussiness = Bussiness.ReSendConfig, operation = Operate.DELETE)
	@RequestMapping("/delReSendConfig.action")
	@ResponseBody
	public ResponseResult delReSendConfig(Integer id,String idType) {

		int del = 0;
		Long startTime = System.currentTimeMillis();
		ReSendConfig oldData = null;
		try {
			if(!StringUtil.isEmpty(idType) && "channel".equals(idType)) {
			oldData = reSendConfigService.findReSendConfigByChannelId(id);
			}else {
				oldData = reSendConfigService.findReSendConfigByAppId(id);
			}
		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] findReSendConfigByAppId by id is error.E={}", e);
		}
		try {
			del = reSendConfigService.delReSendConfig(id);

		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] delReSendConfig is error.E={}", e);
		}
		
		ResponseResult result = new ResponseResult();
		result.setResult(del);
		result.setNewData(null);
		result.setOldData(oldData);
		Long endTime = System.currentTimeMillis();
		logger.debug("[BINS][RESENDCONFIG] oldData=" + oldData + ",time=" + (endTime - startTime) + "ms,result="
				+ result.getResult());

		return result;
	}
	
	@DataOperate(bussiness = Bussiness.ReSendConfig, operation = Operate.DELETEBATCH)
	@RequestMapping("/delResendConfigBatch.action")
	@ResponseBody
	public ResponseResult delResendConfigBatch(@RequestParam(value = "ids[]", required = false, defaultValue="") Integer[] ids){
		logger.debug("[BINS][RESENDCONFIG] id="+ Arrays.toString(ids));
		List<ReSendConfig> oldList = new ArrayList<ReSendConfig>();
		try {
			oldList = reSendConfigService.findResendConfigByIds(ids);
		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] 查询旧数据出错.", e);
		}
		if(oldList == null){
			return new ResponseResult(0, null, ids);
		}
		int del = 0;
		try {
			del = reSendConfigService.delResendConfigByIdBatch(ids);
		} catch (Exception e) {
			logger.error("[ERROR][RESENDCONFIG] 批量删除出错.", e);
		}
		
		return new ResponseResult(del, null, ids);
	}
    
}
