package perococco.perobobbot.program.sample.echo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.User;
import perobobbot.service.core.Services;

@RequiredArgsConstructor
public class EchoAction {

    public static EchoAction create(@NonNull Services services) {
        return new EchoAction(services.getService(IO.class));
    }

    @NonNull
    private final IO io;

    public void performEchoTo(@NonNull ChannelInfo channelInfo, @NonNull User user, @NonNull String contentToEcho) {
        final String answer = String.format("%s said '%s'",
                                            user.getHighlightedUserName(),
                                            contentToEcho);
        io.forChannelInfo(channelInfo)
          .print(answer);
    }

}
