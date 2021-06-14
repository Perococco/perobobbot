package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import perobobbot.twitch.eventsub.api.event.EventSubEvent;

public class NotificationDeserModifier extends BeanDeserializerModifier {

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        if (EventSubEvent.class.isAssignableFrom(beanDesc.getBeanClass())) {
            return new EventSubDeserializer(deserializer);
        } else {
            return deserializer;
        }
    }
}
