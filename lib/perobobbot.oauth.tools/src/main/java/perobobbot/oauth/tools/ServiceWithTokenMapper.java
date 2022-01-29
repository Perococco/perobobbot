package perobobbot.oauth.tools;

import lombok.NonNull;
import perobobbot.lang.Platform;
import perobobbot.oauth.tools._private.DefaultServiceWithTokenMapper;

public interface ServiceWithTokenMapper {

    @NonNull <T,S> S mapService(@NonNull Platform platform,@NonNull T serviceWithToken, @NonNull Class<T> serviceWithTokenType, @NonNull Class<S> serviceType);

    static @NonNull ServiceWithTokenMapper create(@NonNull ApiTokenHelperFactory apiTokenHelperFactory) {
        return new DefaultServiceWithTokenMapper(apiTokenHelperFactory);
    }
}