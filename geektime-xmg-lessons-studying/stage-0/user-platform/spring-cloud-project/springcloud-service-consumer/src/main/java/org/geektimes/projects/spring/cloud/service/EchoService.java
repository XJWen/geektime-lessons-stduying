package org.geektimes.projects.spring.cloud.service;

import org.springframework.cloud.openfeign.*;


@FeignClient("${echo.service.provider.application.name:springcloud-service-provider}")
public interface EchoService {

    @GetMapping("/echo/{message}")
    String echo(@PathVariable String message) throws Exception;
}
