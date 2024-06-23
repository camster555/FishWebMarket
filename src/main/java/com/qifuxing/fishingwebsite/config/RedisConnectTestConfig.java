package com.qifuxing.fishingwebsite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.PostConstruct;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures testing for connecting to redis.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */

@Configuration
public class RedisConnectTestConfig {
    /*
    private static final Logger logger = LoggerFactory.getLogger(RedisConnectTestConfig.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @PostConstruct
    public void testRedisConnection() {
        try {

            // Log the password being used (for debugging purposes)
            logger.info("Using Redis password: {}", redisPassword);

            redisTemplate.opsForValue().set("testKey", "testValue");
            String value = (String) redisTemplate.opsForValue().get("testKey");
            logger.info("Connection to Redis successful, testKey value: {}", value);
        } catch (Exception e) {
            logger.error("Failed to connect to Redis", e);
            if (e instanceof RedisConnectionFailureException) {
                logger.error("RedisConnectionFailureException: Unable to connect to Redis server. Check if Redis server is running and the connection details are correct.");
            } else if (e.getCause() instanceof io.lettuce.core.RedisConnectionException) {
                logger.error("RedisConnectionException: {}", e.getCause().getMessage());
            } else {
                logger.error("An unknown error occurred while connecting to Redis: {}", e.getMessage());
            }
        }
    }
     */
}

