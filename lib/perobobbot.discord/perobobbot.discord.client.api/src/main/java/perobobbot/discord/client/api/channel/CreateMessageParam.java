package perobobbot.discord.client.api.channel;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class CreateMessageParam {

    String content;
    Boolean tts;
    Object[] embeds;
    Boolean allowedMentions;
    Object messageReference;
    Object[] components;
    String[] stickerIds;
    Object[] filesContent;
    String payloadJson;
    Object[] attachments;
}
