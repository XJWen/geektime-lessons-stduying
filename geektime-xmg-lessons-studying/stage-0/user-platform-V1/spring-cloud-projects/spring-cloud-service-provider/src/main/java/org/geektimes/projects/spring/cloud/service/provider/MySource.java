package org.geektimes.projects.spring.cloud.service.provider;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * MySource 是一个被代理的接口
 */
public interface MySource {

    @Output("output1")
    MessageChannel output1();

    @Output("output2")
    MessageChannel output2();

}
