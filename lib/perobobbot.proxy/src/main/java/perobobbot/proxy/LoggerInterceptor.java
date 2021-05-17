package perobobbot.proxy;

import lombok.NonNull;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @author perococco
 */
public class LoggerInterceptor implements Interceptor {

    private final Logger log;

    private final BiConsumer<String, Throwable> exceptionLogger;

    private Set<String> objectMethods = new HashSet<>();


    public LoggerInterceptor(Logger log) {
        this(log, true);
    }

    public LoggerInterceptor(Logger log, boolean filterObject) {
        this(log, filterObject, (String[]) null);
    }

    public LoggerInterceptor(Logger log, String... skipMethodNames) {
        this(log, true, skipMethodNames);
    }

    public LoggerInterceptor(Logger log, boolean filterObject, String... skipMethodNames) {
        this.log = log;
        this.exceptionLogger = (message, error) -> {
            log.warn(message, error.getMessage());
            log.debug(error);
        };
        if (filterObject) {
            Collections.addAll(objectMethods, "toString", "clone", "hashCode", "equals", "getClass");
        }
        if (skipMethodNames != null) {
            Collections.addAll(objectMethods, skipMethodNames);
        }

    }


    @Override
    public Object intercept(@NonNull MethodCallParameter methodCallParameter, @NonNull InterceptorStack interceptorStack) throws Throwable {
        final boolean logEnabled = log.isDebugEnabled() && !objectMethods.contains(
                methodCallParameter.getMethod().getName());
        if (logEnabled) {
            log.debug("[ENTERING  ] {}", methodCallParameter.getMethodNameWithArgs());
        }
        try {
            Object result = interceptorStack.callNextInterceptor(methodCallParameter);
            if (logEnabled) {
                log.debug("[LEAVING   ] {} --> '{}'", methodCallParameter.getMethodName(), result);
            }
            return result;
        } catch (Throwable throwable) {
            if (logEnabled) {
                exceptionLogger.accept(String.format("[EXCEPTION ] %s", methodCallParameter.getMethodName()),
                                       throwable);
                log.debug("[LEAVING   ] {} --> Failed", methodCallParameter.getMethodName());
            }
            throw throwable;
        }
    }
}
