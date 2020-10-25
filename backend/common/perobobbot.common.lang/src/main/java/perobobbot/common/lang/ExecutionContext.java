package perobobbot.common.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Optional;

/**
 * Information about the context the execution
 * is performed in
 */
@RequiredArgsConstructor
public class ExecutionContext {

    @NonNull
    public static Optional<ExecutionContext> from(char prefix, @NonNull MessageContext messageContext) {
        if (!messageContext.doesContentStartWith(prefix)) {
            return Optional.empty();
        }
        final String[] content = messageContext.getContent().split(" ",2);

        final String command = content.length>0?content[0].substring(1):"";
        final String parameters = content.length>1?content[1]:"";

        if (command.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new ExecutionContext(messageContext, command, parameters));
    }

    @Getter
    private final @NonNull MessageContext messageContext;

    /**
     * @return the name of the command
     */
    @Getter
    private final @NonNull String commandName;

    /**
     * @return the rest of the content after the command name
     */
    @Getter
    private final @NonNull String parameters;

    @NonNull
    public ChannelInfo getChannelInfo() {
        return messageContext.getChannelInfo();
    }

    @NonNull
    public User getMessageOwner() {
        return messageContext.getMessageOwner();
    }

    @NonNull
    public String getMessageOwnerId() {
        return getMessageOwner().getUserId();
    }

    @NonNull
    public String getMessageContent() {
        return messageContext.getContent();
    }

    @NonNull
    public Instant getReceptionTime() {
        return messageContext.getReceptionTime();
    }

    public boolean isMessageFromMe() {
        return messageContext.isMessageFromMe();
    }
}
