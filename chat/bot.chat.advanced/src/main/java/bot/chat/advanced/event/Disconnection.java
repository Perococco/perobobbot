package bot.chat.advanced.event;

import lombok.NonNull;

import java.util.Dictionary;

public class Disconnection<M> implements AdvancedChatEvent<M> {

    private static final Disconnection<?> DISCONNECTION = new Disconnection<>();

    @NonNull
    public static <M> Disconnection<M> create() {
        //noinspection unchecked
        return (Disconnection<M>)DISCONNECTION;
    }

    private Disconnection() {}

    @Override
    public void accept(@NonNull AdvancedChatEventVisitor<M> visitor) {
        visitor.visit(this);
    }
}
