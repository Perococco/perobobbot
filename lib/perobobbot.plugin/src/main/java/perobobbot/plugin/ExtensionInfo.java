package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandDefinition;

@RequiredArgsConstructor
@Getter
public class ExtensionInfo {

    @NonNull Extension extension;

    @NonNull ImmutableList<CommandDefinition> commandDefinitions;
}
