package perococco.bot.twitch.chat;

import bot.chat.advanced.*;
import bot.chat.advanced.event.AdvancedChatEvent;
import bot.chat.core.Chat;
import bot.chat.core.ChatManagerFactory;
import bot.common.lang.ThrowableTool;
import bot.twitch.chat.*;
import bot.twitch.chat.message.from.*;
import bot.twitch.chat.message.from.Part;
import bot.twitch.chat.message.to.*;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
@Log4j2
public class PerococcoTwitchChat extends AbstractTwitchChat implements AdvancedChatListener<MessageFromTwitch> {

    @NonNull
    private final ConnectionIdentity connectionIdentity = new ConnectionIdentity(this);

    @NonNull
    private final AdvancedChat<MessageFromTwitch> chatManager;

    public PerococcoTwitchChat(@NonNull URI chatAddress) {
        final Chat chat = ChatManagerFactory.getInstance().create(chatAddress, new TwitchReconnectionPolicy());
        final Chat throttled = new ThrottledChat(chat);
        this.chatManager = AdvancedChatManagerFactory.getInstance().createBasedOn(throttled,new TwitchMatcher(connectionIdentity), new TwitchMessageConverter());
    }

    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<UserState>> join(@NonNull Channel channel) {
        return super.join(channel).whenComplete((r, t) -> {
            if (r != null) {
                connectionIdentity.addJoinedChannel(r.slipAnswer().channel());
            }
        });
    }

    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull Channel channel) {
        return super.part(channel).whenComplete((r, t) -> {
            if (r != null) {
                connectionIdentity.removeJoinedChannel(r.slipAnswer().channel());
            }
        });
    }

    @Override
    @Synchronized
    public @NonNull CompletionStage<TwitchReceiptSlip<GlobalUserState>> start(@NonNull TwitchChatOAuth oAuth) {
        try {
            this.connectionIdentity.setToConnecting(oAuth,chatManager);
            chatManager.start();

            return performAuthentication(chatManager, oAuth)
                    .whenComplete(
                            (result, error) -> {
                                if (error == null) {
                                    connectionIdentity.setToConnected(chatManager);
                                } else {
                                    requestStop();
                                }
                            }
                    )
                    .thenApply(r -> new TwitchReceiptSlip<>(this, r));

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

    @NonNull
    private CompletionStage<ReceiptSlip<GlobalUserState>> performAuthentication(@NonNull AdvancedChatIO<MessageFromTwitch> advancedChatIO, @NonNull TwitchChatOAuth oAuth) {
        final Cap cap = new Cap(Capability.AllCapabilities());
        final Pass pass = new Pass(oAuth.secret());
        final Nick nick = new Nick(oAuth.nick());
        advancedChatIO.sendRequest(cap);
        return advancedChatIO.sendCommand(pass)
                             .thenCompose(r -> advancedChatIO.sendRequest(nick));
    }

    @Override
    public void onChatEvent(@NonNull AdvancedChatEvent<MessageFromTwitch> chatEvent) {
        dispatchToTwitchListeners(chatEvent);
    }

    @Override
    protected @NonNull CompletionStage<TwitchDispatchSlip> sendToChat(@NonNull Command command) {
        return connectionIdentity.witChat(AdvancedChatIO::sendCommand, command)
                                 .thenApply(c -> new TwitchDispatchSlip(this, c));
    }

    @Override
    protected @NonNull <A> CompletionStage<TwitchReceiptSlip<A>> sendToChat(@NonNull Request<A> request) {
        return connectionIdentity.witChat(AdvancedChatIO::sendRequest, request)
                                 .thenApply(c -> new TwitchReceiptSlip<>(this, c));
    }


}
