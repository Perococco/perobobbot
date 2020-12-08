package perobobbot.extension;

import lombok.NonNull;

public interface ExtensionFactory {

    @NonNull Extension create(@NonNull String userId);
}
