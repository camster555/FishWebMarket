package com.qifuxing.fishingwebsite.config;

import com.qifuxing.fishingwebsite.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * FishMW1 - Fishing Market Web Application
 *
 * This class configures security settings for the application.
 *
 * @author WEIHU WANG/KEVIN WANG
 * @date 2024-05-20
 * @version 1.0.0
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //NOTE, somehow when securityConfig class uses UserDetailsService interface, spring tries to create a default
    //implementation since UserDetailService is an interface and don't have any implementations so instance of the
    //interface classed can't be created, however the default one created by spring using ''InMemoryUserDetailsManager
    //makes the default also rely on SecurityConfig class which now creates a loop of
    // UserDetailsService -> SecurityConfig -> UserDetailsService which will produce error during runtime so the
    //solution is to create a custom class called CustomUserDetailsService so when spring calls, it won't call the default
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //userDetailService is used by spring to fetch user details then specifies that passwords needs to be encoded
        //by the passwordEncoder method
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /*
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //disable for development but turn on for production later on
        httpSecurity.csrf().disable()
                //start configuration for specific URL rules
                .authorizeRequests()
                //allow user access to register and login
                //still on authorizeRequest so no need for and()
                .antMatchers("/auth/register","/auth/login").permitAll()
                //then any other request will need to be authenticated
                .anyRequest().authenticated()
                //here and() is needed as we move to a new configuration
                .and()
                //a custom login page at /login. DON'T NEED THIS RIGHT NOW BUT WHEN LOGIN PAGE IS CREATED THEN CAN USE.
                .formLogin().loginPage("/auth/login").permitAll()
                .and()
                //logout function permitted without authentication
                .logout().permitAll();
    }
     */

    //permitting everything for testing grounds
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .httpBasic().disable()
                .formLogin().disable(); // Disable form-based login
    }

    @Bean
    //passwordEncoder interface main function are 'string encode' new password and also verifies it with 'boolean matches' method
    public PasswordEncoder passwordEncoder(){
        //uses hashing function to encode new passwords.
        return new BCryptPasswordEncoder();
    }
}
