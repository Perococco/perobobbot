package perobobbot.twitch.api.deser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.awt.*;
import java.io.IOException;

public class ColorSerializer extends JsonSerializer<Color> {

    @Override
    public void serialize(Color value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        final var webFormat = "#%02X%02X%02X".formatted(value.getRed(),value.getGreen(),value.getBlue());
        gen.writeString(webFormat);
    }
}
