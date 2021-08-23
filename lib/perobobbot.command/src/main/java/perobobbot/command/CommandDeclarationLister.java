package perobobbot.command;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Platform;

import java.util.UUID;

public interface CommandDeclarationLister {

    int VERSION = 1;

    @NonNull ImmutableSet<CommandDeclaration> getAllActiveCommands(@NonNull UUID botId);

    char getPrefix(@NonNull Platform platform);
}
