package com.qifuxing.fishingwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures for connecting to AWS SES Email service.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-01
 * @version 1.0.0
 */

@Configuration
public class ASESConfig {

    //method use to communicate to AWS
    //Authorizing your application to perform specific actions on AWS services based on the permissions
    // assigned to the keys.
    @Bean
    public SesClient sesClient(){

        return SesClient.builder()
                .region(Region.US_EAST_1)
                //'ProfileCredentialsProvider.create()' will auto search for path in
                // Windows: `~/.aws/credentials
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

    }

}
