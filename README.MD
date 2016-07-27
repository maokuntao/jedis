#  jedis学习


> 参考地址：
> 
- [java对redis的基本操作](http://www.cnblogs.com/edisonfeng/p/3571870.html)
- [官方API文档](http://tool.oschina.net/uploads/apidocs/jedis-2.1.0/redis/clients/jedis/Jedis.html)
- [Jedis接口说明](http://my.oschina.net/u/273598/blog/101042/)
- [Redis教程](http://www.yiibai.com/redis/)


## 1. 字符串数据类型（String）接口说明

>
字符串类型是Redis中最为基础的数据存储类型，它在Redis中是二进制安全的，这便意味着该类型可以接受任何格式的数据，如JPEG图像数据或Json对象描述信息等。在Redis中字符串类型的Value最多可以容纳的数据长度是512M。


**设定该Key持有指定的字符串Value，如果该Key已经存在，则覆盖其原有值**

	void set(final String key, final String value)

**获取指定Key的Value，如果该Key不存在，返回null**

	byte[] get(final String key)

**判断该键是否存在，存在返回1，否则返回0**

	boolean exists(final String key)
 
**删除指定的Key**

	long delete(final String... keys)

**重命名指定的Key。如果参数中的两个key的命名相同，或者是源Key不存在，该命令都会返回相关的错误信息。如果newKey已经存在，则直接覆盖**

	void rename(final String oldkey, final String newkey)

**如果新值不存在，则将参数中的原值修改为新值。其它条件和RENAME一致**

	boolean renamenx(final String oldkey, final String newkey)

**设置某个key的过期时间（单位：秒）,在超过该时间后，Key被自动的删除。如果该Key在超时之前被修改，与该键关联的超时将被移除**

	boolean expire(final String key, final int seconds)

**EXPIREAT的作用和EXPIRE类似，都用于为 key 设置生存时间。不同在于 EXPIREAT命令接受的时间参数是 UNIX时间戳(unix timestamp)**

	boolean expireAt(final String key, final long unixTime)

**通过ttl命令查看一下指定Key的剩余存活时间(秒数)，0表示已经过期，-1表示永不过期**	

	long ttl(final String key)

**将当前数据库中的key键移入到ID为dbIndex的数据库中**

	boolean move(final String key, final int dbIndex)
 
**原子性的设置该Key为指定的Value，返回该Key的原有值。如果该Key之前并不存在，则返回null**

	byte[] getSet(final String key, final String value)

**返回一组指定Keys的Values的列表**

	List<byte[]> mget(final String... keys)

**如果指定的Key不存在，则设定该Key持有指定字符串Value，此时其效果等价于SET命令。相反，如果该Key已经存在，该命令将不做任何操作并返回**

	boolean setnx(final String key, final String value)

	boolean setex(final String key, final int seconds, final String value)
 
**该命令原子性的完成参数中所有key/value的设置操作，如果在这一批Keys中有任意一个Key已经存在了，那么该操作将全部回滚，即所有的修改都不会生效**

	boolean msetnx(final String... keysvalues)

**将指定Key的Value原子性的递增1。如果该Key不存在，其初始值为0，在incr之后其值为1,返回递增后的值**

	void incrBy(final String key, final long integer) 

	void incr(final String key)

**将指定Key的Value原子性的递减1。如果该Key不存在，其初始值为-1，在incr之后其值为1,返回递减后的值**

	long decrBy(final String key, final long integer)

	long decr(final String key)

**如果该Key已经存在，APPEND命令将参数Value的数据追加到已存在Value的末尾。如果该Key不存在，APPEND命令将会创建一个新的Key/Value。返回追加后的Value的长度**

	long append(final String key, final String value)


## 2. 字符链表（List）数据类型接口说明

>
在Redis中，List类型是按照插入顺序排序的字符串链表。和数据结构中的普通链表一样，我们可以在其头部 (left)和尾部(right)添加新的元素。在插入时，如果该键并不存在，Redis将为该键创建一个新的链表。与此相反，如果链表中所有的元素均被移除，那么该键也将会从数据库中删除。List中可以包含的最大元素数量是4,294,967,295(42亿左右)。


**在指定Key所关联的List Value的尾部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的尾部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息**

	void rpush(final String key, final String... string)

**在指定Key所关联的List Value的头部插入参数中给出的所有Values。如果该Key不存在，该命令将在插入之前创建一个与该Key关联的空链表，之后再将数据从链表的头部插入。如果该键的Value不是链表类型，该命令将返回相关的错误信息**

	void lpush(final String key, final String... string)

**返回指定Key关联的链表中元素的数量。如果该Key不存在，则返回0。如果与该Key关联的Value的类型不是链表，则返回相关的错误信息**

	long llen(final String key)

**返回指定范围内元素的列表。该命令的参数start和end都是0-based。即0表示链表头部(leftmost)的第一个元素。其中start的值也可以为负值，-1将表示链表中的最后一个元素，即尾部元素，-2表示倒数第二个并以此类推。该命令在获取元素时，start和end位置上的元素也会被取出。如果start的值大于链表中元素的数量，空链表将会被返回。如果end的值大于元素的数量，该命令则获取从start(包括start)开始，链表中剩余的所有元素**

	List<byte[]> lrange(final String key, final long start, final long end)

**该命令将仅保留指定范围内的元素，从而保证链接中的元素数量相对恒定。start和stop参数都是0-based，0表示头部元素。和其他命令一样，start和stop也可以为负值，-1表示尾部元素。如果start大于链表的尾部，或start大于stop，该命令不错报错，而是返回一个空的链表，与此同时该Key也将被删除。如果stop大于元素的数量，则保留从start开始剩余的所有元素**

	void ltrim(final String key, final long start, final long end)

**该命令将返回链表中指定位置(index)的元素。index是0-based，表示头部元素，如果index为-1，表示尾部元素。如果与该Key关联的不是链表，该命令将返回相关的错误信息**

	byte[] lindex(final String key, final long index)

**设定链表中指定位置的值为新值。其中0表示第一个元素，即头部元素，-1表示尾部元素。如果索引值Index超出了链表中元素的数量范围，该命令将返回相关的错误信息**

	void lset(final String key, final long index, final String value)

**在指定Key关联的链表中，删除前count个值等于value的元素。如果count大于0，从头向尾遍历并删除，如果count小于0，则从尾向头遍历并删除。如果count等于0，则删除链表中所有等于value的元素。如果指定的Key不存在，则直接返回0,返回被删除的元素数量**

	long lrem(final String key, long count, final String value)

**返回并弹出指定Key关联的链表中的第一个元素，即头部元素。如果该Key不存，返回null**

	byte[] lpop(final String key)

**返回并弹出指定Key关联的链表中的最后一个元素，即尾部元素。如果该Key不存，返回null**

	byte[]rpop(final String key)

**原子性的从与srckey键关联的链表尾部弹出一个元素，同时再将弹出的元素插入到与dstkey键关联的链表的头部。如果srckey键不存在，该命令将返回null，同时不再做任何其它的操作了。如果srckey和dstkey是同一个键，则相当于原子性的将其关联链表中的尾部元素移到该链表的头部**

	byte[] rpoplpush(final String srckey, final String dstkey)

## 3. Hashes数据类型接口说明

>
我们可以将Redis中的Hashes类型看成具有String Key和String Value的map容器。所以该类型非常适合于存储值对象的信息。如Username、Password和Age等。如果Hash中包含很少的字段，那么 该类型的数据也将仅占用很少的磁盘空间。每一个Hash可以存储4,294,967,295个键值对。


**为指定的Key设定Field/Value对。如果Key不存在，该命令将创建新Key以参数中的Field/Value对，如果参数中的Field在该Key中已经存在，则用新值覆盖其原有值**

	boolean hset(final String key, final String field, final String value)

**返回参数中Field的关联值，如果参数中的Key或Field不存，返回null**

	void hget(final String key, final String field)

**只有当参数中的Key或Field不存在的情况下，为指定的Key设定Field/Value对，否则该命令不会进行任何操作**

	void hsetnx(final String key, final String field, final String value)

**逐对依次设置参数中给出的Field/Value对。如果其中某个Field已经存在，则用新值覆盖原有值。如果Key不存在，则创建新Key，同时设定参数中的Field/Value**

	void hmset(final String key, final Map<String, String> hash)

**获取和参数中指定Fields关联的一组Values。如果请求的Field不存在，其值返回null。如果Key不存在，该命令将其视为空Hash，因此返回一组null**

	byte[] hmget(final String key, final String... fields)

**增加指定Key中指定Field关联的Value的值。如果Key或Field不存在，该命令将会创建一个新Key或新Field，并将其关联的Value初始化为0，之后再指定数字增加的操作。返回运算后的值**

	long hincrBy(final String key, final String field, final long value)

**判断指定Key中的指定Field是否存在**

	void hexists(final String key, final String field)

**从指定Key的Hashes Value中删除参数中指定的多个字段。如果不存在的字段将被忽略。如果Key不存在，则将其视为空Hashes，并返回0.返回实际删除的Field数量**

	void hdel(final String key, final String... fields)

**获取该Key所包含的Field的数量**

	void hlen(finalString key)

**返回指定Key的所有Fields名**

	List<byte[]> hkeys(final String key)

**返回指定Key的所有Values名**

	List<byte[]> hvals(final String key)
 
**获取该键包含的所有Field/Value。其返回格式为一个Field、一个Value，并以此类推**

	Map<byte[],byte[]>  hgetAll(final String key)

## 4. 字符集合（Set）数据类型接口说明

>
在Redis中，我们可以将Set类型看作为没有排序的字符集合，和List类型一样，我们也可以在该类型的数据值上执行添加、删除或判断某一元素是否存在等操作。需要说明的是，这些操作的时间复杂度为O(1)，即常量时间内完成次操作。Set可包含的最大元素数量是 4,294,967,295。

>
和List类型不同的是，Set集合中不允许出现重复的元素，这一点和C++标准库中的set容器是完全相同的。换句话说，如果多次添加相同元素，Set中将仅保留该元素的一份拷贝。和List类型相比，Set类型在功能上还存在着一个非常重要的特性，即在服务器端完成多个Sets之间的聚合计算操作，如unions、intersections和differences。由于这些操作均在服务端完成，因此效率极高，而且 也节省了大量的网络IO开销。

**如果在插入的过程用，参数中有的成员在Set中已经存在，该成员将被忽略，而其它成员仍将会被正常插入。如果执行该命令之前，该Key并不存在，该命令将会创建一个新的Set，此后再将参数中的成员陆续插入。如果该Key的Value不是Set类型，该命令将返回相关的错误信息**

	boolean sadd(final String key, final String... members)

**获取与该Key关联的Set中所有的成员**

	List<byte[]> smembers(final String key)

**从与Key关联的Set中删除参数中指定的成员，不存在的参数成员将被忽略，如果该Key并不存在，将视为空Set处理**

	void srem(final String key, final String... members)

**随机的移除并返回Set中的某一成员。由于Set中元素的布局不受外部控制，因此无法像List那样确定哪个元素位于Set的头部或者尾部**

	byte[] spop(final String key)

**原子性的将参数中的成员从srckey键移入到dstkey键所关联的Set中。因此在某一时刻，该成员或者出现在srckey中，或者出现在dstkey中。如果该成员member在srckey中并不存在，该命令将不会再执行任何操作并返回0，否则，该成员将从srckey移入到dstkey。如果此时该成员已经在dstkey中存在，那么该命令仅是将该成员从srckey中移出。如果和Key关联的Value不是Set，将返回相关的错误信息**

	boolean smove(final String srckey, final String dstkey,final String member)

**获取Set中成员的数量**

	long scard(final String key)

**判断参数中指定成员是否已经存在于与Key相关联的Set集合中**

	boolean sismember(final String key, final String member)

**该命令将返回参数中所有Keys关联的Sets中成员的交集。因此如果参数中任何一个Key关联的Set为空，或某一Key不存在，那么该命令的结果将为空集**

	List<byte[]> sinter(final String... keys)

**该命令和sinter命令在功能上完全相同，两者之间唯一的差别是sinter返回交集的结果成员，而该命令将交集成员存储在dstkey关联的Set中。如果dstkey键已经存在，该操作将覆盖它的成员**

	void sinterstore(final String dstkey, final String... keys)

**该命令将返回参数中所有Keys关联的Sets中成员的并集**

	List<byte[]> sunion(final String... keys)

**该命令和sunion命令在功能上完全相同，两者之间唯一的差别是sunion返回并集的结果成员，而该命令将并集成员存储在dstkey关联的Set中。如果dstkey键已经存在，该操作将覆盖它的成员**

	void sunionstore(final String dstkey, final String... keys)

**返回参数中第一个Key所关联的Set和其后所有Keys所关联的Sets中成员的差异。如果Key不存在，则视为空Set**

	List<byte[]> sdiff(final String... keys)

**该命令和SDIFF命令在功能上完全相同，两者之间唯一的差别是SDIFF返回差异的结果成员，而该命令将差异成员存储在dstkey关联的Set中。如果dstkey键已经存在，该操作将覆盖它的成员**

	void sdiffstore(final String dstkey, final String... keys)

**和SPOP一样，随机的返回Set中的一个成员，不同的是该命令并不会删除返回的成员**

	void srandmember(final String key)


## 5. 有序集合（Sorted-Sets）数据类型接口说明

>
Sorted-Sets和Sets类型极为相似，它们都是字符串的集合，都不允许重复的成员出现在一个Set中。它们之间的主要差别是Sorted-Sets中的每一个成员都会有一个分数(score)与之关联，Redis正是通过分数来为集合中的成员进行从小到大的排序。然而需要额外指出的是，尽管Sorted-Sets中的成员必须是唯一的，但是分数(score)却是可以重复的。

>
在Sorted-Set中添加、删除或更新一个成员都是非常快速的操作，其时间复杂度为集合中成员数量的对数。由于Sorted-Sets中的成员在集合中的位置是有序的，因此，即便是访问位于集合中部的成员也仍然是非常高效的。事实上，Redis所具有的这一特征在很多其它类型的数据库中是很难实现的，换句话说，在该点上要想达到和Redis同样的高效，在其它数据库中进行建模是非常困难的。

**在该命令中我们可以指定多组score/member作为参数。如果在添加时参数中的某一成员已经存在，该命令将更新此成员的分数为新值，同时再将该成员基于新值重新排序。如果键不存在，该命令将为该键创建一个新的Sorted-Sets Value，并将score/member对插入其中。如果该键已经存在，但是与其关联的Value不是Sorted-Sets类型，相关的错误信息将被返回**

	boolean zadd(final String key, final double score, final String member)

**该命令按顺序返回在参数start和stop指定范围内的成员。这里start和stop参数都是0-based，即0表示第一个成员，-1表示最后一个成员。如果start大于该Sorted-Set中的最大索引值，或start > stop，此时一个空集合将被返回。如果stop大于最大索引值，该命令将返回从start到集合的最后一个成员。如果命令中带有可选参数WITHSCORES选项，该命令在返回的结果中将包含每个成员的分数值，如value1,score1,value2,score2...**

	List<byte[]> zrange(final String key, final long start, final long end)

**该命令将移除参数中指定的成员，其中不存在的成员将被忽略。如果与该Key关联的Value不是Sorted-Set，相应的错误信息将被返回**

	boolean zrem(final String key, final String... members)

**该命令将为指定Key中的指定成员增加指定的分数。如果成员不存在，该命令将添加该成员并假设其初始分数为0，此后再将其分数加上increment。如果Key不存，该命令将创建该Key及其关联的Sorted-Sets，并包含参数指定的成员，其分数为increment参数。如果与该Key关联的不是Sorted-Sets类型，相关的错误信息将被返回**

	Double zincrby(final String key, final double score,final String member)

** Sorted-Set中的成员都是按照分数从低到高的顺序存储，该命令将返回参数中指定成员的位置值。其中0表示第一个成员，它是Sorted-Set中分数最低的成员**

	long zrank(final String key, final String member)

**该命令的功能和ZRANK基本相同，唯一的差别在于该命令获取的索引是从高到低排序后的位置，同样0表示第一个元素，即分数最高的成员**

	long zrevrank(final String key, final String member)

**该命令的功能和ZRANGE基本相同，唯一的差别在于该命令是通过反向排序获取指定位置的成员，即从高到低的顺序。如果成员具有相同的分数，则按降序字典顺序排序**

	List<byte[]> zrevrange(final String key, final long start, final long end)

**该命令将返回分数在min和max之间的所有成员。即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回**

	List<byte[]> zrangeWithScores(final String key, final long start,final long end)

**该命令除了排序方式是基于从高到低的分数排序之外，其它功能和参数含义均与ZRANGEBYSCORE相同**

	List<byte[]> zrevrangeWithScores(final String key,final long start,final long end)

**获取与该Key相关联的Sorted-Sets中包含的成员数量**

	long zcard(final String key)

**获取指定Key的指定成员的分数**

	Double zscore(final String key, final String member)

**获取指定Key的指定成员的分数**

	void watch(final String... keys)

**给指定的Key的成员排序**

	Sort sort(final String key)

	Sort sort(final String key, final SortingParams sortingParameters,

            final String dstkey)

	Sort sort(final String key, final String dstkey)

**用于删除和获取列表中的第一个元素，或阻塞直到有可用。 BLPOP命令只返回第一个元素(如果有的话)，或阻塞客户端对指定的时间执行任意命令**

	void blpop(final String[] args)

**该命令用于获取分数(score)在min和max之间的成员数量**

	long zcount(final String key, final double min, final double max)

**该命令将返回分数在min和max之间的所有成员。即满足表达式min <= score <= max的成员，其中返回的成员是按照其分数从低到高的顺序返回，如果成员具有相同的分数，则按成员的字典顺序返回**

	List<byte[]> zrangeByScore(final String key, final double min,final double max)

	List<byte[]> zrangeByScore(final String key, finalString min,final String max)

	List<byte[]> zrangeByScore(final String key, finaldouble min,final double max, final int offset, int count)

	List<byte[]> zrangeByScoreWithScores(final Stringkey, final double min,final double max)

	List<byte[]> zrangeByScoreWithScores(final Stringkey, final double min, final double max, final int offset, final int count)

	void zremrangeByScore(final String key, final double start,final double end)

	void zremrangeByScore(final String key, final String start,final String end)
	
**通过key删除给定区间内的元素**
	
	void zremrangeByRank(final String key, final long start,final long end)

**计算numkeys排序按指定键给出集合的交集，并将结果存储在目的地。它是强制性的传递的输入键和其他(任选)参数之前，以提供输入键(numkeys)的数量**

	void zunionstore(final String dstkey, final String... sets)

	void zunionstore(final String dstkey, final ZParams params,final String... sets)

	void zinterstore(final String dstkey, final String... sets)

	void zinterstore(final String dstkey, final ZParams params,final String... sets)