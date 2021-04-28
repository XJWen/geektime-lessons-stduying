package org.geektimes.web.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(9999)
public class AnotherSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests();
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
//        webSecurity.securityInterceptor();
    }
}
