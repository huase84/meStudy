package com.meStudy.core.toolkit;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @ClassName: RedisUtils
 * @Author wultn
 * @Date 2020/2/10 16:50
 */
public class RedisUtils {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle = 200;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait = 10000;

    private JedisPool jedisPool = null;

    private static RedisUtils redisUtils;

    /** 初始化方法 */
    @PostConstruct
    public void init() {
        RedisUtils.redisUtils = this;
        this.jedisPool = this.getJedisPool();
    }

    /**
     * 初始连接池
     */
    private synchronized JedisPool getJedisPool() {
        if (this.jedisPool == null) {
            try {
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(maxActive);
                config.setMaxIdle(maxIdle);
                config.setMaxWaitMillis(maxWait);
                config.setMinIdle(8); // 设置最小空闲数
                boolean testOnBorrow = true;
                config.setTestOnBorrow(testOnBorrow);
                config.setTestOnReturn(true);
                // Idle时进行连接扫描
                config.setTestWhileIdle(true);
                // 表示idle object evitor两次扫描之间要sleep的毫秒数
                config.setTimeBetweenEvictionRunsMillis(30000);
                // 表示idle object evitor每次扫描的最多的对象数
                config.setNumTestsPerEvictionRun(10);
                // 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object
                // evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
                config.setMinEvictableIdleTimeMillis(60000);
                this.jedisPool = new JedisPool(config, host, port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this.jedisPool;
    }

    private static synchronized Jedis getJedis() {
        int timeoutCount = 0;
        try {
            return RedisUtils.redisUtils.getJedisPool().getResource();
        } catch (Exception e) {
            do {
                if (!(e instanceof JedisConnectionException)) {
                    return RedisUtils.redisUtils.getJedisPool().getResource();
                }
                timeoutCount++;
                System.out.println("getJedis timeoutCount={}" + timeoutCount);
            } while (timeoutCount <= 3);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private static void returnResource(Jedis jedis) {
        if (jedis != null) {
            RedisUtils.redisUtils.getJedisPool().returnResource(jedis);
        }
    }

    public static synchronized Jedis openRedis() {
        return getJedis();
    }

    /**
     * @Author 
     * @Description 登录时保存用户角色信息到redis
     * @Date 
     * @Param [jedis, userId, flag]
     * @return void
     **/
    public static void saveUser(Jedis jedis, Long userId, Integer flag) {
        jedis.set("sysUser@" + userId, String.valueOf(flag));
    }

    /**
     * @Author 
     * @Description 根据userId获取redis中保存的用户角色信息
     * @Date 
     * @Param [jedis, userId]
     * @return java.lang.Long
     **/
    public static Integer getFlagByUserId(Jedis jedis, Long userId) {
        String key = "sysUser@" + userId;
        return Integer.valueOf(jedis.get(key));
    }

    public static void save(Jedis jedis, String guid, Integer socketID) {
        jedis.set(guid, String.valueOf(socketID));
    }

    public static void del(Jedis jedis, String guid) {
        jedis.del(guid);
    }

    public static String getKeyByValue(Jedis jedis, Integer socketID) {
        Set<String> keys = jedis.keys("*");
        for (String guid : keys) {
            if (jedis.get(guid).equals(String.valueOf(socketID))) {
                return guid;
            }
        }
        return null;
    }

    public static String getValueByKey(Jedis jedis, String guid) {
        return jedis.get(guid);
    }

    public static void closeRedis(Jedis jedis) {
        returnResource(jedis);
    }
}
