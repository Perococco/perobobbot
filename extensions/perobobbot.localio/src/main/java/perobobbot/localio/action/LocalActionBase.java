package perobobbot.localio.action;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.localio.LocalAction;

public abstract class LocalActionBase implements LocalAction {

    @Getter
    private final @NonNull String name;
    @Getter
    private final @NonNull String description;

    public LocalActionBase(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }

}
