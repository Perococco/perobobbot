package perobobbot.oauth;

import lombok.NonNull;

public interface OAuthCall {

    <R> @NonNull R accept(@NonNull Visitor<R> visitor)  throws Throwable;

    interface Visitor<R> {
        R visit(@NonNull BasicOAuthCall<?> call) throws Throwable;
        R visit(@NonNull MonoOAuthCall<?> call) throws Throwable;
        R visit(@NonNull FluxOAuthCall<?> call) throws Throwable;
    }

}
