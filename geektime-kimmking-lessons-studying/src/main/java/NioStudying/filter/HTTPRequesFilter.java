package NioStudying.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface HTTPRequesFilter {

    void filter(FullHttpRequest request, ChannelHandlerContext context);
}
