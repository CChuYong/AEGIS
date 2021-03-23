package chuyong.aegis.command.internal;

import chuyong.aegis.AEGIS;
import chuyong.aegis.command.CommandArguments;
import chuyong.aegis.impl.CommandHandler;
import chuyong.aegis.user.AEGISUser;

import static chuyong.aegis.AEGIS.LOGGER;

public class CommandShutdown extends CommandHandler {
    public CommandShutdown() {
        super("shutdown", "aegis.shutdown");
    }

    @Override
    public void onCommand(AEGISUser user, CommandArguments args) {
        if(args.hasProperty("help")){
            user.sendMessage("Usage: shutdown [<Option>]");
            user.sendMessage("Information about the shutdown command.\n");
            user.sendMessage("  --help           show list of commands of shutdown.");
            user.sendMessage("  -t <seconds>     execute shutdown after seconds.");
            user.sendMessage("  --stop           cancel queued shutdown.");
            return;
        }
        if(args.hasProperty("stop")){
            if(AEGIS.getInstance().shutdownThread != null){
                AEGIS.getInstance().shutdownThread.interrupt();
                user.sendMessage("AEGIS Shutdown progress interrupted");
                LOGGER.info("AEGIS Shutdown Progress interrupted by " + user.getName());
                return;
            }
            user.sendMessage("AEGIS Shutdown progress interrupt failed. Shutdown Progress not queued.");
            return;
        }
        if(AEGIS.getInstance().shutdownThread != null){
            user.sendMessage("AEGIS Shutdown progress queue failed. Shutdown Progress already queued.");
            return;
        }
        String in = args.getArgument("t");
        int sec = 0;
        if(in != null){
            sec = Integer.parseInt(in);
        }
        user.sendMessage("Executed AEGIS Shutdown Progress! Execute after " + sec +" seconds");
        LOGGER.info("AEGIS Shutdown Progress queued by " + user.getName());
        AEGIS.getInstance().shutdown(sec * 1000L);
    }
}
