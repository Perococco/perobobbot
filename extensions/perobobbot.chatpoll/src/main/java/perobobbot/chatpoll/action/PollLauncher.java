package perobobbot.chatpoll.action;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.chatpoll.ChatPollExtension;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perococco.perobobbot.poll.PollConfiguration;

import java.time.Duration;

@RequiredArgsConstructor
public class PollLauncher implements CommandAction {

    private final @NonNull ChatPollExtension pollExtension;

    private final @NonNull IO io;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        pollExtension.start(ImmutableSet.of("A", "B", "C"), new PollConfiguration(true,true), Duration.ofSeconds(30));
        io.send(context.getChatConnectionInfo(), context.getChannelName(), "Poll started");
    }
}
