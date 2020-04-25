package perococco.bot.twitch.chat;

import bot.chat.advanced.*;
import bot.chat.advanced.event.*;
import bot.chat.core.Chat;
import bot.chat.core.ChatFactory;
import bot.common.lang.ThrowableTool;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.TryResult;
import bot.twitch.chat.*;
import bot.twitch.chat.message.from.GlobalUserState;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.Cap;
import bot.twitch.chat.message.to.Nick;
import bot.twitch.chat.message.to.Pass;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perococco.bot.twitch.chat.actions.JoinChannels;
import perococco.bot.twitch.chat.actions.SendPrivMsg;
import perococco.bot.twitch.chat.actions.SetupTimeout;
import perococco.bot.twitch.chat.state.ConnectionIdentity;
import perococco.bot.twitch.chat.state.StateUpdater;

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

    @NonNull
    private final Consumer1<MessageFromTwitch> stateUpdater;

    public PerococcoTwitchChat(@NonNull URI chatAddress, @NonNull TwitchChatOptions options) {
        final Chat chat = ChatFactory.getInstance().create(chatAddress, new TwitchReconnectionPolicy());
        final Chat throttled = new ThrottledChat(chat);
        this.chatManager = AdvancedChatFactory.getInstance().createBasedOn(throttled, new TwitchMatcher(connectionIdentity), new TwitchMessageConverter());
        this.options = options;
        this.stateUpdater = new StateUpdater(connectionIdentity).toConsumer();
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<TwitchChatIO> start() {
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
    public @NonNull CompletionStage<TwitchDispatchSlip> message(@NonNull Channel channel, @NonNull String message) {
        final SendPrivMsg sendPrivMsg = new SendPrivMsg(channel, message);
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
        return connectionIdentity.executeWithIO(new JoinChannels(options.channels()))
                                 .thenApply(v -> passThrough);
    }

    @NonNull
    private CompletionStage<ReceiptSlip<GlobalUserState>> performAuthentication(@NonNull AdvancedChatIO<MessageFromTwitch> advancedChatIO) {
        final Cap cap = new Cap(Capability.AllCapabilities());
        final Pass pass = new Pass(options.secret());
        final Nick nick = new Nick(options.nick());
        advancedChatIO.sendRequest(cap);
        return advancedChatIO.sendCommand(pass)
                             .thenCompose(r -> advancedChatIO.sendRequest(nick));
    }

    @Override
    public void onChatEvent(@NonNull AdvancedChatEvent<MessageFromTwitch> chatEvent) {
        updateState(chatEvent);
        dispatchToTwitchListeners(chatEvent);
    }


    private void updateState(@NonNull AdvancedChatEvent<MessageFromTwitch> chatEvent) {
        //TODO
    }

    @NonNull
    private TwitchDispatchSlip convertSlip(@NonNull IO.DispatchSlip slip) {
        return new TwitchDispatchSlip(this, slip.sentCommand(), slip.dispatchingTime());
    }

    @NonNull
    private <A> TwitchReceiptSlip<A> convertSlip(@NonNull IO.ReceiptSlip<A> slip) {
        return TwitchReceiptSlip.<A>builder()
                .twitchChatIO(this)
                .dispatchingTime(slip.dispatchingTime())
                .receptionTime(slip.receptionTime())
                .sentRequest(slip.sentRequest())
                .answer(slip.answer())
                .build();
    }

}
