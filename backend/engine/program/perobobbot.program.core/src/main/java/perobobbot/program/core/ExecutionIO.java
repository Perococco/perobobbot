package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.fp.Function1;

/**
 * The I/O of the execution that can be used to send message back to the channel
 * from which the execution has been initiated.
 *
 * For instance, if the execution is the result of a command on a chat, this I/O
 * can be used to send a message on the chat the command has been written in
 */
public interface ExecutionIO {

    /**
     * Print a message to the IO that received the message that trigger the program
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param message print the message to the IO that received the message that trigger the program
     */
    default void print(@NonNull String message) {
        print(d -> message);
    }


}
