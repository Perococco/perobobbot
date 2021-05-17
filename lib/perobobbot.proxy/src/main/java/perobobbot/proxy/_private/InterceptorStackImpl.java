package perobobbot.proxy._private;

import lombok.NonNull;
import perobobbot.proxy.InterceptorStack;
import perobobbot.proxy.MethodCallParameter;

/**
 * @author perococco
 */
public class InterceptorStackImpl implements InterceptorStack {

    private final InterceptorStackElement element;

    public InterceptorStackImpl(InterceptorStackElement element) {
        this.element = element;
    }

    @Override
    public Object callNextInterceptor(@NonNull MethodCallParameter methodCallParameter) throws Throwable {
        if (element != null) {
            return element.intercept(methodCallParameter);
        } else {
            return methodCallParameter.invoke();
        }
    }
}
