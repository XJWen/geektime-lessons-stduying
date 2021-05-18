package org.geektimes.projects.user.mybatis.interceptor;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.geektimes.projects.user.repository.UserRepository;

import javax.annotation.Resource;
import java.util.Properties;

public class MyInterceptor implements Interceptor {

    @Resource
    private UserRepository userRepository;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
