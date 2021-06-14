package perobobbot.twitch.eventsub.api.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum StreamType implements IdentifiedEnum {
    LIVE("live"),
    PLAYLIST("playlist"),
    WATCH_PARTY("watch_party"),
    PREMIERE("premiere"),
    RERUN("rerun")
    ;
    @Getter private final @NonNull String identification;
}
