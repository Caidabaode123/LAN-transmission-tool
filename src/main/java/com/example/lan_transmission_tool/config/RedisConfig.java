package com.example.lan_transmission_tool.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;
    
    @Bean
    public RedissonClient redissonClient(){
        String address = "redis://"+host+":"+port;
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(address);
        if (StringUtils.hasLength(password)){
            singleServerConfig.setPassword("root");
        }
        config.setCodec(new JsonJacksonCodec());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
