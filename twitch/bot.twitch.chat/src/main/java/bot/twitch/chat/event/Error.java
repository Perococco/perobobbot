package bot.twitch.chat.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Error implements TwitchChatEvent {

    @NonNull
    public static Error with(@NonNull Throwable error) {
        return new Error(error);
    }

    @NonNull
    private final Throwable error;

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }
}
