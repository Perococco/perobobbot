package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.discord.gateway.impl.GatewayMessage;
import perobobbot.discord.gateway.impl.GatewayState;
import perobobbot.discord.gateway.impl.message.OpCode;
import perobobbot.discord.gateway.impl.state.connection.Connection;
import perobobbot.discord.gateway.impl.state.connection.ConnectionEvent;
import perobobbot.lang.Looper;
import perobobbot.discord.resources.GatewayEvent;
import perobobot.discord.gateway.api.Markers;

import java.util.concurrent.TimeUnit;

@Log4j2
public class ConnectedGateway implements GatewayState {

    private final @NonNull StateData stateData;
    private final @NonNull Connection connection;

    private final Looper looper;

    public ConnectedGateway(@NonNull StateData stateData, @NonNull Connection connection, int heartbeatInterval) {
        this.stateData = stateData;
        this.connection = connection;
        this.looper = new MessageHandler(new HeartbeatTimer(heartbeatInterval));
        this.looper.start();
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public GatewayState connect() {
        return this;
    }

    @Override
    public GatewayState disconnect() {
        this.connection.disconnect();
        this.looper.requestStop();
        return new DisconnectedGateway(stateData);

    }

    @RequiredArgsConstructor
    private class MessageHandler extends Looper {

        private final HeartbeatTimer heartbeatTimer;

        private Integer sequenceNumber = null;

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            if (heartbeatTimer.timeToSendHeartbeat()) {
                LOG.debug(Markers.GATEWAY,"Sending heartbeat");
                connection.sendHeartbeat(sequenceNumber);
            }

            long timeout = Math.max(0,heartbeatTimer.delayBeforeNextHeartbeat());
            final var event = connection.pollEvent(timeout, TimeUnit.NANOSECONDS).orElse(null);

            if (event == null) {
                return IterationCommand.CONTINUE;
            }

            return handleEvent(event);
        }

        private IterationCommand handleEvent(ConnectionEvent event) {
            if (event instanceof ConnectionEvent.Message message) {
                final var seq = message.message().getSequenceNumber();
                if (seq != null) {
                    this.sequenceNumber = seq;
                }

                this.warnListeners(message.message());
            }
            //TODO handle other kind of messages
            return IterationCommand.CONTINUE;
        }

        private void warnListeners(GatewayMessage<?> message) {
            final var event = message.getEvent();

            if (event instanceof GatewayEvent gatewayEvent) {
                stateData.getListener().onGatewayEvent(gatewayEvent);
            }
            if (message.getOpCode() == OpCode.Heartbeat) {
                connection.sendHeartbeat(sequenceNumber);
            }
        }

    }

}
