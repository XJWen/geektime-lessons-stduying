package NioStudying;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class OkHttpTest {

    private static OkHttpClient httpClient = null;

    public static void main(String[] args) {
        //初始化OkHttpClient
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3,TimeUnit.SECONDS)
                .build();
        //构建OkHttpClient的Request实体
        String url = "http://localhost:8801";
        Request formPostRequest =
                new Request
                        .Builder()
                        .url(url)
                        .get()
                        .build();
        //发起异步请求
        httpClient.newCall(formPostRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("'code':'404'"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("body:"+response.body().string());
            }
        });
        //清空客户端
        httpClient = null;
    }

}
