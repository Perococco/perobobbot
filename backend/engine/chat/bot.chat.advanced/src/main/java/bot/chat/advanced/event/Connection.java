package bot.chat.advanced.event;

import lombok.NonNull;

/**
 * Event sent when the chat is connected
 * @param <M>
 */
public class Connection<M> implements AdvancedChatEvent<M> {

    private static final Connection<?> CONNECTION = new Connection<>();

    @NonNull
    @SuppressWarnings("unchecked")
    public static <M> Connection<M> create() {
        return (Connection<M>)CONNECTION;
    }

    private Connection() {}

    @Override
    @NonNull
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }
}
