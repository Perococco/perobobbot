package perococco.perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.advanced.*;
import perobobbot.chat.advanced.event.AdvancedChatEvent;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatFactory;
import perobobbot.lang.*;
import perobobbot.twitch.chat.Capability;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChatListener;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.event.TwitchChatEvent;
import perobobbot.twitch.chat.message.TagKey;
import perobobbot.twitch.chat.message.from.GlobalUserState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.Cap;
import perobobbot.twitch.chat.message.to.Nick;
import perobobbot.twitch.chat.message.to.Pass;
import perobobbot.twitch.chat.message.to.Pong;
import perococco.perobobbot.twitch.chat.actions.JoinChannel;
import perococco.perobobbot.twitch.chat.actions.SetupTimeout;
import perococco.perobobbot.twitch.chat.state.ConnectionState;
import perococco.perobobbot.twitch.chat.state.StateUpdater;
import perococco.perobobbot.twitch.chat.state.mutator.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * @author perococco
 **/
@Log4j2
public class TwitchChatConnection implements ChatConnection, AdvancedChatListener<MessageFromTwitch> {

    @Getter
    private final @NonNull ChatConnectionInfo chatConnectionInfo;

    private final @NonNull Listeners<TwitchChatListener> listeners;

    private final @NonNull Identity<ConnectionState> connectionIdentity;

    private final @NonNull AdvancedChat<MessageFromTwitch> chat;

    private final @NonNull Credential credential;

    private final @NonNull EventBridge eventBridge;

    public TwitchChatConnection(@NonNull ChatConnectionInfo chatConnectionInfo,
                                @NonNull Listeners<TwitchChatListener> listeners,
                                @NonNull Instants instants) {
        this.chatConnectionInfo = chatConnectionInfo;
        this.credential = chatConnectionInfo.getCredential();
        this.connectionIdentity = Identity.create(ConnectionState.disconnected(chatConnectionInfo));
        this.chat = createChat(connectionIdentity, instants);
        this.listeners = listeners;
        this.eventBridge = new EventBridge(this::onTwitchChatEvent, new StateUpdater(connectionIdentity));
    }

    private static @NonNull AdvancedChat<MessageFromTwitch> createChat(@NonNull Identity<ConnectionState> identity, @NonNull Instants instants) {
        final var simpleChat = ChatFactory.getInstance().create(TwitchConstants.TWITCH_CHAT_URI, new TwitchReconnectionPolicy(), instants);
        final var throttledChat = new ThrottledChat(simpleChat);
        return AdvancedChatFactory.createAdvancedChatBasedOn(throttledChat, new TwitchMatcher(identity), new TwitchMessageConverter(), instants);
    }

    @Override
    public @NonNull CompletionStage<Optional<TwitchMessageChannelIO>> findChannel(@NonNull String channelName) {
        final var channel = Channel.create(channelName);
        return CompletableFuture.supplyAsync(() -> connectionIdentity.get(state -> state.findChannel(channel)));
    }

    public <T>  @NonNull T operate(@NonNull Operator<ConnectionState,T> operator) {
        return connectionIdentity.operate(operator);
    }

    @Override
    public @NonNull CompletionStage<TwitchMessageChannelIO> join(@NonNull String channelName) {
        final Channel channel = Channel.create(channelName);
        return operate(new JoinChannel(credential.getNick(), channel))
                .thenApply(s -> {
                    final var twitchChannelIO = new TwitchMessageChannelIO(channel,this, new ChannelInfo(getPlatform(),channelName));
                    this.operate(new ChanelAdder(twitchChannelIO).asOperator());
                    return twitchChannelIO;
                });
    }

    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return listeners.addListener(TwitchChatListener.wrap(listener));
    }

    private void onTwitchChatEvent(@NonNull TwitchChatEvent event) {
        if (event.isPing()) {
            operate(OperatorToSendCommand.create(Pong.create()));
        }
        listeners.warnListeners(l -> l.onTwitchChatEvent(event));
    }

    @Synchronized
    public @NonNull CompletionStage<TwitchChatConnection> start() {
        try {
            connectionIdentity.mutate(new SetConnecting(chat.addChatListener(this)));
            chat.start();

            return performAuthentication(chat)
                    .whenComplete(this::handleAuthenticationResult)
                    .thenRun(this::setupTimeout)
                    .thenApply(vd -> this);

        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return CompletableFuture.failedFuture(e);
        }
    }


    @Synchronized
    public void requestStop() {
        connectionIdentity.mutate(new SetDisconnected());
        chat.requestStop();
    }

    @Synchronized
    public boolean isRunning() {
        return chat.isRunning();
    }



    private void handleAuthenticationResult(ReceiptSlip<GlobalUserState> result, Throwable error) {
        if (result != null) {
            final String userId = result.getAnswer().getTag(TagKey.USER_ID);
            connectionIdentity.mutate(new SetConnected(userId,chat));
        } else {
            requestStop();
        }
    }

    @NonNull
    private CompletionStage<Void> setupTimeout() {
        return connectionIdentity.operate(SetupTimeout.create());
    }

    @NonNull
    private CompletionStage<ReceiptSlip<GlobalUserState>> performAuthentication(@NonNull AdvancedChatIO<MessageFromTwitch> advancedChatIO) {
        final Cap cap = new Cap(Capability.AllCapabilities());
        final Pass pass = new Pass(credential.getSecret());
        final Nick nick = new Nick(credential.getNick());
        advancedChatIO.sendRequest(cap);
        return advancedChatIO.sendCommand(pass)
                             .thenCompose(r -> advancedChatIO.sendRequest(nick));
    }

    @Override
    public void onChatEvent(@NonNull AdvancedChatEvent<MessageFromTwitch> chatEvent) {
        chatEvent.accept(eventBridge);
    }

    public @NonNull TwitchChatConnection startAndWait() throws ExecutionException, InterruptedException {
        return start().toCompletableFuture().get();
    }


}
