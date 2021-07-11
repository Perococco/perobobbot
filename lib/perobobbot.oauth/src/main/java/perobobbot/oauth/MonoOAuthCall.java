package perobobbot.oauth;

import lombok.NonNull;
import reactor.core.publisher.Mono;

public interface MonoOAuthCall<T> extends OAuthCall {

    @NonNull Mono<T> call(@NonNull ApiToken apiToken) throws Throwable;

    @NonNull BasicOAuthCall<T> toBasic();

    @Override
    default <R> @NonNull R accept(@NonNull Visitor<R> visitor) throws Throwable {
        return visitor.visit(this);
    }

}
