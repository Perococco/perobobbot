package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.service.core.Services;

public interface ProgramBuilder<P> {

    @NonNull
    Program build();

    @NonNull
    ProgramBuilder<P> setName(@NonNull String name);

    @NonNull
    ProgramBuilder<P> setServices(@NonNull Services services);

    @NonNull
    ProgramBuilder<P> setBackgroundTask(@NonNull BackgroundTask.Factory<P> factory);

    @NonNull
    ProgramBuilder<P> attachChatCommand(@NonNull String commandName, @NonNull Execution.Factory<P> factory, @NonNull ExecutionPolicy policy);

    @NonNull
    ProgramBuilder<P> setMessageHandler(@NonNull MessageHandler.Factory<P> factory);
}
