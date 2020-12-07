package perobobbot.echo;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.ChannelInfo;
import perobobbot.chat.core.IO;
import perobobbot.lang.User;

public class EchoExtension extends ExtensionBase {

    @Getter
    private final @NonNull String name;

    private final @NonNull IO io;

    public EchoExtension(@NonNull String name, @NonNull IO io) {
        this.name = name;
        this.io = io;
    }

    public void performEcho(@NonNull ChannelInfo channelInfo, @NonNull User messageOwner, @NonNull String contentToEcho) {
        if (!isEnabled()) {
            return;
        }
        final String answer = createEchoMessage(messageOwner,contentToEcho);
        io.send(channelInfo,answer);
    }

    private String createEchoMessage(@NonNull User messageOwner, @NonNull String contentToEcho) {
        return String.format("%s said '%s'",
                             messageOwner.getHighlightedUserName(),
                             contentToEcho);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
