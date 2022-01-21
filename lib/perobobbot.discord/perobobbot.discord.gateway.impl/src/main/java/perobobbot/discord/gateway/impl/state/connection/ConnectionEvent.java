package perobobbot.discord.gateway.impl.state.connection;

import perobobbot.discord.gateway.impl.GatewayMessage;

import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;

public sealed interface ConnectionEvent  {

    record Open(EndpointConfig config) implements ConnectionEvent{};
    record Close(CloseReason closeReason) implements ConnectionEvent{};
    record Error(Throwable error) implements ConnectionEvent{};
    record Message(GatewayMessage<?> message) implements ConnectionEvent{};

}
