package chuyong.aegis.connectors;

import chuyong.aegis.impl.AbstractPacket;
import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class DeviceConnection {
    private Channel channel;
    public DeviceConnection(ChannelHandlerContext context){
        this.channel = context.channel();
    }
    public void sendPacket(AbstractPacket packet){
        Preconditions.checkNotNull(packet, "Packet cannot be null");
        channel.writeAndFlush(packet);
    }
    public boolean equals(Channel channel){
        return this.channel.equals(channel);
    }
    public void disconnect(){
        channel.disconnect();
    }
}
