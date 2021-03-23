package chuyong.aegis.util.scheduler;

import chuyong.aegis.AEGIS;
import org.apache.logging.log4j.Level;

public class ScheduledTask implements Runnable{
    private Runnable runnable;
    public ScheduledTask(Runnable runnable){
        this.runnable = runnable;
    }

    @Override
    public void run() {
        try{
            runnable.run();
        }catch(Throwable t){
            AEGIS.LOGGER.log(Level.ERROR, "Uncatchable Exception caught while executing process on AEGIS Async Executor.", t);
        }
    }
}
