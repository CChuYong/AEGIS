package chuyong.aegis.connectors.netty;

import chuyong.aegis.AEGIS;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AEGISServer extends Thread{
    private int port;
    public ArrayList<SocketChannel> chan = new ArrayList<>();
    private ChannelFuture future;
    private Logger LOGGER = LogManager.getLogger(AEGIS.class);
    public AEGISServer(int port) {
        this.port = port;
        this.setName("AEGIS:NettyServer");
        this.setDaemon(false);
    }
    @Override
    public void run(){
        try{
            EventLoopGroup workerGroup = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("AEGIS:Netty IO #%1$d").build());
            final ChannelFutureListener listener = future -> {
                if (future.isSuccess()) {
                    LOGGER.info("Listener Successfully bind to target port: "+port);
                } else {
                    LOGGER.warn("Listener Binding Progress Failed!");
                }
            };
                final HandshakeHandler handler = new HandshakeHandler();
                ServerBootstrap sb = new ServerBootstrap();
                sb.group(workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>(){
                             @Override
                              public void initChannel(SocketChannel ch) throws Exception{
                                    ch.pipeline().addLast(handler);
                             }
                         })
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                future = sb.bind(port).addListener(listener);
        }catch(Exception ex){

        }
    }
    public void shutdown(){
        if(future!=null)
            future.channel().close().syncUninterruptibly();
    }
}
