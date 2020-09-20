package perobobbot.program.core;

import lombok.NonNull;

public interface MessageHandler {

    interface Factory<S> {

        @NonNull
        MessageHandler create(@NonNull S state);

    }

    @NonNull
    ExecutionContext handleMessage(@NonNull ExecutionContext context);
}
