package chuyong.aegis.command.internal;

import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.command.CommandProcessor;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.user.AEGISUser;

import java.util.Set;
import java.util.stream.Collectors;

public class CommandCL extends CommandHandler {
    public CommandCL(){
        super("cl");
    }
    public void onCommand(AEGISUser sender, CommandArguments args) {
        if(args.hasProperty("help")){
            sender.sendMessage("Usage: cl [<Name>]");
            return;
        }
        Set<String> set;
        if(!(args.argument.length > 0)){
            set = CommandProcessor.getCommands();
        }else{
            set = CommandProcessor.getCommands().stream().filter(k->k.startsWith(args.argument[0])).collect(Collectors.toSet());
        }
        sender.sendMessage("There's "+set.size()+" commands in AEGIS.");
        sender.sendMessage(String.join(", ", set));
    }
}
