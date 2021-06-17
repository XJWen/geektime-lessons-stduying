package org.geektimes.projects.spring.cloud.config.server;

/**
 * ${user.dir} 属性实例
 * */
public class UserDirSystemPropertyDemo {

    public static void main(String[] args) {
        // /spring-cloud-projects/spring-cloud-config-server/src/main/resources/config-repo
        System.out.println(System.getProperty("user.dir"));
    }
}
