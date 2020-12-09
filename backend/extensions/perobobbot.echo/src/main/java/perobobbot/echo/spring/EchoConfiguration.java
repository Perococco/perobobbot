package perobobbot.echo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandRegistry;
import perobobbot.echo.EchoExtensionFactory;
import perobobbot.lang.Packages;

@Configuration
@RequiredArgsConstructor
public class EchoConfiguration {

    public static Packages provider() {
        return Packages.with("echo", EchoConfiguration.class);
    }

    private final @NonNull IO io;

    private final @NonNull CommandRegistry commandRegistry;

    private final @NonNull PolicyManager policyManager;

    @Bean
    public EchoExtensionFactory echoExtensionFactory() {
        return new EchoExtensionFactory(io,policyManager,commandRegistry);
    }

}
