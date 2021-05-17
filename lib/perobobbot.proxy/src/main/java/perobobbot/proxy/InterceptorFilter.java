package perobobbot.proxy;

import lombok.NonNull;

/**
 * @author perococco
 */
public interface InterceptorFilter {

    boolean filtered(@NonNull MethodCallParameter methodCallParameter);

    @NonNull InterceptorFilter cached();
}
