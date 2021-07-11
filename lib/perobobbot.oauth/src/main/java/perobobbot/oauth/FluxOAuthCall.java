package perobobbot.oauth;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import reactor.core.publisher.Flux;

public interface FluxOAuthCall<T> extends OAuthCall {

    @NonNull Flux<T> call(@NonNull ApiToken apiToken) throws Throwable;

    @NonNull BasicOAuthCall<ImmutableList<T>> toBasic();

    @Override
    default <R> @NonNull R accept(@NonNull Visitor<R> visitor) throws Throwable {
        return visitor.visit(this);
    }
}
