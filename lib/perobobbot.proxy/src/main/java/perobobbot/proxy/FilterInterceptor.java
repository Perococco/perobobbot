package perobobbot.proxy;

import lombok.NonNull;

/**
 * @author perococco
 */
public class FilterInterceptor implements Interceptor {

    private final InterceptorFilter filter;

    private final Interceptor filteredInterceptor;

    public FilterInterceptor(Interceptor filteredInterceptor, String... methodNames) {
        this.filteredInterceptor = filteredInterceptor;
        this.filter = new MethodNameInterceptorFilter(methodNames);
    }

    public FilterInterceptor(Interceptor filteredInterceptor, InterceptorFilter filter) {
        this.filteredInterceptor = filteredInterceptor;
        this.filter = filter;
    }

    @Override
    public Object intercept(@NonNull MethodCallParameter methodCallParameter, @NonNull InterceptorStack interceptorStack) throws Throwable {
        if (this.filter.filtered(methodCallParameter)) {
            return interceptorStack.callNextInterceptor(methodCallParameter);
        }
        return this.filteredInterceptor.intercept(methodCallParameter, interceptorStack);
    }

}
