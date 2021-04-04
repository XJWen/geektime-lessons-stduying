package NioStudying.filter.impl;

import NioStudying.filter.HTTPResponseFilter;
import io.netty.handler.codec.http.FullHttpResponse;

public class HeadersHTTPResponseFilter implements HTTPResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().get("netty-io");
    }
}
