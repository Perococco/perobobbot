package perobobbot.server.config.command;

import lombok.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.access.ExecutionManager;
import perobobbot.access.ProxyExecutionManager;
import perobobbot.lang.Instants;

@Component
public class SpringExecutionManager extends ProxyExecutionManager {

    public SpringExecutionManager(@NonNull Instants instants) {
        super(ExecutionManager.create(instants));
    }

    @Override
    @Scheduled(fixedDelay = 60_000)
    public void cleanUp() {
        super.cleanUp();
    }
}
