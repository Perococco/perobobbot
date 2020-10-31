package perococco.perobobbot.common.messaging;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.common.lang.*;
import perobobbot.common.messaging.ChatCommand;
import perobobbot.common.messaging.ChatController;

@RequiredArgsConstructor
public class PerococcoChatController implements ChatController {

    @NonNull
    private final ImmutableMap<Platform, Character> prefixes;

    private ImmutableMap<String,  ChatCommand> commands = ImmutableMap.of();

    private ImmutableList<MessageHandler> listeners = ImmutableList.of();

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        final char prefix = getPrefix(messageContext.getPlatform());
        ExecutionContext.from(prefix, messageContext)
                        .ifPresentOrElse(this::executeCommand, () -> warnListeners(messageContext));
    }

    private void warnListeners(@NonNull MessageContext messageContext) {
        for (MessageHandler listener : listeners) {
            if (listener.handleMessage(messageContext)) {
                break;
            }
        }
    }

    private void executeCommand(@NonNull ExecutionContext executionContext) {
        final ChatCommand chatCommand = commands.get(executionContext.getCommandName());
        if (chatCommand != null) {
            chatCommand.execute(executionContext);
        }
    }

    @Override
    @Synchronized
    public @NonNull Subscription addCommand(@NonNull ChatCommand chatCommand) {
        if (commands.containsKey(chatCommand.name())) {
            throw new IllegalArgumentException("Duplicate command '" + chatCommand.name() + "'");
        }
        this.commands = MapTool.add(this.commands, chatCommand.name(), chatCommand);
        return () -> removeCommand(chatCommand.name());
    }

    @Override
    @Synchronized
    public @NonNull Subscription addListener(@NonNull MessageHandler handler) {
        this.listeners = ListTool.addFirst(this.listeners, handler);
        return () -> removeListener(handler);
    }

    @Synchronized
    private void removeCommand(@NonNull String commandName) {
        this.commands = MapTool.remove(this.commands, commandName);
    }

    @Synchronized
    private void removeListener(MessageHandler listener) {
        this.listeners = ListTool.removeFirst(this.listeners, listener);
    }


    private char getPrefix(Platform platform) {
        final Character prefix = prefixes.get(platform);
        if (prefix == null) {
            throw new RuntimeException("No prefix defined for platform '" + platform + "'. This is a bug");
        }
        return prefix;
    }


}
