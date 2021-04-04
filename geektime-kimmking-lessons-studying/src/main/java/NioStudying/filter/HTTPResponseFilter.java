package NioStudying.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public interface HTTPResponseFilter {

    void filter(FullHttpResponse response);
}
