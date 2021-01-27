package perobobbot.play.action;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.play.PlayExtension;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListSound implements CommandAction {

    private final @NonNull PlayExtension playExtension;
    private final @NonNull IO io;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final var sounds = playExtension.getAvailableSounds();
        final var message = formMessage(sounds);
        io.send(context.getChatConnectionInfo(),context.getChannelName(),message);
    }

    private String formMessage(@NonNull ImmutableSet<String> sounds) {
        return sounds.stream().collect(Collectors.joining(
                ", ","Sons disponibles : ",""
        ));
    }
}
