package perococco.perobobbot.common.messaging;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.fp.Function1;
import perobobbot.common.messaging.ChatCommand;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.CommandBundle;
import perobobbot.common.messaging.CommandBundleFactory;


@RequiredArgsConstructor
public class PerococcoCommandFactory<P> implements CommandBundleFactory<P> {

    @NonNull
    private final ChatController chatController;

    @NonNull
    private final Function1<? super P, ImmutableList<? extends ChatCommand>> commands;

    @Override
    public @NonNull CommandBundle create(@NonNull P parameter) {
        return new PerococcoCommandBundle(chatController,commands.f(parameter));
    }

}
