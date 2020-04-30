package bot.common.lang;

import lombok.Getter;
import lombok.NonNull;

public class MutationResult<S,T> extends ProxyIdentity<S> {

    @NonNull
    @Getter
    private final T result;

    public MutationResult(@NonNull Identity<S> delegate, @NonNull T result) {
        super(delegate);
        this.result = result;
    }

}
