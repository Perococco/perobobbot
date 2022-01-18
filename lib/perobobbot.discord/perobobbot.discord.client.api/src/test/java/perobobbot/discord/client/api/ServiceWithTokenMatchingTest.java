package perobobbot.discord.client.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import perobobbot.oauth.ApiProxy;

public class ServiceWithTokenMatchingTest {

    @Test
    void checkMapping() {
        final var proxyMap = ApiProxy.mapProxyMethods(DiscordService.class, DiscordServiceWithToken.class);
        Assertions.assertNotNull(proxyMap);
    }
}
