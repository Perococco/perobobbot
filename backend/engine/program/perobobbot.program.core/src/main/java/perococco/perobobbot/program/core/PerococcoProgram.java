package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.BackgroundTask;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.MessageHandler;
import perobobbot.program.core.Program;

import java.util.Optional;

@RequiredArgsConstructor
public class PerococcoProgram implements Program {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final BackgroundTask backgroundTask;

    @NonNull
    private final ImmutableMap<String, CommandWithPolicyHandling> chatCommands;

    @NonNull
    private final MessageHandler messageHandler;

    public void cleanUp() {
        chatCommands.values().forEach(CommandWithPolicyHandling::cleanup);
    }

    @Override
    public void start() {
        backgroundTask.start();
    }

    @Override
    public void stop() {
        backgroundTask.stop();
    }

    @Override
    public Optional<ChatCommand> findChatCommand(@NonNull String commandName) {
        return Optional.ofNullable(chatCommands.get(commandName));
    }

    @Override
    public @NonNull MessageHandler getMessageHandler() {
        return messageHandler;
    }

    @Override
    public String toString() {
        return "PerococcoProgram{" + name  + '}';
    }
}
