package perobobbot.pause;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionWithCommands;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.pause.action.ExecuteCommand;

import java.time.Duration;

@RequiredArgsConstructor
public class PauseExtensionFactory implements ExtensionFactory {

    public static final String NAME = "pause";

    private final @NonNull Overlay overlay;
    private final @NonNull IO io;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull PolicyManager policyManager;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        final var pauseExtension = new PauseExtension(userId,overlay);
        return new ExtensionWithCommands(pauseExtension,createCommandBundleLifeCycle(pauseExtension));
    }

    private CommandBundleLifeCycle createCommandBundleLifeCycle(@NonNull PauseExtension extension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO));

        return CommandBundle.builder()
                            .add("pause", policy, new ExecuteCommand(io,extension))
                            .build()
                            .createLifeCycle(commandRegistry);


    }


    @Override
    public @NonNull String getExtensionName() {
        return NAME;
    }
}
