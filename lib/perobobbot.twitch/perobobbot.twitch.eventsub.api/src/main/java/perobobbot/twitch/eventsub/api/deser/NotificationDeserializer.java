package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import lombok.NonNull;

import java.io.IOException;

public class NotificationDeserializer extends DelegatingDeserializer {

    public NotificationDeserializer(JsonDeserializer<?> jsonDeserializer) {
        super(jsonDeserializer);
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new NotificationDeserializer(newDelegatee);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return super.deserialize(restructure(p),ctxt);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt, Object intoValue) throws IOException {
        return super.deserialize(restructure(p), ctxt, intoValue);
    }

    @Override
    public Object deserializeWithType(JsonParser p, DeserializationContext ctxt, TypeDeserializer typeDeserializer) throws IOException {
        return super.deserializeWithType(restructure(p), ctxt, typeDeserializer);
    }

    private @NonNull JsonParser restructure(@NonNull JsonParser jp) throws IOException {
        final ObjectNode source = jp.readValueAsTree();
        TreeNodeModifier.modify(source);
        final var newJsonParser = new TreeTraversingParser(source,jp.getCodec());
        newJsonParser.nextToken();
        return newJsonParser;
    }

}
