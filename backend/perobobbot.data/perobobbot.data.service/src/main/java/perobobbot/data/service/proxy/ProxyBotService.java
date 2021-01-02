package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.BotService;

@RequiredArgsConstructor
public class ProxyBotService implements BotService {

    @Delegate
    private final BotService delegate;
}
