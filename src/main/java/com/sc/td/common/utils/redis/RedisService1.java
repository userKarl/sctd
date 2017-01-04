package com.sc.td.common.utils.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

/**
 * 封装redis 缓存服务器服务接口
 * 
 */
@Service
public class RedisService1 {
	// 操作redis客户端
	private static Jedis jedis;
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;

	/**
	 * 通过key删除（字节）
	 * 
	 * @param key
	 */
	public void del(byte[] key) {
		this.getJedis().del(key);
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public void del(String key) {
		this.getJedis().del(key);
	}

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(byte[] key, byte[] value, int liveTime) {
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(String key, String value, int liveTime) {
		this.set(key, value);
		this.getJedis().expire(key, liveTime);
	}

	/**
	 * 添加key value
	 * 
	 * @param key
	 * @param value
	 */
	public void set(String key, String value) {
		this.getJedis().set(key, value);
	}

	/**
	 * 添加key value (字节)(序列化)
	 * 
	 * @param key
	 * @param value
	 */
	public void set(byte[] key, byte[] value) {
		this.getJedis().set(key, value);
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key) {
		String value = this.getJedis().get(key);
		return value;
	}

	/**
	 * 倒序获取redis sorted Set
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> zrevrange(String key) {
		Set<String> set = this.getJedis().zrevrange(key, 0, -1);
		return set;
	}

	/**
	 * 正序获取redis sorted Set
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> zrange(String key) {
		Set<String> set = this.getJedis().zrange(key, 0, -1);
		return set;
	}

	/**
	 * 获取redis value (byte [] )(反序列化)
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		return this.getJedis().get(key);
	}

	/**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> keys(String pattern) {
		return this.getJedis().keys(pattern);
	}

	/**
	 * 检查key是否已经存在
	 * 
	 * @param key
	 * @return
	 */
	public boolean exists(String key) {
		return this.getJedis().exists(key);
	}

	/**
	 * 得到value长度
	 * 
	 * @param key
	 * @return
	 */
	public Long getlen(String key) {
		return this.getJedis().llen(key);
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return
	 */
	public String flushDB() {
		return this.getJedis().flushDB();
	}

	/**
	 * 查看redis里有多少数据
	 */
	public long dbSize() {
		return this.getJedis().dbSize();
	}

	/**
	 * 检查是否连接成功
	 * 
	 * @return
	 */
	public String ping() {
		return this.getJedis().ping();
	}

	/**
	 * type
	 * 
	 * @return
	 */
	public String type(String key) {
		return this.getJedis().type(key);
	}

	/**
	 * smembers
	 * 
	 * @return
	 */
	public Set<String> smembers(String key) {
		return this.getJedis().smembers(key);
	}

	/**
	 * hmset
	 * 
	 * @return
	 */
	public String hmset(String key, Map<String, String> map) {
		return this.getJedis().hmset(key, map);
	}

	public long hlen(String key) {
		return this.getJedis().hlen(key);
	}

	public Set<String> hkeys(String key) {
		return this.getJedis().hkeys(key);
	}

	public List<String> hvals(String key) {
		return this.getJedis().hvals(key);
	}

	public List<String> hmget(String key, String... fields) {
		return this.getJedis().hmget(key, fields);
	}

	public Long hdel(String key, String... fields) {
		return this.getJedis().hdel(key, fields);
	}

	/**
	 * 获取一个jedis 客户端
	 * 
	 * @return
	 */
	private synchronized Jedis getJedis() {
		JedisConnection jedisConnection = jedisConnectionFactory.getConnection();
		try {
			if (jedis == null) {
	            jedis = jedisConnection.getNativeConnection();
				return jedis;
//						.getShardInfo().createResource();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			jedisConnection.close();
			jedisConnectionFactory.destroy();
		}
		
		return jedis;
	}
	
//	private synchronized Jedis getJedis() {
//		if (jedis == null) {
//			return jedisConnectionFactory.getShardInfo().createResource();
//		}
//		return jedis;
//	}

	public void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		this.jedisConnectionFactory = jedisConnectionFactory;
	}

}
