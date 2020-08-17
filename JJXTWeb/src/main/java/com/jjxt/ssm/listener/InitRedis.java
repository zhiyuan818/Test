package com.jjxt.ssm.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.jjxt.ssm.common.Configurator;
import com.jjxt.ssm.common.Constants;

public class InitRedis implements ServletContextListener{
	private static Logger logger = Logger.getLogger(ServletContextListener.class);
	private static final Configurator CONFIG = Configurator.getInstance();
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		if (!CONFIG.loadConfig("classpath:"+Constants.Common.Config.CONF_FILE)) {
			logger.error("[ERR:INIT] Initialization Failed, failed to load config file.");
		}
		logger.debug("[BINS][CONFIG] init config");
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
