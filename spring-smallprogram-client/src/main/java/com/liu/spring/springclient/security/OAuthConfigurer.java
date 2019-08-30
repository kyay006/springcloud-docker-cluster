package com.liu.spring.springclient.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuthConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    //token存储数据库
//    @Bean
//    public JdbcTokenStore jdbcTokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }

    //token存储redis
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //token存储
//        endpoints.tokenStore(jdbcTokenStore());
        endpoints.tokenStore(redisTokenStore());
    }



}
