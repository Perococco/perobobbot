package bot.twitch.chat;

import lombok.*;

/**
 * @author perococco
 **/
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Channel {

    @NonNull
    private final String name;


    @NonNull
    public static Channel create(@NonNull String channelName) {
        if (channelName.startsWith("#")) {
            return new Channel(channelName.substring(1).toLowerCase());
        } else {
            return new Channel(channelName.toLowerCase());
        }
    }

    @Override
    public String toString() {
        return '#'+ name;
    }
}
