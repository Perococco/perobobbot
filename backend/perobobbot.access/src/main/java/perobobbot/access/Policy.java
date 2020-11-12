package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.lang.fp.Consumer1;
import perococco.perobobbot.access.ProviderFromContext;

import java.util.UUID;

/**
 * A policy define how often and by whom an action ({@link Consumer1} or {@link java.util.concurrent.Executor})
 * can be executed.
 *
 * The rules ("how often" and "by whom") are defined at the creation of the policy
 * by the {@link PolicyManager}.
 */
public interface Policy {

    /**
     * @return an id to this policy
     */
    @NonNull
    UUID getId();

    /**
     * Create a child policy. This policy must be accepted before the child policy is even tested
     * @param rule the rule of the child policy
     * @return the child policy
     */
    @NonNull
    Policy createChild(@NonNull AccessRule rule);

    /**
     * @param action the action managed by this policy
     * @param accessInfoExtractor an extractor of the information necessary for the policy to work from the action parameter
     * @param <P> the type of the action parameter
     * @return an access point that execute the provided action in this policy permits it
     */
    @NonNull <P> AccessPoint<P> createAccessPoint(@NonNull Consumer1<? super P> action, @NonNull AccessInfoExtractor<P> accessInfoExtractor);

    /**
     * Same as {@link #createAccessPoint(Consumer1, AccessInfoExtractor)}, but the information
     * are retrived directly from the execution context
     * @param action the action using an execution context as parametrer
     * @return an access point that execute the provided action in this policy permits it
     */
    default @NonNull AccessPoint<ExecutionContext> createAccessPoint(@NonNull Consumer1<? super ExecutionContext> action) {
        return createAccessPoint(action,new ProviderFromContext());
    }

    /**
     * Same as {@link #createAccessPoint(Consumer1, AccessInfoExtractor)}, but the information
     * are retrived directly from the execution context
     * @param action the action using an execution context as parametrer
     * @return an access point that execute the provided action in this policy permits it
     */
    default @NonNull AccessPoint<ExecutionContext> createAccessPoint(@NonNull Executor<? super ExecutionContext> action) {
        return createAccessPoint(action::execute,new ProviderFromContext());
    }

}
