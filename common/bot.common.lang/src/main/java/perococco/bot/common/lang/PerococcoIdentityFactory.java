package perococco.bot.common.lang;

import bot.common.lang.Identity;
import bot.common.lang.IdentityFactory;
import bot.common.lang.IdentityHashSet;
import bot.common.lang.ThrowableTool;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.Set;

/**
 * @author perococco
 **/
@Log4j2
public enum PerococcoIdentityFactory implements IdentityFactory {
    INSTANCE,
    ;

    private final ReferenceQueue<PerococcoIdentity<?>> referenceQueue = new ReferenceQueue<>();

    private final Set<Reference<?>> references = new IdentityHashSet<>();

    @Getter
    private final int priority = Integer.MIN_VALUE;

    PerococcoIdentityFactory() {
        final Thread thread = new Thread(new Runner(),"Identity Factory Releaser");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public @NonNull <S> Identity<S> create(@NonNull S initialValue) {
        final PerococcoIdentity<S> identity = new PerococcoIdentity<>(initialValue);
        identity.start();
        references.add(new WeakReference<>(identity,referenceQueue));
        return identity;
    }

    private class Runner implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    doRun();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private void doRun() throws InterruptedException {
            final Reference<? extends PerococcoIdentity<?>> reference  =referenceQueue.remove();
            try {
                if (references.remove(reference)) {
                    Optional.ofNullable(reference.get()).ifPresent(PerococcoIdentity::stop);
                }
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn("Error while stopping identity",e);
            }
        }
    }


}
