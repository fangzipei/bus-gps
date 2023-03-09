//package com.zonefun.backend.redis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisFactory;
//import redis.clients.jedis.JedisPool;
//
///**
// * @ClassName JedisUtil
// * @Description Jedis工具类
// * @Date 2022/6/24 17:22
// * @Author ZoneFang
// */
//@Component
//@Slf4j
//public class JedisUtil {
//
//    @Autowired
//    private JedisFactory jedisFactory;
//
////    @Autowired
////    private JedisCluster jedisCluster;
//
//    @Autowired
//    private JedisPool jedisPool;
//
//    /**
//     * 设值
//     *
//     * @param key   key
//     * @param value value
//     * @return java.lang.String
//     * @date 2022/6/24 17:26
//     * @author ZoneFang
//     */
//    public String set(String key, String value) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.set(key, value);
//        } catch (Exception e) {
//            log.error("set key:{} value:{} error", key, value, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 设值和过期时间
//     *
//     * @param key        key
//     * @param value      value
//     * @param expireTime 过期时间 单位秒
//     * @return java.lang.String
//     * @date 2022/6/24 17:29
//     * @author ZoneFang
//     */
//    public String setex(String key, String value, long expireTime) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.setex(key, expireTime, value);
//        } catch (Exception e) {
//            log.error("set key:{} value:{} expireTime:{} error", key, value, expireTime, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 取值
//     *
//     * @param key key
//     * @return java.lang.String
//     * @date 2022/6/24 17:30
//     * @author ZoneFang
//     */
//    public String get(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.get(key);
//        } catch (Exception e) {
//            log.error("get key:{} error", key, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 删除key
//     *
//     * @param key key
//     * @return java.lang.Long
//     * @date 2022/6/24 17:30
//     * @author ZoneFang
//     */
//    public Long del(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.del(key.getBytes());
//        } catch (Exception e) {
//            log.error("del key:{} error", key, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key key
//     * @return java.lang.Boolean
//     * @date 2022/6/24 17:35
//     * @author ZoneFang
//     */
//    public Boolean exists(String key) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.exists(key.getBytes());
//        } catch (Exception e) {
//            log.error("exists key:{} error", key, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 设值key过期时间
//     *
//     * @param key        key
//     * @param expireTime 过期时间 单位秒
//     * @return java.lang.Long
//     * @date 2022/6/24 17:35
//     * @author ZoneFang
//     */
//    public Long expire(String key, int expireTime) {
//        Jedis jedis = jedisPool.getResource();
//        try {
//            return jedis.expire(key.getBytes(), expireTime);
//        } catch (Exception e) {
//            log.error("expire key:{} error", key, e);
//            return null;
//        } finally {
//            close(jedis);
//        }
//    }
//
//    /**
//     * 关闭连接
//     *
//     * @param jedis redis连接
//     * @return void
//     * @date 2022/6/24 17:35
//     * @author ZoneFang
//     */
//    private void close(Jedis jedis) {
//        if (null != jedis) {
//            jedis.close();
//        }
//    }
//}
