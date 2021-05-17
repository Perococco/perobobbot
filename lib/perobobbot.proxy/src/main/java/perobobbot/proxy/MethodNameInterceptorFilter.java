package perobobbot.proxy;

import lombok.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author perococco
 */
public class MethodNameInterceptorFilter implements InterceptorFilter {

    private final Set<String> methodNames = new HashSet<>();

    public MethodNameInterceptorFilter(@NonNull String... methodNames) {
        Collections.addAll(this.methodNames, methodNames);
    }

    @Override
    public @NonNull InterceptorFilter cached() {
        return new CachedInterceptorFilter(this);
    }

    @Override
    public boolean filtered(@NonNull MethodCallParameter methodCallParameter) {
        return this.methodNames.contains(methodCallParameter.getMethod().getName());
    }
}
