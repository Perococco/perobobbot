package perobobbot.localio;

import lombok.NonNull;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.User;

public interface LocalChat extends MessageChannelIO {

    String CONSOLE = "console";
    ChannelInfo CONSOLE_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, CONSOLE);
    User LOCAL_USER = new LocalUser();

    String EXTENSION_NAME = "local-io";

}
