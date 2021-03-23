package chuyong.aegis.cache;

import chuyong.aegis.impl.device.Device;
import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class DeviceCache {
    private static List<Device> deviceList;
    private static ReadWriteLock readWriteLock;
    public static void addDevice(Device device){
        Preconditions.checkNotNull(device, "Device can't be null");
        readWriteLock.writeLock().lock();
        deviceList.add(device);
        readWriteLock.writeLock().unlock();
    }
    public static Device getDeviceByName(String name){
        Preconditions.checkNotNull(name, "Name can't be null");
        readWriteLock.readLock().lock();
        for(Device div : deviceList){
            if(div.getName().equals(name))
                return div;
        }
        readWriteLock.readLock().unlock();
        return null;
    }
    public static Set<String> getDeviceNameList(){
        return deviceList.stream().map(x->x.getName()).collect(Collectors.toSet());
    }
    public static void removeDevice(ChannelHandlerContext ctx){
        readWriteLock.writeLock().lock();
        deviceList.removeIf(k->k.getConnection().equals(ctx.channel()));
        readWriteLock.writeLock().unlock();
    }
    static{
        deviceList = new ArrayList<>();
        readWriteLock = new ReentrantReadWriteLock();
    }
}
