package bot.chat.advanced;

import bot.chat.advanced.event.AdvancedChatEvent;
import lombok.NonNull;

public interface AdvancedChatListener<M> {

    void onChatEvent(@NonNull AdvancedChatEvent<M> chatEvent);
}
