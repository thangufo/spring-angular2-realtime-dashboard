package com.thang.realtime.dashboard.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Web Security configuration class, used to configure the security for REST API
 *
 * @author agrawald
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.user.name}")
    String adminUser;
    @Value("${security.user.password}")
    String adminPassword;
    @Value("${user.name}")
    String user;
    @Value("${user.password}")
    String password;

    @Override
    public void init(WebSecurity web) throws Exception {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .fullyAuthenticated();
        http.httpBasic();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(adminUser).password(adminPassword).roles("USER", "ADMIN");
        auth.inMemoryAuthentication().withUser(user).password(password).roles("USER");
    }
}
