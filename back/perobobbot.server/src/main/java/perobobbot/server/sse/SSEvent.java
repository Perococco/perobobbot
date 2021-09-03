package perobobbot.server.sse;

import lombok.NonNull;
import lombok.Value;

@Value
public class SSEvent {

    @NonNull String eventName;

    @NonNull String payload;

}
