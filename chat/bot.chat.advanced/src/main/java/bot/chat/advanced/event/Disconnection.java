package bot.chat.advanced.event;

import lombok.NonNull;

import java.util.Dictionary;

/**
 * Event sent when the chat is disconnected
 * @param <M>
 */
public class Disconnection<M> implements AdvancedChatEvent<M> {

    private static final Disconnection<?> DISCONNECTION = new Disconnection<>();

    @SuppressWarnings("unchecked")
    @NonNull
    public static <M> Disconnection<M> create() {
        return (Disconnection<M>)DISCONNECTION;
    }

    private Disconnection() {}

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }
}
