package chuyong.aegis.command;

import chuyong.aegis.exception.CommandArgumentFailedException;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.user.AEGISUser;
import chuyong.aegis.util.scheduler.AEGISScheduler;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandProcessor {
    private static HashMap<String, List<CommandHandler>> commandMap;
    public boolean process(AEGISUser user, String input){
        String[] args = input.split(" ");
        String[] target = new String[args.length-1];
        for(int i = 1; i < args.length; i++)
            target[i-1] = args[i];
        List<CommandHandler> handler = commandMap.get(args[0].toLowerCase());
        if(handler != null){
            handler.forEach(hd -> {
                if(hd.getPermission()!=null && !user.hasPermission(hd.getPermission())){
                    return;
                }
                CommandArguments arguments = new CommandArguments();
                AEGISScheduler.run(()->{
                    if(!arguments.assign(target)){
                        throw new CommandArgumentFailedException(input);
                    }
                    hd.onCommand(user, arguments);
                });
            });
            return true;
        }
        return false;
    }
    public static void registerCommand(CommandHandler handler){
        Preconditions.checkNotNull(handler, "CommandHandler cannot be null");
        commandMap.computeIfAbsent(handler.getCommand().toLowerCase(), x -> new ArrayList<>()).add(handler);
    }
    public static Set<String> getCommands(){
        return commandMap.keySet();
    }
    static{
        commandMap = new HashMap<>();
    }
}
