package perobobbot.pause.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.command.CommandBundle;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.pause.PauseExtension;
import perobobbot.pause.action.ExecuteCommand;

import java.time.Duration;
import java.util.Optional;

@Component
public class PauseExtensionFactory extends ExtensionFactoryBase<PauseExtension> {

    public static Packages provider() {
        return Packages.with("pause",PauseExtensionFactory.class);
    }

    public static final String NAME = "pause";

    public PauseExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @NonNull
    @Override
    protected PauseExtension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new PauseExtension(userId,parameters.getOverlay());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull PauseExtension extension, @NonNull Parameters parameters) {
        final Policy policy = parameters.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO));

        return Optional.of(CommandBundle.builder()
                                        .add("pause", policy, new ExecuteCommand(parameters.getIo(), extension))
                                        .build());
    }

}
