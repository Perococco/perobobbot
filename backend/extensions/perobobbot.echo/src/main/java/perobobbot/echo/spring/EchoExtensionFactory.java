package perobobbot.echo.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.echo.EchoExtension;
import perobobbot.echo.action.EchoExecutor;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.extension.ExtensionWithCommands;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;

import java.time.Duration;
import java.util.Optional;

@Component
public class EchoExtensionFactory extends ExtensionFactoryBase<EchoExtension> {

    public static Packages provider() {
        return Packages.with("[Extension] Echo", EchoExtensionFactory.class);
    }

    public EchoExtensionFactory(@NonNull Parameters parameters) {
        super(EchoExtension.NAME, parameters);
    }

    @Override
    protected @NonNull EchoExtension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new EchoExtension(userId, parameters.getIo());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull EchoExtension extension, @NonNull Parameters parameters) {
        final var policy = parameters.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));
        return Optional.of(CommandBundle.builder()
                                        .add("echo", policy, new EchoExecutor(extension))
                                        .build());
    }

}
