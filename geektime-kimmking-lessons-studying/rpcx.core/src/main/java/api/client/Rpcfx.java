package api.client;

import com.alibaba.fastjson.parser.ParserConfig;
import api.Filter;
import api.handle.RpcfxInvocationHandler;

import java.lang.reflect.Proxy;

public class Rpcfx {

    static {
        ParserConfig.getGlobalInstance().addAccept("rpc.rpcx");
    }

    public static <T> T create(final Class<T> serviceClass, final String url, Filter... filters) {

        // 0. 替换动态代理 -> AOP
        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url, filters));

    }
}
