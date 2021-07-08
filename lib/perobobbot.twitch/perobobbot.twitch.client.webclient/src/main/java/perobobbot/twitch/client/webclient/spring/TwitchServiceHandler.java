package perobobbot.twitch.client.webclient.spring;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Platform;
import perobobbot.oauth.*;
import perobobbot.oauth.tools.ApiTokenHelperFactory;
import perobobbot.oauth.tools.OAuthCallHelper;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.stream.IntStream;

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

        final var call = new BasicOAuthCall(methodWithToken, prepareProxyArguments(args, tokenIndex), tokenIndex);

        final var helper = OAuthContextHolder.getContext().getTokenIdentifier()
                                             .map(t -> factory.withToken(t))
                                             .orElse(factory::createWithoutToken)
                                             .f(Platform.TWITCH, proxyMethod.getOAuthRequirement());

        return OAuthCallHelper.callWithTokenIdentifier(call, helper);
    }

    private Optional<Object> evaluateNativeMethod(Method method) {
        return switch (method.getName()) {
            case "hashCode" -> Optional.of(twitchServiceWithToken.hashCode());
            case "toString" -> Optional.of(twitchServiceWithToken.toString());
            default -> Optional.empty();
        };
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

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    private class BasicOAuthCall implements OAuthCall {

        private final @NonNull Method method;
        private final Object[] args;
        private final int tokenIndex;

        @Override
        public Object call(@NonNull ApiToken apiToken) throws Throwable {
            args[tokenIndex] = apiToken;
            return method.invoke(twitchServiceWithToken, args);
        }
    }


}
