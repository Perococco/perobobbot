package perobobbot.proxy._private;

import com.google.common.collect.ImmutableList;
import perobobbot.proxy.Interceptor;
import perobobbot.proxy.MethodCallParameter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author perococco
 */
public class InterceptorHandler implements InvocationHandler {

    private final Object instance;

    private final InterceptorStackElement interceptorStackElement;

    public InterceptorHandler(Object instance, ImmutableList<Interceptor> interceptors) {
        this.instance = instance;
        InterceptorStackElement element = null;
        if (interceptors != null && interceptors.size() > 0) {
            for (Interceptor interceptor : interceptors.reverse()) {
                element = new InterceptorStackElementImpl(interceptor, element);
            }
        }
        this.interceptorStackElement = element;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        Throwable throwable = null;

        MethodCallParameter methodCallParameter = new MethodCallParameter(
                this.instance, method, args);

        try {
            result = interceptorStackElement.intercept(methodCallParameter);
        } catch (Throwable t) {
            throwable = t;
        }

        if (throwable != null) {
            throw throwable;
        }
        return result;
    }

}
