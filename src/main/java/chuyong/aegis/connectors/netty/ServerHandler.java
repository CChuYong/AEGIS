package chuyong.aegis.connectors.netty;

import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.connectors.netty.packets.ProtocolHandler;
import chuyong.aegis.device.CeilingLight;
import chuyong.aegis.impl.AbstractPacket;
import chuyong.aegis.impl.Stream;
import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

import static chuyong.aegis.AEGIS.LOGGER;

@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        Preconditions.checkNotNull(ctx, "ChannelHandlerContext can't be null");
        DeviceCache.removeDevice(ctx);
        LOGGER.info("Device disconnected. address: "+ctx.channel().remoteAddress());
        super.channelInactive(ctx);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf in = (ByteBuf) msg;
        if(in.readableBytes() < 4)
            return;
        int packetid = in.readInt();
        Class<? extends AbstractPacket> clazz = ProtocolHandler.getPacketClass(Stream.FROM_CLIENT, packetid);
        if(clazz != null){
            try{
                AbstractPacket pack = clazz.newInstance();
                pack.read(in);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            in.resetReaderIndex();
            ctx.writeAndFlush(in);
            return;
        }
        ctx.writeAndFlush(in);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable ex) throws Exception{
        ex.printStackTrace();
        ctx.close();
    }
}
