package chuyong.aegis.command.internal;

import chuyong.aegis.cache.DeviceCache;
import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.user.AEGISUser;

import java.util.Set;

public class CommandDVList extends CommandHandler {
    public CommandDVList() {
        super("dvlist");
    }

    @Override
    public void onCommand(AEGISUser sender, CommandArguments args) {
        if(args.hasProperty("help")){
            sender.sendMessage("Usage: dvlist");
            return;
        }
        Set<String> names = DeviceCache.getDeviceNameList();
        sender.sendMessage("There's "+names.size()+" devices registered in AEGIS.");
        if(names.size() != 0)
           sender.sendMessage(String.join(", ", names));
    }
}
