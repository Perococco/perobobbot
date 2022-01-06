package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Platform implements IdentifiedEnum {
    TWITCH("Twitch"),
    DISCORD("Discord"),
    SPOTIFY("Spotify"),
    LOCAL("Local"),
    ;

    @Getter
    private final @NonNull String identification;

    public static @NonNull Stream<Platform> stream() {
        return Stream.of(TWITCH,LOCAL,DISCORD,SPOTIFY);
    }

    public boolean isLocal() {
        return LOCAL == this;
    }

}
