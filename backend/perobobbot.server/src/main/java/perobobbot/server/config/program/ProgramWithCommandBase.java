package perobobbot.server.config.program;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.Subscription;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.Program;
import perobobbot.program.manager.ProgramData;

public abstract class ProgramWithCommandBase<P extends Program> implements Program {

    @Getter(AccessLevel.PROTECTED)
    private final @NonNull P delegate;

    private final @NonNull ImmutableList<Command> commands;

    private final @NonNull ChatController chatController;

    private final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();

    protected ProgramWithCommandBase(@NonNull ProgramData<P> programData,
                                     @NonNull ChatController chatController) {
        this(programData.getProgram(),programData.getCommands(),chatController);
    }

    protected ProgramWithCommandBase(@NonNull P program, @NonNull ImmutableList<Command> commands, @NonNull ChatController chatController) {
        this.delegate = program;
        this.commands = commands;
        this.chatController = chatController;
        if (delegate.isEnabled()) {
            this.attachCommandToChat();
        }
    }

    protected abstract P getThis();

    @Override
    public @NonNull String getName() {
        return delegate.getName();
    }

    @Override
    public boolean isEnabled() {
        return delegate.isEnabled();
    }

    @Override
    public void enable() {
        delegate.enable();
        attachCommandToChat();
    }

    @Override
    public void disable() {
        detachCommandFromChat();
        delegate.disable();
    }

    private void detachCommandFromChat() {
        subscriptionHolder.unsubscribe();
    }

    private void attachCommandToChat() {
        subscriptionHolder.replaceWith(() -> commands.stream()
                                                     .map(chatController::addCommand)
                                                     .collect(Subscription.COLLECTOR));
    }


}
