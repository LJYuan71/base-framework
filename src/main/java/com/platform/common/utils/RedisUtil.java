package com.platform.common.utils;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 * 
 * @author AK
 * 
 */
public final class RedisUtil {
	private static String path = AppUtil.getClasspath()+ "properties/app.properties".replace("/", File.separator);
    // Redis服务器IP
	private static String HOST=FileUtil.readFromProperties(path, "redis.host.ip");
    // Redis的端口号
    private static int PORT = Integer.parseInt(FileUtil.readFromProperties(path, "redis.host.port"));
    // 访问密码
    private static String AUTH = FileUtil.readFromProperties(path, "redis.host.password");;
    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = Integer.parseInt(FileUtil.readFromProperties(path, "redis.host.maxActive"));
    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = Integer.parseInt(FileUtil.readFromProperties(path, "redis.host.maxIdle"));
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static long MAX_WAIT = Long.parseLong(FileUtil.readFromProperties(path, "redis.host.maxWait"));
    // 最大延迟时间
    private static int TIMEOUT = Integer.parseInt(FileUtil.readFromProperties(path, "redis.host.timeout"));
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = Boolean.parseBoolean(FileUtil.readFromProperties(path, "redis.host.onBorrow"));
 
    private static JedisPool jedisPool = null;
 
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            if(StringUtils.isEmpty(AUTH)){
            	jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
            }else {
            	jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /**
     * 获取Jedis实例
     * 
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                resource.select(1);//使用Redis db1来存储
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }
    /**
     * 清空所有Redis数据
     */
    public static void clearDB(final Jedis jedis) {
        if (jedis != null) {
            jedis.flushDB();
        }
    }
}
/**
 * 常用命令
    1）连接操作命令
    quit：关闭连接（connection）
    auth：简单密码认证
    help cmd： 查看cmd帮助，例如：help quit
    
    2）持久化
    save：将数据同步保存到磁盘
    bgsave：将数据异步保存到磁盘
    lastsave：返回上次成功将数据保存到磁盘的Unix时戳
    shundown：将数据同步保存到磁盘，然后关闭服务
    
    3）远程服务控制
    info：提供服务器的信息和统计
    monitor：实时转储收到的请求
    slaveof：改变复制策略设置
    config：在运行时配置Redis服务器
    
    4）对value操作的命令
    exists(key)：确认一个key是否存在
    del(key)：删除一个key
    type(key)：返回值的类型
    keys(pattern)：返回满足给定pattern的所有key
    randomkey：随机返回key空间的一个
    keyrename(oldname, newname)：重命名key
    dbsize：返回当前数据库中key的数目
    expire：设定一个key的活动时间（s）
    ttl：获得一个key的活动时间
    select(index)：按索引查询
    move(key, dbindex)：移动当前数据库中的key到dbindex数据库
    flushdb：删除当前选择数据库中的所有key
    flushall：删除所有数据库中的所有key
    
    5）String
    set(key, value)：给数据库中名称为key的string赋予值value
    get(key)：返回数据库中名称为key的string的value
    getset(key, value)：给名称为key的string赋予上一次的value
    mget(key1, key2,…, key N)：返回库中多个string的value
    setnx(key, value)：添加string，名称为key，值为value
    setex(key, time, value)：向库中添加string，设定过期时间time
    mset(key N, value N)：批量设置多个string的值
    msetnx(key N, value N)：如果所有名称为key i的string都不存在
    incr(key)：名称为key的string增1操作
    incrby(key, integer)：名称为key的string增加integer
    decr(key)：名称为key的string减1操作
    decrby(key, integer)：名称为key的string减少integer
    append(key, value)：名称为key的string的值附加value
    substr(key, start, end)：返回名称为key的string的value的子串
    
    6）List 
    rpush(key, value)：在名称为key的list尾添加一个值为value的元素
    lpush(key, value)：在名称为key的list头添加一个值为value的 元素
    llen(key)：返回名称为key的list的长度
    lrange(key, start, end)：返回名称为key的list中start至end之间的元素
    ltrim(key, start, end)：截取名称为key的list
    lindex(key, index)：返回名称为key的list中index位置的元素
    lset(key, index, value)：给名称为key的list中index位置的元素赋值
    lrem(key, count, value)：删除count个key的list中值为value的元素
    lpop(key)：返回并删除名称为key的list中的首元素
    rpop(key)：返回并删除名称为key的list中的尾元素
    blpop(key1, key2,… key N, timeout)：lpop命令的block版本。
    brpop(key1, key2,… key N, timeout)：rpop的block版本。
    rpoplpush(srckey, dstkey)：返回并删除名称为srckey的list的尾元素，

　　　　　　　　　　　　　　并将该元素添加到名称为dstkey的list的头部
    
    7）Set
    sadd(key, member)：向名称为key的set中添加元素member
    srem(key, member) ：删除名称为key的set中的元素member
    spop(key) ：随机返回并删除名称为key的set中一个元素
    smove(srckey, dstkey, member) ：移到集合元素
    scard(key) ：返回名称为key的set的基数
    sismember(key, member) ：member是否是名称为key的set的元素
    sinter(key1, key2,…key N) ：求交集
    sinterstore(dstkey, (keys)) ：求交集并将交集保存到dstkey的集合
    sunion(key1, (keys)) ：求并集
    sunionstore(dstkey, (keys)) ：求并集并将并集保存到dstkey的集合
    sdiff(key1, (keys)) ：求差集
    sdiffstore(dstkey, (keys)) ：求差集并将差集保存到dstkey的集合
    smembers(key) ：返回名称为key的set的所有元素
    srandmember(key) ：随机返回名称为key的set的一个元素
    
    8）Hash
    hset(key, field, value)：向名称为key的hash中添加元素field
    hget(key, field)：返回名称为key的hash中field对应的value
    hmget(key, (fields))：返回名称为key的hash中field i对应的value
    hmset(key, (fields))：向名称为key的hash中添加元素field 
    hincrby(key, field, integer)：将名称为key的hash中field的value增加integer
    hexists(key, field)：名称为key的hash中是否存在键为field的域
    hdel(key, field)：删除名称为key的hash中键为field的域
    hlen(key)：返回名称为key的hash中元素个数
    hkeys(key)：返回名称为key的hash中所有键
    hvals(key)：返回名称为key的hash中所有键对应的value
    hgetall(key)：返回名称为key的hash中所有的键（field）及其对应的value
*/