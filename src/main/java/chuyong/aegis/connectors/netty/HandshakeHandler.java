package chuyong.aegis.connectors.netty;

import chuyong.aegis.AEGIS;
import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.device.CeilingLight;
import chuyong.aegis.impl.device.Device;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

import static chuyong.aegis.AEGIS.LOGGER;

@ChannelHandler.Sharable
public class HandshakeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Preconditions.checkNotNull(ctx, "ChannelHandlerContext can't be null");
        LOGGER.info("Handshake Requested via Netty TCP. address: "+ctx.channel().remoteAddress());
        super.channelActive(ctx);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        if(buf.readableBytes() < 24){
            ctx.disconnect();
            LOGGER.info("Handshake Request dropped by unknown packet. address: "+ctx.channel().remoteAddress());
            return;
        }
        int type = (int)buf.readLongLE();
        long least = buf.readLongLE();
        long most = buf.readLongLE();
        UUID uuid = new UUID(most, least);
        String name = AEGIS.getInstance().getSqlHandler().getName(uuid, type);
        switch(type){
            case 1:
                attachHandler(ctx, new CeilingLight(ctx, name, uuid));
                break;
            default:
                ctx.disconnect();
                LOGGER.info("Handshake Request dropped by unknown device type. address: "+ctx.channel().remoteAddress());
                return;
        }
        super.channelRead(ctx, msg);
    }
    public void attachHandler(ChannelHandlerContext ctx, Device dev){
        final ServerHandler handler = new ServerHandler();
        final ClientHandler handller = new ClientHandler();
        ctx.pipeline().remove(this);
        ctx.pipeline().addLast(handler);
        ctx.pipeline().addFirst(handller);

        Preconditions.checkNotNull(ctx, "ChannelHandlerContext can't be null");
        DeviceCache.addDevice(dev);
        LOGGER.info("Device connected via Netty TCP. address: "+ctx.channel().remoteAddress() +" with UUID: "+dev.getUuid());
    }
}
