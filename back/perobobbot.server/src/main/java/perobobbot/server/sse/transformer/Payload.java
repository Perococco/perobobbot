package perobobbot.server.sse.transformer;

import lombok.NonNull;
import lombok.Value;

@Value
public final class Payload {

    public Payload(Object value) {
        this.type = value.getClass().getName();
        this.value = value;
    }

    @NonNull String type;

    @NonNull Object value;
}
