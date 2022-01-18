package perobobbot.oauth.tools._private;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.*;
import perobobbot.oauth.tools.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

public class ServiceWithTokenHandler<T> implements InvocationHandler {

    private final @NonNull T serviceWithToken;

    private final @NonNull Platform platform;

    private final @NonNull ApiTokenHelperFactory factory;

    private final ImmutableMap<Method, ProxyMethod> proxyMethods;

    public ServiceWithTokenHandler(@NonNull Platform platform,
                                   @NonNull T serviceWithToken,
                                   @NonNull ApiTokenHelperFactory factory,
                                   @NonNull ImmutableMap<Method, ProxyMethod> proxyMethods) {
        this.serviceWithToken = serviceWithToken;
        this.platform = platform;
        this.factory = factory;
        this.proxyMethods = proxyMethods;

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
            return methodWithToken.invoke(serviceWithToken, args);
        }

        final var call = OAuthCallFactory.create(serviceWithToken, methodWithToken, args, tokenIndex);

        final var token = OAuthContextHolder.getContext().getTokenIdentifier();

        final var helper = token
                .map(factory::withToken)
                .orElse(factory::createWithoutToken)
                .f(platform, proxyMethod.getOAuthRequirement());

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
            case "hashCode" -> Optional.of(serviceWithToken.hashCode());
            case "toString" -> Optional.of(serviceWithToken.toString());
            default -> Optional.empty();
        };
    }


}
