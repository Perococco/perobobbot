package perococco.perobobbot.twitch.chat.state;

import lombok.NonNull;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChatState;
import perococco.perobobbot.twitch.chat.TwitchMessageChannelIO;
import perococco.perobobbot.twitch.chat.TwitchIO;

import java.util.Optional;

/**
 * The state of the connection.
 */
public interface ConnectionState extends TwitchChatState {

    /**
     * @return a new disconnected state
     */
    @NonNull
    static DisconnectedState disconnected() {
        return DisconnectedState.create();
    }

    /**
     * @return the io to communicate to Twitch
     * @throws perobobbot.twitch.chat.TwicthChatNotConnected if this is not a connected state
     */
    @NonNull TwitchIO getTwitchIO();

    /**
     * @return true if this is a connected state
     */
    @Override
    boolean isConnected();

    /**
     * @return the user associated to this connection
     * @throws perobobbot.twitch.chat.TwicthChatNotConnected if this is not a connected state
     */
    @NonNull String getUserId();

    /**
     * @param channel the channel to find
     * @return an optional containing the {@link TwitchMessageChannelIO} associated to the provided channel, en empty optional if
     * the channel has not been joined
     * @throws perobobbot.twitch.chat.TwicthChatNotConnected if this is not a connected state
     */
    @NonNull Optional<TwitchMessageChannelIO> findChannel(@NonNull Channel channel);

    default @NonNull Optional<DisconnectedState> asDisconnectedState(){ return Optional.empty(); }
    default @NonNull Optional<ConnectedState> asConnectedState(){ return Optional.empty(); }
    default @NonNull Optional<ConnectingState> asConnectingState(){ return Optional.empty(); }

}
