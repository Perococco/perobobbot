package perobobbot.twitch.client.webclient.channel;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.channel.ChannelInformation;

@Value
public class GetChannelInformationResponse {

    @NonNull ChannelInformation[] data;
}
