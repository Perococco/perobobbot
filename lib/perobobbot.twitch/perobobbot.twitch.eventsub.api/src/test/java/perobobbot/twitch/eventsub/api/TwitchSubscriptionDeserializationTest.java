package perobobbot.twitch.eventsub.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

import java.util.stream.Stream;

public class TwitchSubscriptionDeserializationTest {

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().forEach(m -> this.mapper.registerModule(m));
    }

    public static @NonNull Stream<String> samples() {
        return Stream.of(DATA,DATA_WITHOUT_PAGINATION);
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void name(@NonNull String serialized) throws JsonProcessingException {
        var value = mapper.readValue(serialized, TwitchSubscriptionData.class);
        var serialize2 = mapper.writeValueAsString(value);
        var value2 = mapper.readValue(serialize2, TwitchSubscriptionData.class);
        Assertions.assertEquals(value2,value);
    }

    public static final String DATA = """
                {
              "total": 2,
              "data": [
                {
                  "id": "26b1c993-bfcf-44d9-b876-379dacafe75a",
                  "status": "enabled",
                  "type": "stream.online",
                  "version": "1",
                  "condition": {
                    "broadcaster_user_id": "1234"
                  },
                  "created_at": "2020-11-10T20:08:33Z",
                  "transport": {
                    "method": "webhook",
                    "callback": "https://this-is-a-callback.com"
                  },
                  "cost": 1
                },
                {
                  "id": "35016908-41ff-33ce-7879-61b8dfc2ee16",
                  "status": "webhook_callback_verification_pending",
                  "type": "user.update",
                  "version": "1",
                  "condition": {
                    "user_id": "1234"
                  },
                  "created_at": "2020-11-10T20:31:52Z",
                  "transport": {
                    "method": "webhook",
                    "callback": "https://this-is-a-callback.com"
                  },
                  "cost": 0
                }
              ],
              "total_cost": 1,
              "max_total_cost": 10000,
              "pagination": {}
            }
            """;
    public static final String DATA_WITHOUT_PAGINATION = """
                {
              "total": 2,
              "data": [
                {
                  "id": "26b1c993-bfcf-44d9-b876-379dacafe75a",
                  "status": "enabled",
                  "type": "stream.online",
                  "version": "1",
                  "condition": {
                    "broadcaster_user_id": "1234"
                  },
                  "created_at": "2020-11-10T20:08:33Z",
                  "transport": {
                    "method": "webhook",
                    "callback": "https://this-is-a-callback.com"
                  },
                  "cost": 1
                },
                {
                  "id": "35016908-41ff-33ce-7879-61b8dfc2ee16",
                  "status": "webhook_callback_verification_pending",
                  "type": "user.update",
                  "version": "1",
                  "condition": {
                    "user_id": "1234"
                  },
                  "created_at": "2020-11-10T20:31:52Z",
                  "transport": {
                    "method": "webhook",
                    "callback": "https://this-is-a-callback.com"
                  },
                  "cost": 0
                }
              ],
              "total_cost": 1,
              "max_total_cost": 10000
            }
            """;
}
