package org.geektimes.configuration.demo;

public class SecurityDemo {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        System.out.println(new SecurityManager());
        //该操作无法进行，需要修改配置文件
        System.setProperty("java.version","1.7.0_1");
        System.out.println(System.getProperty("java.version"));

    }
}
