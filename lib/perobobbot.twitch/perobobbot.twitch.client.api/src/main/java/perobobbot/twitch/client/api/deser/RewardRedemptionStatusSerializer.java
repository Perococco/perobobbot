package perobobbot.twitch.client.api.deser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import perobobbot.twitch.api.RewardRedemptionStatus;

import java.io.IOException;

public class RewardRedemptionStatusSerializer extends JsonSerializer<RewardRedemptionStatus> {

    @Override
    public void serialize(RewardRedemptionStatus value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.name());
    }
}
