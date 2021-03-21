package org.geektimes.management;

import org.geektimes.projects.user.domain.User;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class UserMBeanDemo {

    public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
        // 获取平台 MBean Server
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        // 为 UserMXBean 定义 ObjectName
        ObjectName objectName = new ObjectName("org.geektimes.management:type=User");
        // 创建 UserMBean 实例
        User user = new User();
        mBeanServer.registerMBean(createUserMBean(user),objectName);
        while (true){
            Thread.sleep(2000);
            System.out.println(user);
        }
    }

    private static Object createUserMBean(User user){
        return  new UserManager(user);
    }
}
