package perobobbot.localio;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.User;

public interface LocalChat extends ChatPlatform {

    String CONSOLE = "console";
    ChannelInfo CONSOLE_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, CONSOLE);

    String GUI = "gui";
    ChannelInfo GUI_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, GUI);
    User LOCAL_USER = new LocalUser();

    String EXTENSION_NAME = "local-io";

    @NonNull
    LocalChat enable();

    void disable();

    void showGui();

    void hideGui();

    @Override
    default @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }

}
