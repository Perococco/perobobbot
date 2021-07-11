package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

public class TestTwitchSubscriptionRequest {

    @Test
    public void testSerialization() throws JsonProcessingException {
        final var objectMapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().stream().forEach(objectMapper::registerModule);

        final var request = TwitchSubscriptionRequest.builder()
                                                     .transport(new TransportRequest(TransportMethod.WEBHOOK, "toto","asdasda"))
                .condition(ImmutableMap.of(CriteriaType.BROADCASTER_USER_ID,"asdasda"))
                .version("1")
                .type(SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD)
                .build();

        final var serialize = objectMapper.writeValueAsString(request);

        System.out.println(serialize);

    }
}
