package org.geektimes.management;

import com.sun.jmx.mbeanserver.Introspector;
import org.geektimes.projects.user.domain.User;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;

public class PlatformJMXMBeansDemo {

    public static void main(String[] args) throws NotCompliantMBeanException {
        //客户端获取  classLoadingMXBean 对象(代理对象)
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();

        classLoadingMXBean.getLoadedClassCount();
        //所有类型的MBean都会转换成DynamicMBean
        DynamicMBean mBean = Introspector.makeDynamicMBean(new UserManager(new User()));
    }
}
