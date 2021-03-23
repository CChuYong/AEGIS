package chuyong.aegis.connectors.netty;

import chuyong.aegis.connectors.netty.packets.ProtocolHandler;
import chuyong.aegis.impl.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof AbstractPacket){
            AbstractPacket pack = (AbstractPacket) msg;
            int id = ProtocolHandler.getPacketId(pack.getClass());
            if(id == -1){
                ctx.write(msg, promise);
                return;
            }
            ByteBuf tar = ctx.alloc().buffer();
            tar.writeInt(id);
            pack.write(tar);
            ctx.writeAndFlush(tar, promise);
            return;
        }
        ctx.write(msg, promise);
    }
}
