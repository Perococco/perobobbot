package bot.program.core;

import bot.common.lang.User;
import bot.common.lang.UserRole;
import lombok.NonNull;

public interface ExecutionContext extends ProgramIO {

    @NonNull
    User executingUser();

    @NonNull
    String rawPayload();

    @NonNull
    String message();

    default boolean canExecutingUserActAs(@NonNull UserRole userRole) {
        return executingUser().canActAs(userRole);
    }
}
