package chuyong.aegis.connectors.netty.packets;

import chuyong.aegis.connectors.netty.packets.in.DeviceInfo;
import chuyong.aegis.connectors.netty.packets.out.*;
import chuyong.aegis.impl.AbstractPacket;
import chuyong.aegis.impl.Stream;

public enum ProtocolHandler {
    DEVICE_INFO(Stream.FROM_CLIENT,0x01, DeviceInfo.class),
    TURN_ON(Stream.TO_CLIENT, 0x01, TurnOn.class),
    TURN_OFF(Stream.TO_CLIENT, 0x02, TurnOff.class),
    TOGGLE(Stream.TO_CLIENT, 0x03, Toggle.class) ;

    private Stream path;
    private int packetId;
    private Class<? extends AbstractPacket> packet;
    ProtocolHandler(Stream path, int packetid, Class<? extends AbstractPacket> packet){
        this.path = path;
        this.packetId = packetid;
        this.packet = packet;
    }
    public int getPacketId(){
        return packetId;
    }
    public Stream getPath(){
        return path;
    }
    public Class<? extends AbstractPacket> getPacket(){
        return packet;
    }
    public static Class<? extends AbstractPacket> getPacketClass(Stream path, int id){
        for(ProtocolHandler handler : ProtocolHandler.values()){
            if(handler.getPath() == path && handler.getPacketId() == id)
                return handler.getPacket();
        }
        return null;
    }
    public static int getPacketId(Class<? extends AbstractPacket> packet){
        for(ProtocolHandler handler : ProtocolHandler.values()){
            if(packet.equals(handler.getPacket())){
                return handler.getPacketId();
            }
        }
        return -1;
    }
}
