package com.sc.td.common.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {

	private static final Logger log = LoggerFactory.getLogger(RedisDataSourceImpl.class);
	
	@Autowired
    private JedisPool  jedisPool;
	
	@Override
	public Jedis getRedisClient() {
		try {
            Jedis jedis = jedisPool.getResource();
            return jedis;
        } catch (Exception e) {
        	 log.error("getRedisClent error", e);	
        }
        return null;
	}

	@Override
	public void returnResource(Jedis Jedis) {
		jedisPool.returnResource(Jedis);
	}

	@Override
	public void returnResource(Jedis Jedis, boolean broken) {
		if (broken) {
			jedisPool.returnBrokenResource(Jedis);
        } else {
        	jedisPool.returnResource(Jedis);
        }
	}

}
