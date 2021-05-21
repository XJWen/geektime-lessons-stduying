package org.geektimes.projects.user.service.impl;

import org.apache.dubbo.rpc.RpcContext;
import org.geektimes.projects.user.service.EchoService;

import static java.lang.String.format;

public class EchoServiceImpl implements EchoService {

    @Override
    public String echo(String message) {
        RpcContext rpcContext = RpcContext.getContext();

        return format("[%s %s]ECHO - %s",rpcContext.getLocalHost(),rpcContext.getLocalPort(),message);
    }
}
