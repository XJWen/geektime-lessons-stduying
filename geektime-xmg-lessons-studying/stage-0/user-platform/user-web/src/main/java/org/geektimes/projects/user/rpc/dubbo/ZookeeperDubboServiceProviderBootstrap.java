package org.geektimes.projects.user.rpc.dubbo;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.geektimes.projects.user.service.EchoService;
import org.geektimes.projects.user.service.impl.EchoServiceImpl;


import static org.apache.dubbo.common.constants.CommonConstants.*;

public class ZookeeperDubboServiceProviderBootstrap {

    public static void main(String[] args) {
        DubboBootstrap.getInstance()
                .application("zookeeper-dubbo-provider",app -> app.metadata(COMPOSITE_METADATA_STORAGE_TYPE))
                .registry(builder-> builder.address("127.0.0.1:2181").protocol("zookeeper"))
                .protocol("dubbo",builder->builder.port(-1).name("dubbo"))
                .protocol("rest",builder->builder.port(8081).name("rest").server("netty"))
                .service("echo1",builder->builder.interfaceClass(EchoService.class).ref(new EchoServiceImpl()).protocolIds("dubbo,rest"))
                .start().await();
    }
}
