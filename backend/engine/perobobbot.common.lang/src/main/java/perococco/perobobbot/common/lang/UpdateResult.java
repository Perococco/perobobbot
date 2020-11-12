package perococco.perobobbot.common.lang;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UpdateResult<R,S> {

    @NonNull
    private final R oldRoot;

    @NonNull
    private final R newRoot;

    @NonNull
    private final S result;
}
