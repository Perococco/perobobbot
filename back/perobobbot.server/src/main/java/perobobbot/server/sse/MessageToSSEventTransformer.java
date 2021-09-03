package perobobbot.server.sse;

import lombok.NonNull;

public interface MessageToSSEventTransformer {

    @NonNull SSEvent transform(@NonNull Object message);
}
