package chuyong.aegis.command.internal;

import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.exception.DeviceNotFoundException;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.impl.device.Device;
import chuyong.aegis.impl.device.ToggleableDevice;
import chuyong.aegis.user.AEGISUser;

public class CommandTurnOff extends CommandHandler {
    public CommandTurnOff() {
        super("turnoff");
    }

    @Override
    public void onCommand(AEGISUser sender, CommandArguments args) {
        if(args.hasProperty("help")){
            sender.sendMessage("Usage: turnoff [Device Name] [<Option..>]");
            return;
        }
        if(args.argument.length < 1){
            sender.sendMessage("Usage: turnoff [Device Name] [<Option..>]");
            return;
        }
        Device div = DeviceCache.getDeviceByName(args.argument[0]);
        if(div == null){
            throw new DeviceNotFoundException("Following device can't be found in runtime.");
        }
        if(!div.isAuthorized(sender)){
            sender.sendMessage("You don't have enough privileges to manage this device.");
            return;
        }
        if(!(div instanceof ToggleableDevice)){
            sender.sendMessage("Following device isn't supporting toggle.");
            return;
        }
        ((ToggleableDevice)div).turnOff();
    }
}
