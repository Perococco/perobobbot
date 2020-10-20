package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.core.PerococcoProgramBuilder;

import java.util.Optional;

public interface Program {

    /**
     * @return the name of the program
     */
    @NonNull
    String getName();

    /**
     * start the program.
     * launch the background task associated with it
     */
    void start();

    /**
     * stop the program.
     * stop the background task associated with it
     */
    void stop();

    /**
     * method call periodically to perform
     * maintenance clean up
     */
    void cleanUp();

    /**
     * Find the chat command with the specific name
     * @param commandName the name of the chat command to find
     * @return an optional containing the chat command with the provided name if any, an empty optional otherwise
     */
    Optional<Execution> findChatCommand(@NonNull String commandName);

    @NonNull
    MessageHandler getMessageHandler();

    static <P> ProgramBuilder<P> builder(@NonNull Function1<? super Services, ? extends P> parameterFactory) {
        return new PerococcoProgramBuilder<>(parameterFactory);
    }

    static <P> ProgramBuilder<P> builder(@NonNull P parameter) {
        return builder(s -> parameter);
    }
}
