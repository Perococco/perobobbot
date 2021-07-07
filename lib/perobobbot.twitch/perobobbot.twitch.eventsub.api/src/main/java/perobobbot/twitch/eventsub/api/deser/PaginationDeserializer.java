package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import perobobbot.twitch.api.Pagination;

import java.io.IOException;

public class PaginationDeserializer extends JsonDeserializer<Pagination> {
    @Override
    public Pagination deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final ObjectNode node = p.readValueAsTree();
        final var cursor = node.get("cursor");
        if (cursor == null || cursor.isNull()) {
            return null;
        }
        return new Pagination(cursor.asText());
    }
}
