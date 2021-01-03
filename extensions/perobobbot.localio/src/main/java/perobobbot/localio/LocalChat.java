package perobobbot.localio;

import lombok.NonNull;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ChatUser;
import perobobbot.lang.Platform;

public interface LocalChat extends MessageChannelIO {

    String CONSOLE = "console";
    ChannelInfo CONSOLE_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, CONSOLE);
    ChatUser LOCAL_USER = new LocalUser();

    String EXTENSION_NAME = "local-io";

}
