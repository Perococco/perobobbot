package bot.twitch.chat.event;

import bot.common.lang.CastTool;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Function1;
import lombok.NonNull;

import java.util.Optional;

public interface TwitchChatEvent {

    @NonNull
    default <E extends TwitchChatEvent> Optional<E> as(@NonNull Class<E> type) {
        return CastTool.cast(type,this);
    }

    @NonNull
    default <T> Optional<T> applyIfIsReceivedMessage(@NonNull Function1<? super ReceivedMessages, ? extends T> function) {
        return as(ReceivedMessages.class).map(function);
    }

    default void acceptIfIsReceiveMessage(@NonNull Consumer1<? super ReceivedMessages> consumer) {
        as(ReceivedMessages.class).ifPresent(consumer);
    }
}
