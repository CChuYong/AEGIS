package chuyong.aegis;

import chuyong.aegis.command.CommandProcessor;
import chuyong.aegis.command.internal.*;
import chuyong.aegis.connectors.netty.AEGISServer;
import chuyong.aegis.connectors.netty.packets.out.TurnOn;
import chuyong.aegis.user.AEGISUser;
import chuyong.aegis.util.scheduler.AEGISScheduler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;

import java.util.UUID;


public class AEGIS extends AEGISUser {
    public static Logger LOGGER;
    private static AEGISServer SERVER;
    private static AEGIS INSTANCE;
    private static boolean isRunning = false;
    private CommandProcessor commandProcessor;
     private LineReader reader;
    public AEGIS(){
        INSTANCE = this;
        isRunning = false;
        Thread.currentThread().setName("AEGIS:Main Thread");
        LOGGER = LogManager.getLogger(AEGIS.class);
        commandProcessor = new CommandProcessor();
        System.setOut(IoBuilder.forLogger(LOGGER).setLevel(Level.INFO).buildPrintStream());
        System.setErr(IoBuilder.forLogger(LOGGER).setLevel(Level.ERROR).buildPrintStream());
        AEGISScheduler.init();
        try{
             reader = LineReaderBuilder.builder().terminal(
                     TerminalBuilder.builder()
                    .dumb(true)
                   .system(true)
                   .jansi(true)
                   .build()).build();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public static AEGIS getInstance(){
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "AEGISConsole";
    }

    public void test(){
        SERVER.chan.forEach(c -> c.writeAndFlush(new TurnOn()));
    }
    public void start(){
        LOGGER.info("AEGIS Engine Initialization Process...");
        logo();
        initDefaultElements();
        LOGGER.info("Starting Netty NIO TCP Server...");
        SERVER = new AEGISServer(10050);
        try{
            SERVER.start();
            LOGGER.info("Netty NIO TCP Server Initialization Success.");
        }catch(Exception ex){
            ex.printStackTrace();
        }

        isRunning = true;
        String input;
        while(isRunning && (input = reader.readLine("> "))!=null){
            if(!commandProcessor.process(this, input)){
                LOGGER.info("Unknown command. Enter \"cl\" to see list of commands.");
            }
        }
        LOGGER.info("Console input has been exited.");
    }
    public void logo(){
        System.out.println("");
        System.out.println("    ___    _____________________");
        System.out.println("   /   |  / ____/ ____/  _/ ___/");
        System.out.println("  / /| | / __/ / / __ / / \\__ \\ ");
        System.out.println(" / ___ |/ /___/ /_/ // / ___/ / ");
        System.out.println("/_/  |_/_____/\\____/___//____/  ");
        System.out.println("");
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid.getLeastSignificantBits());
        System.out.println(uuid.getMostSignificantBits());
    }
    public void initDefaultElements(){
        CommandProcessor.registerCommand(new CommandCL());
        CommandProcessor.registerCommand(new CommandShutdown());
        CommandProcessor.registerCommand(new CommandTurnOn());
        CommandProcessor.registerCommand(new CommandTurnOff());
        CommandProcessor.registerCommand(new CommandToggle());
        CommandProcessor.registerCommand(new CommandMvDiv());
        CommandProcessor.registerCommand(new CommandRMDiv());
        CommandProcessor.registerCommand(new CommandDVList());
        CommandProcessor.registerCommand(new CommandAEGIS());
        CommandProcessor.registerCommand(new CommandDebug());
    }
    public Thread shutdownThread;
    public void shutdown(long time){
        isRunning = false;
        shutdownThread = new Thread("AEGIS:Shutdown"){
            @Override
            public void run(){
                try{
                    Thread.sleep(time);
                    LOGGER.info("Netty NIO TCP Server Shutdown progress started.");
                    SERVER.shutdown();
                    LOGGER.info("Netty NIO TCP Server Shutdown progress completed.");
                    System.exit(0);
                }catch(InterruptedException ex){
                    LOGGER.info("Shutdown Interrupted.");
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        shutdownThread.start();
    }
    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }
    @Override
    public boolean hasPermission(String perm){
        return true;
    }
}
