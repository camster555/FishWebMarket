package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.config.ASESConfig;
import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service contains business logic for managing emails.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-01
 * @version 1.0.0
 */

@Service
public class EmailService {
    private final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private SesClient sesClient;
    @Value("${aws.ses.source-email}")
    private String email;

    public void sendEmail(String to,String subject,String body){

        try {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(to).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(subject).build())
                            .body(Body.builder()
                                    .text(Content.builder().data(body).build())
                                    .build())
                            .build())
                    .source(email)
                    .build();

            sesClient.sendEmail(emailRequest);
            logger.info("Email success sent to: " + to);
        } catch (SesException e){
            logger.error("Failed to send email to {}", to, e);
            throw new ResourceNotFoundException("Email sending failed" + e);
        }

    }

}
