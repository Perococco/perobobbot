package perobobbot.server.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import perobobbot.access.PolicyManager;
import perobobbot.access.ProxyPolicyManager;

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
