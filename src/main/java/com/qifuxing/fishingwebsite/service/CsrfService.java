package com.qifuxing.fishingwebsite.service;

import com.qifuxing.fishingwebsite.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This service provides custom user details for authentication.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-07-05
 * @version 1.0
 */

@Service
public class CsrfService {

    private static final Logger logger = LoggerFactory.getLogger(CsrfService.class);

    public String getCsrfToken(HttpServletRequest request){

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrfToken == null){
            logger.error("CSRF token not found in request.");
            throw new ResourceNotFoundException("CSRF token not found in request.");
        }

        return csrfToken.getToken();
    }

}
