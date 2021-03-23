package chuyong.aegis.impl.device;

import chuyong.aegis.connectors.DeviceConnection;
import chuyong.aegis.user.AEGISUser;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Device {
    private DeviceConnection connection;
    @Getter
    private UUID uuid;
    @Getter
    @Setter
    private String name;
    public Device(ChannelHandlerContext context, String name, UUID uuid){
        connection = new DeviceConnection(context);
        this.uuid = uuid;
        this.name = name;
    }
    public boolean isAuthorized(AEGISUser user){
        return user.hasPermission("aegis.device."+name);
    }
    public DeviceConnection getConnection(){
        return connection;
    }
}
