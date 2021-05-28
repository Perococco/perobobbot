package perobobbot.server.config.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import perobobbot.command.Command;
import perobobbot.command.CommandExecutor;
import perobobbot.lang.ExecutionContext;
import perobobbot.oauth.ChatTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;

@Component
@Qualifier("with-oauth-context")
@RequiredArgsConstructor
public class CommandExecutorWithOAuthContext implements CommandExecutor {

    private final @Qualifier("with-access-rule")
    @NonNull CommandExecutor delegate;

    @Override
    public void execute(@NonNull Command command, @NonNull ExecutionContext context) {
        this.initializeOAuthContextWithMessageInformation(context);
        try {
            delegate.execute(command, context);
        } finally {
            OAuthContextHolder.remove();
        }
    }

    private void initializeOAuthContextWithMessageInformation(@NonNull ExecutionContext context) {
        final var identifier = new ChatTokenIdentifier(context.getBotId(), context.getMessageOwner().getUserId(), context.getPlatform(), context.getChannelName());
        OAuthContextHolder.getContext().setTokenIdentifier(identifier);
    }

}
