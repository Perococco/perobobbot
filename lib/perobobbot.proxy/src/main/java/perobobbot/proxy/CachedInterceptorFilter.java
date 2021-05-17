package perobobbot.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class CachedInterceptorFilter implements InterceptorFilter {

    private final Map<Method, Boolean> cached = new HashMap<>();

    private final @NonNull InterceptorFilter cachedFilter;

    @Override
    public @NonNull InterceptorFilter cached() {
        return this;
    }

    @Override
    public boolean filtered(@NonNull MethodCallParameter methodCallParameter) {
        boolean result;
        Boolean cached = this.cached.get(methodCallParameter.getMethod());
        if (cached == null) {
            result = cachedFilter.filtered(methodCallParameter);
            this.cached.put(methodCallParameter.getMethod(), result);
        } else {
            result = cached;
        }
        return result;
    }
}
