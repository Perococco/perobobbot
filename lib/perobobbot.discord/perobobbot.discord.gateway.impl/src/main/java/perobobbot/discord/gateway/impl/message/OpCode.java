package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.state.connection.ConnectionError;
import perobobbot.discord.resources.HelloMessage;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    final @NonNull Function<String, Class<?>> eventTypeGetter;

    OpCode(int value, Class<?> eventType) {
        this.value = value;
        this.eventTypeGetter = t -> eventType;
    }

    public static @NonNull OpCode fromValue(int op) {
        final var opCode = Holder.VALUES.get(op);
        if (opCode == null) {
            throw new ConnectionError("Unknown opcode value : " + op);
        }
        return opCode;
    }


    public @NonNull Class<?> getEventType(String eventType) {
        return this.eventTypeGetter.apply(eventType);
    }

    private static class Holder {
        private static final ImmutableMap<Integer, OpCode> VALUES = Arrays.stream(values()).collect(ImmutableMap.toImmutableMap(OpCode::getValue, o -> o));
    }
}
