package perobobbot.data.com;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final Bot bot;

    private final Platform platform;

    private final String channelName;
}
