package chuyong.aegis.util.scheduler;

import chuyong.aegis.AEGIS;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AEGISScheduler {
    private static ExecutorService service;
    private static Logger logger;
    public static void init(){
        service = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setNameFormat("AEGIS:Async Thread").build());
        logger =  AEGIS.LOGGER;
        logger.info("AEGIS Internal Executor Service Active. Scheduling system can initiated.");
    }
    public static void run(Runnable r){
        Preconditions.checkNotNull(r, "Runnable object cannot be null");
        service.execute(new ScheduledTask(r));
    }
}
