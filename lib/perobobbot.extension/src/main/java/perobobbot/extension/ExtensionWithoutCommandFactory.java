package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import jplugman.api.ServiceProvider;
import lombok.NonNull;
import perobobbot.command.CommandDeclaration;
import perobobbot.plugin.Extension;

public interface ExtensionWithoutCommandFactory extends ExtensionFactory<Extension> {

    @Override
    default @NonNull ImmutableList<CommandDeclaration> createCommandDefinitions(@NonNull Extension extension, @NonNull ServiceProvider serviceProvider, CommandDeclaration.@NonNull Factory factory) {
        return ImmutableList.of();
    }
}
