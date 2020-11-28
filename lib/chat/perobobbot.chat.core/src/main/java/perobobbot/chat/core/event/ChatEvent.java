package perobobbot.chat.core.event;

import lombok.NonNull;

public interface ChatEvent {

    void accept(@NonNull ChatEventVisitor visitor);

}
