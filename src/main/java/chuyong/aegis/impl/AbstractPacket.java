package chuyong.aegis.impl;

import io.netty.buffer.ByteBuf;

public abstract class AbstractPacket {
    public void read(ByteBuf buf){}
    public void write(ByteBuf buf){}
}
