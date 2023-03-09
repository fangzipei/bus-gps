//package com.zonefun.backend.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.connection.BitFieldSubCommands;
//import org.springframework.data.redis.connection.RedisStringCommands;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public final class RedisUtil {
//
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//
//    public void setEnableTransactionSupport(Boolean value) {
//        redisTemplate.setEnableTransactionSupport(value);
//    }
//
//    public void watch(String key) {
//        redisTemplate.watch(key);
//    }
//
//    public void multi() {
//        redisTemplate.multi();
//    }
//
//    public List exec() {
//        return redisTemplate.exec();
//    }
//
//    public void unwatch() {
//        redisTemplate.unwatch();
//    }
//
//    public Set<String> keys(String keys) {
//        try {
//            return redisTemplate.keys(keys);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Set<Object> hkeys(String key) {
//        return redisTemplate.opsForHash().keys(key);
//    }
//
//    /**
//     * 指定缓存失效时间
//     *
//     * @param key  键
//     * @param time 时间(秒)
//     * @return
//     */
//    public boolean expire(String key, long time) {
//        try {
//            if (time > 0) {
//                redisTemplate.expire(key, time, TimeUnit.SECONDS);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 根据key 获取过期时间
//     *
//     * @param key 键 不能为null
//     * @return 时间(秒) 返回0代表为永久有效
//     */
//    public long getExpire(String key) {
//        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 判断key是否存在
//     *
//     * @param key 键
//     * @return true 存在 false不存在
//     */
//    public boolean hasKey(String key) {
//        try {
//            return redisTemplate.hasKey(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 删除缓存
//     *
//     * @param key 可以传一个值 或多个
//     */
//    public void del(String key) {
//        redisTemplate.delete(key);
//    }
//
//    /**
//     * 普通缓存获取
//     *
//     * @param key 键
//     * @return 值
//     */
//    public Object get(String key) {
//        return key == null ? null : redisTemplate.opsForValue().get(key);
//    }
//
//    /**
//     * 普通缓存放入
//     *
//     * @param key   键
//     * @param value 值
//     * @return true成功 false失败
//     */
//    public boolean set(String key, Object value) {
//        try {
//            redisTemplate.opsForValue().set(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 普通缓存放入并设置时间
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
//     * @return true成功 false 失败
//     */
//    public boolean set(String key, Object value, long time) {
//        try {
//            if (time > 0) {
//                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
//            } else {
//                set(key, value);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 递增
//     *
//     * @param key   键
//     * @param delta 要增加几(大于0)
//     * @return
//     */
//    public long incr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递增因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, delta);
//    }
//
//    /**
//     * 递减
//     *
//     * @param key   键
//     * @param delta 要减少几(小于0)
//     * @return
//     */
//    public long decr(String key, long delta) {
//        if (delta < 0) {
//            throw new RuntimeException("递减因子必须大于0");
//        }
//        return redisTemplate.opsForValue().increment(key, -delta);
//    }
//
//    /**
//     * HashGet
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return 值
//     */
//    public Object hget(String key, String item) {
//        return redisTemplate.opsForHash().get(key, item);
//    }
//
//    /**
//     * 获取hashKey对应的所有键值
//     *
//     * @param key 键
//     * @return 对应的多个键值
//     */
//    public Map<Object, Object> hmget(String key) {
//        return redisTemplate.opsForHash().entries(key);
//    }
//
//    /**
//     * HashSet
//     *
//     * @param key 键
//     * @param map 对应多个键值
//     * @return true 成功 false 失败
//     */
//    public boolean hmset(String key, Map<String, Object> map) {
//        try {
//            redisTemplate.opsForHash().putAll(key, map);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * HashSet 并设置时间
//     *
//     * @param key  键
//     * @param map  对应多个键值
//     * @param time 时间(秒)
//     * @return true成功 false失败
//     */
//    public boolean hmset(String key, Map<String, Object> map, long time) {
//        try {
//            redisTemplate.opsForHash().putAll(key, map);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     *
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @return true 成功 false失败
//     */
//    public boolean hset(String key, String item, Object value) {
//        try {
//            redisTemplate.opsForHash().put(key, item, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 向一张hash表中放入数据,如果不存在将创建
//     *
//     * @param key   键
//     * @param item  项
//     * @param value 值
//     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
//     * @return true 成功 false失败
//     */
//    public boolean hset(String key, String item, Object value, long time) {
//        try {
//            redisTemplate.opsForHash().put(key, item, value);
//            if (time > 0) {
//                expire(key, time);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    /**
//     * 删除hash表中的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 可以使多个 不能为null
//     */
//    public void hdel(String key, Object... item) {
//        redisTemplate.opsForHash().delete(key, item);
//    }
//
//    /**
//     * 判断hash表中是否有该项的值
//     *
//     * @param key  键 不能为null
//     * @param item 项 不能为null
//     * @return true 存在 false不存在
//     */
//    public boolean hHasKey(String key, String item) {
//        return redisTemplate.opsForHash().hasKey(key, item);
//    }
//
//    /**
//     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
//     *
//     * @param key  键
//     * @param item 项
//     * @param by   要增加几(大于0)
//     * @return
//     */
//    public double hincr(String key, String item, double by) {
//        return redisTemplate.opsForHash().increment(key, item, by);
//    }
//
//    /**
//     * hash递减
//     *
//     * @param key  键
//     * @param item 项
//     * @param by   要减少记(小于0)
//     * @return
//     */
//    public double hdecr(String key, String item, double by) {
//        return redisTemplate.opsForHash().increment(key, item, -by);
//    }
//
//    /**
//     * 根据key获取Set中的所有值
//     *
//     * @param key 键
//     * @return
//     */
//    public Set<Object> sGet(String key) {
//        try {
//            return redisTemplate.opsForSet().members(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 根据value从一个set中查询,是否存在
//     *
//     * @param key   键
//     * @param value 值
//     * @return true 存在 false不存在
//     */
//    public boolean sHasKey(String key, Object value) {
//        try {
//            return redisTemplate.opsForSet().isMember(key, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将数据放入set缓存
//     *
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSet(String key, Object... values) {
//        try {
//            return redisTemplate.opsForSet().add(key, values);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 将set数据放入缓存
//     *
//     * @param key    键
//     * @param time   时间(秒)
//     * @param values 值 可以是多个
//     * @return 成功个数
//     */
//    public long sSetAndTime(String key, long time, Object... values) {
//        try {
//            Long count = redisTemplate.opsForSet().add(key, values);
//            if (time > 0)
//                expire(key, time);
//            return count;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 获取set缓存的长度
//     *
//     * @param key 键
//     * @return
//     */
//    public long sGetSetSize(String key) {
//        try {
//            return redisTemplate.opsForSet().size(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 移除值为value的
//     *
//     * @param key    键
//     * @param values 值 可以是多个
//     * @return 移除的个数
//     */
//    public long setRemove(String key, Object... values) {
//        try {
//            Long count = redisTemplate.opsForSet().remove(key, values);
//            return count;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 取2个集合的交集
//     *
//     * @param key      键
//     * @param otherKey 键
//     * @return 移除的个数
//     */
//    public Set<Object> setInter(String key, String otherKey) {
//        try {
//            Set<Object> sets = redisTemplate.opsForSet().intersect(key, otherKey);
//            return sets;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 取多个集合的交集
//     *
//     * @param otherKeys 键
//     * @return 移除的个数
//     */
//    public Set<Object> setInter(List<String> otherKeys) {
//        try {
//            Set<Object> sets = redisTemplate.opsForSet().intersect(otherKeys);
//            return sets;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//    // ===============================list=================================
//
//    /**
//     * 获取list缓存的内容
//     *
//     * @param key   键
//     * @param start 开始
//     * @param end   结束 0 到 -1代表所有值
//     * @return
//     */
//    public List<Object> lGet(String key, long start, long end) {
//        try {
//            return redisTemplate.opsForList().range(key, start, end);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 获取list缓存的长度
//     *
//     * @param key 键
//     * @return
//     */
//    public long lGetListSize(String key) {
//        try {
//            return redisTemplate.opsForList().size(key);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    /**
//     * 通过索引 获取list中的值
//     *
//     * @param key   键
//     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
//     * @return
//     */
//    public Object lGetIndex(String key, long index) {
//        try {
//            return redisTemplate.opsForList().index(key, index);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @return
//     */
//    public boolean lSet(String key, Object value) {
//        try {
//            redisTemplate.opsForList().rightPush(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     * @return
//     */
//    public boolean lSet(String key, Object value, long time) {
//        try {
//            redisTemplate.opsForList().rightPush(key, value);
//            if (time > 0)
//                expire(key, time);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @return
//     */
//    public boolean lSet(String key, List<Object> value) {
//        try {
//            redisTemplate.opsForList().rightPushAll(key, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 将list放入缓存
//     *
//     * @param key   键
//     * @param value 值
//     * @param time  时间(秒)
//     * @return
//     */
//    public boolean lSet(String key, List<Object> value, long time) {
//        try {
//            redisTemplate.opsForList().rightPushAll(key, value);
//            if (time > 0)
//                expire(key, time);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 根据索引修改list中的某条数据
//     *
//     * @param key   键
//     * @param index 索引
//     * @param value 值
//     * @return
//     */
//    public boolean lUpdateIndex(String key, long index, Object value) {
//        try {
//            redisTemplate.opsForList().set(key, index, value);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    /**
//     * 移除N个值为value
//     *
//     * @param key   键
//     * @param count 移除多少个
//     * @param value 值
//     * @return 移除的个数
//     */
//    public long lRemove(String key, long count, Object value) {
//        try {
//            Long remove = redisTemplate.opsForList().remove(key, count, value);
//            return remove;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
//
//    public Boolean tryLock(String key, String value, long expireTime) {
//        // 该操作redisTemplate中是用的execute 即具有原子性 所以无需考虑多线程安全问题
//        // 非自旋锁
//        key = "OpenBox_" + key;
//        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value, expireTime, TimeUnit.MILLISECONDS);
//        if (Boolean.TRUE.equals(lock)) {
//            log.info("成功获取锁：{}", key);
//        } else {
//            log.warn("无法获取锁：{}", key);
//        }
//        return lock;
//    }
//
//    public Boolean releaseLock(String key) {
//        key = "OpenBox_" + key;
//        Boolean res = redisTemplate.delete(key);
//        if (Boolean.TRUE.equals(res)) {
//            log.info("成功回收锁：【key:{}】", key);
//        } else {
//            log.error("回收锁失败：【key:{}】，可能需要人工删除", key);
//        }
//        return res;
//    }
//
//    public Boolean getBit(String key, long index) {
//        try {
//            return redisTemplate.opsForValue().getBit(key, index);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public List<Long> getBit(String key) {
//        try {
//            BitFieldSubCommands command = BitFieldSubCommands.create()
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(1)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(2)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(3)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(4)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(5)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(6)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(7)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(8)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(9)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(10)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(11)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(12)
//                    .get(BitFieldSubCommands.BitFieldType.unsigned(1)).valueAt(13);
//            return redisTemplate.opsForValue().bitField(key, command);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Boolean setBit(String key, long index, Boolean value) {
//        try {
//            return redisTemplate.opsForValue().setBit(key, index, value);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public Long execute(String key1, String key2) {
//        try {
//            String andMap = "signedAllMonth11";
//            redisTemplate.execute((RedisCallback) conn -> conn.bitOp(RedisStringCommands.BitOperation.AND, andMap.getBytes(), key1.getBytes(), key2.getBytes()));
//            return (Long) redisTemplate.execute((RedisCallback<Long>) conn -> conn.bitCount(andMap.getBytes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public void sign(String key, int employeeNumber) {
//        redisTemplate.opsForValue().setBit(key, employeeNumber - 1, true);
//    }
//
//    public boolean isSigned(String key, int employeeNumber) {
//        return redisTemplate.execute((RedisCallback<Boolean>) conn -> conn.getBit(key.getBytes(), employeeNumber - 1));
//    }
//
//}
