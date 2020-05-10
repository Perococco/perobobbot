package bot.chat.core;

import bot.chat.core.event.ChatEvent;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ChatListener {

    void onChatEvent(@NonNull ChatEvent event);

}
