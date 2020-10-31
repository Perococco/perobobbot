package perobobbot.program.echo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.core.Policy;
import perobobbot.common.lang.*;
import perobobbot.common.messaging.ChatCommand;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.core.Program;

@RequiredArgsConstructor
public class EchoProgram implements Program {

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    @Getter
    private final @NonNull String name;

    private final @NonNull IO io;

    private final @NonNull ChatController chatController;

    private final @NonNull Policy policy;

    @Override
    @Synchronized
    public void enable() {
        final Subscription subscription = chatController.addCommand(ChatCommand.simple("echo", policy.createAccessPoint(this::performEcho)));
        subscriptionHolder.replaceWith(subscription);
    }

    @Override
    @Synchronized
    public void disable() {
        subscriptionHolder.unsubscribe();
    }

    @Override
    public boolean isEnabled() {
        return subscriptionHolder.hasSubscription();
    }

    private void performEcho(@NonNull ExecutionContext context) {
        if (context.isMessageFromMe()) {
            return;
        }
        final User user = context.getMessageOwner();
        final String contentToEcho = context.getParameters();
        final String answer = String.format("%s said '%s'",
                                            user.getHighlightedUserName(),
                                            contentToEcho);
        io.print(context.getChannelInfo(),answer);
    }



}
