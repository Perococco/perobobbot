package perobobbot.localio;

import lombok.NonNull;
import perobobbot.localio.action.SimpleLocalAction;

public interface LocalAction {

    static @NonNull LocalAction with(@NonNull String name, @NonNull String description, @NonNull Runnable execution) {
        return new SimpleLocalAction(name, description, execution);
    }

    @NonNull String getName();

    @NonNull String getDescription();

    void execute(@NonNull String[] parameters);
}
