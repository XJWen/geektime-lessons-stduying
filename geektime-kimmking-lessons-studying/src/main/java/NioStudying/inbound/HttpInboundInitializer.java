package NioStudying.inbound;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import java.util.List;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {

    private List<String> proxyServers;

    public HttpInboundInitializer(List<String> proxyServers) {
        this.proxyServers = proxyServers;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

    }
}
