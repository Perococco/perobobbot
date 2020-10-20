package perobobbot.program.core;

import lombok.NonNull;

public interface Execution {


    interface Factory<P> {
        @NonNull
        Execution create(@NonNull P parameter);
    }

    /**
     * @param executionContext the context this command is executing in
     */
    void execute(@NonNull ExecutionContext executionContext);

}
