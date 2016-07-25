package com.taomk.util.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 操作Jedis连接池
 * @author taomk
 * 2016年7月25日 下午3:56:25
 */
public class TestRedisPool {

//	redis服务器地址
	private static final String REDIS_SERVER_ADDRESS = "127.0.0.1";
	
//	redis服务器端口号
	private static final int REDIS_SERVER_PORT = 6379;
	
//	连接redis服务器时的最大超时时间
	private static final int TIMEOUT = 10*1000;
	
//	可用的最大连接实例，默认值为8
//	如果赋值为－1，则表示不限制；
//	如果pool已经分配了maxActive个redis实例，则测试pool的状态为exhausted（耗尽）
	private static final int MAX_ACTIVE = 1024;
	
//	控制一个pool最多有多少个状态为idle（空闲）的jedis实例，默认值为8
	private static final int MAX_IDLE = 200;
	
//	等待可用连接的最大时间。单位是毫秒，默认值是－1，表示永不超时。
//	如果超时，抛出JedisConnectionEaception
	private static final long MAX_WAIT = 10*1000L;
	
//	在borrow一个jedis实例时，是否提前进行validate操作；
//	如果为true，则得到的jedis实例都是可用的
	private static final boolean VALIDATE_BEFORE_BORROW = true;
	
//	全局JedisPool实例
	private static JedisPool jedisPool;
	
//	初始化JedisPool实例
	static{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(VALIDATE_BEFORE_BORROW);
			
			jedisPool = new JedisPool(config , REDIS_SERVER_ADDRESS , REDIS_SERVER_PORT , TIMEOUT);
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("连接redis时出错。检查redis服务是否可用！");
		}
	}
	
	 /**
	  * 从jedis池中获取jedis实例
	 * @return jedis实例
	 */
	public synchronized static Jedis getJedis(){
		 try {
			if(jedisPool!=null){
				Jedis resources = jedisPool.getResource();
				return resources;
			}else{
				return null;
			}
		} catch (Exception e) {
			System.err.println(e);
			System.err.println("从jedis池中获取jedis实例时出错。");
			return null;
		}
	 }
	
	/**
	 * 释放jedis资源
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void releaseResource(final Jedis jedis){
		if(jedis!=null && jedisPool!=null){
			jedisPool.returnResource(jedis);
		}
	}
	
	public static void main(String[] args) {
		
		for (int i = 0; i < 50; i++) {
			getJedis().set("newname"+i, "中文测试" + i);
		}
		
		System.out.println(jedisPool.getNumActive());
		
		System.out.println(jedisPool.getNumWaiters());
		
		System.out.println(jedisPool.getNumIdle());
		for (int i = 0; i < 50; i++) {
			System.out.println(getJedis().get("newname"+i));
		}
	}
	
	
}