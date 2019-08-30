//package com.liu.spring.springoauth.utils.redis;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// * @author yys
// * Description:
// * @date created in 9:44 2018-04-25
// * Modified by:
// */
//@Configuration
//public class RedisConfig {
//
//    @Value("${customConfig.redis.server.host}")
//    private String host;
//    @Value("${customConfig.redis.server.password}")
//    private String password;
//    @Value("${customConfig.redis.server.port}")
//    private int port;
//    @Value("${customConfig.redis.server.jedis.pool.max-idle}")
//    private int maxIdl;
//    @Value("${customConfig.redis.server.jedis.pool.min-idle}")
//    private int minIdl;
////    @Value("${customConfig.redis.server.keytimeout}")
////    private long keytimeout;
//    @Value("${customConfig.redis.server.timeout}")
//    private int timeout;
//
//    @Primary
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdl);
//        poolConfig.setMinIdle(minIdl);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setDatabase(0);//编号为0的shu'j
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory1() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdl);
//        poolConfig.setMinIdle(minIdl);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setDatabase(1);//编号为1的数据库
//        return jedisConnectionFactory;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory2() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(maxIdl);
//        poolConfig.setMinIdle(minIdl);
//        poolConfig.setTestOnBorrow(true);
//        poolConfig.setTestOnReturn(true);
//        poolConfig.setTestWhileIdle(true);
//        poolConfig.setNumTestsPerEvictionRun(10);
//        poolConfig.setTimeBetweenEvictionRunsMillis(60000);
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
//        jedisConnectionFactory.setHostName(host);
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setPort(port);
//        jedisConnectionFactory.setDatabase(3);//编号为3的数据库
//        return jedisConnectionFactory;
//    }
//
//    @Bean(name = "redisTemplateObject0")
//    //此处注意定义的bean要注入到相应的service中使用 只有对应service才能处理对应的数据库
//    public RedisTemplate<String, Object> redisTemplateObject1() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }
//
//    @Bean(name = "redisTemplateObject1")
//    //此处注意定义的bean要注入到相应的service中使用 只有对应service才能处理对应的数据库
//    public RedisTemplate<String, Object> redisTemplateObject2() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory1());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }
//
//    @Bean(name = "redisTemplateObject2")
//    //此处注意定义的bean要注入到相应的service中使用 只有对应service才能处理对应的数据库
//    public RedisTemplate<String, Object> redisTemplateObject3() throws Exception {
//        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();
//        redisTemplateObject.setConnectionFactory(redisConnectionFactory2());
//        setSerializer(redisTemplateObject);
//        redisTemplateObject.afterPropertiesSet();
//        return redisTemplateObject;
//    }
//
//    private void setSerializer(RedisTemplate<String, Object> template) {
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(template.getStringSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.setHashValueSerializer(jackson2JsonRedisSerializer); //在使用String的数据结构的时候使用这个来更改序列化方式
//        // /*RedisSerializer<String> stringSerializer = new StringRedisSerializer(); template.setKeySerializer(stringSerializer ); template.setValueSerializer(stringSerializer ); template.setHashKeySerializer(stringSerializer ); template.setHashValueSerializer(stringSerializer );*/
//        }
//}