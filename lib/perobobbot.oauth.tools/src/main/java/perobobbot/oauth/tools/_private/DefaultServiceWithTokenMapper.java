package perobobbot.oauth.tools._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Platform;
import perobobbot.oauth.ApiProxy;
import perobobbot.oauth.tools.ApiTokenHelperFactory;
import perobobbot.oauth.tools.ServiceWithTokenMapper;

import java.lang.reflect.Proxy;

@RequiredArgsConstructor
public class DefaultServiceWithTokenMapper implements ServiceWithTokenMapper {

    private final @NonNull ApiTokenHelperFactory apiTokenHelperFactory;

    @Override
    public <T, S> @NonNull S mapService(@NonNull Platform platform,  @NonNull T serviceWithToken, @NonNull Class<T> serviceWithTokenType, @NonNull Class<S> serviceType) {
        final var proxyMethods = ApiProxy.mapProxyMethods(serviceType, serviceWithTokenType);
        final var serviceHandler = new ServiceWithTokenHandler<>(platform,serviceWithToken, apiTokenHelperFactory, proxyMethods);

        final var proxy = Proxy.newProxyInstance(serviceWithTokenType.getClassLoader(),
                new Class<?>[]{serviceType},
                serviceHandler);

        return serviceType.cast(proxy);
    }
}
