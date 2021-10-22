package perobobbot.endpoint;

import lombok.NonNull;
import perobobbot.security.reactor.TransferableSecurityScheduler;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public interface EndPoint<I> {

    Scheduler SCHEDULER = new TransferableSecurityScheduler(Schedulers.boundedElastic());

    @NonNull Class<I> getBodyType();

    Object handle(I body);

}

