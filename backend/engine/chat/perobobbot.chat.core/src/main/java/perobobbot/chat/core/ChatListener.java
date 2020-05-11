package perobobbot.chat.core;

import perobobbot.chat.core.event.ChatEvent;
import lombok.NonNull;

/**
 * @author perococco
 **/
public interface ChatListener {

    void onChatEvent(@NonNull ChatEvent event);

}
