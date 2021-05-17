package perobobbot.proxy._private;

import perobobbot.proxy.MethodCallParameter;

/**
 * @author perococco
 */
public class InvokerStackElement implements InterceptorStackElement {

    @Override
    public Object intercept(MethodCallParameter parameter) throws Throwable {
        return parameter.invoke();
    }
}
