package perobobbot.extension;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandBundle;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ExtensionFactoryBase<E extends Extension> implements ExtensionFactory {

    @Getter(AccessLevel.PUBLIC)
    private final @NonNull String extensionName;

    private final @NonNull Parameters parameters;

    @Override
    public final @NonNull Extension create(@NonNull String userId) {
        final E extension = createExtension(userId, parameters);
        final var commandBundle = createCommandBundle(extension, parameters);

        return commandBundle.map(cb -> cb.createLifeCycle(parameters.getCommandRegistry()))
                .<Extension>map(c -> new ExtensionWithCommands(extension, c))
                .orElse(extension);
    }

    /**
     * @param userId the user id the extension is associated to
     * @param parameters the parameters containing some services the extension might use
     * @return a new instance of the extension
     */
    protected abstract @NonNull E createExtension(@NonNull String userId, @NonNull Parameters parameters);

    /**
     * @param extension the extension the commands will apply to
     * @param parameters this factory parameters containing some services the extension might use
     * @return an optional containing the command bundle that command the extension, an empty optional if the extension has no command
     */
    protected abstract Optional<CommandBundle> createCommandBundle(@NonNull E extension, @NonNull Parameters parameters);

}
