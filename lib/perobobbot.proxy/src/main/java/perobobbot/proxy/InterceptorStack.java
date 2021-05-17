package perobobbot.proxy;

import lombok.NonNull;

/**
 * @author perococco
 */
public interface InterceptorStack {

    Object callNextInterceptor(@NonNull MethodCallParameter methodCallParameter) throws Throwable;

}
