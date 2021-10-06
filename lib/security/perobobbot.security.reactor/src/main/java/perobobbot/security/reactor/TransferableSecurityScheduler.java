package perobobbot.security.reactor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.oauth.TokenIdentifier;
import reactor.core.Disposable;
import reactor.core.scheduler.Scheduler;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class TransferableSecurityScheduler implements Scheduler {

    private final @NonNull Scheduler delegate;

    @Override
    public @NonNull Disposable schedule(@NonNull Runnable task) {
        return delegate.schedule(new TransferableSecurityRunnable(task));
    }

    @Override
    public @NonNull Worker createWorker() {
        return new TransferableSecurityWorker(delegate.createWorker());
    }

    @Override
    public @NonNull Disposable schedulePeriodically(@NonNull Runnable task, long initialDelay, long period, TimeUnit unit) {
        return delegate.schedulePeriodically(new TransferableSecurityRunnable(task),initialDelay,period,unit);
    }

    @Override
    public @NonNull Disposable schedule(@NonNull Runnable task, long delay,@NonNull TimeUnit unit) {
        return delegate.schedule(new TransferableSecurityRunnable(task),delay,unit);
    }

    @RequiredArgsConstructor
    private static class TransferableSecurityWorker implements Worker {

        private final Authentication authentication;
        private final TokenIdentifier tokenIdentifier;
        private final @NonNull Worker delegate;

        public TransferableSecurityWorker(@NonNull Worker delegate) {
            this.delegate = delegate;
            this.authentication = SecurityContextHolder.getContext().getAuthentication();
            this.tokenIdentifier = OAuthContextHolder.getContext().getTokenIdentifier().orElse(null);
        }

        @Override
        public @NonNull Disposable schedule(@NonNull Runnable task) {
            return delegate.schedule(new TransferableSecurityRunnable(task,authentication,tokenIdentifier));
        }

        @Override
        public @NonNull Disposable schedule(@NonNull Runnable task, long delay, @NonNull TimeUnit unit) {
            return delegate.schedule(new TransferableSecurityRunnable(task,authentication,tokenIdentifier), delay, unit);
        }

        @Override
        public @NonNull Disposable schedulePeriodically(@NonNull Runnable task, long initialDelay, long period, TimeUnit unit) {
            return delegate.schedulePeriodically(new TransferableSecurityRunnable(task,authentication,tokenIdentifier), initialDelay, period, unit);
        }

        @Override
        public void dispose() {
            delegate.dispose();
        }

        @Override
        public boolean isDisposed() {
            return delegate.isDisposed();
        }
    }

    @RequiredArgsConstructor
    private static class TransferableSecurityRunnable implements Runnable {

        private final @NonNull Runnable delegate;
        private final Authentication authentication;
        private final TokenIdentifier tokenIdentifier;

        public TransferableSecurityRunnable(@NonNull Runnable delegate) {
            this.delegate = delegate;
            this.authentication = SecurityContextHolder.getContext().getAuthentication();
            this.tokenIdentifier = OAuthContextHolder.getContext().getTokenIdentifier().orElse(null);
        }

        @Override
        public void run() {
            final var currentAuth = Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
            final var currentToken = OAuthContextHolder.getContext().getTokenIdentifier();
            try {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                OAuthContextHolder.getContext().setTokenIdentifier(tokenIdentifier);
                delegate.run();
            } finally {
                currentAuth.ifPresentOrElse(SecurityContextHolder.getContext()::setAuthentication, SecurityContextHolder::clearContext);
                currentToken.ifPresentOrElse(OAuthContextHolder.getContext()::setTokenIdentifier, OAuthContextHolder::remove);
            }
        }
    }
}
