package chuyong.aegis.command.internal;

import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.user.AEGISUser;

public class CommandAEGIS extends CommandHandler {
    public CommandAEGIS(){
        super("aegis");
    }
    public void onCommand(AEGISUser sender, CommandArguments args) {
        sender.sendMessage("AEGIS-0.0.1-SNAPSHOT by SongYeongMin in 2021");
    }
}
