package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.MessageContext;
import perobobbot.common.lang.User;

import java.time.Instant;
import java.util.Optional;

/**
 * Information about the context the execution
 * is performed in
 */
@RequiredArgsConstructor
public class ExecutionContext {

    @NonNull
    public static Optional<ExecutionContext> from(@NonNull MessageContext messageContext) {
        final String[] content = messageContext.getContent().split(" ",2);
        if (content.length == 0) {
            return Optional.empty();
        }
        final String parameters = content.length>1?content[1]:"";
        return Optional.of(new ExecutionContext(messageContext, content[0], parameters));
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
}
