package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandBundle;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.PlatformIO;
import perobobbot.lang.Role;
import perobobbot.localio.Local;
import perobobbot.localio.LocalIO;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class LocalIOConfiguration {

    private final @NonNull PolicyManager policyManager;
    private final @NonNull ApplicationCloser applicationCloser;

    @Bean(destroyMethod = "disable")
    public LocalIO console() {
        return new Local(applicationCloser).enable();
    }

    @Bean(name= LocalIO.EXTENSION_NAME)
    public CommandBundle commandBundle(@NonNull LocalIO local) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.THE_BOSS, Duration.ofSeconds(0)));

        return CommandBundle.builder()
                .add("lio show-gui", policy, new ShowGui(local))
                .add("lio hide-gui", policy,local::hideGui)
                .build();
    }
}
