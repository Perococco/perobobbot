package perobobbot.server.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.access.core.PolicyManager;
import perobobbot.access.core.ProxyPolicyManager;

@Component
public class SpringPolicyManager extends ProxyPolicyManager {

    public SpringPolicyManager() {
        super(PolicyManager.create());
    }

    @Override
    @Scheduled(fixedDelay = 60_000)
    public void cleanUp() {
        super.cleanUp();
    }
}
