//package com.zonefun.backend.redis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.TimeUnit;
//
///**
// * @ClassName RedisUtil
// * @Description redisTemplate工具类
// * @Date 2022/7/716:08
// * @Author ZoneFang
// */
//@Component
//@Slf4j
//public class RedisLock {
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    /**
//     * 尝试对相应key进行加锁 如果上锁成功则返回true 上锁失败返回false 非自旋锁
//     * 会自动在传入的key前加上该系统redis锁的前缀
//     *
//     * @param key        锁key
//     * @param value      锁value
//     * @param expireTime 锁过期时间
//     * @return java.lang.Boolean
//     * @date 2022/7/7 16:29
//     * @author ZoneFang
//     */
//    public Boolean tryLock(String key, String value, long expireTime) {
//        // 该操作redisTemplate中是用的execute 即具有原子性 所以无需考虑多线程安全问题
//        // 非自旋锁
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
//        if (Boolean.TRUE.equals(lock)) {
//            log.info("成功获取锁：{}", key);
//        } else {
//            log.warn("无法获取锁：{}", key);
//        }
//        return lock;
//    }
//
//    /**
//     * 加锁 默认半小时失效时间
//     *
//     * @param key   锁key
//     * @param value 锁value
//     * @return java.lang.Boolean
//     * @date 2022/7/7 16:29
//     * @author ZoneFang
//     */
//    public Boolean tryLock(String key, String value) {
//        return this.tryLock(key, value, 1800000);
//    }
//
//    /**
//     * 释放锁 删除锁key
//     *
//     * @param key 锁key
//     * @return java.lang.Boolean
//     * @date 2022/7/7 16:39
//     * @author ZoneFang
//     */
//    public Boolean releaseLock(String key) {
//        String value = redisTemplate.opsForValue().get(key);
//        Boolean res = redisTemplate.delete(key);
//        if (Boolean.TRUE.equals(res)) {
//            log.info("成功回收锁：【key:{},value:{}】", key, value);
//        } else {
//            log.error("回收锁失败：【key:{},value:{}】，可能需要人工删除", key, value);
//        }
//        return res;
//    }
//
//}
