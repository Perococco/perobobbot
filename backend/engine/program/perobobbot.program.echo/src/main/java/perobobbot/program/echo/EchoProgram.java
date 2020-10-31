package perobobbot.program.echo;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.lang.User;
import perobobbot.common.messaging.CommandBundleFactory;
import perobobbot.program.core.ProgramWithCommandBundle;

public class EchoProgram extends ProgramWithCommandBundle<EchoProgram> {

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    private final @NonNull IO io;

    public EchoProgram(@NonNull String name, @NonNull CommandBundleFactory<EchoProgram> bundleFactory, @NonNull IO io) {
        super(name, bundleFactory);
        this.io = io;
    }

    @Override
    protected EchoProgram getThis() {
        return this;
    }

    public void performEcho(@NonNull ExecutionContext context) {
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
