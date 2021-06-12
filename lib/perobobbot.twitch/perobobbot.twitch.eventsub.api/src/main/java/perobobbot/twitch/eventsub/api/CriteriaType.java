package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum CriteriaType implements IdentifiedEnum {
    BROADCASTER_USER_ID("broadcaster_user_id"),
    FROM_BROADCASTER_USER_ID("from_broadcaster_user_id"),
    TO_BROADCASTER_USER_ID("to_broadcaster_user_id"),
    REWARD_ID("reward_id"),
    EXTENSION_CLIENT_ID("extension_client_id"),
    CLIENT_ID("client_id"),
    USER_ID("user_id"),
    ;

    @Getter
    private final @NonNull String identification;
}
