package org.geektimes.projects.spring.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Echo 服务接口
 */
@FeignClient("${echo.service.provider.application.name:spring-cloud-service-provider}")
public interface EchoService {

    @GetMapping("/echo/{message}")
    String echo(@PathVariable String message);

}
