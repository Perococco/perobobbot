package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.fp.Function1;

public interface ProgramIO {

    void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    default void print(@NonNull String message) {
        print(d -> message);
    }


}
