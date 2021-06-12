package perobobbot.twitch.eventsub.api;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum TransportMethod implements IdentifiedEnum {
    WEBHOOK("webhook"),
    ;

    @Getter
    private final @NonNull String identification;

}
