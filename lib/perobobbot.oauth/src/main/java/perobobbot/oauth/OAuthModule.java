package perobobbot.oauth;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;

import java.io.IOException;

public class OAuthModule extends SimpleModule {

    public OAuthModule() {
        this.addSerializer(Scope.class, SCOPE_SERIALIZER)
            .addDeserializer(Scope.class, SCOPE_DESERIALIZER)
            .addSerializer(Secret.class, SECRET_SERIALIZER)
            .addDeserializer(Secret.class, SECRET_DESERIALIZER);
    }

    private static final JsonSerializer<Scope> SCOPE_SERIALIZER = new JsonSerializer<>() {
        @Override
        public void serialize(Scope value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getName());
        }
    };

    private static final JsonDeserializer<Scope> SCOPE_DESERIALIZER = new JsonDeserializer<Scope>() {
        @Override
        public Scope deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return Scope.basic(p.getValueAsString());
        }
    };

    private static final JsonSerializer<Secret> SECRET_SERIALIZER = new JsonSerializer<>() {
        @Override
        public void serialize(Secret value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getValue());
        }
    };

    private static final JsonDeserializer<Secret> SECRET_DESERIALIZER = new JsonDeserializer<Secret>() {
        @Override
        public Secret deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return Secret.with(p.getValueAsString());
        }
    };

}
