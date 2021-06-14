package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import lombok.NonNull;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.TwitchSubscription;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

import java.io.IOException;

public class NotificationDeserializer extends JsonDeserializer<EventSubNotification> {

    @Override
    public EventSubNotification deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final ObjectNode node = jp.readValueAsTree();
        final var subscriptionNode = node.get("subscription");
        final var eventNode = node.get("event");

        final TwitchSubscription subscription = deserializeJsonNode(subscriptionNode,TwitchSubscription.class, jp.getCodec());
        final EventSubEvent event = deserializeJsonNode(eventNode, subscription.getEventType(), jp.getCodec());

        return new EventSubNotification(subscription,event);
    }

    private <T> T deserializeJsonNode(@NonNull JsonNode node, @NonNull Class<T> clazz, @NonNull ObjectCodec codec) throws IOException {
        final var newJsonParser = new TreeTraversingParser(node, codec);
        newJsonParser.nextToken();
        return newJsonParser.readValueAs(clazz);
    }
}
