package org.geektimes.projects.user.configuration.demo;

import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.client.serviceregistry.ServiceRegistry;

import java.util.List;

public class CompositeServiceRegistry implements ServiceRegistry<Registration> {

    private final List<ServiceRegistry<Registration>> serviceRegistries;

    public CompositeServiceRegistry(List<ServiceRegistry<Registration>> serviceRegistries) {
        this.serviceRegistries = serviceRegistries;
    }


    @Override
    public void register(Registration registration) {
        // 等同于 ServiceInstance
        serviceRegistries.forEach(registry -> registry.register(registration));
        // 假设第一个元素是：EurekaServiceRegistry 依赖 EurekaRegistration
        // 第二个元素是：NacosServiceRegistry 依赖 NacosRegistration
        // 仅有一种情况满足两种注册中心实现：
        // XXXRegistration 继承了 EurekaRegistration，又继承 NacosRegistration
        // registration is EurekaRegistration & registration is NacosRegistration
        // 否则会抛出 ClassCastException
    }

    @Override
    public void deregister(Registration registration) {

    }

    @Override
    public void close() {

    }

    @Override
    public void setStatus(Registration registration, String status) {

    }

    @Override
    public <T> T getStatus(Registration registration) {
        return null;
    }
}
