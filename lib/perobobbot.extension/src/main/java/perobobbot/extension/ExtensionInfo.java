package perobobbot.extension;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.command.CommandDefinition;

@RequiredArgsConstructor
public class ExtensionInfo {
    final @NonNull Extension extension;
    @Getter
    final @NonNull ImmutableList<CommandDefinition> commandDefinitions;

    public @NonNull String getExtensionName() {
        return extension.getName();
    }
}
