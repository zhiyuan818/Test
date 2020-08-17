package com.jjxt.ssm.redis;

/**
 * 
 * @author DoveDeng
 * @date 2017-04-11
 *
 */
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolAbstract;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class JJMRedisPool {
	private static final Logger LOGGER = LoggerFactory.getLogger(JJMRedisPool.class);
	private final String name;
	private JedisPoolAbstract pool;
	private final boolean isSentinel;
	private final boolean isPwd;
	private JJMRedisConfig con;
	private AtomicInteger sum = new AtomicInteger();

	public JJMRedisPool(String name, boolean isSentinel, JJMRedisConfig conf, boolean isPwd) {
		this.isSentinel = isSentinel;
		this.isPwd = isPwd;
		this.name = name;
		con = conf;
		init();
	}

	private synchronized void init() {
		if(con == null){
			LOGGER.error("[REDIS:ERR] redis {} config file is null", name);
			return;
		}
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(con.getMaxTotal());
		config.setMaxIdle(con.getMaxIdle());
		config.setMaxWaitMillis(con.getMaxWait());
		// TODO:add more config for redis
		JedisPoolAbstract pool1 = null;
		try {
			if(isSentinel && isPwd){
				pool1 = new JedisSentinelPool(name, con.getIps(), config,con.getPasswd());
			}else if (isSentinel) {
				pool1 = new JedisSentinelPool(name, con.getIps(), config);
			}else if (isPwd){
				String[] args = new String[2];
				for (String s : con.getIps()) {
					args = s.split(":");
					break;
				}
				int port = Integer.parseInt(args[1]);
				pool1 = new JedisPool(config, args[0], port, con.getReadTimeout(), con.getPasswd());
			} else {
				String[] args = new String[2];
				for (String s : con.getIps()) {
					args = s.split(":");
					break;
				}
				int port = Integer.parseInt(args[1]);
				pool1 = new JedisPool(config, args[0], port, con.getReadTimeout());
			}
		} catch (Exception e) {
			LOGGER.error("[REDIS:ERR] redis {} init is error:{}", name, e.getMessage());
		}
		this.pool = pool1;
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	public void returnResource(final Jedis redis) {
		if (redis != null) {
			redis.close();
			if (sum.get() > 0) {
				sum.decrementAndGet();
			}
		}
	}

	public Jedis getJedis() {
		Jedis j = null;
		try{
			j = pool.getResource();
		}catch(Exception e){
			e.printStackTrace();
		}
		return j;
	}

	public String getName() {
		return name;
	}
}
