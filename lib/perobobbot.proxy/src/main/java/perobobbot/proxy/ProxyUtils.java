package perobobbot.proxy;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.proxy._private.InterceptorInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author perococco
 */
public class ProxyUtils {


    @SuppressWarnings("unchecked")
    public static <I> I proxy(@NonNull Class<I> interfaceClazz,@NonNull InvocationHandler handler) {
        return (I) Proxy.newProxyInstance(interfaceClazz.getClassLoader(), asArray(interfaceClazz), handler);
    }

    @SuppressWarnings("unchecked")
    public static <I> I proxy(@NonNull Class<I> interfaceClazz, @NonNull I instance, @NonNull Interceptor... interceptors) {
        return proxy(interfaceClazz, instance, ImmutableList.copyOf(interceptors));
    }

    @SuppressWarnings("unchecked")
    public static <I> I proxy(@NonNull Class<I> interfaceClazz, @NonNull I instance, @NonNull ImmutableList<Interceptor> interceptors) {
        final Object result = Proxy.newProxyInstance(interfaceClazz.getClassLoader(), asArray(interfaceClazz),
                                                     new InterceptorInvoker(instance, interceptors));
        return (I) result;
    }

    private static Class<?>[] asArray(Class<?>... args) {
        return args;
    }

}
