package org.geektimes.projects.spring.cloud.service.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
//@EnableCircuitBreaker
@EnableBinding({MySource.class})
@EnableScheduling
public class ServiceProvider {

    @Autowired
    private InetUtils inetUtils;

    private String local_ip;

    private int port;

    @Autowired
    private MySource mySource;

    @PostConstruct
    public void init(){
        this.local_ip = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
    }

    @EventListener(WebServerInitializedEvent.class)
    public void  onWebServerInitialized(WebServerInitializedEvent event){
        this.port = event.getWebServer().getPort();
    }

    @Bean
    public ApplicationRunner runner(){
        return  args ->
                sendMessage();
    }

    @Scheduled(fixedRate =2000)
    private void sendMessage() {
        MessageChannel messageChannel = mySource.output1();
        GenericMessage<String> message = new GenericMessage("Hello World");
        messageChannel.send(message);
    }

    @Scheduled(fixedRate = 3000)
    public void sendMessage2() {
        MessageChannel messageChannel = mySource.output2();
        GenericMessage<String> message = new GenericMessage("2021");
        messageChannel.send(message);
    }

    public String echo(@PathVariable String message ) { return messageWithExtraInfo("[ECHO]:"+message);}

    // 辅助信息包括：
    // - IP
    // - Port
    // - Thread
    private String messageWithExtraInfo(String message) {
        return String.format("IP: %s,Port: %d,Thread: %s - %s",
                local_ip,
                port,
                Thread.currentThread().getName(),
                message
                );

    }

    public String fallback(String message,Throwable throwable) {
        return "FALLBACK";
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceProvider.class,args);
    }

}
