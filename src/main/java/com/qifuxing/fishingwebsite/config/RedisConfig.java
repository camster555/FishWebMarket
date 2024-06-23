package com.qifuxing.fishingwebsite.config;


import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures web settings for connecting to redis.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-16
 * @version 1.0.0
 */

//MySQL stores data on disks (HDDs or SSDs). Access Time: Reading from and writing to disk takes longer because it involves physical movement
// (in HDDs) or slower electronic access (in SSDs) compared to RAM.
//High Volume: With millions of users, the database has to handle a large number of read and write queries. Resource Intensive:
// Each query consumes time and resources, leading to slower response times and potential bottlenecks as user load increases.
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    //Redis stores data in RAM. Access Time: RAM access is extremely fast because it’s electronic and doesn’t involve the delays associated with disk access.
    //Performance: Redis can handle a large number of operations per second due to the speed of RAM. Efficiency: Data retrieval and storage operations are quick,
    // allowing Redis to efficiently manage high volumes of user sessions and other data.
    @Bean
    //return type is RedisConnectionFactory which is an interface that allows backend to connect to Redis.
    public RedisConnectionFactory redisConnectionFactory() {
        //returning a new instance of LettuceConnectionFactory which is a popular Redis client for Java and handles
        //the actual connection to the Redis server.
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .clientOptions(ClientOptions.builder()
                         // RESP stands for REdis Serialization Protocol which is a protocol that Redis use to
                        //communicate with redis, but it's the second version.
                        .protocolVersion(ProtocolVersion.RESP2)
                        // Finalize ClientOptions configuration
                        .build())
                // Finalize LettuceClientConfiguration
                .build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        //creating a new instance of RedisTemplate which is used to interact with Redis.
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        //setting the connection factory to the one created above and this is the connection to redis.
        template.setConnectionFactory(redisConnectionFactory());

        //this means to convert the key that is sent from our backend to a string, like for example if we send a
        //number as a key it will be converted to a string.
        template.setKeySerializer(new StringRedisSerializer());

        //here converting the value that is sent from our backend to a JSON object for better processing and storage.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

}
