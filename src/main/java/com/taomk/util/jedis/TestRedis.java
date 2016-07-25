/**
 * 
 */
package com.taomk.util.jedis;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author taomk
 *
 */
public class TestRedis {

	private Jedis jedis;
	
	@Before
	public void setup() {

		// 连接redis服务器，127.0.0.1:6379
		jedis = new Jedis("127.0.0.1" , 6379);
		
		// 权限认证
//		jedis.auth("admin");
		
	}
	
	/**
	 * redis存贮字符串
	 */
	@Test
	public void testString(){
		
		jedis.set("name", "taomk");
		System.out.println(jedis.get("name"));
		
		jedis.append("name", " is me. ");
		System.out.println(jedis.get("name"));
		
		jedis.del("name");
		System.out.println(jedis.get("name"));
		
		jedis.mset("name" , "ttt" , "age" , "23" , "address" , "beijing");
		jedis.incr("1");
		System.out.println(jedis.get("name") + "-" + jedis.get("age")  + "-"+ jedis.get("address"));
	}
}
