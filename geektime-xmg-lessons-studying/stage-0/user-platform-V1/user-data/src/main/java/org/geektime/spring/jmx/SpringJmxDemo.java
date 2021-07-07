package org.geektime.spring.jmx;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.LiveBeansView;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;

import java.io.IOException;

/**
 * @author WenXJ
 */
@Configuration
public class SpringJmxDemo {

    @Bean
    public AnnotationMBeanExporter beanExporter(){return new AnnotationMBeanExporter();}

    @Bean
    public User user(){return new User();}
    /**
     * Spring boot  /actuator/beans 替换
     * */
    @Bean
    public LiveBeansView liveBeansView(){return new LiveBeansView();}

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringJmxDemo.class);
        context.refresh();
        System.in.read();
        context.close();
    }

}
