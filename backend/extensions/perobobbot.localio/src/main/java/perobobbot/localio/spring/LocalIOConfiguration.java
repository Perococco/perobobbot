package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.ApplicationCloser;
import perobobbot.localio.LocalChatPlatform;

@Configuration
@RequiredArgsConstructor
public class LocalIOConfiguration {

    private final @NonNull ApplicationCloser applicationCloser;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull PolicyManager policyManager;

    @Bean(destroyMethod = "disconnectAll")
    public LocalChatPlatform console() {
        return new LocalChatPlatform(applicationCloser, commandRegistry, policyManager);
    }
}
