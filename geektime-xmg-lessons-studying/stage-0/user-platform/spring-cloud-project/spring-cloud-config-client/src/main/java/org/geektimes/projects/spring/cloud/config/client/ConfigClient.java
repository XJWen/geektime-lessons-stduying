package org.geektimes.projects.spring.cloud.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ConfigClient {

    @Value("${my.name}")
    private String myName;

    @Value("${my.age}")
    private int myAge;

    public ApplicationRunner runner(){
        return args -> System.out.printf("my name = %s, my age = %d %n",myName,myAge);
    }

    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerInitialized(WebServerInitializedEvent event){
        WebServer webServer =event.getWebServer();
        System.out.println("当前Web服务器端口:"+webServer.getPort());
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient.class,args);
    }
}
