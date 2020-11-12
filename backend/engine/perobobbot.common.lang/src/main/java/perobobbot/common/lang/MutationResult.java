package perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;

public class MutationResult<S,T> extends ProxyAsyncIdentity<S> {

    @NonNull
    @Getter
    private final T result;

    public MutationResult(@NonNull AsyncIdentity<S> delegate, @NonNull T result) {
        super(delegate);
        this.result = result;
    }

}
