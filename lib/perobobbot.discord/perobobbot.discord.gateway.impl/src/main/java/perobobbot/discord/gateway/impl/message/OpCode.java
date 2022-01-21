package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.state.connection.ConnectionError;
import perobobbot.discord.resources.HelloMessage;

import java.util.function.Function;

@RequiredArgsConstructor
public enum OpCode {
    Dispatch(0, new DispatchTypeGetter()),
    Heartbeat(1, JsonNode.class),
    Presence(3, JsonNode.class),
    Voice(4, JsonNode.class),
    Resume(6, JsonNode.class),
    Request(8, JsonNode.class),
    Invalid(9, JsonNode.class),
    Identify(2, Identify.class),
    Hello(10, HelloMessage.class),
    HeartbeatAck(11, JsonNode.class),
    ;

    @Getter
    private final int value;

    @Getter
    final @NonNull Function<String,Class<?>> eventTypeGetter;

    OpCode(int value, Class<?> eventType) {
        this.value = value;
        this.eventTypeGetter = t -> eventType;
    }

    public static @NonNull OpCode fromValue(int op) {
        return Holder.VALUES.stream()
                            .filter(o -> o.value == op)
                            .findFirst()
                            .orElseThrow(() -> new ConnectionError("Unknown opcode value : "+op));
    }


    public @NonNull Class<?> getEventType(String eventType) {
        return this.eventTypeGetter.apply(eventType);
    }

    private static class Holder {
        private static final ImmutableSet<OpCode> VALUES = ImmutableSet.copyOf(values());
    }
}
