package perococco.bot.twitch.chat;

import bot.chat.advanced.*;
import bot.chat.core.ChatClient;
import bot.common.lang.ThrowableTool;
import bot.twitch.chat.*;
import bot.twitch.chat.message.from.Join;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.Part;
import bot.twitch.chat.message.to.*;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
@Log4j2
public class PerococcoTwitchChat extends AbstractTwitchChat implements AdvancedChatListener<MessageFromTwitch>, AdvancedChatClientListener<MessageFromTwitch> {

    @NonNull
    private final ConnectionIdentity connectionIdentity = new ConnectionIdentity(this);

    @NonNull
    private final AdvancedChatClient<MessageFromTwitch> chatClient;

    public PerococcoTwitchChat(@NonNull ChatClient chatClient) {
        this.chatClient = AdvancedChatClient.basedOn(
                chatClient,
                new TwitchMatcher(connectionIdentity),
                new TwitchMessageConverter()
        );
    }


    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<Join>> join(@NonNull Channel channel) {
        return super.join(channel).whenComplete((r, t) -> {
            if (r != null) {
                connectionIdentity.addJoinedChannel(r.answer().channel());
            }
        });
    }

    @Override
    public @NonNull CompletionStage<TwitchReceiptSlip<Part>> part(@NonNull Channel channel) {
        return super.part(channel).whenComplete((r, t) -> {
            if (r != null) {
                connectionIdentity.removeJoinedChannel(r.answer().channel());
            }
        });
    }

    @Override
    @Synchronized
    public CompletionStage<TwitchReceiptSlip<OAuthResult>> connect(@NonNull TwitchChatOAuth oAuth) {
        try {
            this.connectionIdentity.setToConnecting(oAuth);
            final AdvancedChat<MessageFromTwitch> chat = chatClient.connect();

            return performAuthentication(chat, oAuth)
                    .whenComplete(
                            (result, error) -> {
                                if (isAuthenticationSuccessful(result, error)) {
                                    connectionIdentity.setToConnected(chat);
                                } else {
                                    connectionIdentity.setToDisconnected();
                                }
                            }
                    )
                    .thenApply(r -> new TwitchReceiptSlip<>(this, r));

        } catch (Exception e) {
            ThrowableTool.interruptThreadIfCausedByInterruption(e);
            return CompletableFuture.failedFuture(e);
        }
    }

    @NonNull
    private CompletionStage<ReceiptSlip<OAuthResult>> performAuthentication(@NonNull AdvancedChat<MessageFromTwitch> advancedChat, @NonNull TwitchChatOAuth oAuth) {
        final Cap cap = new Cap(Capability.AllCapabilities());
        final Pass pass = new Pass(oAuth.secret());
        final Nick nick = new Nick(oAuth.nick());
        advancedChat.sendRequest(cap);
        return advancedChat.sendCommand(pass)
                           .thenCompose(r -> advancedChat.sendRequest(nick));

    }

    private static boolean isAuthenticationSuccessful(ReceiptSlip<OAuthResult> result, Throwable error) {
        assert result != null || error != null;
        return error == null && result.answer().isSuccess();
    }

    @Override
    @Synchronized
    public void disconnect() {
        connectionIdentity.setToDisconnected();
        chatClient.disconnect();
    }


    @Override
    public void onConnection(@NonNull AdvancedChat<MessageFromTwitch> chat) {
    }

    @Override
    public void onDisconnection() {

    }

    @Override
    public void onReceivedMessage(@NonNull MessageFromTwitch receivedMessage) {
        listeners().warnListeners(TwitchChatListener::onMessageFromTwitch, receivedMessage);
    }

    @Override
    public void onPostMessage(@NonNull Message postMessage) {
        if (postMessage instanceof MessageToTwitch) {
            listeners().warnListeners(TwitchChatListener::onMessageToTwitch, (MessageToTwitch) postMessage);
        }
    }

    @Override
    public void onError(@NonNull Throwable throwable) {
        LOG.warn("Twitch chat error ", throwable);
    }

    @Override
    protected @NonNull CompletionStage<TwitchDispatchSlip> sendToChat(@NonNull Command command) {
        return connectionIdentity.witChat(AdvancedChat::sendCommand, command)
                                 .thenApply(c -> new TwitchDispatchSlip(this, c));
    }

    @Override
    protected @NonNull <A> CompletionStage<TwitchReceiptSlip<A>> sendToChat(@NonNull Request<A> request) {
        return connectionIdentity.witChat(AdvancedChat::sendRequest, request)
                                 .thenApply(c -> new TwitchReceiptSlip<>(this, c));
    }


}
