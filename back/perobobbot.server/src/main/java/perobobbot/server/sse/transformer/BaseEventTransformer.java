package perobobbot.server.sse.transformer;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.server.sse.SSEvent;
import perobobbot.server.sse.SSEventAccess;

@RequiredArgsConstructor
public abstract class BaseEventTransformer<T> implements EventTransformer<T> {

    private final @NonNull DefaultPayloadConstructor payloadConstructor;

    @Getter
    private final @NonNull Class<T> eventType;


    @Override
    public @NonNull SSEvent transform(@NonNull T event) {
        final var eventName = getEventName();
        final var payload = createPayload(event);

        return new SSEvent(getAuthorizedLogins(event), eventName,payload);
    }

    protected @NonNull String createPayload(@NonNull T event) {
        return payloadConstructor.createPayload(event);
    }


    protected abstract @NonNull String getEventName();


    protected abstract @NonNull SSEventAccess getAuthorizedLogins(@NonNull T event);
}
