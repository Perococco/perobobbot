package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.discord.resources.GatewayEvent;
import perobobbot.discord.resources.Message;
import perobobbot.discord.resources.Ready;

import java.util.Objects;
import java.util.function.Function;

/**
 * Function to retrieve the Java type of Discord gateway message from its type name. If
 * no Java type could be found, returns {@link JsonNode}
 */
public class DispatchTypeGetter implements Function<String,Class<?>> {

    private final ImmutableMap<String,Class<? extends GatewayEvent>> typePerEventName = ImmutableMap.<String,Class<? extends GatewayEvent>>builder()
                                                                                                    .put("READY", Ready.class)
                                                                                                    .put("MESSAGE_CREATE", Message.class)
                                                                                                    .build();

    @Override
    public @NonNull Class<?> apply(String eventName) {
        assert eventName != null : "Event name must not be null for dispatch OpCode";

        final var eventType = typePerEventName.get(eventName.toUpperCase());

        return Objects.requireNonNullElse(eventType, JsonNode.class);

    }
}
