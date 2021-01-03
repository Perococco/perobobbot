package perobobbot.server.config.command;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.access.ExecutionManager;
import perobobbot.access.ProxyExecutionManager;

@Component
public class SpringExecutionManager extends ProxyExecutionManager {

    public SpringExecutionManager() {
        super(ExecutionManager.create());
    }

    @Override
    @Scheduled(fixedDelay = 60_000)
    public void cleanUp() {
        super.cleanUp();
    }
}
