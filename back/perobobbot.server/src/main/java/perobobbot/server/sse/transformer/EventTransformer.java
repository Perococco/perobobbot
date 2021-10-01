package perobobbot.server.sse.transformer;

import lombok.NonNull;
import perobobbot.server.sse.SSEvent;

public interface EventTransformer<T> {

    @NonNull Class<T> getEventType();

    @NonNull SSEvent transform(@NonNull T event);
}
