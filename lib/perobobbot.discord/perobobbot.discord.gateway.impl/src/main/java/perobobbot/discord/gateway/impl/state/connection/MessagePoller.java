package perobobbot.discord.gateway.impl.state.connection;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.GatewayMessage;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagePoller {


    public static @NonNull GatewayMessage poll(@NonNull Connection connection, long timeout, TimeUnit timeUnit) throws InterruptedException {
        return new MessagePoller(connection).poll(timeout, timeUnit);
    }

    private final @NonNull Connection connection;

    private @NonNull GatewayMessage poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
        do {
            final var event = connection.pollEvent(timeout, timeUnit).orElseThrow(() -> new ConnectionError("No message received from Discord"));

            if (event instanceof ConnectionEvent.Message message) {
                return message.message();
            }
        } while (true);
    }


}
