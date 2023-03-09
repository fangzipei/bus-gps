//package com.zonefun.backend.redis;
//
//import jdk.jfr.Description;
//import lombok.Setter;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.util.ObjectUtils;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @ClassName JedisFactory
// * @Description 读取redis配置
// * @Date 2022/6/23 15:46
// * @Author ZoneFang
// */
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis")
//@Setter
//public class JedisFactory {
//
//    @Autowired
//    private RedisProperties redisProperties;
//
//    @Description("最大尝试数")
//    private int maxAttempts;
//
//    @Description("连接超时时长")
//    private int connectionTimeout;
//
//    @Description("socket连接超时时长")
//    private int soTimeout;
//
//    /**
//     * 获取一个redis集群连接
//     *
//     * @return redis.clients.jedis.JedisCluster
//     * @date 2022/6/24 9:17
//     * @author ZoneFang
//     */
//    @Bean("JedisCluster")
//    @Primary
//    public JedisCluster getJedisCluster() {
//        if (!ObjectUtils.isEmpty(redisProperties.getCluster())) {
//            GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
//            // 读取配置确定redis池配置
//            poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
//            poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
//            poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
//            // 读取配置装配redis集群节点
//            Set<HostAndPort> redisClusterNodes = redisProperties.getCluster().getNodes().stream().filter(node -> node.contains(":")).map(node ->
//                    new HostAndPort(node.split(":")[0], Integer.parseInt(node.split(":")[1]))
//            ).collect(Collectors.toSet());
//            return new JedisCluster(redisClusterNodes, this.connectionTimeout, this.soTimeout, this.maxAttempts, redisProperties.getPassword(), poolConfig);
//        }
//        return null;
//    }
//
//    @Bean("JedisPool")
//    @Primary
//    public JedisPool jedisPool() {
//        GenericObjectPoolConfig<Jedis> poolConfig = new GenericObjectPoolConfig<>();
//        // 读取配置确定redis池配置
//        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
//        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
//        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
//        return new JedisPool(poolConfig, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getUsername(), redisProperties.getPassword());
//    }
//}
