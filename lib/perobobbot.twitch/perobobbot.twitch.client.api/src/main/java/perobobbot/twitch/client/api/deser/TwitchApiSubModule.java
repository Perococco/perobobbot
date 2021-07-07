package perobobbot.twitch.client.api.deser;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.client.api.TwitchApiPayload;
import perobobbot.twitch.api.deser.TwitchApiPayloadDeserModifier;

import java.util.List;

public class TwitchApiSubModule extends SimpleModule {

    public static JsonModuleProvider provider() {
        return () ->  List.of(
                new TwitchApiSubModule(), new JavaTimeModule());
    }

    public TwitchApiSubModule() {
        this.setNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        this.setDeserializerModifier(new TwitchApiPayloadDeserModifier(TwitchApiPayload.class::isAssignableFrom));
    }

}
