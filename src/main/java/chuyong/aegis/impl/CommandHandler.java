package chuyong.aegis.impl;

import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.user.AEGISUser;

public abstract class CommandHandler {
    private String command;
    private String permission = null;
    public CommandHandler(String command){
         this.command = command;
    }
    public CommandHandler(String command, String permission){
        this.command = command;
        this.permission = permission;
    }
    public String getCommand(){
        return command;
    }
    public String getPermission(){
        return permission;
    }
    public abstract void onCommand(AEGISUser sender, CommandArguments args);
}
