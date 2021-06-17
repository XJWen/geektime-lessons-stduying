package org.geektimes.projects.spring.cloud.service.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

    @Input("inputChannel")
    SubscribableChannel inputChannel();
}
