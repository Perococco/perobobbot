package perobobbot.program.echo;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.User;
import perobobbot.program.core.ProgramBase;

public class EchoProgram extends ProgramBase {

    @Getter
    private final @NonNull String name;

    private final @NonNull IO io;

    public EchoProgram(@NonNull String name, @NonNull IO io) {
        this.name = name;
        this.io = io;
    }

    public void performEcho(@NonNull ChannelInfo channelInfo, @NonNull User messageOwner, @NonNull String contentToEcho) {
        final String answer = createEchoMessage(messageOwner,contentToEcho);
        io.print(channelInfo,answer);
    }

    private String createEchoMessage(@NonNull User messageOwner, @NonNull String contentToEcho) {
        return String.format("%s said '%s'",
                             messageOwner.getHighlightedUserName(),
                             contentToEcho);
    }



}
