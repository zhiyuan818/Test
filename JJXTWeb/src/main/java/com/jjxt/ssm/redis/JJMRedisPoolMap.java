package com.jjxt.ssm.redis;

/**
 * 
 * @author DoveDeng
 * @date 2017-04-11
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JJMRedisPoolMap {
	private static final Logger LOGGER = LoggerFactory.getLogger(JJMRedisPoolMap.class);
	private static JJMRedisPoolMap pool;
	private Map<String, JJMRedisPool> map;
	private List<JJMRedisConfig> list = null;

	private JJMRedisPoolMap() {
	}

	public static JJMRedisPoolMap getInstance() {
		if (pool == null) {
			pool = new JJMRedisPoolMap();
		}
		return pool;
	}

	public void init(List<JJMRedisConfig> list) {
		if(list != null){
			this.list = list;
		}
		Map<String, JJMRedisPool> p = new HashMap<String, JJMRedisPool>();
		for (JJMRedisConfig c : this.list) {
			try {
				String[] splits = c.getRedisName().split(":");
				String redisName = c.getRedisName();
				if(splits.length == 1){
					redisName = splits[0];
				}else if(splits.length == 2){
					redisName = splits[1];
				}
				JJMRedisPool pool = new JJMRedisPool(redisName, c.isSentinel(), c, c.isPwd());
				p.put(c.getRedisName(), pool);
			} catch (Exception e) {
				LOGGER.error("[REDIS:ERR] redis {} is init error, mode is {} ,{}", c.getRedisName(), c.isSentinel(),
						e.getMessage());
			}
		}
		map = p;
	}

	public JJMRedisPool getRedisPool(String key) {
		if (map.containsKey(key)) {
			return map.get(key);
		}
		LOGGER.error("[ERR:REDIS] Redis get pool is wrong for:{}", key);
		return null;
	}
}
