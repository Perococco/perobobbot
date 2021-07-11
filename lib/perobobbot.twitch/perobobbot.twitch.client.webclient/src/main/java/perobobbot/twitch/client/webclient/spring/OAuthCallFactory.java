package perobobbot.twitch.client.webclient.spring;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.FluxTools;
import perobobbot.oauth.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.stream.IntStream;

public class OAuthCallFactory {


    public static @NonNull OAuthCall create(@NonNull Object target, @NonNull Method method, Object[] args, int tokenIndex) {
        final var returnType = method.getReturnType();
        final var preparedArguments = prepareProxyArguments(args,tokenIndex);
        if (returnType.equals(Mono.class)) {
            return new M<>(target,method,preparedArguments,tokenIndex);
        }
        if (returnType.equals(Flux.class)) {
            return new F<>(target,method,preparedArguments,tokenIndex);
        }
        return new B<>(target,method,preparedArguments,tokenIndex);
    }

    private static class B<T> extends OAuthCallBase<T> implements BasicOAuthCall<T> {
        public B(Object target, @NonNull Method method, Object[] args, int tokenIndex) {
            super(target, method, args, tokenIndex);
        }
    }

    private static class M<T> extends OAuthCallBase<Mono<T>> implements MonoOAuthCall<T> {
        public M(Object target, @NonNull Method method, Object[] args, int tokenIndex) {
            super(target, method, args, tokenIndex);
        }

        @Override
        public @NonNull BasicOAuthCall<T> toBasic() {
            return apiToken -> M.this.call(apiToken).block();
        }
    }

    private static class F<T> extends OAuthCallBase<Flux<T>> implements FluxOAuthCall<T> {
        public F(Object target, @NonNull Method method, Object[] args, int tokenIndex) {
            super(target, method, args, tokenIndex);
        }

        @Override
        public @NonNull BasicOAuthCall<ImmutableList<T>> toBasic() {
            return apiToken -> FluxTools.toCompletionStage(F.this.call(apiToken)).toCompletableFuture().get();
        }
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private static abstract class OAuthCallBase<R> implements OAuthCall {

        protected final Object target;
        protected final @NonNull Method method;
        protected final Object[] args;
        protected final int tokenIndex;

        public @NonNull R call(@NonNull ApiToken apiToken) throws Throwable {
            args[tokenIndex] = apiToken;
            return (R)method.invoke(target, args);
        }
    }

    private static Object[] prepareProxyArguments(Object[] args, int tokenIndex) {
        if (args == null) {
            return new Object[1];
        }
        return IntStream.range(0, args.length + 1)
                        .mapToObj(i -> switch (Integer.signum(i - tokenIndex)) {
                            case -1 -> args[i];
                            case 1 -> args[i - 1];
                            default -> null;
                        }).toArray();
    }

}
