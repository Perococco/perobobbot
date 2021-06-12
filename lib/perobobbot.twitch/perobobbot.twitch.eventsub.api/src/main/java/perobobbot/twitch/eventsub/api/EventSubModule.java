package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.JsonModuleProvider;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EventSubModule extends SimpleModule {


    public static @NonNull JsonModuleProvider provider() {
        return () -> List.of(new GuavaModule(), new EventSubModule());
    }

    public EventSubModule() {
        Stream.of(SubscriptionStatus.class,
                  SubscriptionType.class,
                  SubscriptionStatus.class,
                  CriteriaType.class,
                  TransportMethod.class)
              .forEach(this::addIdentifiedEnumToModule);
    }

    private <T extends IdentifiedEnum> void addIdentifiedEnumToModule(Class<T> type) {
        this.addSerializer(type,IdentifiedEnumTools.createSerializer());
        this.addDeserializer(type, IdentifiedEnumTools.createDeserializer(type));
    }

    public static class ConditionSerializer extends JsonSerializer<ImmutableMap<CriteriaType,String>> {
        @Override
        public void serialize(ImmutableMap<CriteriaType, String> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            for (Map.Entry<CriteriaType, String> entry : value.entrySet()) {
                gen.writeStringField(entry.getKey().getIdentification(), entry.getValue());
            }
            gen.writeEndObject();
        }
    };


}
