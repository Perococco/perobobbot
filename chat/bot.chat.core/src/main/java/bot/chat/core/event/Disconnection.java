package bot.chat.core.event;

import lombok.NonNull;

public class Disconnection implements ChatEvent {

    private static final Disconnection DISCONNECTION = new Disconnection();

    @NonNull
    public static Disconnection create() {
        return DISCONNECTION;
    }

    private Disconnection() {}

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }
}
