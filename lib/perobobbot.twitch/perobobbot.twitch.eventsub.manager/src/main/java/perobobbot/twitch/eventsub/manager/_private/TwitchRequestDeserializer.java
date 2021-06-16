package perobobbot.twitch.eventsub.manager._private;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.EventSubVerification;
import perobobbot.twitch.eventsub.api.Markers;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TwitchRequestDeserializer {

    public static @NonNull Optional<EventSubRequest> deserialize(@NonNull ObjectMapper objectMapper, @NonNull String type, @NonNull byte[] content) {
        return new TwitchRequestDeserializer(objectMapper,type, content).deserialize();
    }

    private static final ImmutableMap<String,Class<? extends EventSubRequest>> CLASS_PER_TYPE = ImmutableMap.of(
            "notification", EventSubNotification.class,
            "webhook_callback_verification", EventSubVerification.class,
            "revocation", EventSubVerification.class
    );

    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull String type;
    private final @NonNull byte[] content;

    private Optional<EventSubRequest> deserialize() {
        final var clazz = CLASS_PER_TYPE.get(type);
        if (clazz == null) {
            LOG.error(Markers.EVENT_SUB_MARKER, "Unknown EventSub Twitch request type {}",type);
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(objectMapper.readValue(content, clazz));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
