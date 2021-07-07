package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Platform implements IdentifiedEnum {
    TWITCH("Twitch"),
    LOCAL("Local"),
    ;

    @Getter
    private final @NonNull String identification;

    public boolean isLocal() {
        return LOCAL == this;
    }


    public static @NonNull Stream<Platform> platformWithSubscriptions() {
        return Stream.of(
                TWITCH
        );
    }



}
