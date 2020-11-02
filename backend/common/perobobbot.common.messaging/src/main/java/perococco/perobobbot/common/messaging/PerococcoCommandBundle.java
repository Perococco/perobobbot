package perococco.perobobbot.common.messaging;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Subscription;
import perobobbot.common.messaging.Command;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.CommandBundle;


@RequiredArgsConstructor
public class PerococcoCommandBundle implements CommandBundle {

    @NonNull
    private final ChatController chatController;

    @NonNull
    private final ImmutableList<? extends Command> commands;

    @Override
    public Subscription attachCommandsToChat() {
        return commands.stream()
                       .map(chatController::addCommand)
                       .collect(Subscription.COLLECTOR);
    }

}
