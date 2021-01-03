package perobobbot.localio.action;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.localio.LocalAction;

@RequiredArgsConstructor
public class SimpleLocalAction implements LocalAction {

    @Getter
    private final @NonNull String name;
    @Getter
    private final @NonNull String description;
    private final @NonNull Runnable execution;

    @Override
    public void execute(@NonNull String[] parameters) {
        execution.run();
    }
}
