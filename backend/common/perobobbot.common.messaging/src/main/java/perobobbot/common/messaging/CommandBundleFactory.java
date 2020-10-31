package perobobbot.common.messaging;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;
import perococco.perobobbot.common.messaging.PerococcoCommandFactory;

public interface CommandBundleFactory<P> {

    @NonNull
    CommandBundle create(@NonNull P parameter);

    @NonNull
    static <P> CommandBundleFactory<P> with(@NonNull ChatController chatController, @NonNull Function1<? super P, ImmutableList<? extends ChatCommand>> commands) {
        return new PerococcoCommandFactory<>(chatController, commands);
    }

}
