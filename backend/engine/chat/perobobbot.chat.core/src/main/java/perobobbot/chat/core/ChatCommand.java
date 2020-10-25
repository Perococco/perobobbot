package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;

public interface ChatCommand {

    @NonNull
    String name();

    void execute(@NonNull ExecutionContext context);
}
