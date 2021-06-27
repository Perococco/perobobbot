package perobobbot.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.ThreadFactories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class ExecutorServices {


    @Bean("cachedThreadPool")
    public ExecutorService cachedThreadPool() {
        return java.util.concurrent.Executors.newCachedThreadPool(ThreadFactories.daemon("chached_pool_%d"));
    }

    @Bean("scheduledThreadPool")
    public ScheduledExecutorService scheduledExecutorService() {
        final int corePoolSize = Runtime.getRuntime().availableProcessors();
        return java.util.concurrent.Executors.newScheduledThreadPool(corePoolSize,ThreadFactories.daemon("scheduled_%d"));
    }
}
