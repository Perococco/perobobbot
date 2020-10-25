package perobobbot.program.echo;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.access.core.Policy;
import perobobbot.chat.core.ChatController;
import perobobbot.common.lang.*;
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
    public void start() {
        final Subscription subscription = chatController.addCommand("echo", policy.createAccessPoint(this::performEcho));
        subscriptionHolder.replace(subscription);
    }

    @Override
    @Synchronized
    public void requestStop() {
        subscriptionHolder.unsubscribe();
    }

    @Override
    public boolean isRunning() {
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
