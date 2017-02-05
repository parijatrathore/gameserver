package com.junglee.assignment.cache.redis;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.junglee.assignment.utils.JSONUtils;
import com.junglee.assignment.utils.Properties;

import redis.clients.jedis.Jedis;

public final class JungleeRedis {

	private static final JungleeRedis	_instance	= new JungleeRedis();

	private static Logger				logger		= LoggerFactory.getLogger(JungleeRedis.class);

	private Jedis						jedis		= null;

	private JungleeRedis() {
		this.jedis = new Jedis(Properties.REDIS_HOST);
	}

	public static JungleeRedis getInstance() {
		return _instance;
	}

	public void setString(String key, String value) {
		jedis.set(key, value);
	}

	public void cacheObject(String key, Object object) throws IOException {
		jedis.set(Serializer.serialize(key), Serializer.serialize(object));
	}
	
	public void cacheObject(int key, Object object) throws IOException {
		cacheObject(String.valueOf(key), object);
	}
	
	public Object get(String key){
		return jedis.get(key);
	}
	
	public Object get(int key){
		return get(String.valueOf(key));
	}

	public Object remove(String key) throws ClassNotFoundException, IOException {
		Object obj = Serializer.deserialize(jedis.get(Serializer.serialize(key)));
		jedis.del(key);
		return obj;
	}

	public void setList(String key, List<?> values) {
		logger.info("List " + values.size() + " values");
		for (Iterator iterator = values.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			jedis.lpush(key, JSONUtils.objectToString(object));
		}
	}
}
