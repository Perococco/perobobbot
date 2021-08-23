package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import jplugman.api.ServiceProvider;
import lombok.NonNull;
import perobobbot.command.CommandDeclaration;
import perobobbot.plugin.Extension;

public abstract class ExtensionWithoutCommandFactory extends ExtensionFactoryBase<Extension> {

    public ExtensionWithoutCommandFactory(@NonNull String extensionName) {
        super(extensionName);
    }

    @Override
    protected @NonNull ImmutableList<CommandDeclaration> createCommandDefinitions(@NonNull Extension extension, @NonNull ServiceProvider serviceProvider, CommandDeclaration.@NonNull Factory factory) {
        return ImmutableList.of();
    }
}
