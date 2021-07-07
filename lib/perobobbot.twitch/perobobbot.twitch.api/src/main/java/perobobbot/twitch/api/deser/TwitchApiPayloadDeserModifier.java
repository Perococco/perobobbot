package perobobbot.twitch.api.deser;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class TwitchApiPayloadDeserModifier extends BeanDeserializerModifier {

    private final Predicate<? super Class<?>> shouldModifyPayload;

    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        final var beanClass = beanDesc.getBeanClass();
        if (shouldModifyPayload.test(beanClass)) {
            return new DeserializerWithTreeModifier(deserializer);
        } else {
            return deserializer;
        }
    }
}
