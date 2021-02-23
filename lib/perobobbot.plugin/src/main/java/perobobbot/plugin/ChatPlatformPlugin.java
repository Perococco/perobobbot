package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.data.service.BotService;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.Instants;
import perobobbot.lang.StandardInputProvider;

public interface ChatPlatformPlugin extends Plugin {

    @NonNull ChatPlatform create(@NonNull Parameters parameters);

    interface Parameters {

        @NonNull BotService getBotService();
        @NonNull Instants getInstants();

        StandardInputProvider getStandardInputProvider();

        ApplicationCloser getApplicationCloser();
    }

}
