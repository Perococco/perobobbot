package perobobbot.chat.core;

import lombok.NonNull;
import perobobbot.chat.core.event.ChatEvent;

/**
 * @author perococco
 **/
public interface ChatListener {

    void onChatEvent(@NonNull ChatEvent event);

}
