package perobobbot.twitch.eventsub.api.deser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.eventsub.api.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class EventSubModule extends SimpleModule {


    public static @NonNull JsonModuleProvider provider() {
        return () -> List.of(new GuavaModule(), new JavaTimeModule(), new EventSubModule());
    }

    public EventSubModule() {
        Stream.of(SubscriptionStatus.class,
                  SubscriptionType.class,
                  Tier.class,
                  SubscriptionStatus.class,
                  PollStatus.class,
                  CriteriaType.class,
                  TransportMethod.class)
              .forEach(this::addIdentifiedEnumToModule);
        this.setDeserializerModifier(new NotificationDeserModifier());
    }

    private <T extends IdentifiedEnum> void addIdentifiedEnumToModule(Class<T> type) {
        this.addSerializer(type, IdentifiedEnumTools.createSerializer());
        this.addDeserializer(type, IdentifiedEnumTools.createDeserializer(type));
    }

    public static class ConditionSerializer extends JsonSerializer<ImmutableMap<CriteriaType, String>> {
        @Override
        public void serialize(ImmutableMap<CriteriaType, String> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            for (Map.Entry<CriteriaType, String> entry : value.entrySet()) {
                gen.writeStringField(entry.getKey().getIdentification(), entry.getValue());
            }
            gen.writeEndObject();
        }
    }
}
