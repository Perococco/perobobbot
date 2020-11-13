package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.advanced.*;
import perobobbot.chat.advanced.event.AdvancedChatEvent;
import perobobbot.chat.core.Chat;
import perobobbot.chat.core.ChatFactory;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.fp.TryResult;
import perobobbot.twitch.chat.*;
import perobobbot.twitch.chat.event.TwitchChatEvent;
import perobobbot.twitch.chat.message.from.GlobalUserState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.Cap;
import perobobbot.twitch.chat.message.to.Nick;
import perobobbot.twitch.chat.message.to.Pass;
import perobobbot.twitch.chat.message.to.Pong;
import perococco.perobobbot.twitch.chat.actions.JoinChannels;
import perococco.perobobbot.twitch.chat.actions.SendPrivMsg;
import perococco.perobobbot.twitch.chat.actions.SetupTimeout;
import perococco.perobobbot.twitch.chat.state.ConnectionIdentity;
import perococco.perobobbot.twitch.chat.state.StateUpdater;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
@Log4j2
public class PerococcoTwitchChat extends TwitchChatBase implements AdvancedChatListener<MessageFromTwitch> {

    @NonNull
    private final ConnectionIdentity connectionIdentity = new ConnectionIdentity();

    @NonNull
    private final AdvancedChat<MessageFromTwitch> chatManager;

    @NonNull
    private final TwitchChatOptions options;

    private final EventBridge eventBridge;

    public PerococcoTwitchChat(@NonNull URI chatAddress, @NonNull TwitchChatOptions options) {
        final Chat chat = ChatFactory.getInstance().create(chatAddress, new TwitchReconnectionPolicy());
        final Chat throttled = new ThrottledChat(chat);
        this.chatManager = AdvancedChatFactory.getInstance().createBasedOn(throttled, new TwitchMatcher(connectionIdentity), new TwitchMessageConverter());
        this.options = options;
        this.eventBridge = new EventBridge(this::onTwitchChatEvent, new StateUpdater(connectionIdentity));
    }

    private void onTwitchChatEvent(@NonNull TwitchChatEvent event) {
        if (event.isPing()) {
            connectionIdentity.executeWithIO(io -> io.sendToChat(Pong.create()));
        }
        this.warnListeners(event);
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<TwitchChat> start() {
        try {
            connectionIdentity.setToConnecting(chatManager.addChatListener(this));
            chatManager.start();

            return performAuthentication(chatManager)
                    .whenComplete((r, e) -> handleAuthenticationResult(TryResult.withStageResult(r, e)))
                    .thenRun(this::setupTimeout)
                    .thenCompose(this::joinChannels)
                    .thenApply(r -> this);

        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return CompletableFuture.failedFuture(e);
        }
    }


    @Override
    @Synchronized
    public void requestStop() {
        connectionIdentity.setToDisconnected();
        chatManager.requestStop();
    }

    @Override
    @Synchronized
    public boolean isRunning() {
        return chatManager.isRunning();
    }

    @Override
    public @NonNull CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final SendPrivMsg sendPrivMsg = new SendPrivMsg(channel, messageBuilder);
        return connectionIdentity.executeWithIO(sendPrivMsg)
                                 .thenApply(this::convertSlip);
    }




    private void handleAuthenticationResult(@NonNull TryResult<Throwable, ReceiptSlip<GlobalUserState>> authenticationResult) {
        if (authenticationResult.isSuccess()) {
            connectionIdentity.setToConnected(chatManager);
        } else {
            requestStop();
        }
    }

    @NonNull
    private CompletionStage<Void> setupTimeout() {
        return connectionIdentity.executeWithIO(SetupTimeout.create());
    }

    @NonNull
    private <T> CompletionStage<T> joinChannels(@NonNull T passThrough) {
        return connectionIdentity.executeWithIO(new JoinChannels(options.getChannels()))
                                 .thenApply(v -> passThrough);
    }

    @NonNull
    private CompletionStage<ReceiptSlip<GlobalUserState>> performAuthentication(@NonNull AdvancedChatIO<MessageFromTwitch> advancedChatIO) {
        final Cap cap = new Cap(Capability.AllCapabilities());
        final Pass pass = new Pass(options.getSecret());
        final Nick nick = new Nick(options.getNick());
        advancedChatIO.sendRequest(cap);
        return advancedChatIO.sendCommand(pass)
                             .thenCompose(r -> advancedChatIO.sendRequest(nick));
    }

    @Override
    public void onChatEvent(@NonNull AdvancedChatEvent<MessageFromTwitch> chatEvent) {
        chatEvent.accept(eventBridge);
    }

    @NonNull
    private TwitchDispatchSlip convertSlip(@NonNull IO.DispatchSlip slip) {
        return new TwitchDispatchSlip(this, slip.getSentCommand(), slip.getDispatchingTime());
    }

    @NonNull
    private <A> TwitchReceiptSlip<A> convertSlip(@NonNull IO.ReceiptSlip<A> slip) {
        return TwitchReceiptSlip.<A>builder()
                .twitchChatIO(this)
                .dispatchingTime(slip.getDispatchingTime())
                .receptionTime(slip.getReceptionTime())
                .sentRequest(slip.getSentRequest())
                .answer(slip.getAnswer())
                .build();
    }

}
