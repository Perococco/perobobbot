package bot.chat.core.event;

import lombok.NonNull;

public class Connection implements ChatEvent {

    private static final Connection CONNECTION = new Connection();

    @NonNull
    public static Connection create() {
        return CONNECTION;
    }

    private Connection() {
    }

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }
}
