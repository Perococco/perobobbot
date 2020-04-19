package bot.chat.advanced.event;

import lombok.NonNull;

public interface AdvancedChatEvent<M> {

    @NonNull
    void accept(@NonNull AdvancedChatEventVisitor<M> visitor);

}
