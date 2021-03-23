package chuyong.aegis.command.internal;

import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.exception.DeviceNotFoundException;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.impl.device.Device;
import chuyong.aegis.user.AEGISUser;
import com.google.common.base.Preconditions;

public class CommandRMDiv extends CommandHandler {
    public CommandRMDiv() {
        super("rmdiv");
    }

    @Override
    public void onCommand(AEGISUser sender, CommandArguments args) {
        if(args.hasProperty("help")){
            sender.sendMessage("Usage: rmdiv [Device Name] [<Option..>]");
            return;
        }
        if(args.argument.length < 1){
            sender.sendMessage("Usage: rmdiv [Device Name] [<Option..>]");
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
        div.getConnection().disconnect();
    }
}
