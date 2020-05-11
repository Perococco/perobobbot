package perobobbot.program.core;

import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.fp.Function1;
import lombok.NonNull;

public interface ProgramIO {

    void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    default void print(@NonNull String message) {
        print(d -> message);
    }


}
