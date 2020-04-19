package bot.chat.advanced.event;

import bot.chat.advanced.Message;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class PostedMessage<M> implements AdvancedChatEvent<M> {

    @NonNull
    private final Instant dispatchingTime;

    @NonNull
    @Getter
    private final Message postedMessage;

    @Override
    public void accept(@NonNull AdvancedChatEventVisitor<M> visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        final String payload = postedMessage.payload();
        final int idx = payload.indexOf("PASS");
        final String value;
        if (idx >= 0) {
            value = payload.substring(0,idx+4)+" ******";
        } else {
            value = payload;
        }
        return "PostedMessage{" +value+"}";
    }

}
