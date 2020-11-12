package perobobbot.echo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.common.command.Command;
import perobobbot.common.command.CommandBundle;
import perobobbot.echo.EchoExecutor;
import perobobbot.echo.EchoExtension;
import perobobbot.lang.IO;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class EchoConfiguration {

    public static Packages provider() {
        return Packages.with("echo", EchoConfiguration.class);
    }

    private final @NonNull IO io;

    private final @NonNull PolicyManager policyManager;

    @Bean
    public EchoExtension echoExtension() {
        return new EchoExtension("echo", io);
    }

    @Bean(name = "echo")
    public CommandBundle commandBundle(@NonNull EchoExtension extension) {
        final var policy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));
        return Command.factory()
                      .add("echo", policy, new EchoExecutor(extension))
                      .build();
    }
}
