package perobobbot.discord.gateway.impl.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.IntNode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.discord.gateway.impl.GatewayMessage;
import perobobbot.discord.gateway.impl.MessageMapper;
import perobobbot.discord.gateway.impl.state.connection.ConnectionError;

@RequiredArgsConstructor
public class DefaultMessageMapper implements MessageMapper {

    private final @NonNull ObjectMapper objectMapper;

    @Override
    public @NonNull GatewayMessage<?> map(@NonNull String message) {
        try {
            final var rawGatewayMessage = objectMapper.readValue(message, RawGatewayMessage.class);
            final var opCode = OpCode.fromValue(rawGatewayMessage.getOp());
            final var eventValue = rawGatewayMessage.getD();

            final var eventType = opCode.getEventType(rawGatewayMessage.getT());

            final Object payload = eventValue == null ? null : objectMapper.treeToValue(eventValue, eventType);
            return new GatewayMessage<>(opCode, payload, rawGatewayMessage.getS(), rawGatewayMessage.getT());
        } catch (JsonProcessingException e) {
            throw new ConnectionError("Could not deserialize message " + message, e);
        }
    }


    @Override
    public @NonNull String map(@NonNull SentGatewayEvent event) {
        try {
            final var opCode = event.getOpCode();
            final var eventPayload = objectMapper.valueToTree(event);
            final var rawMessage = RawGatewayMessage.builder()
                                                    .op(opCode.getValue())
                                                    .d(eventPayload)
                                                    .build();

            return this.objectMapper.writeValueAsString(rawMessage);
        } catch (JsonProcessingException e) {
            throw new ConnectionError("Could not serialize event '" + event + "'", e);
        }
    }

    @Override
    public @NonNull String mapHeartbeat(Integer sequenceNumber) {
        try {

            final var rawMessage = RawGatewayMessage.builder()
                                                    .op(OpCode.Heartbeat.getValue())
                                                    .d(sequenceNumber == null ? null : new IntNode(sequenceNumber))
                                                    .build();
            return this.objectMapper.writeValueAsString(rawMessage);
        } catch (JsonProcessingException e) {
            throw new ConnectionError("Could not serialize heartbeat seq='"+sequenceNumber+"'",e);
        }

    }

}
