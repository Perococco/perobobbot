package perobobbot.server.sse;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.server.sse.impl.FixedSseEventBuilder;

import java.time.Instant;

public interface EventBuffer {

    void addMessage(@NonNull SSEvent event);

    long getSmallestId();

    /**
     * @param firstId  a message id
     * @return an unmodifiable list containing the messages with an id above or equal to the provided 'firstId'
     */
    @NonNull ImmutableList<Event> getAllMessagesFrom(long firstId);

    void cleanBuffer();

    @lombok.Value
    class Event {
        long id;
        @NonNull SSEventAccess ssEventAccess;
        @NonNull Instant timestamp;
        @NonNull FixedSseEventBuilder payload;

        public boolean isAllowedToSend(String login) {
            return ssEventAccess.isAllowed(login);
        }
    }


}
