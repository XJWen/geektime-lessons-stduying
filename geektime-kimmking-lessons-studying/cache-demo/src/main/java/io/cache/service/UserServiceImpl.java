package io.cache.service;

import io.cache.entity.User;
import io.cache.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public class UserServiceImpl  implements UserService{

    @Autowired
    UserMapper mapper;

    @Cacheable(key = "#id",value="userCache")
    @Override
    public User find(int id) {
        System.out.println("==>"+id);
        return mapper.find(id);
    }
    //开启Spring Cache
    @Cacheable
    @Override
    public List<User> list() {
        return mapper.list();
    }
}
