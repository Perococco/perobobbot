package perobobbot.consoleio;

import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.Platform;
import perobobbot.common.lang.PlatformIO;
import perobobbot.common.lang.User;

public interface LocalIO extends PlatformIO {

    String CONSOLE = "console";
    ChannelInfo CONSOLE_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, CONSOLE);

    String GUI = "gui";
    ChannelInfo GUI_CHANNEL_INFO = new ChannelInfo(Platform.LOCAL, GUI);
    User LOCAL_USER = new LocalUser();

    String EXTENSION_NAME = "local-io";

    @NonNull
    LocalIO enable();

    void disable();

    void showGui();

    void hideGui();

    @Override
    default @NonNull Platform getPlatform() {
        return Platform.LOCAL;
    }

}
