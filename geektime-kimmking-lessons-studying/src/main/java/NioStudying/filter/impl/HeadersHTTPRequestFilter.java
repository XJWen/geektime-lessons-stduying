package NioStudying.filter.impl;

import NioStudying.filter.HTTPRequesFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeadersHTTPRequestFilter implements HTTPRequesFilter {
    @Override
    public void filter(FullHttpRequest request, ChannelHandlerContext context) {
        request.headers().add("netty-io",context);
    }
}
