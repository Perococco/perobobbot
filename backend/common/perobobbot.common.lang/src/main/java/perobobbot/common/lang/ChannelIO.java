package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;

public interface ChannelIO {

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