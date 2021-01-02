package perobobbot.extension;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.command.ROCommandRegistry;
import perobobbot.lang.Bot;

public interface ExtensionManager {

    @NonNull ImmutableSet<String> listAllExtensions();

}
