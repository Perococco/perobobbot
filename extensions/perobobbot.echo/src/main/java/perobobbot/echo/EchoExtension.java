package perobobbot.echo;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.Bot;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.User;
import perobobbot.lang.fp.Function1;

public class EchoExtension extends ExtensionBase {

    public static final String NAME = "echo";

    private final @NonNull Bot bot;

    private final @NonNull IO io;

    public EchoExtension(@NonNull Bot bot, @NonNull IO io) {
        super(NAME);
        this.bot = bot;
        this.io = io;
    }

    public void performEcho(@NonNull ChannelInfo channelInfo, @NonNull User messageOwner, @NonNull String contentToEcho) {
        if (!isEnabled()) {
            return;
        }
        final String answer = createEchoMessage(messageOwner,contentToEcho);
        io.send(bot,channelInfo,answer);
    }

    private @NonNull String createEchoMessage(@NonNull User messageOwner, @NonNull String contentToEcho) {
        return String.format("%s said '%s'",
                             messageOwner.getHighlightedUserName(),
                             contentToEcho);
    }

}
