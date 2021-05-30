package api.server;

import org.springframework.context.ApplicationContext;
import api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;


public class RpcfxInvoker {

    private RpcfxResolver resolver;

    private ApplicationContext applicationContext;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }

    public RpcfxResponse invoke(RpcfxRequest request){
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

//        Object service = resolver.resolve(serviceClass);
        Object service = this.applicationContext.getBean(serviceClass);

        try {
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
        } catch ( IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getMethods()).filter(method ->
        method.getName().equalsIgnoreCase(methodName)
        ).findFirst().get();
    }
}
