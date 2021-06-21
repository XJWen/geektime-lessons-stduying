package io.cache.controller;

import io.cache.entity.User;
import io.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value ="/user/find")
    User find(Integer id) {
        return userService.find(id);
    }

    @RequestMapping(value ="/user/list")
    List<User> list() {
        return userService.list();
    }

}
