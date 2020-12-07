package perobobbot.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class SmartLock implements Lock {

    @NonNull
    public static SmartLock reentrant() {
        return new SmartLock(new ReentrantLock());
    }

    @NonNull
    @Delegate
    private final Lock delegate;

    public void runLocked(@NonNull Runnable action) {
        delegate.lock();
        try {
            action.run();
        } finally {
            delegate.unlock();
        }
    }

    public void await(@NonNull Condition condition) throws InterruptedException {
        delegate.lock();
        try {
            condition.await();
        } finally {
            delegate.unlock();
        }
    }

    @NonNull
    public <V> V call(@NonNull InterruptibleCallable<V> callable) throws InterruptedException {
        delegate.lock();
        try {
            return callable.call();
        } finally {
            delegate.unlock();
        }
    }

    public <V> V get(@NonNull Supplier<V> supplier) {
        delegate.lock();
        try {
            return supplier.get();
        } finally {
            delegate.unlock();
        }
    }

    @NonNull
    public <T> T getLocked(@NonNull Supplier<T> action) {
        delegate.lock();
        try {
            return action.get();
        } finally {
            delegate.unlock();
        }
    }

}
