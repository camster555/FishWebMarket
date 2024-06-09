package com.qifuxing.fishingwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures web settings for the application.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-06-06
 * @version 1.0.0
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //CORS configuration allowing frontend on a different port to communicate with backend.
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/api/**")
                .allowedOrigins("http://127.0.0.1:5500", "https://camster555.github.io/qi-fu-xing/" , "https://camster555.github.io")
                //.allowedOrigins("http://localhost:5500")
                .allowedMethods("GET","POST","PUT","DELETE")
                //allowing all headers to be passed from frontend to backend
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    //configuration for cors to allow frontend to communicate
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //these are the websites allowed to backend to communicate with
        configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:5500", "https://camster555.github.io", "https://camster555.github.io/qi-fu-xing/"));
        //specifying the methods allowed to be used
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        //this object is used to register the configuration to the backend
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //this is to allow all URL to use the configuration
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
