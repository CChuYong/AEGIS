package chuyong.aegis.device;

import chuyong.aegis.connectors.netty.packets.out.Toggle;
import chuyong.aegis.connectors.netty.packets.out.TurnOff;
import chuyong.aegis.connectors.netty.packets.out.TurnOn;
import chuyong.aegis.impl.device.Device;
import chuyong.aegis.impl.device.ToggleState;
import chuyong.aegis.impl.device.ToggleableDevice;
import io.netty.channel.ChannelHandlerContext;

import java.util.UUID;

public class CeilingLight extends Device implements ToggleableDevice {
    public CeilingLight(ChannelHandlerContext context, String name, UUID uuid){
        super(context, name, uuid);
    }
    @Override
    public void turnOn() {
        getConnection().sendPacket(new TurnOn());
    }

    @Override
    public void turnOff() {
        getConnection().sendPacket(new TurnOff());
    }

    @Override
    public ToggleState getState() {
        return null;
    }

    @Override
    public ToggleState toggle() {
        getConnection().sendPacket(new Toggle());
        return null;
    }
}
