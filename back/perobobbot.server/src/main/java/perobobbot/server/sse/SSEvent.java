package perobobbot.server.sse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class SSEvent {

    @NonNull SSEventAccess access;

    @NonNull String eventName;

    @NonNull String payload;

}
