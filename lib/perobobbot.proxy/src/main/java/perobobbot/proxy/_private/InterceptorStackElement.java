package perobobbot.proxy._private;

import lombok.NonNull;
import perobobbot.proxy.MethodCallParameter;

/**
 * @author perococco
 */
public interface InterceptorStackElement {

    Object intercept(@NonNull MethodCallParameter parameter) throws Throwable;

}
