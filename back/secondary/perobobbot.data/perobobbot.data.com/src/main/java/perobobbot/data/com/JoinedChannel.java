package perobobbot.data.com;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;
import perobobbot.lang.ViewerIdentity;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final UUID id;

    private final Bot bot;

    private final ViewerIdentity viewerIdentity;

    private final String channelName;

}
