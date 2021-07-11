package perobobbot.twitch.client.webclient.spring;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.*;
import perobobbot.oauth.tools.*;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

public class TwitchServiceHandler implements InvocationHandler {

    public static @NonNull TwitchService createService(
            @NonNull TwitchServiceWithToken twitchServiceWithToken,
            @NonNull ApiTokenHelperFactory factory) {
        return (TwitchService) Proxy.newProxyInstance(twitchServiceWithToken.getClass().getClassLoader(),
                new Class<?>[]{TwitchService.class}, new TwitchServiceHandler(twitchServiceWithToken, factory));
    }

    private final @NonNull TwitchServiceWithToken twitchServiceWithToken;

    private final @NonNull ApiTokenHelperFactory factory;

    private final ImmutableMap<Method, ProxyMethod> proxyMethods;

    public TwitchServiceHandler(@NonNull TwitchServiceWithToken twitchServiceWithToken,
                                @NonNull ApiTokenHelperFactory factory) {
        this.twitchServiceWithToken = twitchServiceWithToken;
        this.factory = factory;
        this.proxyMethods = ApiProxy.mapProxyMethods(TwitchService.class, TwitchServiceWithToken.class);

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args == null || args.length == 0) {
            final var result = evaluateNativeMethod(method);
            if (result.isPresent()) {
                return result.get();
            }
        }

        final var proxyMethod = proxyMethods.get(method);
        final int tokenIndex = proxyMethod.getTokenPosition().orElse(-1);
        final Method methodWithToken = proxyMethod.getMethodWithToken();
        if (tokenIndex < 0) {
            return methodWithToken.invoke(twitchServiceWithToken, args);
        }

        final var call = OAuthCallFactory.create(twitchServiceWithToken, methodWithToken, args, tokenIndex);

        final var token = OAuthContextHolder.getContext().getTokenIdentifier();

        final var helper = token
                .map(factory::withToken)
                .orElse(factory::createWithoutToken)
                .f(Platform.TWITCH, proxyMethod.getOAuthRequirement());

        final var callHelper = call.accept(new OAuthCall.Visitor<OAuthCallHelper<?>>() {
            @Override
            public OAuthCallHelper<?> visit(@NonNull BasicOAuthCall<?> call) throws Throwable {
                return new BasicOAuthCallHelper<>(call, helper);
            }

            @Override
            public OAuthCallHelper<?> visit(@NonNull MonoOAuthCall<?> call) throws Throwable {
                return new MonoOAuthCallHelper<>(call, helper);
            }

            @Override
            public OAuthCallHelper<?> visit(@NonNull FluxOAuthCall<?> call) throws Throwable {
                return new FluxOAuthCallHelper<>(call, helper);
            }
        });

        return callHelper.call();
    }

    private Optional<Object> evaluateNativeMethod(Method method) {
        return switch (method.getName()) {
            case "hashCode" -> Optional.of(twitchServiceWithToken.hashCode());
            case "toString" -> Optional.of(twitchServiceWithToken.toString());
            default -> Optional.empty();
        };
    }


}
