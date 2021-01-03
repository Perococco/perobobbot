package perobobbot.echo;

import lombok.NonNull;
import perobobbot.chat.core.IO;
import perobobbot.extension.ExtensionBase;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.ChatUser;

public class EchoExtension extends ExtensionBase {

    public static final String NAME = "echo";

    private final @NonNull IO io;

    public EchoExtension(@NonNull IO io) {
        super(NAME);
        this.io = io;
    }

    public void performEcho(@NonNull ChatConnectionInfo connectionInfo, @NonNull String channelName, @NonNull ChatUser messageOwner, @NonNull String contentToEcho) {
        if (!isEnabled()) {
            return;
        }
        final String answer = createEchoMessage(messageOwner,contentToEcho);
        io.send(connectionInfo,channelName, answer);
    }

    private @NonNull String createEchoMessage(@NonNull ChatUser messageOwner, @NonNull String contentToEcho) {
        return String.format("%s said '%s'",
                             messageOwner.getHighlightedUserName(),
                             contentToEcho);
    }

}
