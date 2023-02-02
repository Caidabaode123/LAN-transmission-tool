package com.example.lan_transmission_tool;

import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class LanTransmissionToolApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void contextLoads() {
        System.out.println("/file/165465".substring("/file/".length()));
        RBucket<Object> test = redissonClient.getBucket("test");
        Map<String, String> stringStringMap = new HashMap();
        stringStringMap.put("test","test");
        stringStringMap.put("test1","test");
        stringStringMap.put("test2","test");
        test.set(stringStringMap);
        RLock teststees = redissonClient.getLock("teststees");
        redissonClient.getList("test");

        teststees.lock();
        System.out.println(test.get());

    }

}
