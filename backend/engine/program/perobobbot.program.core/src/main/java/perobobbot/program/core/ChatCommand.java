package perobobbot.program.core;

import lombok.NonNull;

public interface ChatCommand extends Execution {

    @NonNull
    String getName();

    @NonNull
    ExecutionPolicy getExecutionPolicy();
}
