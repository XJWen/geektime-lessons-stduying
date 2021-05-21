package org.geektimes.projects.user.rpc.dubbo;

import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHashLoadBalance;
import org.geektimes.projects.user.service.EchoService;

import static org.apache.dubbo.common.constants.CommonConstants.*;
import static org.apache.dubbo.common.constants.RegistryConstants.*;

public class ZookeeperDubboServiceConsumerBootstrap {

    public static void main(String[] args) throws InterruptedException {

        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance()
                .application("zookeeper-dubbo-consumer",app->app.metadata(COMPOSITE_METADATA_STORAGE_TYPE))
                .registry("zookeeper",builder->builder.address("zookeeper://127.0.0.1:2181")
                            .parameter(REGISTRY_TYPE_KEY, SERVICE_REGISTRY_TYPE)
                            .useAsConfigCenter(true)
                            .useAsMetadataCenter(true))
                .reference("echo",builder->builder.interfaceClass(EchoService.class)
                        .loadbalance(ConsistentHashLoadBalance.NAME)
                        .protocol("rest").services("zookeeper-dubbo-provider"))
                .start();


        EchoService echoService = dubboBootstrap.getCache().get(EchoService.class);

        for (int i =0;i<100;i++){
            Thread.sleep(250L);
            System.out.println(echoService.echo("Hello,this is dubbo"));
        }

        dubboBootstrap.stop();
    }
}
