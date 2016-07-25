package com.taomk.util.jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * Jedis测试类
 * 
 * @author taomk
 *
 */
public class TestRedis {

	private Jedis jedis;

	@Before
	public void setup() {

		// 连接redis服务器，127.0.0.1:6379
		jedis = new Jedis("127.0.0.1", 6379);

		// 权限认证
		// jedis.auth("admin");

	}

	/**
	 * redis操作字符串
	 */
	@Test
	public void testString() {

		// 添加数据
		jedis.set("name", "taomk");
		// 获取数据
		System.out.println(jedis.get("name"));

		// 拼接数据
		jedis.append("name", " is me. ");
		System.out.println(jedis.get("name"));

		// 删除某个键对应的数据
		jedis.del("name");
		System.out.println(jedis.get("name"));
		// 设置多个键值对
		jedis.mset("name", "ttt", "age", "23", "address", "beijing");
		// 进行加1操作
		jedis.incr("age");
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-" + jedis.get("address"));
	}

	/**
	 * redis操作Map
	 */
	@Test
	public void testMap() {

		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("name", "taomk");
		valueMap.put("age", "18");
		valueMap.put("address", "beijing");

		// 添加数据
		jedis.hmset("userinfo", valueMap);

		// 获取数据
		// 第一个参数是存入redis时的key ， 后面的参数是Map中的key
		List<String> valueList = jedis.hmget("userinfo", "name", "age", "address");
		System.out.println(valueList);

		// 删除某个键值
		jedis.hdel("userinfo", "address");
		System.out.println(jedis.hget("userinfo", "address"));

		// 查看key为userinfo的值中存放的值的个数
		System.out.println(jedis.hlen("userinfo"));

		// 查看redis中是否存在key为userinfo的记录
		System.out.println(jedis.exists("userinfo"));

		// 返回map对象中所有的key
		System.out.println(jedis.hkeys("userinfo"));

		// 返回map对象中所有的value
		System.out.println(jedis.hvals("userinfo"));

		Iterator<String> iter = jedis.hkeys("uderinfo").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + " : " + jedis.hmget("userinfo", key));
		}
	}

	/**
	 * jedis操作List
	 */
	@Test
	public void testList() {

		String globalKey = "Java Framework";

		// 首先清空数据
		jedis.del(globalKey);
		// 获取数据
		// 第一个参数是key，第二个参数是起始位置，第三个参数是结束位置（可以通过jedis.llen来获取长度，－1表示取得所有）
		System.out.println(jedis.lrange(globalKey, 0, -1));

		// 存放数据
		jedis.lpush(globalKey, "Spring");
		jedis.lpush(globalKey, "Structs");
		jedis.lpush(globalKey, "Hibernate");

		// 获取数据
		System.out.println(jedis.lrange(globalKey, 0, -1));

	}

	/**
	 * jedis操作Set
	 */
	@Test
	public void testSet() {

		// 添加数据

		System.out.println(jedis.sadd("username", "maokun"));
		System.out.println(jedis.sadd("username", "zhangzhzh"));
		System.out.println(jedis.sadd("userusernameinfo", "lkp"));

		// 移除某个数据
		System.out.println(jedis.srem("username", "lkp"));

		// 获取所有加入的value
		System.out.println(jedis.smembers("username"));

		// 判断"lkp"是否是userinfo集合中的元素
		System.out.println(jedis.sismember("username", "lkp"));

		// 随机返回Set中的一个成员
		System.out.println(jedis.srandmember("username"));

		// 返回集合的元素个数
		System.out.println(jedis.scard("username"));
	}

	/**
	 * Jedis操作有序Set
	 */
	@Test
	public void tesSortedSet() {
		// 先清空数据
		jedis.del("sortedSet");

		// 在指定key所关联的List value的尾部插入参数中给出的所有的values
		// 如果该key不存在，该方法将在插入之前创建一个与该key关联的空链表，之后再将数据从链表的尾部插入；
		// 如果该键的values不是链表类型，该方法将返回相关的错误信息
		jedis.lpush("sortedSet", "1");
		jedis.lpush("sortedSet", "8");
		jedis.lpush("sortedSet", "2");

		// 在指定key所关联的List value的头部插入参数中给出的所有values
		// 如果该key不存在，该方法将在插入之前创建一个与该key关联的空链表，之后再将数据从链表的头部插入；
		// 如果该键的values不是链表类型，该方法将返回相关的错误信息
		jedis.rpush("sortedSet", "0");
		jedis.rpush("sortedSet", "3");
		jedis.rpush("sortedSet", "5");

		// 返回指定范围类元素的列表。
		// 该方法的参数start和end都是0-based，即0表示链表头部（leftmost）的第一个元素。
		// 其中start的值也可以为负值，－1将表示链表中的最后一个元素，即尾部元素；－2表示倒数第二个并以此类推。
		// 在该方法获取元素是，start和end位置上的元素也会被取出。
		// 如果start值大于链表中元素的数量，将返回一个空链表。
		// 如果end值大于元素的数量，该方法将返回从start（包含start）开始，链表中剩余的所有元素。
		System.out.println(jedis.lrange("sortedSet", 0, -1));

		// 排序
		System.out.println(jedis.sort("sortedSet"));

		// 经过排序之后，不会该表redis中Set元素的顺序
		System.out.println(jedis.lrange("sortedSet", 0, -1));
	}

}
