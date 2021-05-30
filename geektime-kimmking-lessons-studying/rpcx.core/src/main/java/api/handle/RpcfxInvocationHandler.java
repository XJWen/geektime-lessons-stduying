package api.handle;

import com.alibaba.fastjson.JSON;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import api.Filter;
import api.RpcfxRequest;
import api.RpcfxResponse;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RpcfxInvocationHandler implements InvocationHandler {

    public static final MediaType JSONTYPE = MediaType.get("application/json; charset=utf-8");


    private final Class<?> serviceClass;
    private final String url;
    private final Filter[] filters;

    public RpcfxInvocationHandler(Class<?> serviceClass, String url, Filter[] filters) {
        this.serviceClass = serviceClass;
        this.url = url;
        this.filters = filters;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcfxRequest request = new RpcfxRequest();
        request.setServiceClass(this.serviceClass.getName());
        request.setMethod(method.getName());
        request.setParams(args);

        //匹配过滤
        if (this.filters!=null){
            for (Filter filter:filters){
                if (!filter.filte(request)){
                    return null;
                }
            }
        }

        RpcfxResponse response = post(request,url);

        return JSON.parse(response.getResult().toString());
    }

    private RpcfxResponse post(RpcfxRequest req, String url) throws IOException {
        String reqJson = JSON.toJSONString(req);
        System.out.println("req json: "+reqJson);


        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(JSONTYPE, reqJson))
                .build();
        String respJson = client.newCall(request).execute().body().string();
        System.out.println("resp json: "+respJson);
        return JSON.parseObject(respJson, RpcfxResponse.class);
    }

}
