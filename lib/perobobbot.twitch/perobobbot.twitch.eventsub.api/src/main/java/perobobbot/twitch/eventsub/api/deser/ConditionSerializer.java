package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.ImmutableMap;
import perobobbot.twitch.eventsub.api.CriteriaType;

import java.io.IOException;
import java.util.Map;

public class ConditionSerializer extends JsonSerializer<ImmutableMap<CriteriaType, String>> {
    @Override
    public void serialize(ImmutableMap<CriteriaType, String> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        for (Map.Entry<CriteriaType, String> entry : value.entrySet()) {
            gen.writeStringField(entry.getKey().getIdentification(), entry.getValue());
        }
        gen.writeEndObject();
    }
}
