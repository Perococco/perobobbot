package perobobbot.proxy._private;

import com.google.common.collect.ImmutableList;
import perobobbot.proxy.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * @author perococco
 */
public class InterceptorInvoker implements InvocationHandler {

    private Object instance;

    private final ImmutableList<Interceptor> interceptors;

    public InterceptorInvoker(Object instance, Interceptor... interceptors) {
        this(instance, ImmutableList.copyOf(interceptors));
    }

    public InterceptorInvoker(Object instance, Collection<Interceptor> interceptors) {
        this.instance = instance;
        this.interceptors = interceptors == null ? ImmutableList.of() : ImmutableList.copyOf(interceptors);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvocationHandler delegate = new InterceptorHandler(this.instance, interceptors);
        return delegate.invoke(proxy, method, args);
    }

    @SuppressWarnings("unchecked")
    public <I> I proxy(Class<I> interfaceClass) {
        if (!interfaceClass.isAssignableFrom(instance.getClass())) {
            throw new IllegalArgumentException(
                    String.format("Instance (%s) of this InterceptorInvoker should implements the given interface (%s)",
                                  this.instance.getClass(), interfaceClass.getClass()));
        }
        return (I) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, this);
    }

}
