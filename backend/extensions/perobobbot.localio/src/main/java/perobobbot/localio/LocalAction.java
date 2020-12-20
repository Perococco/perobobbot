package perobobbot.localio;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LocalAction {

    public static LocalAction with(@NonNull String name, @NonNull String description, @NonNull Runnable execution) {
        return new LocalAction(name,description,execution);
    }

    private final @NonNull String name;

    private final @NonNull String description;

    private final @NonNull Runnable execution;
}
