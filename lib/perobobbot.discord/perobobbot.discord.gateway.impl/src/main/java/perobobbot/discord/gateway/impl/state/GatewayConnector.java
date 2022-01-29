package perobobbot.discord.gateway.impl.state;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.Intents;
import perobobbot.discord.gateway.impl.message.ConnectionProperties;
import perobobbot.discord.gateway.impl.state.connection.GatewayConnection;
import perobobbot.discord.resources.HelloMessage;
import perobobbot.discord.gateway.impl.message.Identify;
import perobobbot.discord.gateway.impl.message.OpCode;
import perobobbot.discord.gateway.impl.state.connection.ConnectionError;
import perobobbot.discord.gateway.impl.state.connection.MessagePoller;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GatewayConnector {

    public static final long DEFAULT_CONNECTION_TIMEOUT_IN_MS = 5_000;
    public static final int INTENTS = Intents.computeMask(Intents.DIRECT_MESSAGES, Intents.GUILD_MESSAGES);

    public static @NonNull ConnectedGateway connect(
            @NonNull StateData stateData) {
        return new GatewayConnector(stateData).connect();
    }


    private final @NonNull StateData stateData;

    private URI gatewayURI;
    private GatewayConnection gatewayConnection;

    private @NonNull ConnectedGateway connect() {
        this.createGatewayURI();
        this.createGatewayConnection();

        try {
            gatewayConnection.connect();
            final var event1 = MessagePoller.poll(gatewayConnection, DEFAULT_CONNECTION_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);

            if (event1.getOpCode() != OpCode.Hello) {
                throw new ConnectionError("Invalid opcode for first Discord event '" + event1.getOpCode() + "'");
            }
            final var helloMessage = (HelloMessage) event1.getEvent();

            final var identify = createIdentifyMessage();

            gatewayConnection.send(identify);

            final var event2 = MessagePoller.poll(gatewayConnection, DEFAULT_CONNECTION_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);

            if (event2.getOpCode() == OpCode.Dispatch && event2.getEventName().equalsIgnoreCase("Ready")) {
                System.out.println(" Got ready event "+event2.getEvent());
            } else {
                throw new ConnectionError("Connection failed");
            }

            return new ConnectedGateway(stateData, gatewayConnection, helloMessage.getHeartbeatInterval());

        } catch (Throwable e) {
            gatewayConnection.disconnect();
            throw new ConnectionError(e);
        }

    }

    private @NonNull Identify createIdentifyMessage() {
        return Identify.builder()
                       .token(this.stateData.getBotToken().getValue())
                       .intents(INTENTS)
                       .shard(new int[]{0, stateData.getGatewayBot().getShards()})
                       .properties(ConnectionProperties.builder()
                                                       .os("linux")
                                                       .browser("perobobbot")
                                                       .device("perobobbot")
                                                       .build())
                       .build();
    }


    private void createGatewayURI() {
        this.gatewayURI = GatewayUriBuilder.INSTANCE.createGatewayUri(stateData.getGatewayBot().getUrl(), stateData.getVersion(), "json");
    }

    private void createGatewayConnection() {
        gatewayConnection = new GatewayConnection(gatewayURI, stateData.getMessageMapper());
    }


}
