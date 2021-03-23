package chuyong.aegis.command.internal;

import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.exception.DeviceNotFoundException;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.impl.device.Device;
import chuyong.aegis.user.AEGISUser;
import com.google.common.base.Preconditions;

public class CommandMvDiv extends CommandHandler {
    public CommandMvDiv() {
        super("mvdiv");
    }

    @Override
    public void onCommand(AEGISUser sender, CommandArguments args) {
        if(args.hasProperty("help")){
            sender.sendMessage("Usage: mvdiv [Current Name] [Target Name] [<Option..>]");
            return;
        }
        if(args.argument.length < 2){
            sender.sendMessage("Usage: mvdiv [Current Name] [Target Name] [<Option..>]");
            return;
        }
        Preconditions.checkNotNull(args.argument[0], "Please follow the usage of command. Type mvdiv --help");
        Preconditions.checkNotNull(args.argument[1], "Please follow the usage of command. Type mvdiv --help");
        Device div = DeviceCache.getDeviceByName(args.argument[0]);
        if(div == null){
            throw new DeviceNotFoundException("Following device can't be found in runtime.");
        }
        if(!div.isAuthorized(sender)){
            sender.sendMessage("You don't have enough privileges to manage this device.");
            return;
        }
        div.setName(args.argument[1]);
        sender.sendMessage("The device named "+args.argument[0]+" successfully renamed to "+args.argument[1]);
    }
}
