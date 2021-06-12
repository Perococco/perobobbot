package perobobbot.lang;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NonNull;
import perobobbot.lang.fp.Function1;

import java.io.IOException;
import java.util.Optional;

public class IdentifiedEnumTools {

    public static <E extends IdentifiedEnum> Function1<String,E> mapper(@NonNull Class<E> type) {
        final var values = type.getEnumConstants();
        return s -> getEnum(s,values);
    }


    public static <E extends IdentifiedEnum> @NonNull E getEnum(@NonNull String id, @NonNull Class<E> type) {
        return getEnum(id,type.getEnumConstants());
    }

    public static <E extends IdentifiedEnum> @NonNull Optional<E> findEnum(@NonNull String id, @NonNull Class<E> type) {
        return Optional.ofNullable(findEnum(id, type.getEnumConstants()));
    }


    private static <E extends IdentifiedEnum> @NonNull E getEnum(@NonNull String id, @NonNull E[] values) {
        final var value = findEnum(id,values);
        if (value != null) {
            return value;
        }
        if (values.length == 0) {
            throw new IllegalArgumentException("Could not convert '" + id + "' to an enum");
        }
        throw new IllegalArgumentException("Could not convert '" + id + "' to a " + values[0].getClass());
    }

    private static <E extends IdentifiedEnum> E findEnum(@NonNull String id, @NonNull E[] values) {
        for (E value : values) {
            if (value.getIdentification().equals(id)) {
                return value;
            }
        }
        return null;
    }

    public static <E extends IdentifiedEnum> @NonNull JsonDeserializer<E> createDeserializer(@NonNull Class<E> enumType) {
        return new JsonDeserializer<E>() {
            @Override
            public E deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                return IdentifiedEnumTools.getEnum(p.getValueAsString(),enumType);
            }
        };
    }

    public static @NonNull JsonSerializer<IdentifiedEnum> createSerializer() {
        return new JsonSerializer<>() {
            @Override
            public void serialize(IdentifiedEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.getIdentification());
            }
        };
    }
}
