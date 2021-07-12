package org.geektimes.projects.spring.cloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.event.EventListener;

/**
 * Spring Cloud Config Server 引导类
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServer {

    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerInitialized(WebServerInitializedEvent event){
        WebServer webServer = event.getWebServer();
        System.out.println("当前Web服务端口:"+webServer.getPort());
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class,args);
    }

}
