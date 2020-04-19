package bot.chat.core.event;

import lombok.NonNull;

public interface ChatEvent {

    @NonNull
    void accept(@NonNull ChatEventVisitor visitor);

}
