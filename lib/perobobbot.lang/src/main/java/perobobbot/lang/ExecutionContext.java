package perobobbot.lang;

import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * Information about the context the execution
 * is performed in
 */
public interface ExecutionContext {

    @NonNull MessageContext getMessageContext();

    /**
     * @return the full command without the prefix
     */
    @NonNull String getCommand();

    @NonNull
    static Optional<ExecutionContext> createFrom(char prefix, @NonNull MessageContext messageContext) {
        if (!messageContext.doesContentStartWith(prefix)) {
            return Optional.empty();
        }
        return splitCommandParameters(messageContext, messageContext.getContent().substring(1));
    }


    @NonNull
    private static Optional<ExecutionContext> splitCommandParameters(@NonNull MessageContext messageContext, String command) {
        if (command.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new BasicExecutionContext(messageContext, command));
    }

    default @NonNull UUID getBotId() {
        return getMessageContext().getPlatformBotId();
    }

    default @NonNull ChatUser getMessageOwner() {
        return getMessageContext().getMessageOwner();
    }

    default @NonNull String getChannelName() {
        return getMessageContext().getChannelName();
    }

    default @NonNull Platform getPlatform() {
        return getMessageContext().getPlatform();
    }

    default @NonNull Instant getReceptionTime() {
        return getMessageContext().getReceptionTime();
    }

    default @NonNull ChatConnectionInfo getChatConnectionInfo() {
        return getMessageContext().getChatConnectionInfo();
    }

    default @NonNull ChannelInfo getChannelInfo() {
        return getMessageContext().getChannelInfo();
    }

    default boolean isMessageFromMe() {
        return getMessageContext().isMessageFromMe();
    }

    default @NonNull String getContent() {
        return getMessageContext().getContent();
    }
}
