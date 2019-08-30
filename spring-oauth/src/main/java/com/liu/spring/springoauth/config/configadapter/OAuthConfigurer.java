package com.liu.spring.springoauth.config.configadapter;

import com.liu.spring.springoauth.config.provider.MyCustomUserDetailsService;
import com.liu.spring.springoauth.config.token.JwtAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.security.KeyPair;

/**
 * 授权方式定义
 */
@Configuration
@EnableAuthorizationServer
public class OAuthConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @Autowired
    private MyCustomUserDetailsService customUserDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

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


    /**
     * jdbc版本，需要表:oauth_code
     * 共享Authentication code,这是为了oauth项目多开时,授权码的集群共享
     * @return
     */
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        return new JdbcAuthorizationCodeServices(dataSource);
//    }

    /**
     * redis版本
     * 共享Authentication code,这是为了oauth项目多开时,授权码的集群共享
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new RandomValueAuthorizationCodeServices() {
            @Override
            protected void store(String code, OAuth2Authentication authentication) {
                RedisConnection conn = redisConnectionFactory.getConnection();
                try {
                    conn.hSet("auth_code".getBytes("utf-8"), code.getBytes("utf-8"),SerializationUtils.serialize(authentication));
                } catch (Exception e) {
                    System.out.println("保存authentication code 失败" + e.getMessage());
                } finally {
                    conn.close();
                }
            }

            @Override
            protected OAuth2Authentication remove(String code) {
                RedisConnection conn = redisConnectionFactory.getConnection();
                OAuth2Authentication authentication = null;
                try {
                    try {
                        authentication = SerializationUtils.deserialize(conn.hGet("auth_code".getBytes("utf-8"), code.getBytes("utf-8")));
                    } catch (Exception e) {
                        return null;
                    }

                    if (null != authentication) {
                        conn.hDel("auth_code".getBytes("utf-8"), code.getBytes("utf-8"));
                    }
                    return authentication;
                } catch (Exception e) {
                    return null;
                } finally {
                    conn.close();
                }
            }
        };
    }






    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessToken();
        KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "tc123456".toCharArray()).getKeyPair("tycoonclient");
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
//        clients.inMemory()
        //以下这些都配置到表里面了，oauth_client_details 这个表
//                .withClient("ssoclient")
//                .secret("ssosecret")
//                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .accessTokenValiditySeconds(7200)   //设置Access Token的存活时间
//                .accessTokenValiditySeconds(2)   //设置Access Token的存活时间
//                .scopes("openid")
//                .scopes("all")//调取Api请求附带令牌，api增删查改等功能，而scopes的值就是all（全部权限），read，write等权限。就是第三方访问资源的一个权限，访问范围
//                .redirectUris("http://localhost:8005/getHello")//如：http://localhost:8005/getHello?code=XXFzbd&state=rOBMf7
//                .autoApprove(false);//如果autoApprove设置为true的话，客户端就不会进入用户授权页面。对于本系统的客户端就不需要用户确认授权操作
        //表示到数据库去查
        clients.withClientDetails(clientDetails());
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }



//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security
                .tokenKeyAccess("permitAll()")  // 开启/oauth/token_key验证端口无权限访问
                .checkTokenAccess("isAuthenticated()")  // 开启/oauth/check_token验证端口认证权限访问
                .allowFormAuthenticationForClients()
//                .passwordEncoder(passwordEncoder());
                .passwordEncoder(securityConfiguration.bCryptPasswordEncoder());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        //token存储
//        endpoints.tokenStore(jdbcTokenStore());
        endpoints.tokenStore(redisTokenStore());

        endpoints.accessTokenConverter(jwtAccessTokenConverter());

        // refresh_token需要userDetailsService
        endpoints.reuseRefreshTokens(false).userDetailsService(customUserDetailsService);
        endpoints.tokenServices(defaultTokenServices());

        //共享Authentication code,这是为了oauth项目多开时,授权码的集群共享
        endpoints.authorizationCodeServices(authorizationCodeServices());
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices(){
        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(jdbcTokenStore());
        tokenServices.setTokenStore(redisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetails());
//        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 12); // token有效期自定义设置，默认12小时
        tokenServices.setAccessTokenValiditySeconds(60 * 60 * 3); // token有效期自定义设置
//        tokenServices.setAccessTokenValiditySeconds(60); // token有效期自定义设置，默认2小时
        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);//默认30天，这里修改7天
        return tokenServices;
    }


    /**
     * 跨域, 开发环境使用 vue-cli 代理，正式用nginx
     */
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean =  new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


}
