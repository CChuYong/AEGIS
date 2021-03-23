package chuyong.aegis.command;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandArguments {
    public HashMap<String, String> argsMap;
    public ArrayList<String> argsList;
    public String[] argument;
    public CommandArguments(){
        argsMap = new HashMap<>();
        argsList = new ArrayList<>();
    }
    public boolean assign(String[] args){
        this.argument = args;
        for(int i = 0; i < args.length; i++){
            if(args[i].startsWith("--")){
                argsList.add(args[i].replace("--", ""));
                continue;
            }
            if(args[i].startsWith("-")){
                if(args.length == i+1 || args[i+1].startsWith("-")){
                    return false;
                }
                argsMap.put(args[i].replace("-", ""), args[i+1]);
                if(i < args.length)
                    i++;
                continue;
            }
        }
        return true;
    }
    public boolean hasProperty(String property){
        return argsList.contains(property);
    }
    public String getArgument(String argsHeader){
        return argsMap.get(argsHeader);
    }
}
