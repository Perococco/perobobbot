package perobobbot.proxy._private;

import perobobbot.proxy.Interceptor;
import perobobbot.proxy.InterceptorStack;
import perobobbot.proxy.MethodCallParameter;

/**
 * @author perococco
 */
public class InterceptorStackElementImpl implements InterceptorStackElement {

    private final Interceptor interceptor;

    private final InterceptorStack stack;

    public InterceptorStackElementImpl(Interceptor interceptor, InterceptorStackElement next) {
        this.interceptor = interceptor;
        this.stack = new InterceptorStackImpl(next);
    }

    @Override
    public Object intercept(MethodCallParameter parameter) throws Throwable {
        return this.interceptor.intercept(parameter, this.stack);
    }
}
