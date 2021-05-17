package perobobbot.proxy;

import lombok.NonNull;

/**
 * @author perococco
 */
public interface Interceptor {

    /**
     * @param methodCallParameter the information about the called method
     * @param interceptorStack the interceptor stack to call to continue the chain of interceptors
     * @return the return of the called method (or a modified version)
     * @throws Throwable
     */
    Object intercept(@NonNull MethodCallParameter methodCallParameter,@NonNull InterceptorStack interceptorStack) throws Throwable;

}
