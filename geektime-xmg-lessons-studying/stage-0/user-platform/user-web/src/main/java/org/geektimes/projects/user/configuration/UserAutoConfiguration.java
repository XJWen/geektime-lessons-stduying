package org.geektimes.projects.user.configuration;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.impl.UserServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(User.class)
public class UserAutoConfiguration {

    @Bean
    public UserService queryUser(User user){
        UserService userService = new UserServiceImpl();
        userService.queryUserByNameAndPassword(user.getName(),user.getPassword());
        return userService;
    }
}
