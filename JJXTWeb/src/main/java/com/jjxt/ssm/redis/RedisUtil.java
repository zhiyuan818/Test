package com.jjxt.ssm.redis;
/**
 * @author DoveDeng 2017-04-17
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjxt.ssm.utils.QueueUtils;
import com.jjxt.ssm.utils.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.set.SetParams;

public class RedisUtil {
	protected static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	public static List<byte[]> getList(String key, int count, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		List<byte[]> list = new ArrayList<byte[]>();
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}

			if (!redis.exists(key)) {
				return list;
			}

			for (int i = 0; i < count; i++) {
				byte[] b = redis.rpop(key.getBytes());
				if (b != null) {
					list.add(b);
				} else {
					return list;
				}
			}
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return list;
	}

	public static byte[] rpop(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			int count = 0;// 获取5次
			while (redis == null && count <= 5) {
				redis = pool.getJedis();
				count++;
			}
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis retring count:{},redis is null", name, count);
				return null;
			}
			return redis.rpop(key.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return null;
	}

	public static byte[] hget(String key, String field, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.hget(key.getBytes(), field.getBytes());
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static ScanResult<Entry<byte[], byte[]>> hscan(String key, String cursor, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.hscan(key.getBytes(), cursor.getBytes());
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	public static ScanResult<Entry<byte[], byte[]>> hscan(String key, String cursor,Integer count, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.hscan(key.getBytes(), cursor.getBytes(),count);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static String getLSet(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}

			String s = redis.get(key);
			if (s.equals("nil")) {
				return null;
			}
			return s;
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static String lSet(String key, long index, String value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		String result = "";
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}

			result = redis.lset(key, index, value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}

	public static long hset(String key, String feild, byte[] value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		long result = -1;
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return result;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return result;
			}
			result = redis.hset(key.getBytes(), feild.getBytes(), value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}

	public static String hSet(String key, Map<String, String> map, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.hmset(key, map);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long lpush(String key, byte[] value, String name) {
		List<byte[]> list = new ArrayList<byte[]>();
		list.add(value);
		return lpush(key, list, name);
	}
	
	public static long rpush(String key, byte[] value, String name) {
		List<byte[]> list = new ArrayList<byte[]>();
		list.add(value);
		return rpush(key, list, name);
	}

	public static long lpush(String key, List<byte[]> value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		long result = -1;
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return result;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return result;
			}
			for (byte[] b : value) {
				if (b == null) {
					continue;
				}
				result = redis.lpush(key.getBytes(), b);
			}
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}
	
	public static long rpush(String key, List<byte[]> value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		long result = -1;
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return result;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return result;
			}
			for (byte[] b : value) {
				if (b == null) {
					continue;
				}
				result = redis.rpush(key.getBytes(), b);
			}
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return result;
	}

	public static long sadd(String key, byte[] value, String name) {
		List<byte[]> list = new ArrayList<byte[]>();
		list.add(value);
		return sadd(key, list, name);
	}

	public static long sadd(String key, List<byte[]> value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			Pipeline p = redis.pipelined();
			for (byte[] b : value) {
				if (b == null) {
					continue;
				}
				p.sadd(key.getBytes(), b);
			}
			p.sync();
			return value.size();
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long getHashLen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.hlen(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long getSetLen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.scard(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long getListLen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}

			return redis.llen(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long delKey(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.del(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long hdel(String key, String field, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return -1;
			}
			return redis.hdel(key, field);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long hlen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return -1;
			}
			return redis.hlen(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long renameKey(String key, String newKey, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.renamenx(key, newKey);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long expire(String key, int second, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.expire(key, second);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static boolean sisMember(String key, byte[] value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return false;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return false;
			}

			return redis.sismember(key.getBytes(), value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static boolean hexist(String key, String field, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return false;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return false;
			}
			return redis.hexists(key, field);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long hincrBy(String key, String field, long value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is= {},redis is null", name);
				return -1;
			}

			return redis.hincrBy(key, field, value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static long zadd(String key, double score, byte[] value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return -1;
			}
			return redis.zadd(key.getBytes(), score, value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static Set<Tuple> rangeWithScore(String key, long start, long end, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.zrangeWithScores(key, start, end);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}

	}

	public static boolean rem(String key, byte[] member, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return false;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return false;
			}
			redis.zrem(key.getBytes(), member);
			return true;
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}

	}

	public boolean isShutdown(String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return false;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return false;
			}

			String ping = redis.ping();
			if (ping.equals("PONG")) {
				return true;
			}

			return false;
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public boolean shutdown(String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return false;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return false;
			}

			redis.shutdown();
			return true;
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}


	/**从sync redis取数据入库,勾兑状态报告 类 qklv 2017-9-15
	 * 
	 * @param key
	 * @param name
	 * @param count 总数
	 * @param pageSize 页大小
	 * @return
	 */
	public static List<byte[]> rpopPipeline(String key, String name, int count, int pageSize) {
		List<byte[]> lrange = new ArrayList<byte[]>();
		if (key == null || name == null) {
			return lrange;
		}

		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
				return lrange;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return lrange;
			}
			Map<String, Response<byte[]>> map = new HashMap<String, Response<byte[]>>();
			int len = 0;
			while (len < count) {
				if (jedis.llen(key) < pageSize) {
					len = count;
				}
				Pipeline pipeline = jedis.pipelined();
				for (Integer i = 0; i < pageSize; i++) {
					map.put(i.toString(), pipeline.rpop(key.getBytes()));
					len++;
				}
				pipeline.sync();
				
				for (Integer i = 0; i < pageSize; i++) {
					byte[] result = map.get(i.toString()).get();
					if (result != null && result.length > 0) {
						lrange.add(result);
					}
				}
				map.clear();
			}
			return lrange;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is={},exception={}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
		return lrange;
	}

	public static List<byte[]> rpopPipeline(String key, String name, int count) {
		List<byte[]> lrange = new ArrayList<byte[]>();
		if (key == null || name == null || count==0) {
			return lrange;
		}

		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
				return lrange;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return lrange;
			}
			Map<String, Response<byte[]>> map = new HashMap<String, Response<byte[]>>();

			Pipeline pipeline = jedis.pipelined();
			for (Integer i = 0; i < count; i++) {
				map.put(i.toString(), pipeline.rpop(key.getBytes()));
			}
			pipeline.sync();

			for (Integer i = 0; i < count; i++) {
				byte[] result = map.get(i.toString()).get();
				if (result != null && result.length > 0) {
					lrange.add(result);
				}
			}

			return lrange;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
		return null;
	}

	public static void hSetpipeLine(String msgext, String ranExt, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return;
			}
			Pipeline line = redis.pipelined();
			line.hset(QueueUtils.LOGIC_MSGEXT_RANEXT.getBytes(), msgext.getBytes(), ranExt.getBytes());
			line.hset(QueueUtils.LOGIC_RANEXT_MSGEXT.getBytes(), ranExt.getBytes(), msgext.getBytes());
			line.sync();
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static void hSetpipeLine(String key, List<String> rptList, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return;
			}
			Pipeline line = redis.pipelined();
			
			for (String rpt : rptList) {
				String[] rptArr = rpt.split(",", -1);

				if (rptArr.length != 9 || null == rptArr) {
					LOGGER.error("[ERR:INSERT_RPT]{},this messageArr is error!!!  length={},message={}.",
							Thread.currentThread().getName(), rptArr.length, rpt);
					continue;
				}
				String channel_id = rptArr[1] == null || rptArr[1].length() <= 0 ? "" : rptArr[1];
				String channel_msg_id = rptArr[2] == null || rptArr[2].length() <= 0 ? "" : rptArr[2];

				String field = channel_id + "_" + channel_msg_id;
				line.hset(key.getBytes(), field.getBytes(), rpt.getBytes());
			}
			line.sync();
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static String set(String key, String value, int expireTime, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return "";
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return "";
			}
			final SetParams params = SetParams.setParams();
			params.nx();
			params.ex(expireTime);
			// params.px(expireTime*1000);
			return redis.set(key, value, params);
		} catch(Exception e){
			LOGGER.error("[REDIS:ERR] exec={}",e);
			e.printStackTrace();
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}

		return "";
	}

	public static long incrBy(String key, long value, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is= {},redis is null", name);
				return -1;
			}

			return redis.incrBy(key, value);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static Set<String> keys(String pattern, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is= {},redis is null", name);
				return null;
			}

			return redis.keys(pattern);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static ScanResult<Entry<String, String>> hscan(String key, String cursor, ScanParams scanParams,
			String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is= {},redis is null", name);
				return null;
			}

			return redis.hscan(key, cursor, scanParams);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static long hdelPipeline(String key, List<String> list, String name) {

		long num = 0;
		if (key == null || name == null || null == list || list.size() < 1) {
			return num;
		}

		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
				return num;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return num;
			}

			Pipeline pipeline = jedis.pipelined();
			List<Response<Long>> result = new ArrayList<>();
			for (String filed : list) {
				Response<Long> hdel = pipeline.hdel(key, filed);
				result.add(hdel);
			}
			pipeline.sync();
			
			for (Response<Long> response : result) {
				num += response.get();
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
		return num;
	}
	
	
	
	public static void dellongMsgPipeline(String key, String field, String name) {

		if (key == null || name == null || null == field) {
			return;
		}

		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
				return;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return;
			}

			Pipeline pipeline = jedis.pipelined();

			
			pipeline.hdel(key,field);
			pipeline.del(field);
			pipeline.sync();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
	}
	

	public static String get(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name={}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is= {},redis is null", name);
				return null;
			}

			return redis.get(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}

	public static void lpushPipeline(String key, List<byte[]> list, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return;
			}
			Pipeline pipeline = redis.pipelined();
			for (byte[] syncByte : list) {
				if (syncByte == null || syncByte.length == 0) {
					continue;
				}
				pipeline.lpush(key.getBytes(), syncByte);
			}
			pipeline.sync();
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static long zSetLen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return 0;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return 0;
			}
			return redis.zcard(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static Set<String> zRangeWithScores(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.zrangeByScore(key, 0, -1);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	public static Set<String> zRevrange(String key,Integer start,Integer end, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.zrevrange(key, start, end);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	public static Double zScore(String key,String member, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return null;
			}
			return redis.zscore(key, member);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	
	public static void pipeline(String[][] objs, String name) {
		if(objs == null || StringUtil.isEmpty(name)) {
			return ;
		}

		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
				return ;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return ;
			}
			
			Pipeline p = jedis.pipelined();
			for(String[] obj :objs) {
				String ot = obj[0];
				String key = obj[1];
				String field = obj[2];
				String value = obj[3];
				switch(ot) {
				case "del":
					p.del(key);
					break;
				case "hdel":
					p.hdel(key, field);
					break;
				case "hset":
					p.hset(key, field, value);
					break;
				case "lpush":
					p.lpush(key, field);
					break;
				case "rpop":
					p.rpop(key);
				default:
					break;
				}
			}
			p.sync();
			

		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
		
	}
	
	
	public static long lLen(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return -1;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return -1;
			}
			return redis.llen(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static Map<String, String> hgetAll(String key, String name) {
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is={},redis is null", name);
				return null;
			}
			return redis.hgetAll(key);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static Set<Entry<byte[], byte[]>> getSetByHscan(String redisKey,String redisName){
		Set<Entry<byte[], byte[]>> set=new HashSet<Entry<byte[], byte[]>>();
		boolean flag=true;
		String cursor="0";
		while(flag){
			ScanResult<Entry<byte[], byte[]>> hscan = hscan(redisKey, cursor, redisName);
			cursor=hscan.getCursor();
			set.addAll(hscan.getResult());
			if("0".equals(cursor)){
				flag=false;
			}
		}
		return set;
	}
	public static List<byte[]> lRange(String redisKey,int start,int end,String redisName){
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(redisName);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", redisName);
			return null;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", redisName);
				return null;
			}
			return redis.lrange(redisKey.getBytes(), start, end);
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
	}
	
	public static long hsetPipeLine(String key, List<String> list, String name) {
		long num=0;
		if (key == null || name == null || null == list || list.size() < 1) {
			return num;
		}
		JJMRedisPool pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
		if (pool == null) {
			LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
			return num;
		}
		Jedis redis = null;
		try {
			redis = pool.getJedis();
			if (redis == null) {
				LOGGER.error("[REDIS:ERR] get pool name is: {},redis is null", name);
				return num;
			}
			Pipeline line = redis.pipelined();
			List<Response<Long>> result = new ArrayList<>();
			for (String field : list) {
				Response<Long> hset = line.hset(key.getBytes(), field.getBytes(), "".getBytes());
				result.add(hset);
			}
			line.sync();
			
			for (Response<Long> response : result) {
				num += response.get();
			}
		} finally {
			if (redis != null && pool != null) {
				pool.returnResource(redis);
			}
		}
		return num;
	}
	
	public static Set<String> hexistsPipeline(String phone, String name) {
		Set<String> levelSet=new HashSet<>();
		JJMRedisPool pool = null;
		Jedis jedis = null;
		try {
			pool = JJMRedisPoolMap.getInstance().getRedisPool(name);
			if (pool == null) {
				LOGGER.error("[REDIS:ERR] get pool is null for redis name:{}", name);
				return null;
			}

			jedis = pool.getJedis();
			int restartCount = 0;// 获取10次
			while (jedis == null && restartCount <= 10) {
				Thread.sleep(1000);
				jedis = pool.getJedis();
				restartCount++;
			}
			if (jedis == null) {
				LOGGER.error("[ERR:REDIS] get pool name is: {},redis retring count:{},redis is null", name,
						restartCount);
				return null;
			}
			Map<String, Response<Boolean>> map = new HashMap<String, Response<Boolean>>();
			Set<String> keys = jedis.keys("logic:summary:level:*");
			
			Pipeline pipeline = jedis.pipelined();
			for (String key : keys) {
				map.put(key, pipeline.hexists(key, phone));
			}
			pipeline.sync();
			
			for (Entry<String, Response<Boolean>> entry : map.entrySet()) {
				if(entry.getValue().get()) {
					levelSet.add(entry.getKey().replaceAll("[^0-9]", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("[REDIS:ERR] get pool name is: {},exception: {}", name, e);
		} finally {
			if (jedis != null && pool != null) {
				pool.returnResource(jedis);
			}
		}
		return levelSet;
		
	}
	
	public static void main(String[] args) {
		String name="logic:summary:level:1";
//		String replaceAll = name.replaceAll("[^0-9]*$", "");
		String replaceAll = name.replaceAll("[^0-9]", "");
		System.out.println(replaceAll);
	}
}
