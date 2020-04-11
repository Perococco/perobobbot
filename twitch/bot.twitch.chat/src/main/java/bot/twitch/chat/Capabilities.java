package bot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public enum Capabilities {
    COMMANDS("twitch.tv/commands"),
    TAGS("twitch.tv/tags"),
    MEMBERSHIP("twitch.tv/membership"),
    ;

    @NonNull
    @Getter
    private final String ircValue;

}
