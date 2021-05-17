package perobobbot.proxy;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author perococco
 */
@Log4j2
public class LockInterceptor implements Interceptor {

    private final Lock lock;

    public LockInterceptor() {
        this(new ReentrantLock());
    }

    public LockInterceptor(Lock lock) {
        this.lock = lock;
    }

    @Override
    public Object intercept(@NonNull MethodCallParameter methodCallParameter, @NonNull InterceptorStack interceptorStack) throws Throwable {
        String name = null;
        boolean logEnabled = LOG.isDebugEnabled();


        if (logEnabled) {
            name = String.format("%s.%s", methodCallParameter.getInstance(), methodCallParameter.getMethod().getName());
            LOG.debug(String.format("[LOCKING] %s", name));
        }
        lock.lock();
        LOG.debug("[LOCKED ] {}", name);
        try {
            return interceptorStack.callNextInterceptor(methodCallParameter);
        } finally {
            lock.unlock();
            LOG.debug("[UNLOCK ] {}", name);
        }
    }
}
