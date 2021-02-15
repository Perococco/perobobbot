package perobobbot.action;

import lombok.NonNull;
import perobobbot.lang.Nil;

/**
 * Launches an action from its type
 *  */
public interface ActionLauncher {

    /**
     * @param actionType the class of the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the parameter type
     * @return an action ticket
     */
    @NonNull
    <P,R> ActionTicket<R> pushAction(@NonNull Launchable<P,R> actionType, @NonNull P parameter);


    /**
     * @param actionType the class of the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the parameter type
     * @return n action ticket
     */
    @NonNull
    default <P,R> ActionTicket<R> pushAction(@NonNull Class<? extends Action<P,R>> actionType, @NonNull P parameter) {
        return pushAction(Launchable.single(actionType),parameter);
    }

    /**
     * short cut to <code>execute(actionType,Nil.NULL)</code>
     */
    @NonNull
    default <R> ActionTicket<R> pushAction(@NonNull Class<? extends Action<Nil,R>> actionType) {
        return pushAction(Launchable.single(actionType), Nil.NIL);
    }

    /**
     * short cut to <code>execute(actionType,Nil.NULL)</code>
     */
    @NonNull
    default <R> ActionTicket<R> pushAction(@NonNull Launchable<Nil,R> actionType) {
        return pushAction(actionType,Nil.NIL);
    }


}
