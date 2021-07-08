package perobobbot.oauth;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ApiProxy {

    public static @NonNull ImmutableMap<Method, ProxyMethod> mapProxyMethods(@NonNull Class<?> service, @NonNull Class<?> serviceWithToken) {
        return streamProxyMethods(service, serviceWithToken).collect(ImmutableMap.toImmutableMap(ProxyMethod::getMethodWithoutToken, m -> m));
    }

    public static Stream<ProxyMethod> streamProxyMethods(@NonNull Class<?> service, @NonNull Class<?> serviceWithToken) {
        return new ApiProxy(service, serviceWithToken).listProxyMethods();
    }

    private final @NonNull Class<?> service;
    private final @NonNull Class<?> serviceWithToken;

    private Method[] methods;
    private Map<String, List<Method>> methodsWithToken;

    private @NonNull Stream<ProxyMethod> listProxyMethods() {
        this.listServiceMethods();
        this.listServiceWithTokenMethods();
        return Arrays.stream(methods).map(this::createProxyMethod);
    }

    private void listServiceMethods() {
        this.methods = service.getMethods();
    }

    private void listServiceWithTokenMethods() {
        this.methodsWithToken = Arrays.stream(serviceWithToken.getMethods())
                                      .collect(Collectors.groupingBy(Method::getName));
    }

    private ProxyMethod createProxyMethod(Method method) {
        final var oauthRequirement = OAuthRequirement.fromMethod(method);

        return methodsWithToken.getOrDefault(method.getName(), List.of())
                               .stream()
                               .map(mWithToken -> createProxyMethod(method, mWithToken, oauthRequirement))
                               .flatMap(Optional::stream)
                               .findAny()
                               .orElseThrow(() -> new IllegalArgumentException("Could not find proxy for " + method));

    }


    private @NonNull Optional<ProxyMethod> createProxyMethod(@NonNull Method method,
                                                             @NonNull Method mWithToken,
                                                             @NonNull OAuthRequirement oauthRequirement) {
        if (!method.getReturnType().isAssignableFrom(mWithToken.getReturnType())) {
            return Optional.empty();
        }

        final var parametersOutside = method.getParameterTypes();
        final var parametersInside = mWithToken.getParameterTypes();

        if (parametersInside.length < parametersOutside.length) {
            return Optional.empty();
        }

        return Optional.of(new ProxyMethod(method, mWithToken, oauthRequirement, 0));
    }


}
