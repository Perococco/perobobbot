package perobobbot.extension;

import lombok.NonNull;
import perobobbot.command.CommandBundle;

import java.util.Optional;

public abstract class ExtensionWithoutCommandFactory extends ExtensionFactoryBase<Extension> {

    public ExtensionWithoutCommandFactory(@NonNull String extensionName, @NonNull Parameters parameters) {
        super(extensionName, parameters);
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull Extension extension, @NonNull Parameters parameters) {
        return Optional.empty();
    }
}
