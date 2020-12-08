package perobobbot.echo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.echo.action.EchoExecutor;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.lang.Role;

import java.time.Duration;

@RequiredArgsConstructor
public class EchoExtensionFactory implements ExtensionFactory {

    private final @NonNull IO io;

    private final @NonNull PolicyManager policyManager;

    private final @NonNull CommandRegistry commandRegistry;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        return new EchoExtension(userId, io, this::buildCommandBundle);
    }

    private CommandBundleLifeCycle buildCommandBundle(@NonNull EchoExtension extension) {
        final var policy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));
        return CommandBundle.builder()
                            .add("echo", policy, new EchoExecutor(extension))
                            .build()
                            .createLifeCycle(commandRegistry);
    }

}
