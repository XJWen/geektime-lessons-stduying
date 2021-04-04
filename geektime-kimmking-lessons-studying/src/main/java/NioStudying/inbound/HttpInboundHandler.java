package NioStudying.inbound;

import NioStudying.filter.HTTPRequesFilter;
import NioStudying.filter.impl.HeadersHTTPRequestFilter;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);

    private final List<String> proxyServer;

    private HTTPRequesFilter filter = new HeadersHTTPRequestFilter();

    public HttpInboundHandler(List<String> proxyServer) {
        this.proxyServer = proxyServer;
    }

}
