package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandDefinition;

@RequiredArgsConstructor
public class ExtensionInfo {
    final @NonNull Extension extension;
    @Getter
    final @NonNull ImmutableList<CommandDefinition> commandDefinitions;

    public void enabled() {
        extension.enable();
    }

    public void disable() {
        extension.disable();
    }

    public @NonNull String getExtensionName() {
        return extension.getName();
    }
}
