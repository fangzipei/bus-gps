//package com.zonefun.backend.redis;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.*;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
//
//import java.util.stream.Collectors;
//
///**
// * @ClassName LettuceFactory
// * @Description Lettuce工厂bean覆盖
// * @Date 2022/6/27 14:24
// * @Author ZoneFang
// */
//public class LettuceFactory {
//
//    @Autowired
//    private RedisProperties redisProperties;
//
//    /**
//     * 集群lettuce连接工厂bean
//     *
//     * @return org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
//     * @date 2022/6/27 14:17
//     * @author ZoneFang
//     */
//    @Bean("ClusterLettuceFactory")
//    public LettuceConnectionFactory clusterLettuceConnectionFactory() {
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
//        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
//        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
//        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getTimeout().toMillis());
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        redisClusterConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
//        redisClusterConfiguration.setClusterNodes(
//                redisProperties.getCluster().getNodes().stream().map(node ->
//                        new RedisNode(node.split(":")[0], Integer.parseInt(node.split(":")[1]))
//                ).collect(Collectors.toSet())
//        );
//        redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(redisProperties.getTimeout())
//                .shutdownTimeout(redisProperties.getConnectTimeout())
//                .poolConfig(genericObjectPoolConfig)
//                .build();
//
//        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfig);
//    }
//
//    @Bean
//    @Primary
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        // 连接池配置
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
//        genericObjectPoolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
//        genericObjectPoolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
//        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getTimeout().toMillis());
//        // 常规连接
//        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(),redisProperties.getPort());
//        standaloneConfiguration.setPassword(redisProperties.getPassword());
//
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(redisProperties.getTimeout())
//                .shutdownTimeout(redisProperties.getConnectTimeout())
//                .poolConfig(genericObjectPoolConfig)
//                .build();
//
//        return new LettuceConnectionFactory(standaloneConfiguration, clientConfig);
//    }
//}
