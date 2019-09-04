package com.liu.util.rediscluster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class RedisConfig {

    @Value("${customConfig.redis.server.host:#{null}}")
    private String redisNodes;

    @Value("${customConfig.redis.server.timeout}")
    private Integer timeout;

    @Bean
    public JedisCluster getJedisCluster() {
        String[] serverArray = redisNodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));

        }
        return new JedisCluster(nodes, timeout);
    }
}