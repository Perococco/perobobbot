package perobobbot.discord.gateway.impl.state.connection;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.GatewayMessage;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagePoller {


    public static @NonNull GatewayMessage poll(@NonNull GatewayConnection gatewayConnection, long timeout, TimeUnit timeUnit) throws InterruptedException {
        return new MessagePoller(gatewayConnection).poll(timeout, timeUnit);
    }

    private final @NonNull GatewayConnection gatewayConnection;

    private @NonNull GatewayMessage poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
        do {
            final var event = gatewayConnection.pollEvent(timeout, timeUnit).orElseThrow(() -> new ConnectionError("No message received from Discord"));

            if (event instanceof ConnectionEvent.Message message) {
                return message.message();
            }
        } while (true);
    }


}
